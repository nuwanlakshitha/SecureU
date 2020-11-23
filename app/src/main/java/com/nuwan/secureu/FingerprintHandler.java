package com.nuwan.secureu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nuwan on 05/22/2019.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{
    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void auth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("Authentication Error: "+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication Failed!",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication Succeeded!",true);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(500);
                    Intent i = new Intent(context, HomeActivity.class);
                    context.startActivity(i);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update(String s, boolean b) {
        TextView txtMessage = ((Activity)context).findViewById(R.id.txtMsg);
        ImageView image = ((Activity)context).findViewById(R.id.image);
        txtMessage.setText(s);
        if(b){
            txtMessage.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            image.setImageResource(R.mipmap.done);
        }else {
            txtMessage.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }
    }
}
