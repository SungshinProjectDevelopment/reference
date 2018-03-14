package com.example.jisungkim.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.jisungkim.app.MainActivity.msgStack;

public class Date extends AppCompatActivity {
    public static String msgStack = "";
    Elements elements;
    ArrayList<String> textList;
    String text, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        Intent intent= getIntent();
        text=intent.getStringExtra("a1");
        text2=intent.getStringExtra("a2");
        text3=intent.getStringExtra("a3");
        text4=intent.getStringExtra("a4");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //상단 메뉴바 인플레이트 한다
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        메뉴아이템 누르면 넘어가는 인덴트 만들어야 함
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Date.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public class ResConnection extends AsyncTask<String, String, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            // Jsoup을 이용한 맛집 데이터 Parsing하기 try
            try{
                //성신여대 맛집,데이트맛집,가족맛집,친구맛집 조회url
                String path = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=성신여대+맛집";

                Document document = Jsoup.connect(path).get();
                elements = document.select("a.name[title]");

                System.out.println(elements);

                textList = new ArrayList<String>();

                for (int i=0; i < elements.size(); i++) {
                    textList.add(elements.get(i).attr("title").toString());
                }
                return textList;
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> textList) {
            super.onPostExecute(textList);

            for(int i = 0; i < textList.size(); i++) {
                msgStack += textList.get(i) + " ";
            }
            String[] t = msgStack.split(" ");

            Toast.makeText(getApplicationContext(), msgStack, Toast.LENGTH_LONG).show();

            ArrayList<String> array=new ArrayList<String>();

            text = t[0];

            array.add(text);
            array.add(text2);
            array.add(text3);
            array.add(text4);

            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);

            ListView list=(ListView)findViewById(R.id.list_item);
            //list.setAdapter(adapter);
        }
    }
}
