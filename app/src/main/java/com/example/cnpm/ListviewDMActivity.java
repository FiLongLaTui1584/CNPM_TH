package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cnpm.modal.Contact;

public class ListviewDMActivity extends AppCompatActivity {
    ListView lvDM;
    EditText edtAddDM;
    Button btnSave,btnClear;

    ArrayAdapter adapterData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_dmactivity);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact =new Contact();
                contact.setNameDM(edtAddDM.getText().toString());
                adapterData.add(contact);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddDM.setText("");
            }
        });
        lvDM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) adapterData.getItem(position);
                edtAddDM.setText(contact.getId());
            }
        });
    }

    private void addControls() {
        edtAddDM=findViewById(R.id.edtAddDM);
        btnSave=findViewById(R.id.btnSave);
        btnClear=findViewById(R.id.btnClear);
        lvDM=findViewById(R.id.lvDM);
        adapterData= new ArrayAdapter(ListviewDMActivity.this, android.R.layout.simple_list_item_1);
        lvDM.setAdapter(adapterData);
    }


}