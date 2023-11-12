package com.cookandroid.memo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    TextView timestamp;
    SQLiteDatabase sqlDB;
    Button modify;
    Button delete;
    Button finish;

    myDB myHelper;

    @SuppressLint("Range")
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmemo);
        myHelper = new myDB(this);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        timestamp =(TextView)findViewById(R.id.timestamp);


        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            String index = intent.getStringExtra("index");
            String pos = String.valueOf(Integer.parseInt(index)+1);
            setTitle(pos+"번 메모 ");

            sqlDB = myHelper.getReadableDatabase();
            Cursor cursor;
            String sql = "SELECT title, content, created_at from memo20203000 where id="+pos;

            String title_v;
            String content_v;
            String timestamp_v;

            cursor = sqlDB.rawQuery(sql,null);

            if(cursor != null && cursor.moveToFirst()){
                title_v = cursor.getString(cursor.getColumnIndex("title"));
                content_v = cursor.getString(cursor.getColumnIndex("content"));
                timestamp_v = cursor.getString(cursor.getColumnIndex("created_at"));
                System.out.println(title_v+content_v+timestamp_v);

                title.setText(title_v);
                content.setText(content_v);
                timestamp.setText(timestamp_v);
            }
            cursor.close();
        }
    }

}
