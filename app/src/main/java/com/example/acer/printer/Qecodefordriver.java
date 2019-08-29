package com.example.acer.printer;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Qecodefordriver extends AppCompatActivity {

    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";

    public static final String URL="http://192.168.64.2/MyApi/public/OrderidforgenQr";


    Bitmap bitmap ;

    String order_id[];

    String order="";

    private String getuser;
    private TextView etqr;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qecodefordriver);


        getuser=getIntent().getStringExtra("Getuser");
        iv = (ImageView) findViewById(R.id.iv);
        etqr = (TextView) findViewById(R.id.etqr);



//        etqr.setText(getuser);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(etqr.getText().toString().trim().length() == 0){
//                    Toast.makeText(Qecodefordriver.this, "Enter String!", Toast.LENGTH_SHORT).show();
//                }else {
//                    try {
//                        bitmap = TextToImageEncode(etqr.getText().toString());
//                        iv.setImageBitmap(bitmap);
//                        //String path = saveImage(bitmap);  //give read write permission
//                        //Toast.makeText(Qecodefordriver.this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
//                    } catch (WriterException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });


Getorderid();
    }

//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        // have the object build the directory structure, if needed.
//
//        if (!wallpaperDirectory.exists()) {
//            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();   //give read write permission
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//
//    }



    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }



    public void Getorderid(){
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj=new JSONObject(response);
                            JSONArray resultarray=obj.getJSONArray("order_id");

                            order_id=new String[resultarray.length()];


                            for (int i=0;i<resultarray.length();i++){

                                JSONObject resultobject=resultarray.getJSONObject(i);





                                order_id[i]=resultobject.getString("order_id");


                            order+=order_id[i].concat(",");







                            }


                            String ordernew=order.substring(0,order.length()-1);
                            System.out.println("Order"+ordernew);

                            try {
                                bitmap = TextToImageEncode(ordernew);
                                iv.setImageBitmap(bitmap);
                                //String path = saveImage(bitmap);  //give read write permission
                                //Toast.makeText(Qecodefordriver.this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }






//                            SetspinnerSeason();


                        }


                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Cusname",getuser);


                return params;
            }
        };
        queue.add(postRequest);


    }



}
