package com.example.acer.printer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.printer.Orderstatusclass;
import com.example.acer.printer.Paymentdetailclass;
import com.example.acer.printer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Paymentadapter extends RecyclerView.Adapter<Paymentadapter.ProductViewholder> {

    private Context mCtx;

    public static final String URL="http://192.168.43.222/ecomfish/ecomfish/Timageproduct/";

    private List<Paymentdetailclass> paymentdetailclass=new ArrayList<>();
    Paymentdetailclass paymentdetailclass1;

    public Paymentadapter(Context mCtx, List<Paymentdetailclass> paymentdetailclass) {
        this.mCtx = mCtx;
        this.paymentdetailclass = paymentdetailclass;

    }



    @NonNull
    @Override
    public Paymentadapter.ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mCtx).inflate(R.layout.recyclerviewpayment_detail,parent,false);




        return new Paymentadapter.ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Paymentadapter.ProductViewholder productViewholder, int position) {
        paymentdetailclass1=paymentdetailclass.get(position);



        final int pos =productViewholder.getLayoutPosition();




        //  productViewholder.textViewtitle.setText(product.getPro_Id()+product.getTitle());

        productViewholder.payment_id.setText("บิลที่ : "+paymentdetailclass1.getPayment_id());
        productViewholder.cusname.setText("Username :"+paymentdetailclass1.getCusname());
        productViewholder.total.setText("ทั้งหมดเป็นเงิน: "+paymentdetailclass1.getTotal());
        productViewholder.date.setText("ณ วันที่: "+paymentdetailclass1.getDate());
        //productViewholder.Stock.setText("สินค้าเหลือจำนวน: "+product.getStock());




        Picasso.get().load(URL+paymentdetailclass1.getPicture()).into(productViewholder.Image);
    }





    @Override
    public int getItemCount() {
        return paymentdetailclass.size();
    }


    class ProductViewholder extends RecyclerView.ViewHolder{

        TextView payment_id,cusname,total,date;
        ImageView Image;



        int keepId;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            payment_id=(TextView)itemView.findViewById(R.id.payment_id);
            cusname=(TextView)itemView.findViewById(R.id.cusname);
            total=(TextView)itemView.findViewById(R.id.total);
            date=(TextView)itemView.findViewById(R.id.date);
            Image=(ImageView)itemView.findViewById(R.id.card_view_image);


        }
    }



}
