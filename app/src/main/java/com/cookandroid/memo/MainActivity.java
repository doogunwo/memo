package com.cookandroid.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    myDB myHelper;
    SQLiteDatabase sqlDB;

    ListView memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("내 메모");
        myHelper = new myDB(this);
        sqlDB = myHelper.getWritableDatabase();
        memo = (ListView) findViewById(R.id.memo);
        final String[] mid ={};
        ArrayList<String> memoDetails;
        //
        memoDetails = getMemo();

        memo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //클릭되면 보임
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                String index = Integer.toString(i);
                intent.putExtra("index",index);
                startActivity(intent);

            }
        });

        //리스트뷰 표시하는 부분


        //---여기까지


    }
    public ArrayList<String> getMemo(){
        ArrayList<String> List = new ArrayList<>();

        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, created_at FROM memo20203000", null);

        while(cursor.moveToNext()){
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String details = "제목: " + title + "\n" + "작성 시간: " + createdAt;
            List.add(details);
        }
        cursor.close();
        System.out.println(List);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,List);
        memo.setAdapter(adp);
        return List;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId(); // 변수로 값을 받아옴
        if(itemId==R.id.make){
            createNewMemo();
        }
        if(itemId==R.id.info){
            showDeveloperInfo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewMemo(){

        Toast.makeText(this,"메모 작성 누름",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), newmemo.class);
        startActivity(intent);

    }

    public void showDeveloperInfo(){
        Toast.makeText(this,"개발자 정보 누름",Toast.LENGTH_SHORT).show();

        Dialog dialog = new Dialog(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView devInfo = new TextView(this);
        devInfo.setText("개발자 정보");
        devInfo.setTextSize(20);
        devInfo.setPadding(20, 20, 20, 20);
        devInfo.setTextColor(Color.BLACK);

        // 동의대학교 컴퓨터공학과 도건우
        TextView nameInfo = new TextView(this);
        nameInfo.setText("동의대학교 컴퓨터공학과 도건우");
        nameInfo.setTextSize(16);
        nameInfo.setPadding(20, 20, 20, 20);
        nameInfo.setTextColor(Color.BLACK);

        // 이메일
        TextView emailInfo = new TextView(this);
        emailInfo.setText("20203000@office.deu.ac.kr");
        emailInfo.setTextSize(16);
        emailInfo.setPadding(20, 20, 20, 20);
        emailInfo.setTextColor(Color.BLACK);

        //확인 버튼
        Button closeButton = new Button(this);
        closeButton.setText("확인");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 팝업을 닫는 기능 추가
            }
        });

        layout.addView(devInfo);
        layout.addView(nameInfo);
        layout.addView(emailInfo);
        layout.addView(closeButton);
        dialog.setContentView(layout);
        dialog.show();

    }

}

