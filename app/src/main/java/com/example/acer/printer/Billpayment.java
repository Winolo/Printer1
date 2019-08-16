package com.example.acer.printer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;

public class Billpayment extends AppCompatActivity {


    EditText Name, Price, Date;

    String datetime;
    String filename;
    Button Confirm;

    String Getuser;


    private static int RESULT_LOAD_IMAGE = 1;

ImageView imageView;

    final Calendar myCalendar = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences Getgs = this.getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billpayment);

        Name = (EditText) findViewById(R.id.Name);
        Price = (EditText) findViewById(R.id.Price);
        Date = (EditText) findViewById(R.id.Date);
        Confirm=(Button)findViewById(R.id.Confirm);



        imageView=(ImageView)findViewById(R.id.imageView);

        Button Browse = (Button) findViewById(R.id.Browse);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Billpayment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


Confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Call<ResponseBody> call=RetrofitClient.getInstance().getApi()
                .recieveproduct(filename,Getuser, Double.parseDouble(Price.getText().toString()),datetime);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(Billpayment.this,"ทำรายการสำเร็จ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
});



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);





                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                    String path=selectedImage.getPath();
                     filename=path.substring(path.lastIndexOf("/")+1);
                    //Date.setText(filename);

                }
                break;
        }
    }



        private void updateLabel () {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String strDate = sdf.format(myCalendar.getTime());

            datetime = strDate;


            Date.setText(datetime);
        }

    }

