package com.example.acer.printer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.printer.adapters.Listviewqueue;
import com.example.acer.printer.adapters.Queuedriverclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueDriver extends AppCompatActivity {


    public static final String URL="http://192.168.2.50/Myapi/public/Getdriverqueue";

    TextView namedriver;
    ListView listView;

    List<Queuedriverclass> queuedriverclassList;
    Button watchcus;

    String getname;

    String que_id[];

    String catcar[];

    String newgetname;

    EditText catcarr,queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_driver);

        namedriver=(TextView)findViewById(R.id.namedriver);


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


        Change2jason();

    }





    private void Change2jason() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj=new JSONObject(response);
                            JSONArray resultarray=obj.getJSONArray("result");

                            for(int i=0;i<resultarray.length();i++){

                                JSONObject resultobject=resultarray.getJSONObject(i);
                                Queuedriverclass chemicalClass=new Queuedriverclass(resultobject.getString("queue_id")
                                        ,resultobject.getString("catcar_name")


                                );


                                queuedriverclassList.add(chemicalClass);

                                // seedsourceListForView.add(seedsourcecClassForView);



                            }
                            Listviewqueue adapter = new Listviewqueue(queuedriverclassList, getApplicationContext());

                            listView.setAdapter(adapter);





                        }


                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();


                return params;
            }
        };

        requestQueue.add(stringRequest);

    }






}
