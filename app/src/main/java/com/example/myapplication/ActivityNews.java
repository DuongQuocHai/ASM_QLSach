package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ActivityNews extends AppCompatActivity {
    ListView lvTieuDe;
    ArrayList<String> arrayTitle,arrayLink;
    ArrayAdapter adapter;
    String link;
    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        imageButton = findViewById(R.id.btn_backnews);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
            }
        });

        new ReadRSS().execute("https://vnexpress.net/rss/khoa-hoc.rss");

        lvTieuDe = findViewById(R.id.listviewTieuDe);
        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayTitle);
        lvTieuDe.setAdapter(adapter);
        lvTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityNews.this,ActivityNewsDetail.class);
                intent.putExtra("link",arrayLink.get(i));
                startActivity(intent);
            }
        });




    }
    private class ReadRSS extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String tieuDe = "";
            String ngay = "";
            for (int i =0;i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                tieuDe  = parser.getValue(element,"title" ) ;
                arrayTitle.add(tieuDe );
                arrayLink.add(parser.getValue(element,"link"));
            }
            adapter.notifyDataSetChanged();
         }
    }

}
