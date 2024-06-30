package com.example.cnpm;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResgisterActivity2 extends AppCompatActivity {

    ImageButton btnLogin;
    EditText edtEmail,edtPass;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControls();
        addEvents();
    }
    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        Drawable icERR=getResources().getDrawable(R.drawable.ic_error);
        icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
        String email=edtEmail.getText().toString();
        String pass=edtPass.getText().toString();
        if(TextUtils.isEmpty(email)){
            edtEmail.setCompoundDrawables(null,null,icERR,null);
            edtEmail.setError("Vui lòng nhập email!",icERR);
            Toast.makeText(ResgisterActivity2.this,"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            edtPass.setCompoundDrawables(null,null,icERR,null);
            edtPass.setError("Vui lòng nhập pass!",icERR);
            Toast.makeText(ResgisterActivity2.this,"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user =mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                    DocumentReference df =fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("UserEmail", edtEmail.getText().toString());
                    userInfo.put("NormalUser","1");
                    df.set(userInfo);

                    Intent i=new Intent(ResgisterActivity2.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addControls() {
        btnLogin=findViewById(R.id.btnLogin1);
        edtEmail=findViewById(R.id.edtEmail1);
        edtPass=findViewById(R.id.edtPass1);

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
    }
}
