package com.example.cnpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ImageButton btnLogin;
    TextView register;
    EditText edtEmail,edtPass;

    TextView forgotPassword;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {login();}
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {moRegister();}
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                View dialogView=getLayoutInflater().inflate(R.layout.dialog_forgot,null);
                EditText emailBox=dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog= builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail=emailBox.getText().toString();

                        if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginActivity.this, "Nhập email bạn đã đăng kí!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Kiểm tra email của bạn", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(LoginActivity.this,"Gửi thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }

    private void login() {
        Drawable icERR=getResources().getDrawable(R.drawable.ic_error);
        icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
        String email=edtEmail.getText().toString();
        String pass=edtPass.getText().toString();
        if(TextUtils.isEmpty(email)){
            edtEmail.setCompoundDrawables(null,null,icERR,null);
            edtEmail.setError("Vui lòng nhập email!",icERR);
            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            edtPass.setCompoundDrawables(null,null,icERR,null);
            edtPass.setError("Vui lòng nhập pass!",icERR);
            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                /*Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();*/
                checkUserAccessLevel(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*Ham check muc do truy cap*/
    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fstore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                //check user hay admin

                if(documentSnapshot.getString("MainUser" )!=null){
                    Toast.makeText(LoginActivity.this,"MainUser đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }
                if (documentSnapshot.getString("NormalUser")!=null){
                    Toast.makeText(LoginActivity.this,"NormalUser đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }
    private void moRegister() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        btnLogin=findViewById(R.id.btnLogin);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        register=(TextView)findViewById(R.id.btn_register);
        forgotPassword=findViewById(R.id.forgotPassword);

        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
    }
}