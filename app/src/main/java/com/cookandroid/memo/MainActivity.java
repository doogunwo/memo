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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    myDB myHelper;
    SQLiteDatabase sqlDB;

    ListView memo;

    Button searchButton;

    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("내 메모");
        myHelper = new myDB(this);
        sqlDB = myHelper.getWritableDatabase();
        memo = (ListView) findViewById(R.id.memo);

    }

    protected  void onResume(){
        super.onResume();
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchButton = (Button) findViewById(R.id.searchButton);
        ArrayList<ArrayList<String>> memoID;
        String swch = searchEditText.getText().toString();
        memoID = getMemo(swch);

        ArrayAdapter<String> adp = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, memoID.get(1));
        memo.setAdapter(adp);

        memo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //클릭되면 보임
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                String index = Integer.toString(i);
                intent.putExtra("index",index);//memoID.get(1)
                intent.putExtra("id",memoID.get(0).get(i));
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"검색 버튼 누름",Toast.LENGTH_SHORT).show();
                //검색되면 -> like 출력
                //검색어없을때 -> 전부 다 출력
                //검색어있지만 like없을때 - > 검색결과없음 출력
                String swch = searchEditText.getText().toString();
                ArrayList<ArrayList<String>> memoID;
                memoID = getMemo(swch);

                ArrayAdapter<String> adp = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, memoID.get(1));
                memo.setAdapter(adp);

                memo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //클릭되면 보임
                        Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                        String index = Integer.toString(i);
                        intent.putExtra("index",index);//memoID.get(1)
                        intent.putExtra("id",memoID.get(0).get(i));
                        startActivity(intent);
                    }
                });




            }
        });


    }


    public ArrayList<ArrayList<String>> getMemo(String searchWord) {
        ArrayList<String> List = new ArrayList<>(); // 제목 표시용
        ArrayList<String> List2 = new ArrayList<>(); // id 전달용

        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor;

        if (searchWord.isEmpty()) {
            cursor = db.rawQuery("SELECT title, created_at, id FROM memo20203000", null);
        } else {
            String sql = "SELECT title, created_at, id FROM memo20203000 WHERE title LIKE '%" + searchWord + "%'";
            cursor = db.rawQuery(sql, null);
        }

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            String details1 = "제목: " + title + "\n" + "작성 시간: " + createdAt;
            List.add(details1);
            List2.add(id);
        }
        cursor.close();
        ArrayList<ArrayList<String>> Listset = new ArrayList<>();
        Listset.add(List2);
        Listset.add(List);

        return Listset;
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

