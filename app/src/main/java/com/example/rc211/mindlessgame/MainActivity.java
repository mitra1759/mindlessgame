package com.example.rc211.mindlessgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button star=  findViewById(R.id.start);
    private ImageView pendulum = findViewById(R.id.pendulum);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    star.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            System.out.println("fdg");
        }
    });
}
