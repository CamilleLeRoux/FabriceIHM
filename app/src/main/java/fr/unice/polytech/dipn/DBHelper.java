package fr.unice.polytech.dipn;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30/04/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DIPN_database";
    public static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + "INCIDENT" + " (" +
                    "ID INTEGER PRIMARY KEY," +
                    "AUTHOR VARCHAR(20)," +
                    "TITLE VARCHAR(50)," +
                    "DESCRIPTION TEXT," +
                    "ADVANCEMENT INT," +
                    "IMPORTANCE INT," +
                    "LATITUDE DOUBLE" +
                    "LONGITUDE DOUBLE" +
                    "DATE VARCHAR(50)" +
                    ")";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + "INCIDENT";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
