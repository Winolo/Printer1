package com.example.acer.printer.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class Driverwatchadapter extends RecyclerView.Adapter<Driverwatchadapter.ProductViewholder> {

    public static final String URL="http://192.168.43.222/ecomfish/Timageproduct/";
    private Context mCtx;

    private List<Orderstatusclass> orderstatusclassList=new ArrayList<>();
    Orderstatusclass orderstatusclass;


    public Driverwatchadapter(Context mCtx, List<Orderstatusclass> orderstatusclassList) {
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
    public void onBindViewHolder(@NonNull ProductViewholder productViewholder, int position) {
        orderstatusclass=orderstatusclassList.get(position);



        final int pos =productViewholder.getLayoutPosition();




        //  productViewholder.textViewtitle.setText(product.getPro_Id()+product.getTitle());

        productViewholder.Order_id.setText("คำสั่งซื้อที่ : "+orderstatusclass.getOrder_id());
        productViewholder.Quantity.setText("จำนวน :"+orderstatusclass.getQty());
        productViewholder.Price.setText("ราคา : "+orderstatusclass.getPrice_normal());
        //productViewholder.Stock.setText("สินค้าเหลือจำนวน: "+product.getStock());


        productViewholder.Cancelorder.setVisibility(View.INVISIBLE);

        Picasso.get().load(URL+orderstatusclass.getPro_pix()).into(productViewholder.Image);
    }





    @Override
    public int getItemCount() {
        return orderstatusclassList.size();
    }


    class ProductViewholder extends RecyclerView.ViewHolder{

        TextView Quantity,Price,textViewtitle,Order_id;
        ImageView Image;

        Button Cancelorder;


        int keepId;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            textViewtitle=(TextView)itemView.findViewById(R.id.card_view_image_title);
            Quantity=(TextView)itemView.findViewById(R.id.Quantity);
            Price=(TextView)itemView.findViewById(R.id.Price);
            Order_id=(TextView)itemView.findViewById(R.id.order_id);
            Image=(ImageView)itemView.findViewById(R.id.card_view_image);
            Cancelorder=(Button)itemView.findViewById(R.id.Cancelorder);

        }
    }

}
