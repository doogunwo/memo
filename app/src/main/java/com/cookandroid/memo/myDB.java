package com.cookandroid.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class myDB extends SQLiteOpenHelper {
    public myDB(Context context) {
        super(context, "memo20203000", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE memo20203000 (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    title TEXT,\n" +
                "    content TEXT,\n" +
                "    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    last_modified DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                ");\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS memo20203000");
        onCreate(sqLiteDatabase);
    }


}
