package com.cookandroid.memo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class newmemo extends Activity {


    Button finish_button;
    Button record_button;

    EditText title;
    EditText content;

    SQLiteDatabase sqlDB;
    myDB myHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmemo);
        setTitle("새 메모 작성");
        finish_button = (Button)findViewById(R.id.cancle);
        record_button = (Button)findViewById(R.id.memo_record);
        myHelper = new myDB(this);
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = (EditText) findViewById(R.id.title_value);
                content = (EditText) findViewById(R.id.content_value);

                String titleValue = title.getText().toString();
                String contentValue = content.getText().toString();

                sqlDB = myHelper.getWritableDatabase();

                String sql = "INSERT INTO memo20203000 (title, content) VALUES (?, ?)";
                sqlDB.execSQL(sql,new String[]{titleValue,contentValue});
                sqlDB.close();

                Toast.makeText(getApplicationContext(), "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
