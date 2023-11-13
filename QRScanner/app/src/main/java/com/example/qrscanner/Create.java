package com.example.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Create extends AppCompatActivity {


//    private ImageView imageViewQRCode;
//    private EditText editTextInput;
//    private Button buttonGenerate;

    BottomNavigationView bnView;
//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

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
                        startActivity(new Intent(getApplicationContext(), scanner.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent (getApplicationContext(),History.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

//        ImageView imageViewQRCode = findViewById(R.id.imageViewQRCode);
//        String data = "Hello, QR Code!"; // The data you want to encode
//        int qrCodeDimension = 500; // The size of the QR code image
//
//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//        try {
//            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, qrCodeDimension, qrCodeDimension);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//            imageViewQRCode.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//chat GPT
//        imageViewQRCode = findViewById(R.id.imageViewQRCode);
//        editTextInput = findViewById(R.id.editTextInput);
//        buttonGenerate = findViewById(R.id.buttonGenerate);
//
//        buttonGenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                generateQRCode();
//            }
//        });
//    }
//    private void generateQRCode() {
//        String data = editTextInput.getText().toString().trim();
//        if (data.isEmpty()) {
//            editTextInput.setError("Please enter some input");
//            return;
//        }
//        int qrCodeDimension = 500; // The size of the QR code image
//
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        try {
//            Map<EncodeHintType, Object> hints = new HashMap<>();
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, qrCodeDimension, qrCodeDimension, hints);
//            int width = bitMatrix.getWidth();
//            int height = bitMatrix.getHeight();
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
//                }
//            }
//            imageViewQRCode.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }


//        return null;
    }
}