package com.nuwan.secureu;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by nuwan on 05/26/2019.
 */

@Dao
public interface DAO {

    @Insert
    public void addUserCredential(UserCredential userCredential);

    @Query("select * from usercredential")
    public List<UserCredential> viewUserCredential();

    @Query("select url from usercredential")
    public List<String> getUrls();

    @Update
    public void editUserCredential(UserCredential userCredential);

    @Delete
    public void deleteUserCredential(UserCredential userCredential);
}
