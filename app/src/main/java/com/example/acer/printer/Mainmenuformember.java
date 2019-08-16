package com.example.acer.printer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mainmenuformember extends AppCompatActivity {


    Button allproduct,Orderstatus,productlist,Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenuformember);

        allproduct=(Button)findViewById(R.id.Allproduct);

        Orderstatus=(Button)findViewById(R.id.Orderstatus);

        productlist=(Button)findViewById(R.id.productlist);

        Logout=(Button)findViewById(R.id.Logout);


        allproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1=new Intent(Mainmenuformember.this,Typeink.class);
                    startActivity(go1);
            }
        });

        Orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go2=new Intent(Mainmenuformember.this,Checkstatus.class);
                startActivity(go2);
            }
        });


        productlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go3=new Intent(Mainmenuformember.this,Productlist.class);
                startActivity(go3);
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goout=new Intent(Mainmenuformember.this,LoginActivity.class);
                        startActivity(goout);

            }
        });

    }
}
