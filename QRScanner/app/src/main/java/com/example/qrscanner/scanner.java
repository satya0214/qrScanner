package com.example.qrscanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class scanner extends AppCompatActivity {

    BottomNavigationView bnView;
    Button start_scane;
//    Button btn_scan;
     String scannedData;
//    SharedPreferences.Editor editor;

    SharedPreferences.Editor editor;
    ArrayList<String> scanDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanner);
        //for storage
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("QRhistory", 0);
//        editor = pref.edit();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("QRhistory", 0);
        editor = pref.edit();

        // Retrieve existing scan data from shared preferences
        String scanDataString = pref.getString("scanDataList", "");
        scanDataList = new ArrayList<>(Arrays.asList(scanDataString.split(",")));
        if (scanDataList == null) {
            scanDataList = new ArrayList<>();
        }
//        Intent intent = new Intent(scanner.this, History.class);
//        intent.putStringArrayListExtra("scanDataList", scanDataList);
//        startActivity(intent);


        //for store the scan data in array list
//        ArrayList<String> scanDataList = new ArrayList<>();

//        SharedPreferences.Editor editor = getSharedPreferences("QRhistory", MODE_PRIVATE).edit();



        bnView = findViewById(R.id.bnView);

        bnView.setSelectedItemId(R.id.start_scan);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.create:
                        startActivity(new Intent (getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.start_scan:
                        scanCode();
                        //for storage




                        return true;
                    case R.id.history:
//                        Intent intent = new Intent(scanner.this, History.class);
//                        intent.putStringArrayListExtra("scanDataList", scanDataList);
//                        startActivity(intent);
//                         startActivity(new Intent (getApplicationContext(),History.class));
//                        overridePendingTransition(0,0);
                        Intent intent = new Intent(getApplicationContext(), History.class);
                        intent.putStringArrayListExtra("scanDataList", scanDataList);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


//        start_scane=findViewById(R.id.btn_scan);
////        QR code and Barcode scanner code
//
//        start_scane.setOnClickListener(v ->
//        {
//            scanCode();
//        });


//        return null;
    }

    private void scanCode() {

            ScanOptions options = new ScanOptions();
            options.setPrompt("Scanning");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            barLaucher.launch(options);



    }
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents()!=null)
        {
            //for storage

            String scanData = result.getContents();
            // Add new scan data to the ArrayList
            scanDataList.add(scanData);

            // Convert the updated ArrayList to a string
            String scanDataString = TextUtils.join(",", scanDataList);

            // Save the updated string back to shared preferences
            editor.putString("scanDataList", scanDataString);
            editor.apply();
//            String scanData = result.getContents();
//            ArrayList<String> scanDataList = new ArrayList<>();
//            scanDataList.add(scanData);
//            String scanDataString = TextUtils.join(",", scanDataList);
//            editor.putString("scanDataList", scanDataString);
//            editor.apply();


//            //for storage
//            String scannedData = result.getContents();
//            editor.putString("qr_code_data",scannedData);
//            editor.apply();


            AlertDialog.Builder builder=new AlertDialog.Builder(scanner.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}