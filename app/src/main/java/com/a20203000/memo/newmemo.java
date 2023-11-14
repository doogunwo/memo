package com.a20203000.memo;

import android.app.ActionBar;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a20303000.memo.R;

/*

 *개인프로젝트:  newmemo.java
 *개발자:  컴퓨터공학과 20203000 도건우
 *20203000@office.deu.ac.kr

 */

public class newmemo extends AppCompatActivity {


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
