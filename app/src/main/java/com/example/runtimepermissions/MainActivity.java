package com.example.runtimepermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CONTACTS = 1;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (LinearLayout)findViewById(R.id.main_layout);
        Button btnCamera = (Button)findViewById(R.id.btn_show_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
        Button btnContacts = (Button)findViewById(R.id.btn_show_contacts);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
            }
        });
    }


    private void showCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            showCameraInternal();
        }
    }
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(mLayout, "이 기능을 사용하려면 Camera permission이 필요합니다. 승인하려면 OK를 누르세요.",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }
    private void showCameraInternal() {
        showSnackbar("Camera permission은 이미 승인 받았고, Camera 관련 API 사용 가능합니다");
    }


    private void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermissions();
        } else {
            showContactsInternal();
        }
    }
    private void requestContactsPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {
                    Snackbar.make(mLayout, "이 기능을 사용하려면 Contact permission이 필요합니다. 승인하려면 OK를 누르세요.",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_CONTACT,
                                    REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }
    private void showContactsInternal() {
        showSnackbar("Contacts permission은 이미 승인받았고, Contact 관련 API 사용 가능합니다");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSnackbar("Camera permission을 승인받았습니다");
            } else {
                showSnackbar("Camera permission을 승인받지 못했습니다");
            }
        } else if (requestCode == REQUEST_CONTACTS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                showSnackbar("Contact permission을 모두 승인받았습니다");
            } else {
                showSnackbar("Contact permission을 모두 승인받지 못했습니다");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showSnackbar(String text) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);
        Snackbar.make(layout, text, Snackbar.LENGTH_SHORT).show();
    }


}
