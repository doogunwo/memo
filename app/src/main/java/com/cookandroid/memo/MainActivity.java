package com.cookandroid.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("내 메모");
        //제목 변경

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
    }

    public void showDeveloperInfo(){
        Toast.makeText(this,"개발자 정보 누름",Toast.LENGTH_SHORT).show();
    }


}