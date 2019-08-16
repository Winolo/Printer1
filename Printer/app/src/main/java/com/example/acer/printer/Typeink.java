package com.example.acer.printer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Typeink extends AppCompatActivity {

    Button True,Fake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeink);

        True=(Button)findViewById(R.id.True);
        Fake=(Button)findViewById(R.id.Fake);


        True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Typeink.this,memberActivities.class);

                    startActivity(intent1);
            }
        });

        Fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2=new Intent(Typeink.this,memberActivities2.class);

                startActivity(intent2);


            }
        });


    }
}
