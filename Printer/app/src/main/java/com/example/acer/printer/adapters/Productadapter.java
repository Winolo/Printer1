package com.example.acer.printer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.printer.Checkstatus;
import com.example.acer.printer.Product;
import com.example.acer.printer.Productlist;
import com.example.acer.printer.R;
import com.example.acer.printer.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productadapter extends RecyclerView.Adapter<Productadapter.ProductViewholder> {

    public static final String URL="http://192.168.43.222/ecomfish/Timageproduct/";
    private Context mCtx;
    String Getuser;
    private List<Product> productList;
    Product product;

     String order_id;

    String datetime;
int keepview;
     EditText input;




    Calendar c = null;
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                c = Calendar.getInstance();
            }
        }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                String strDate = sdf.format(c.getTime());
                datetime=strDate;
            }
        }

    public Productadapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_product,parent,false);




            return new ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewholder productViewholder, int position) {
             product=productList.get(position);

             final int pos =productViewholder.getLayoutPosition();




             productViewholder.textViewtitle.setText(product.getPro_Id()+".)"+product.getTitle());
            productViewholder.Detail.setText("รายละเอียด: "+product.getDetail());
            productViewholder.Price.setText("ราคา: "+product.getPrice_normal());
            productViewholder.Stock.setText("สินค้าเหลือจำนวน: "+product.getStock());

            productViewholder.buying.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //Make Dialog

                    Dialog(pos);


                }
            });

        Picasso.get().load(URL+product.getPro_pic()).into(productViewholder.Image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void Dialog(final int keepview){

        product=productList.get(keepview);

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(mCtx);
        alertDialog.setTitle("กรุณาระบุจำนวนที่ต้องการซื้อ");

         input = new EditText(mCtx);

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
                if(Double.parseDouble(input.getText().toString())>Double.parseDouble(product.getStock()) &&!input.getText().toString().isEmpty() ){

                    Toast.makeText(mCtx,"ซื้อเกินจำนวนที่มีไม่ได้ครับ หรือ กรอกผิดครับ",Toast.LENGTH_SHORT).show();
                }


                    else {
                    Buying((product.getPro_Id()));
                    Order();
                    getorderid();
                }

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

        //ลดของใน Stock ลง
    public void Buying(int Id){

        Call<ResponseBody> buy=RetrofitClient
                .getInstance()
                .getApi()
                .buyingproduct(Id,input.getText().toString());
        buy.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.body()!=null) {
                        String res = response.body().string();

                        Toast.makeText(mCtx, res, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mCtx, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void Order(){
        SharedPreferences Getgs = mCtx.getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();

            Getuser=username;




        Call<ResponseBody> order=RetrofitClient
                .getInstance()
                .getApi()
                .orders(Getuser,datetime,"true");
        order.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.body()!=null) {
                        String res = response.body().string();

                        Toast.makeText(mCtx, res, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mCtx, "Error", Toast.LENGTH_LONG).show();
            }
        });




    }

    public void getorderid(){
        SharedPreferences Getgs = mCtx.getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();



        Getuser=username;
        Call<ResponseBody> getorderid=RetrofitClient
                .getInstance()
                .getApi()
                .getorderid(Getuser);
        getorderid.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.body()!=null || !response.body().string().equals("")) {
                        order_id = response.body().string();
                        Toast.makeText(mCtx, order_id, Toast.LENGTH_LONG).show();

                        if (!order_id.isEmpty()) {
                            Orderdetail(order_id, product.getPro_Id(), input.getText().toString(), Double.parseDouble(product.getPrice_normal()), product.getPro_pic());
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mCtx, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void Orderdetail(String order_id, int pro_id, String qty, double price_normal,String pic){
        SharedPreferences Getgs = mCtx.getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;
        Call<ResponseBody> orderdetail=RetrofitClient
                .getInstance()
                .getApi()
                .orderdetail(order_id,pro_id,qty,Double.parseDouble(qty)*price_normal,pic);
        orderdetail.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Intent intent=new Intent(mCtx,Checkstatus.class);
                mCtx.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mCtx, "Error", Toast.LENGTH_LONG).show();
            }
        });



    }


    class ProductViewholder extends RecyclerView.ViewHolder{

            TextView textViewtitle,Detail,Price,Stock;
            ImageView Image;
            EditText Qauntity;
            Button buying;

            int keepId;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            textViewtitle=(TextView)itemView.findViewById(R.id.card_view_image_title);
            Detail=(TextView)itemView.findViewById(R.id.Detail);
            Price=(TextView)itemView.findViewById(R.id.Price);
            Stock=(TextView)itemView.findViewById(R.id.Stock);
            Image=(ImageView)itemView.findViewById(R.id.card_view_image);

            buying=(Button)itemView.findViewById(R.id.Buying);
        }
    }

}
