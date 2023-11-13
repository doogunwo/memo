package com.cookandroid.memo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    TextView title;
    TextView content;
    TextView timestamp;

    TextView timstamp_modify_tag;
    SQLiteDatabase sqlDB;
    Button modify;
    Button delete;
    Button finish;
    LinearLayout noshow;
    LinearLayout noshow_support;

    myDB myHelper;

    @SuppressLint("Range")
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmemo);
        myHelper = new myDB(this);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        timestamp =(TextView)findViewById(R.id.timestamp);
        timstamp_modify_tag = (TextView) findViewById(R.id.timestamp_modify);
        noshow = (LinearLayout) findViewById(R.id.noshow);
        noshow_support = (LinearLayout) findViewById(R.id.noshow_support);
        Intent intent = getIntent();

        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        modify = (Button) findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getStringExtra("index");
                String id = intent.getStringExtra("id");
                String pos = String.valueOf(Integer.parseInt(index)+1);
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("index",pos);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getStringExtra("index");
                String pos = String.valueOf(Integer.parseInt(index)+1);

                String id = intent.getStringExtra("id");

                AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this);
                String text= "이 메모("+pos+")번을 삭제하시겠습니까?";
                builder.setMessage(text);
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 삭제 작업 수행
                        Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_SHORT).show();
                        String sql = "DELETE from memo20203000 where id=?";
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL(sql,new String[]{id});
                        sqlDB.close();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        updateView(intent);
    }
    @SuppressLint("Range")
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        updateView(intent);
    }

    @SuppressLint("Range")
    private void updateView(Intent intent){
        String index = intent.getStringExtra("index");
        String pos = String.valueOf(Integer.parseInt(index)+1);
        setTitle(pos+"번 메모 ");

        String id = intent.getStringExtra("id");

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        String sql = "SELECT title, content, created_at,last_modified from memo20203000 where id="+id;

        String title_v;
        String content_v;
        String timestamp_v;
        String timestamp_modify;

        cursor = sqlDB.rawQuery(sql,null);
        // id는 기본키라 1개뿐 그래서 while 문으로 불필요한 순회x
        if(cursor != null && cursor.moveToFirst()){
            title_v = cursor.getString(cursor.getColumnIndex("title"));
            content_v = cursor.getString(cursor.getColumnIndex("content"));
            timestamp_v = cursor.getString(cursor.getColumnIndex("created_at"));
            timestamp_modify = cursor.getString(cursor.getColumnIndex("last_modified"));

            if(timestamp_v.equals(timestamp_modify)){//그대로 출력한다.
                //수정 안한거는 noshow, noshow_support
                // noshow 레이아웃 =  gone
                // noshow_support = 비지빌리티
                title.setText(title_v);
                content.setText(content_v);
                timestamp.setText(timestamp_v);
                timstamp_modify_tag.setText(timestamp_modify);
                noshow.setVisibility(View.GONE);
            }
            else{//Visible = 비지빌리티, 1번 수정이 되었다는 의미
                //noshow.setVisibility(View.VISIBLE);
                //노쇼 서포트는 gone하면 높이가 맞을듯
                title.setText(title_v);
                content.setText(content_v);
                timestamp.setText(timestamp_v);
                timstamp_modify_tag.setText(timestamp_modify);
                noshow.setVisibility(View.VISIBLE);
                noshow_support.setVisibility(View.GONE);
            }

        }
        cursor.close();

    }

}
