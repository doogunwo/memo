package com.a20203000.memo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.a20303000.memo.R;

/*

 *개인프로젝트:  ModifyActivity.java
 *개발자:  컴퓨터공학과 20203000 도건우
 *20203000@office.deu.ac.kr

 */
public class ModifyActivity extends AppCompatActivity {

    EditText title;
    EditText content;

    Button modify;
    Button finish;

    myDB myHelper;

    SQLiteDatabase sqlDB;

    @SuppressLint("Range")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifymemo);
        Intent intent = getIntent();
        myHelper = new myDB(this);

        String index = intent.getStringExtra("index");
        String id = intent.getStringExtra("id");
        String pos = String.valueOf(Integer.parseInt(index));
        setTitle(pos+"번 메모 수정");
        
        //변수 선언
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        /*
            if(cursor != null && cursor.moveToFirst()){

        cursor != null은 cursor 객체가 비어있지 않은지 확인하는 것이고, cursor.moveToFirst()는 커서를 첫 번째 행으로 이동시키려고 시도하며,
        이 작업이 성공하면 true를 반환합니다.

        따라서, 이 코드는 cursor 객체가 비어있지 않고, 첫 번째 행으로 올바르게 이동할 수 있는
        경우를 조건으로 하는 작업을 수행할 때 사용됩니다.

         */
        // 여기는 가져오기 sql문 select
        if(intent != null){
            sqlDB = myHelper.getWritableDatabase();

            String title_v;
            String content_v;

            String sql1 = "SELECT title, content from memo20203000 where id="+id;
            Cursor cursor = sqlDB.rawQuery(sql1,null);

            if(cursor != null && cursor.moveToFirst()) {
                title_v = cursor.getString(cursor.getColumnIndex("title"));
                content_v = cursor.getString(cursor.getColumnIndex("content"));

                title.setText(title_v);
                content.setText(content_v);
            }
        }

        //여기부터는 수정하기 수정하면 시간도 업데이트됨
        modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                String text= "정말  이 메모("+pos+")번을 수정하시겠습니까?";
                builder.setMessage(text);

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 수정 하기
                        String getTitle;
                        String getContent;

                        getTitle = title.getText().toString();
                        getContent = content.getText().toString();
                        Toast.makeText(getApplicationContext(),"modify",Toast.LENGTH_SHORT).show();
                        String sql = "UPDATE memo20203000 SET title=?, content=?, last_modified=CURRENT_TIMESTAMP WHERE id=?";

                        sqlDB.execSQL(sql,new String[]{getTitle,getContent,id});
                        sqlDB.close();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                String text= "현재 수정하는 메모를 취소하시겠습니까?";
                builder.setMessage(text);

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }


}
