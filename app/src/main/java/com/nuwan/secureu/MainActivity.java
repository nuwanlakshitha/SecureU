package com.nuwan.secureu;

import android.Manifest;
import android.app.KeyguardManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private TextView txtMessage;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),Database.class,"database").allowMainThreadQueries().build();

        Intent intent = new Intent(getApplicationContext(),MyAccessibilityService.class);
        startService(intent);

        txtMessage = findViewById(R.id.txtMsg);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if(fingerprintManager.isHardwareDetected()){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED){
                    if(keyguardManager.isKeyguardSecure()){
                        if(fingerprintManager.hasEnrolledFingerprints()){
                            txtMessage.setText("Place your fingerprint on scanner!");
                            FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                            fingerprintHandler.auth(fingerprintManager,null);
                        }else{
                            txtMessage.setText("Add at least one fingerprint!");
                        }
                    }else{
                        txtMessage.setText("Make a lock for your device!");
                    }
                }else{
                    txtMessage.setText("Permission not granted to use fingerprint!");
                }
            }else{
                txtMessage.setText("Fingerprint scanner is not detected!");
            }
        }else{
            txtMessage.setText("Your device does not support this app!");
        }/*

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if(!fingerprintManager.isHardwareDetected()){
                txtMessage.setText("Fingerprint Scanner is not detected!");
            }else if(ContextCompat.checkSelfPermission(this,Manifest.permission.USE_FINGERPRINT)!=PackageManager.PERMISSION_GRANTED){
                txtMessage.setText("Permission not granted to use fingerprint!");
            }else if(!keyguardManager.isKeyguardSecure()){
                txtMessage.setText("Make a lock!");
            }else if(!fingerprintManager.hasEnrolledFingerprints()){
                txtMessage.setText("Add atleast one fingerprint!");
            }else{
                txtMessage.setText("Place fingerprint!");
            }
        }*/

    }

}
