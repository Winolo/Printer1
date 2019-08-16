package com.example.acer.printer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sumproduct extends AppCompatActivity {


TextView text,result;
String getYearformuser;

Button godetail;

String getmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumproduct);


        text=(TextView)findViewById(R.id.Text);

        result=(TextView)findViewById(R.id.result);

        godetail=(Button)findViewById(R.id.Detail);


        //Get Year
        SharedPreferences settings = getSharedPreferences("Year", 0);

        final String  getyear=settings.getString("Getyear", "").toString();


        getYearformuser=getyear;


        //Get season

        SharedPreferences getsea = getSharedPreferences("Month", 0);

        final String  getseason=getsea.getString("Getmonth", "").toString();

        getmonth=getseason;

        text.setText("ยอดขายในปีที่"+getYearformuser+"เดือนที่"+getmonth+"มียอดรวมเท่ากับ"+" =");


        getSumproduct();


        godetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go=new Intent(Sumproduct.this,paymentdetail.class);

                    startActivity(go);
            }
        });

    }

    private void getSumproduct() {


        Call<ResponseBody> call=RetrofitClient.getInstance().getApi().Sumproduct(getYearformuser,getmonth);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res=response.body().string();

                    result.setText(res);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
