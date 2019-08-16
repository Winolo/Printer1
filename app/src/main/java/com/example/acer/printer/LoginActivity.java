package com.example.acer.printer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText Username;
    private EditText Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        Username = (EditText) findViewById(R.id.Username);

        Password = (EditText) findViewById(R.id.password);

        Button SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginuser();
            }
        });






    }

    //Log in สำหรับสมาชิก

    public void Loginuser() {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .userlogin(Username.getText().toString(), Password.getText().toString());



        Call<ResponseBody> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .Driverlogin(Username.getText().toString(), Password.getText().toString());

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {


                    String res = response.body().string();




                        if (res.contains("success")) {


                            Intent showqueue = new Intent(LoginActivity.this, QueueDriver.class);

                            startActivity(showqueue);


                            SharedPreferences settings = getSharedPreferences("Driveruser", 0);

                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("namedriver", res);
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_LONG).show();
                        } else {
                            //Toast.makeText(LoginActivity.this,"เข้าสู่ระบบไม่สำเร็จ", Toast.LENGTH_LONG).show();
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });








        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {


                    String res = response.body().string();


                    if(res.equals("success")){




                        Intent showproduct=new Intent(LoginActivity.this,Mainmenuformember.class);

                        startActivity(showproduct);


                        SharedPreferences settings=getSharedPreferences("User",0);

                        SharedPreferences.Editor editor=settings.edit();

                        editor.putString("Username", Username.getText().toString());
                        editor.commit();
                        Toast.makeText(LoginActivity.this,"เข้าสู่ระบบสำเร็จ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Toast.makeText(LoginActivity.this,"เข้าสู่ระบบไม่สำเร็จ", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


    }







    }


