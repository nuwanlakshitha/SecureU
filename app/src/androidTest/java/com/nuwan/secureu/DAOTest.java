package com.nuwan.secureu;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nuwan on 05/30/2019.
 */
public class DAOTest {
    Database db;
    Context appContext;
    @Before
    public void init(){
        appContext = InstrumentationRegistry.getTargetContext();
        db = Room.databaseBuilder(appContext.getApplicationContext(),Database.class,"database").allowMainThreadQueries().build();
    }


    @Test
    public void addUserCredentials(){
        db.dao().addUserCredential(new UserCredential("fgf","bjb","dtdt"));
    }

    @Test
    public void viewUserCredential(){
        List<UserCredential> users = db.dao().viewUserCredential();
        List<UserCredential> expected = new ArrayList<>();
        expected.add(new UserCredential("https://gmail.com","nuwan","nuwanlak"));
        Assert.assertArrayEquals(expected.toArray(),users.toArray());
    }



    @Test
    public void getUrls() throws Exception {
        List<String> users = db.dao().getUrls();
        List<String> expected = new ArrayList<>();
        expected.add("https://google.com");
        Assert.assertArrayEquals(expected.toArray(),users.toArray());
    }

    @Test
    public void editUserCredential() throws Exception {
        db.dao().editUserCredential(new UserCredential("https://instagram.com","nuwan","laknuw"));
    }

    @Test
    public void deleteUserCredential() throws Exception {
        db.dao().deleteUserCredential(new UserCredential("https://google.com","nuwa","laknuwan"));
    }

}