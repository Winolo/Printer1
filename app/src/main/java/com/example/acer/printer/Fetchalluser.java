package com.example.acer.printer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fetchalluser extends AppCompatActivity {


    public static final String URL = "http://192.168.64.2/MyApi/public/Getalluser";


Intent Genqr;

    String User[];

    String Getuser;

    Button ConfirmUser;

    Spinner user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchalluser);


        //พอกดเลือกยืนยันเสร็จ คราวนี้จะไปทำการFetch Order_ID ล้ะไป Gen เป็น QR CODE ให้สแกน

         user = (Spinner) findViewById(R.id.User);

         ConfirmUser=(Button)findViewById(R.id.ConfirmUser);

         Getuser();




         //Recieve ORDER_ID AND GEN QR CODE

         ConfirmUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

        startActivity(Genqr);

             }
         });


    }



    public void Getuser () {

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new com.android.volley.Response.Listener // CHANGES HERE
                        <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {

                            JSONArray resultarray = response.getJSONArray("Getalluser");

                            User = new String[resultarray.length()];

                            for (int i = 0; i < resultarray.length(); i++) {

                                JSONObject resultobject = resultarray.getJSONObject(i);


                                User[i] = resultobject.getString("username");

                                // Season1[i]=resultobject.getString("Season");


                            }


                            Setspinneryear();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener // CHANGES HERE
                        () {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);

    }







    public void Setspinneryear() {

        user.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, User));


        if (User.length == 0) {
            ConfirmUser.setEnabled(false);
        }

        user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Getuser = parent.getItemAtPosition(position).toString();


                //check.setText(getYear+getSeason);




                Getuser=parent.getItemAtPosition(position).toString();



                Genqr=new Intent(Fetchalluser.this,Qecodefordriver.class);

                    Genqr.putExtra("Getuser",Getuser);



                //Get season Where Year
//                GetseasonWhereYear();

//
//                //Get year session
//                SharedPreferences settings = getSharedPreferences("Year", 0);
//
//                SharedPreferences.Editor editor = settings.edit();
//
//                editor.putString("Getyear", getYear.toString());
//                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
