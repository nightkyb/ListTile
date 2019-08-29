package com.nightkyb.listtiledemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nightkyb.listtile.ListTile;

public class MainActivity extends AppCompatActivity {
    private ListTile listTile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // listTile.getLeading().setImageResource(R.drawable.ic_modify_password_24dp);
                listTile.getExtraText().setText("哈1.0.0版本本本版本本版本版本本本本版本版本本本本版本版哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                listTile.getLeading().setImageResource(R.drawable.ic_modify_password_24dp);
                listTile.tintLeading(R.color.colorPrimaryDark);
                listTile.refresh();
            }
        });

        listTile = findViewById(R.id.listTile);
    }
}
