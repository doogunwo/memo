package com.a20203000.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*

 *개인프로젝트:   myDB.java
 *개발자:  컴퓨터공학과 20203000 도건우
 *20203000@office.deu.ac.kr

 */


public class myDB extends SQLiteOpenHelper {
    public myDB(Context context) {
        super(context, "prj20203000.db", null, 1);
    }
    // prj 2020 3000 db 를 만들기
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE memo20203000 (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    title TEXT,\n" +
                "    content TEXT,\n" +
                "    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    last_modified DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                ");\n");
    }//테이블은 memo 2020 3000 으로 만들고

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS memo20203000");
        onCreate(sqLiteDatabase);
        //있으면 삭제하라  초기실행할때는 상관없음.
    }


}
