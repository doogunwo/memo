package com.cookandroid.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    myDB myHelper;
    SQLiteDatabase sqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("내 메모");
        myHelper = new myDB(this);
        sqlDB = myHelper.getWritableDatabase();

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

