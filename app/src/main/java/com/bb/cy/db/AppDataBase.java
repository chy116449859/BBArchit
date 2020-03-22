package com.bb.cy.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bb.cy.dao.UserBean;
import com.bb.cy.dao.UserDao;

@Database(entities = {UserBean.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

    private static AppDataBase INSTANCE;
    private static final Object sLock = new Object();

    public static AppDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "user.db")
                                .build();
            }
            return INSTANCE;
        }
    }
}
