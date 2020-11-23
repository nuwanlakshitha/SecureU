package com.nuwan.secureu;

import android.accessibilityservice.AccessibilityService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOW")){
            AccessibilityNodeInfo nodeInfo = event.getSource();
            dfs(nodeInfo);
        }
    }

//    public void getChromeUrl(AccessibilityNodeInfo nodeInfo) {
//        //Use the node info tree to identify the proper content.
//        //For now we'll just log it to logcat.
//        Log.d(TAG, toStringHierarchy(nodeInfo, 0));
//    }

//    private String toStringHierarchy(AccessibilityNodeInfo info, int depth) {
//        if (info == null) return "";
//
//        String result = "|";
//        for (int i = 0; i < depth; i++) {
//            if (result.contains("http")) {
//                Log.d(TAG, "Found URL!!!!!!!!!!!!!!" + result);
//            }
//            result += "  ";
//        }
//
//        result += info.toString();
//
//        for (int i = 0; i < info.getChildCount(); i++) {
//            result += "\n" + toStringHierarchy(info.getChild(i), depth + 1);
//        }
//
//        return result;
//    }
//    private static void debug(Object object) {
//        Log.d(TAG, object.toString());
//    }

    public void dfs(AccessibilityNodeInfo info) {
        if (info == null)
            return;
        if (info.getText()!=null && info.getText().toString().contains("http") && info.getText().length() > 0 && info.getClassName().toString().contains("EditText")) {
            //List<String> urls = HomeActivity.database.dao().getUrls();
            //if(urls.contains(info.getText().toString())) {
            Database db = Room.databaseBuilder(getApplicationContext(),Database.class,"database").allowMainThreadQueries().build();
            List<String> urls = db.dao().getUrls();
            if (urls.contains(info.getText().toString())) {
                System.out.println(info.getText() + " class: " + info.getClassName());
                final Intent dialogIntent = new Intent(this, FingerprintActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                new CountDownTimer(1000, 100) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        startActivity(dialogIntent);
                    }
                }.start();
                // }
            }
            for (int i = 0; i < info.getChildCount(); i++) {
                AccessibilityNodeInfo child = info.getChild(i);
                dfs(child);
                if (child != null) {
                    child.recycle();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
