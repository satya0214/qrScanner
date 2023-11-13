package com.example.qrscanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String temp;
//for buttomnavigation
    BottomNavigationView bnView;

    private ImageView imageViewQRCode;
    private EditText editTextInput;
    private Button buttonGenerate;
    private Button buttonShare;
    private TextView qrcodeTV;

    private Bitmap generatedQRCodeBitmap;
    Bitmap generateData;

//    String generateData;

    //For storage
//    SharedPreferences.Editor editor;
//for storage chatGPT
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("QRGenerator", 0);
//        editor = pref.edit();

        pref = getApplicationContext().getSharedPreferences("QRGenerator", 0);
        editor = pref.edit();


        //bottomNavigation

        bnView = findViewById(R.id.bnView);

        bnView.setSelectedItemId(R.id.create);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.start_scan:
//                        scanCode();
                        startActivity(new Intent(getApplicationContext(), scanner.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:

                        startActivity(new Intent(MainActivity.this, History.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        imageViewQRCode = findViewById(R.id.imageViewQRCode);
        editTextInput = findViewById(R.id.editTextInput);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        buttonShare = findViewById(R.id.buttonShare);
        qrcodeTV = findViewById(R.id.idTVGenerateQR);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQRCode();
            }
        });


//        return null;
    }
    private void generateQRCode() {
        String data = editTextInput.getText().toString().trim();
        if (data.isEmpty()) {
            editTextInput.setError("Please enter some input");
            return;
        }
        int qrCodeDimension = 500;
        // The size of the QR code image

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, qrCodeDimension, qrCodeDimension, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            generatedQRCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);





            for (int x = 0; x < width; x++) {

                for (int y = 0; y < height; y++) {
                    generatedQRCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrcodeTV.setVisibility(View.GONE);
            imageViewQRCode.setImageBitmap(generatedQRCodeBitmap);
            //for storage

            String generatedQRCodeString = BitMapToString(generatedQRCodeBitmap);
            editor.putString("qr_generator_data", generatedQRCodeString);
            editor.apply();

//            String generate = BitMapToString(generatedQRCodeBitmap);
//            editor.putString("qr_generator_data",generate);
//            editor.apply();




        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


//for storage


//    public String BitMapToString(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String temp = Base64.encodeToString(b, Base64.DEFAULT);
//        return temp;
//    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }




    private void shareQRCode() {
        if (generatedQRCodeBitmap != null) {
            String shareText = "Sharing QR Code";
            String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), generatedQRCodeBitmap, "QR Code", null);

//            String generate = imagePath;
//            editor.putString("qr_generator_data",generate);
//            editor.apply();

            Uri imageUri = Uri.parse(imagePath);

            //for storage chatGPT
            // Store the generated QR code data in shared preferences
            String generatedQRCodeString = imagePath;
            editor.putString("qr_generator_data", generatedQRCodeString);
            editor.apply();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(shareIntent, "Share QR Code"));
        } else {
            Toast.makeText(this, "No QR Code generated yet", Toast.LENGTH_SHORT).show();
        }
    }


}