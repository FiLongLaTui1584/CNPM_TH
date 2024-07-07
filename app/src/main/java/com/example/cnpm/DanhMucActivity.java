package com.example.cnpm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm.modal.Item;
import com.example.cnpm.modal.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class DanhMucActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_muc);



        RecyclerView recyclerView =findViewById(R.id.recyclerview);

        List<Item> item =new ArrayList<Item>();
        item.add(new Item("An Uong","nguyengiap@example.com"));
        item.add(new Item("Nghi Duong","nguyengiap@example.com"));
        item.add(new Item("Di hoc","nguyengiap@example.com"));
        item.add(new Item("Kham Benh","nguyengiap@example.com"));
        item.add(new Item("Suc Khoe","nguyengiap@example.com"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),item));
    }
}