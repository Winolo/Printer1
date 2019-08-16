package com.example.acer.printer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.acer.printer.adapters.Driverwatchadapter;
import com.example.acer.printer.adapters.Orderstatusadapter;
import com.example.acer.printer.adapters.Productlistadapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Getdrivervieworder extends AppCompatActivity {


    private RecyclerView recyclerView;






    private Driverwatchadapter orderstatusadapter;

    private List<Orderstatusclass> orderstatusclassList;

    String Getuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdrivervieworder);

        SharedPreferences Getgs = getSharedPreferences("User", 0);
        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(Getdrivervieworder.this));


        Call<Orderstatusresponse> call=RetrofitClient.getInstance().getApi().Getdriverqueueorder();

        call.enqueue(new Callback<Orderstatusresponse>() {
            @Override
            public void onResponse(Call<Orderstatusresponse> call, Response<Orderstatusresponse> response) {
                orderstatusclassList=response.body().getOrderstatusclassList();
                orderstatusadapter=new Driverwatchadapter(Getdrivervieworder.this,orderstatusclassList);
                recyclerView.setAdapter(orderstatusadapter);

            }

            @Override
            public void onFailure(Call<Orderstatusresponse> call, Throwable t) {

            }
        });





    }
}
