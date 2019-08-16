package com.example.acer.printer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.acer.printer.adapters.Productadapter;
import com.example.acer.printer.adapters.Productlistadapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productlist extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Productlistadapter productadapter;


    private List<Productlistclass> productList;



    String Getuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        SharedPreferences Getgs = getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(Productlist.this));




        Call<Productlistresponse> call=RetrofitClient.getInstance().getApi().getproductwherebuy(Getuser);

        call.enqueue(new Callback<Productlistresponse>() {
            @Override
            public void onResponse(Call<Productlistresponse> call, Response<Productlistresponse> response) {
                productList=response.body().getProductList();
                productadapter=new Productlistadapter(Productlist.this,productList);
                recyclerView.setAdapter(productadapter);

            }

            @Override
            public void onFailure(Call<Productlistresponse> call, Throwable t) {

            }
        });
    }
}
