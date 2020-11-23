package com.nuwan.secureu;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FingerprintActivity extends AppCompatActivity {
    private TextView fingerprintMessage;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        fingerprintMessage = findViewById(R.id.fingerprintMessage);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if(fingerprintManager.isHardwareDetected()){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED){
                    if(keyguardManager.isKeyguardSecure()){
                        if(fingerprintManager.hasEnrolledFingerprints()){
                            fingerprintMessage.setText("Place your fingerprint on scanner!");
                            FprintHandler fprintHandler = new FprintHandler(this);
                            fprintHandler.auth(fingerprintManager,null);
                        }else{
                            fingerprintMessage.setText("Add at least one fingerprint!");
                        }
                    }else{
                        fingerprintMessage.setText("Make a lock for your device!");
                    }
                }else{
                    fingerprintMessage.setText("Permission not granted to use fingerprint!");
                }
            }else{
                fingerprintMessage.setText("Fingerprint scanner is not detected!");
            }
        }else{
            fingerprintMessage.setText("Your device does not support this app!");
        }
    }
}
