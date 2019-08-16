package com.example.acer.printer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class Adminactivities extends AppCompatActivity {

    Button Confirm;
    Spinner Year,Month;

    String getYear;
    String getMonth;

    public static final String URL="http://192.168.2.50/Myapi/public/Getyear";

    public static final String URL2="http://192.168.2.50/Myapi/public/Monthwhereyear";

    String Year1[];

    String Month1[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivities);



            Year=(Spinner)findViewById(R.id.Year);
            Month=(Spinner)findViewById(R.id.Month);

            Confirm=(Button)findViewById(R.id.Confirm);

            Getyear();

            Confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gosum=new Intent(Adminactivities.this,Sumproduct.class);

                        startActivity(gosum);
                }
            });


    }

    public void Getyear() {

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new com.android.volley.Response.Listener // CHANGES HERE
                        <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {

                            JSONArray resultarray=response.getJSONArray("Yearlist");

                            Year1=new String[resultarray.length()];

                            for (int i=0;i<resultarray.length();i++){

                                JSONObject resultobject=resultarray.getJSONObject(i);




                                Year1[i] = resultobject.getString("YEAR");

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

public void Setspinneryear(){

    Year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Year1));


    if (Year1.length==0){
        Confirm.setEnabled(false);
    }

    Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getYear=parent.getItemAtPosition(position).toString();





            //check.setText(getYear+getSeason);


            //Get season Where Year
            GetseasonWhereYear();



            //Get year session
            SharedPreferences settings=getSharedPreferences("Year",0);

            SharedPreferences.Editor editor=settings.edit();

            editor.putString("Getyear", getYear.toString());
            editor.commit();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });








}



    public void GetseasonWhereYear(){
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL2,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj=new JSONObject(response);
                            JSONArray resultarray=obj.getJSONArray("Monthlist");

                            Month1=new String[resultarray.length()];


                            for (int i=0;i<resultarray.length();i++){

                                JSONObject resultobject=resultarray.getJSONObject(i);





                                Month1[i]=resultobject.getString("Month");






                            }


                            SetspinnerSeason();


                        }


                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Year",getYear);


                return params;
            }
        };
        queue.add(postRequest);


    }


    public void SetspinnerSeason(){




        Month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Month1));


        if (Month1.length==0){
            Confirm.setEnabled(false);
        }

        Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




                getMonth=parent.getItemAtPosition(position).toString();




                //Get season session
                SharedPreferences season=getSharedPreferences("Month",0);

                SharedPreferences.Editor getseason=season.edit();

                getseason.putString("Getmonth", getMonth.toString());
                getseason.commit();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



}
