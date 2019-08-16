package com.example.acer.printer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.acer.printer.adapters.Orderstatusadapter;
import com.example.acer.printer.adapters.Productlistadapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkstatus extends AppCompatActivity {


    private RecyclerView recyclerView;




Button Confirmbill;



    private Orderstatusadapter orderstatusadapter;

    private List<Orderstatusclass> orderstatusclassList;

    Orderstatusclass orderstatusclass;


    String Getuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkstatus);

        SharedPreferences Getgs = getSharedPreferences("User", 0);
        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);



        recyclerView.setLayoutManager(new LinearLayoutManager(Checkstatus.this));


        Call<Orderstatusresponse> call=RetrofitClient.getInstance().getApi().ordernotconfirm(Getuser);

        call.enqueue(new Callback<Orderstatusresponse>() {
            @Override
            public void onResponse(Call<Orderstatusresponse> call, Response<Orderstatusresponse> response) {
                orderstatusclassList=response.body().getOrderstatusclassList();
                orderstatusadapter=new Orderstatusadapter(Checkstatus.this,orderstatusclassList);
                recyclerView.setAdapter(orderstatusadapter);


            }

            @Override
            public void onFailure(Call<Orderstatusresponse> call, Throwable t) {

            }
        });







    }


    public void Dialog(){




        EditText input;




        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        alertDialog.setTitle("กรุณาระบุจำนวนที่ต้องการซื้อ");

        input = new EditText(this);





        for(int i=0;i<orderstatusclassList.size()-1;i++){

            //orderstatusclassList.get(2);


            input.setText(orderstatusclass.getPrice_normal());

        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line

        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                // Toast.makeText(mCtx,String.valueOf(product.getPro_Id()) ,Toast.LENGTH_SHORT).show();

                //ไม่ให้สั่งซื้อเกิน Stcokได้


                /*Intent goreceive=new Intent(mCtx,Productlist.class);

                        mCtx.startActivity(goreceive);
                        */
            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub



            }
        });

        alertDialog.show();
    }




}
