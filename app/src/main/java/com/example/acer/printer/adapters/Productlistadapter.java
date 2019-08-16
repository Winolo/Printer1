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

import com.example.acer.printer.Mainmenuformember;
import com.example.acer.printer.Product;
import com.example.acer.printer.Productlist;
import com.example.acer.printer.Productlistclass;
import com.example.acer.printer.Productresponse;
import com.example.acer.printer.R;
import com.example.acer.printer.ReaderActivity;
import com.example.acer.printer.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productlistadapter extends RecyclerView.Adapter<Productlistadapter.ProductViewholder> {
    public static final String URL="http://192.168.64.2/ecomfish/ecomfish/Timageproduct/";
    private Context mCtx;
    String Getuser;
    private List<Productlistclass> productList;
    Productlistclass product;

    Productadapter productadapter1;





    String order_id;

    String Imageurl;

    String datetime;
    int keepview;
    EditText input;

    Calendar c = null;
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            c = Calendar.getInstance();
        }
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String strDate = sdf.format(c.getTime());
            datetime=strDate;
        }
    }


    public Productlistadapter(Context mCtx, List<Productlistclass> productList) {

        this.mCtx = mCtx;

        this.productList = productList;


    }

    @NonNull
    @Override
    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_productlist,parent,false);




        return new ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewholder productViewholder, int position) {

        product=productList.get(position);



        final int pos =productViewholder.getLayoutPosition();








        //productViewholder.textViewtitle.setText(product.getPro_Id()+product.getTitle());

        productViewholder.Quantity.setText("จำนวน"+product.getQty());
        productViewholder.Price.setText("ราคา: "+product.getPrice_normal());
        //productViewholder.Stock.setText("สินค้าเหลือจำนวน: "+product.getStock());

        //เมื่อกดปุ่มรับสินค้า

        productViewholder.recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //removeAt(pos);
                recieveproduct(pos);
                updatestatus(pos);
                //Update status false จัดส่งเสร็จแล้ว
            }
        });

        productViewholder.Qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Qrcode=new Intent(mCtx,ReaderActivity.class);
                 mCtx.startActivity(Qrcode);
            }
        });

        Picasso.get().load(URL+product.getPro_pix()).into(productViewholder.Image);
    }

    @Override
    public int getItemCount() {
        if(productList.size()==0){
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(mCtx);
            alertDialog.setTitle("ยังไม่มีสินค้าที่จะรับได้น้ะครับ");



            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    // Toast.makeText(mCtx,String.valueOf(product.getPro_Id()) ,Toast.LENGTH_SHORT).show();

                    //ไม่ให้สั่งซื้อเกิน Stcokได้




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





        return productList.size();
    }


    public void removeAt(int position) {



    Toast.makeText(mCtx,"คุณลบตำแหน่งที่: "+position+"ไซส์"+productList.size(),Toast.LENGTH_LONG).show();
        productList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productList.size()+1);
    }

    public void recieveproduct(int pos){

        SharedPreferences Getgs = mCtx.getSharedPreferences("User", 0);

        final String username=Getgs.getString("Username", "").toString();

        Getuser=username;


            if (pos >= productList.size() - 1 && productList.size()>0) {


                if(productList.size()>=pos){
                    pos=pos+0;
                }
                else {
                    pos = pos - 1;
                }


            }



        product=productList.get(pos);

        Call<ResponseBody> call=RetrofitClient.getInstance()
                .getApi().recieveproduct(product.getPro_pix(),Getuser,product.getPrice_normal(),datetime);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void updatestatus(int pos){


        if (pos >= productList.size() - 1 && productList.size()>0) {


            if(productList.size()>=pos){
                pos=pos+0;
            }
            else {
                pos = pos - 1;
            }


        }
        product=productList.get(pos);

        Call<ResponseBody> call=RetrofitClient.getInstance()
                .getApi().updatestatus(product.getOrder_id());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


               // Intent goback=new Intent(Productlistadapter.this, Mainmenuformember.class);

                Intent Qrcode=new Intent(mCtx,Mainmenuformember.class);
                mCtx.startActivity(Qrcode);
                Toast.makeText(mCtx,"รับสินค้าสำเร็จ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    class ProductViewholder extends RecyclerView.ViewHolder{

        TextView Quantity,Price,textViewtitle;
        ImageView Image;
        Button recieve,Qrcode;


        int keepId;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            textViewtitle=(TextView)itemView.findViewById(R.id.card_view_image_title);
            Quantity=(TextView)itemView.findViewById(R.id.Quantity);
            Price=(TextView)itemView.findViewById(R.id.Price);
            recieve=(Button)itemView.findViewById(R.id.recieve);
            Qrcode=(Button)itemView.findViewById(R.id.Qrcode);
            Image=(ImageView)itemView.findViewById(R.id.card_view_image);


        }
    }

}
