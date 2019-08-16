package com.example.acer.printer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueueDriver extends AppCompatActivity {


    public static final String URL="http://192.168.43.222/Myapi/public/Getdriverqueue";

    TextView namedriver;

    Button watchcus;

    String getname;

    String[] que_id;

    String[] catcar;

    String newgetname;

    EditText catcarr,queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_driver);

        namedriver=(TextView)findViewById(R.id.namedriver);

        queue=(EditText)findViewById(R.id.Queue);

        catcarr=(EditText)findViewById(R.id.catcar);

        watchcus=(Button)findViewById(R.id.Watchcusorder);

        //Get Drivername
        SharedPreferences settings = getSharedPreferences("Driveruser", 0);

        final String  getdrivername=settings.getString("namedriver", "").toString();


        getname=getdrivername;

        if(getname.contains("success")) {

            getname.replaceFirst("success"," ");

            newgetname=getname.replaceAll("success"," ");
        }
        namedriver.setText("ยินดีต้อนรับคุณ"+newgetname);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            namedriver.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        namedriver.setTextSize(30);

        watchcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(QueueDriver.this,Getdrivervieworder.class);

                    startActivity(go);
            }
        });


        getdriverqueue();

    }

    private void getdriverqueue() {


        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new com.android.volley.Response.Listener // CHANGES HERE
                        <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {

                            JSONArray resultarray=response.getJSONArray("result");

                            que_id=new String[resultarray.length()];

                            catcar=new String[resultarray.length()];
                            for (int i=0;i<resultarray.length();i++){

                                JSONObject resultobject=resultarray.getJSONObject(i);




                                que_id[i] = resultobject.getString("queue_id");

                                catcar[i]=resultobject.getString("catcar_name");

                                // Season1[i]=resultobject.getString("Season");



                                queue.setText("คุณเป็นคิวที่ :"+que_id[i]);

                                catcarr.setText("ใช้ยานพาหนะเป็น :"+catcar[i] );


                            }


                           // queue.setText("คุณเป็นคิวที่ :"+que_id);

                            //catcarr.setText("ใช้ยานพาหนะเป็น :"+catcar );

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
}
