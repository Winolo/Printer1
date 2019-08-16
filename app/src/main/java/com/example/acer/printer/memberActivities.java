package com.example.acer.printer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.printer.adapters.Productadapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class memberActivities extends AppCompatActivity {



    private RecyclerView recyclerView;

    private Productadapter productadapter;

    private List<Product> productList;

    TextView setusername;

    String Getuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_activities);

        SharedPreferences Getgs = getSharedPreferences("User", 0);

        final String  username=Getgs.getString("Username", "").toString();


        Getuser=username;


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        setusername=(TextView)findViewById(R.id.Username);

        setusername.setText("ยินดีต้อนรับคุณ: "+Getuser);

        recyclerView.setLayoutManager(new LinearLayoutManager(memberActivities.this));

        Call<Productresponse> call=RetrofitClient.getInstance().getApi().getallproduct();

        call.enqueue(new Callback<Productresponse>() {
            @Override
            public void onResponse(Call<Productresponse> call, Response<Productresponse> response) {
                productList=response.body().getProductList();
                productadapter=new Productadapter(memberActivities.this,productList);


                recyclerView.setAdapter(productadapter);

               // Toast.makeText(memberActivities.this, (CharSequence) productList,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Productresponse> call, Throwable t) {

            }
        });

    }
}
