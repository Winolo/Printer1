package com.example.acer.printer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.acer.printer.Mainmenuformember;
import com.example.acer.printer.Product;
import com.example.acer.printer.Productlist;
import com.example.acer.printer.R;
import com.example.acer.printer.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productadapter extends RecyclerView.Adapter<Productadapter.ProductViewholder> {

    private Context mCtx;
    String Getuser;
    private List<Product> productList;
    Product product;

     String order_id;

     Picasso picasso;


    String datetime;
int keepview;
     EditText input;

    public static final String URL="http://192.168.64.2/";


    final Calendar c = Calendar.getInstance();



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

        productViewholder.buying.setVisibility(View.INVISIBLE);
            /*
            productViewholder.buying.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //Make Dialog

                    Dialog(pos);


                }
            });
*/
        Picasso.get().load(URL+product.getPro_pic()).into(productViewholder.Image);


        // ถ้ามีการเลือกสินค้าชนิดนั้นก็จะไปอีกหน้าทันที ล้ะไปอยู่ในหน้าสั่งซื้อ
        productViewholder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog(pos);

                Toast.makeText(mCtx,String.valueOf(product.getPro_Id()),Toast.LENGTH_SHORT).show();


                //ต้อง Share order_id ให้อีกหน้านึง



            }
        });


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

                else if(Double.parseDouble(input.getText().toString())<=0){

                    Toast.makeText(mCtx,"กรุณากรอกจำนวนที่ไม่น้อยกว่าหรือเป็น0ครับ",Toast.LENGTH_LONG).show();
                }

                else {
                    Buying((product.getPro_Id()));
                    Order();

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
                    String res=response.body().string();
                    Toast.makeText(mCtx, res, Toast.LENGTH_LONG).show();

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


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = sdf.format(c.getTime());

        datetime = strDate;


        //System.out.println(datetime+"kuyyyyy");

        Call<ResponseBody> order=RetrofitClient
                .getInstance()
                .getApi()
                .orders(Getuser,datetime,"true");
        order.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String res=response.body().string();
                    Toast.makeText(mCtx, res, Toast.LENGTH_LONG).show();



                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    getorderid();
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

        order_id="";

        Getuser=username;
        Call<ResponseBody> getorderid=RetrofitClient
                .getInstance()
                .getApi()
                .getorderid(Getuser);
        getorderid.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    order_id = response.body().string();
                    Toast.makeText(mCtx, order_id, Toast.LENGTH_LONG).show();

                    Orderdetail(order_id, product.getPro_Id(), input.getText().toString(), Double.parseDouble(product.getPrice_normal()), product.getPro_pic());

                    Intent intent = new Intent(mCtx, Mainmenuformember.class);
                    mCtx.startActivity(intent);


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
