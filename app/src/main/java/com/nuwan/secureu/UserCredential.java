package com.nuwan.secureu;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.crypto.EncryptedPrivateKeyInfo;

/**
 * Created by nuwan on 05/26/2019.
 */


@Entity
public class UserCredential {
    @PrimaryKey @NonNull
    private String url;
    @ColumnInfo
    private String username;
    @ColumnInfo
    private String password;

    public UserCredential(String url, String username, String password){
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return decrypt(password);
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    public String encrypt(String password){
        String encryptedPassword = "";
        String[] alpha = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String[] encryption  = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        ArrayList<String> alphabet = new ArrayList<>();
        ArrayList<String> encryptionSchema = new ArrayList<>();
        for (String c:alpha){
            alphabet.add(c);
        }
        for (String c:encryption){
            encryptionSchema.add(c);
        }
        for(int i=0;i<password.length();i++){
            encryptedPassword += encryptionSchema.get(alphabet.indexOf(password.substring(i,i+1)));
        }
        return encryptedPassword;
    }


    public String decrypt(String encryptedPassword){
        String password = "";
        String[] alpha = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String[] encryption  = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        ArrayList<String> alphabet = new ArrayList<>();
        ArrayList<String> encryptionSchema = new ArrayList<>();
        for (String c:alpha){
            alphabet.add(c);
        }
        for (String c:encryption){
            encryptionSchema.add(c);
        }
        for(int i=0;i<encryptedPassword.length();i++){
            password += alphabet.get(encryptionSchema.indexOf(encryptedPassword.substring(i,i+1)));
        }
        return password;
    }
}

