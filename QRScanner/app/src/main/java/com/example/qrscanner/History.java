package com.example.qrscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    public static final String PRODUCT_PHOTO = "photo";

    String str_bitmap;
    private  Bitmap bitmap;
//    private ImageView imageView_photo;

    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //For storage

        // Retrieve the scanDataList ArrayList from the intent
        ArrayList<String> scanDataList = getIntent().getStringArrayListExtra("scanDataList");

        TextView textView = findViewById(R.id.textView);
        StringBuilder stringBuilder = new StringBuilder();

        if (scanDataList != null) {
            for (String scanData : scanDataList) {
                stringBuilder.append(scanData).append("\n");
            }
        }

        textView.setText(stringBuilder.toString());

//        ArrayList<String> scanDataList = getIntent().getStringArrayListExtra("scanDataList");
////        for (String scanData : scanDataList) {
////            // Display scanData as required (e.g., in a TextView, ListView, RecyclerView, etc.)
////        }
//
//        TextView textView = findViewById(R.id.textView);
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (String scanData : scanDataList) {
//            stringBuilder.append(scanData).append("\n");
//        }
//
//        textView.setText(stringBuilder.toString());


//        TextView scanDataTextView;
//        scanDataTextView = findViewById(R.id.scanDataTextView);
//        SharedPreferences prefs = getSharedPreferences("QRhistory", MODE_PRIVATE);
//        String scanDataString = prefs.getString("scanDataList", "");
//        ArrayList<String> retrievedScanDataList = new ArrayList<>(Arrays.asList(scanDataString.split(",")));
//        String displayText = TextUtils.join("\n", retrievedScanDataList);
//        scanDataTextView.setText(displayText);



//
//        SharedPreferences sharedPreferences = getSharedPreferences("QRhistory", Context.MODE_PRIVATE);
//        // Retrieve the stored QR code data
//
//        String scannedData = sharedPreferences.getString("qr_code_data", "");
//
//        // Find the TextView
//        TextView textView = findViewById(R.id.textView);
//
//        // Set the retrieved data to the TextView
//        textView.setText(scannedData);


        //for generator QR as image
        ImageView qrCodeImageView;
        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("QRGenerator", 0);
        String storedQRCodeData = pref.getString("qr_generator_data", "");
        Bitmap qrCodeBitmap = StringToBitMap(storedQRCodeData);

        qrCodeImageView.setImageBitmap(qrCodeBitmap);



        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.history);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.start_scan:
                        startActivity(new Intent (getApplicationContext(), scanner.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:

                        return true;
                }

                return false;
            }
        });

    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}