package com.nuwan.secureu;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTaskRoot()) {
            AlertDialog.Builder exitBuilder = new AlertDialog.Builder(this);
            exitBuilder.setMessage("Are you sure you want to exit?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(1);
                }
            }).setNegativeButton("No", null);
            AlertDialog alert = exitBuilder.create();
            alert.show();
        }
    }



    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        //database = Room.databaseBuilder(getApplicationContext(),Database.class,"database").allowMainThreadQueries().build();
        if(findViewById(R.id.container)!=null){
            if(savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.container,new ContainerFragment()).commit();
        }
    }

}
