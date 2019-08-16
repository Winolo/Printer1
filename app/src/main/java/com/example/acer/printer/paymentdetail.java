package com.example.acer.printer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.acer.printer.adapters.Paymentadapter;
import com.example.acer.printer.adapters.Productlistadapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paymentdetail extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Paymentadapter paymentadapter;


    private List<Paymentdetailclass> paymentdetailclasses;

    String getYearformuser,getmonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetail);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(paymentdetail.this));





        //Get Year
        SharedPreferences settings = getSharedPreferences("Year", 0);

        final String  getyear=settings.getString("Getyear", "").toString();


        getYearformuser=getyear;


        //Get season

        SharedPreferences getsea = getSharedPreferences("Month", 0);

        final String  getseason=getsea.getString("Getmonth", "").toString();

        getmonth=getseason;




        Call<Paymentresponse> call=RetrofitClient.getInstance().getApi().paymentdetail(getYearformuser,getmonth);

        call.enqueue(new Callback<Paymentresponse>() {
            @Override
            public void onResponse(Call<Paymentresponse> call, Response<Paymentresponse> response) {
                paymentdetailclasses=response.body().getPaymentdetail();
                paymentadapter=new Paymentadapter(paymentdetail.this,paymentdetailclasses);
                recyclerView.setAdapter(paymentadapter);

            }

            @Override
            public void onFailure(Call<Paymentresponse> call, Throwable t) {

            }
        });
    }
}
