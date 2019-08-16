package com.example.acer.printer.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.printer.Getdrivervieworder;
import com.example.acer.printer.QueueDriver;
import com.example.acer.printer.R;

import java.util.ArrayList;
import java.util.List;

public class Listviewqueue extends ArrayAdapter<Queuedriverclass> {


    Button watchcus;
    private Context mCtx;

   private List<Queuedriverclass> queuedriverclassList;


    public Listviewqueue(List<Queuedriverclass> queuedriverclassList, Context mCtx) {
        super(mCtx, R.layout.listview_driverque,queuedriverclassList);
        this.queuedriverclassList = queuedriverclassList;
        this.mCtx = mCtx;

    }


    //this method will return the list item
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);






        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.listview_driverque, null, true);







        watchcus=(Button)listViewItem.findViewById(R.id.Watchcusorder);

        TextView textViewType=(TextView)listViewItem.findViewById(R.id.textViewType);



        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);

        // TextView textViewDelete=(TextView)listViewItem.findViewById(R.id.textViewDelete);


        final Queuedriverclass queuedriverclass=queuedriverclassList.get(position);


        textViewType.setText(queuedriverclass.getQueue_id());

        textViewType.setTextColor(Color.BLACK);

        textViewName.setText(queuedriverclass.getCatcar_name());

        textViewName.setTextColor(Color.BLACK);


        watchcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(mCtx, Getdrivervieworder.class);

                mCtx.startActivity(go);
            }
        });


        //getting text views
        //      TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        //  TextView textViewVoice = (TextView) listViewItem.findViewById(R.id.textViewVoice);

        //Getting the speech for the specified position
        //Speech speech = speechList.get(position);

        //setting text values to textviews
        //textViewId.setText(speech.getid());
        // textViewVoice.setText(speech.getVoice());

        //returning the listitem
        return listViewItem;
    }









}
