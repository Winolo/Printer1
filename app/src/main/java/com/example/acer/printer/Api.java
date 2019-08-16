package com.example.acer.printer;

import android.widget.EditText;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("userlogin")
    Call<ResponseBody> userlogin(

            @Field("Username") String Username,
            @Field("Password") String Password

    );

    @FormUrlEncoded
    @POST("Driverlogin")
    Call<ResponseBody> Driverlogin(

            @Field("Username") String Username,
            @Field("Password") String Password

    );


    @FormUrlEncoded
    @POST("adminlogin")
    Call<ResponseBody> adminlogin(

            @Field("Username") String Username,
            @Field("Password") String Password

    );

    @GET("Getallproduct")
    Call<Productresponse> getallproduct();

    @GET("Getallproduct2")
    Call<Productresponse> getallproduct2();

    @GET("Getdriverqueueorder")
    Call<Orderstatusresponse> Getdriverqueueorder();


    @FormUrlEncoded
    @POST("GetproductwhereId")
    Call<Productresponse> getProduct(

            @Field("product_Id") String product_Id,
            @Field("Quantity") String Quantity

    );

    @FormUrlEncoded
    @POST("Buying")
    Call<ResponseBody> buyingproduct(

            @Field("product_Id") int product_Id,
            @Field("Quantity") String Quantity

    );


    @FormUrlEncoded
    @POST("Orders")
    Call<ResponseBody> orders(

            @Field("username") String username,
            @Field("order_date") String order_date,

            @Field("status") String status


    );

    @FormUrlEncoded
    @POST("getorderid")
    Call<ResponseBody> getorderid(

            @Field("username") String user

    );

    @FormUrlEncoded
    @POST("Orderdetail")
    Call<ResponseBody> orderdetail(

            @Field("order_id") String order_id,
            @Field("pro_id") int pro_id,
            @Field("qty") String qty,
            @Field("price_normal") double price_normal,
            @Field("pro_pix") String pic



    );

    @FormUrlEncoded
    @POST("getproductwherebuy")
    Call<Productlistresponse> getproductwherebuy(

            @Field("username") String user

    );

    @FormUrlEncoded
    @POST("GetproductwhereId")
    Call<Productresponse> GetproductwhereId(


            @Field("pro_id") int pro_id


    );


    @FormUrlEncoded
    @POST("recieveproduct")
    Call<ResponseBody> recieveproduct(


            @Field("picture") String Picture,
             @Field("cusname") String Cus,
            @Field("total") double Total,
            @Field("date") String date


    );


    @FormUrlEncoded
    @POST("updatestutus")
    Call<ResponseBody> updatestatus(


            @Field("order_id") String order_id



    );



    @GET("Getyear")
    Call<List<Yearlist>> getyear();


    @FormUrlEncoded
    @POST("Monthwhereyear")
    Call<ResponseBody> Monthwhereyear(


            @Field("Year") String Year



    );



    @FormUrlEncoded
    @POST("Sumproduct")
    Call<ResponseBody> Sumproduct(

            @Field("Year") String Year,
            @Field("Month") String Month




    );


    @GET("Getdriverqueue")
    Call<ResponseBody> Getdriverqueue();

    @FormUrlEncoded
    @POST("paymentdetail")
    Call<Paymentresponse> paymentdetail(

            @Field("Year") String Year,
            @Field("Month") String Month


    );



    @FormUrlEncoded
    @POST("Notconfirm")
    Call<Orderstatusresponse> ordernotconfirm(

            @Field("User") String User

    );

    @FormUrlEncoded
    @POST("Cancelorder")
    Call<ResponseBody> Cancelorder(
            @Field("pro_id") String pro_id,
            @Field("qty") String qty,
            @Field("Order_id") String Order_id
    );


}
