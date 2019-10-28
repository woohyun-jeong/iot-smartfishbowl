package com.example.i411_10.smart_fish;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView list;
    ListViewAdapter adapter; //리스트 뷰 연결 어뎁터
    EditText editsearch; //글 입력 에디터 텍스트
    String[] fish_temp;
    String[] fish_name;
    ArrayList<Fish> arraylist = new ArrayList<>(); //모든 데이터 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //테스트 데이터
        fish_temp = new String[]{"24~28도", "22~24도", "24~26도", "27~32도", "24~28도", "25~27도", "26~28도"};
        fish_name = new String[]{"구피", "금붕어", "니그로", "디스커스", "엔젤 피쉬", "엘리펀트 노즈", "피라냐"};

        list = (ListView) findViewById(R.id.listview);

        //1. ArrayList에 모든 데이터 저장
        for (int i = 0; i < fish_name.length; i++) {
            Fish f = new Fish(fish_temp[i], fish_name[i]);
            arraylist.add(f);
        }
        //2. Adapter를 호출 리스트 내용을 어뎁터를 사용 xml에 출력
        adapter = new ListViewAdapter(this, arraylist);
        list.setAdapter(adapter);

        //텍스트 자동완성
        editsearch = (EditText) findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable arg0) {
                //값을 스트링으로 변환 후
                String text = editsearch.getText().toString();
                //필터 함수를 통해 리스트에 자동완성 기능으로 보여준다
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
        });
    }
}
