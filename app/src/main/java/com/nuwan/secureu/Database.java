package com.nuwan.secureu;

import android.arch.persistence.room.RoomDatabase;

/**
 * Created by nuwan on 05/26/2019.
 */
@android.arch.persistence.room.Database(entities = UserCredential.class,version = 1)
public abstract class Database extends RoomDatabase {
    public abstract DAO dao();
}
