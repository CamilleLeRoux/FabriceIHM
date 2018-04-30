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

    private static String DB_NAME = "DIPN_database";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + DB_NAME + " (" +
                    "ID INTEGER PRIMARY KEY," +
                    "AUTHOR VARCHAR(20)," +
                    "TITLE VARCHAR(50)," +
                    "DESCRIPTION TEXT," +
                    "ADVANCEMENT INT," +
                    "IMPORTANCE INT," +
                    "LATITUDE DOUBLE" +
                    "LONGITUDE DOUBLE" +
                    "DATE DATE" +
                    ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
