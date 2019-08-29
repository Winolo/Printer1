package com.example.acer.printer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;

    String test="15,16,21,23";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });





//        String win[]=test.split(",");
//
////            System.out.println(win[]);
//
//        String keep[]=new String[win.length];
//
//        for (int i=0;i<win.length;i++){
//
//            keep[i]=win[i];
//
//            System.out.println(keep[i]);
//
//            //ส่งไปให้Set Status Finish
//
//            Setstatusfinish(keep[i]);
//
//        }




    }



    //Setเพื่อเข้ารายการที่สั่งแล้วรอรับ
    private void Setstatusfinish(String order_id){

        Call<ResponseBody> call=RetrofitClient
                .getInstance()
                .getApi()
                .Setbillfinish(order_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result= null;
                try {
                    result = response.body().string();
                    Toast.makeText(ReaderActivity.this,result,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents().toString(),Toast.LENGTH_LONG).show();




                String win[]=result.toString().split(",");

//            System.out.println(win[]);

                String keep[]=new String[win.length];

                for (int i=0;i<win.length;i++){

                    keep[i]=win[i];

                    System.out.println(keep[i]);

                    //ส่งไปให้Set Status Finish

                    Setstatusfinish(keep[i]);

                }







                //ต้องส่งไปให้หลังบ้านหัก status false
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
