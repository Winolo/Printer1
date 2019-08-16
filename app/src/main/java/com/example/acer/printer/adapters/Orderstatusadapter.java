package com.example.acer.printer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.printer.Orderstatusclass;
import com.example.acer.printer.Productlistclass;
import com.example.acer.printer.R;
import com.example.acer.printer.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orderstatusadapter extends RecyclerView.Adapter<Orderstatusadapter.ProductViewholder> {
    public static final String URL="http://192.168.2.44/ecomfish/ecomfish/Timageproduct/";

    private Context mCtx;

    int keepcount;
    double keepsum;
    EditText input;

    private List<Orderstatusclass> orderstatusclassList=new ArrayList<>();
    Orderstatusclass orderstatusclass;


    public Orderstatusadapter(Context mCtx, List<Orderstatusclass> orderstatusclassList) {
        this.mCtx = mCtx;
        this.orderstatusclassList = orderstatusclassList;
    }


    @NonNull
    @Override
    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_orderstatus,parent,false);




        return new ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewholder productViewholder, final int position) {
        orderstatusclass=orderstatusclassList.get(position);

        //Button button1 = new Button(mCtx);


        final int pos =productViewholder.getLayoutPosition();




        //  productViewholder.textViewtitle.setText(product.getPro_Id()+product.getTitle());

       // productViewholder.Order_id.setText("คำสั่งซื้อที่ : "+orderstatusclass.getOrder_id());
        productViewholder.Quantity.setText("จำนวน :"+orderstatusclass.getQty());
        productViewholder.Price.setText("ราคา : "+orderstatusclass.getPrice_normal());


        for (int i=0;i<pos;i++){


                productViewholder.Confirmorder.setVisibility(View.INVISIBLE);

        }



        //productViewholder.Stock.setText("สินค้าเหลือจำนวน: "+product.getStock());

        //เมื่อกดปุ่มยกเลิกสินค้า
        productViewholder.Cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cancelorder(pos);
                removeAt(pos);
            }
        });

productViewholder.Confirmorder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {





        int sumquantity=0;

        int count=0;


        for (int i=0;i<=orderstatusclassList.size()-1;i++) {

            //keep[i]+=orderstatusclass.getQty()+",";
            orderstatusclass=orderstatusclassList.get(i);


            sumquantity+=orderstatusclass.getPrice_normal();


            count++;

            //Toast.makeText(mCtx, orderstatusclass.getQty()+"", Toast.LENGTH_LONG).show();
           // System.out.println(orderstatusclass.getOrder_id()+"");

        }
        keepcount=count;
        keepsum=sumquantity;
        System.out.println(keepcount);
        System.out.println(sumquantity);
       // Toast.makeText(mCtx, keep+"", Toast.LENGTH_LONG).show();
        Dialog(keepcount,keepsum);


    }
});



        Picasso.get().load(URL+orderstatusclass.getPro_pix()).into(productViewholder.Image);



    }



    public void Dialog(final int keepcount,final double sum){

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(mCtx);
        alertDialog.setTitle("รายการที่ทำการยืนยัน : "+keepcount+" ชิ้น"+"\n"+"ราคารวมของสินค้า       : "+keepsum);

       // input = new EditText(mCtx);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
       // alertDialog.setView(input); // uncomment this line


        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                // Toast.makeText(mCtx,String.valueOf(product.getPro_Id()) ,Toast.LENGTH_SHORT).show();

                //ไม่ให้สั่งซื้อเกิน Stcokได้

                //input.setText("ddd"+keepcount);


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






    private void Cancelorder(int pos) {

        if(pos>orderstatusclassList.size()-1){

            pos=pos-1;
        }
        orderstatusclass=orderstatusclassList.get(pos);
        Call<ResponseBody> call=RetrofitClient
                .getInstance()
                .getApi()
                .Cancelorder(String.valueOf(orderstatusclass.getPro_id()), String.valueOf(orderstatusclass.getQty()),orderstatusclass.getOrder_id());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result= null;
                try {
                    result = response.body().string();
                    Toast.makeText(mCtx,result,Toast.LENGTH_SHORT).show();
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
    public int getItemCount() {
        return orderstatusclassList.size();
    }

    public void removeAt(int position) {

        if(position>orderstatusclassList.size()-1){

            position=position-1;
        }


        Toast.makeText(mCtx,"คุณลบตำแหน่งที่: "+position+"ไซส์"+orderstatusclassList.size(),Toast.LENGTH_LONG).show();
        orderstatusclassList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderstatusclassList.size());
    }

     class ProductViewholder extends RecyclerView.ViewHolder{

        TextView Quantity,Price,textViewtitle,Order_id;
        ImageView Image;

        Button Cancelorder,Confirmorder;


        int keepId;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            textViewtitle=(TextView)itemView.findViewById(R.id.card_view_image_title);
            Quantity=(TextView)itemView.findViewById(R.id.Quantity);
            Price=(TextView)itemView.findViewById(R.id.Price);
            Order_id=(TextView)itemView.findViewById(R.id.order_id);
            Image=(ImageView)itemView.findViewById(R.id.card_view_image);
            Cancelorder=(Button)itemView.findViewById(R.id.Cancelorder);
            Confirmorder=(Button)itemView.findViewById(R.id.Confirmorder);

        }
    }

}
