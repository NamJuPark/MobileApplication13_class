package com.example.mobileapplication13_class;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main3Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<String>data = new ArrayList<String >();
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                Log.d("남주","49");
                URL url = new URL("https://news.google.com/news?cf=all&hl=ko&pz=1&ned=kr&topic=m&output=rss");
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                Log.d("남주","52");
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.d("남주","54");
                    int itemCount = readData(urlConnection.getInputStream());
                    Log.d("남주","56");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("남주","61");
                            adapter.notifyDataSetChanged();
                            Log.d("남주","62");
                        }
                    });
                    Log.d("남주","65");
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private int readData(InputStream inputStream) {//parsing할 수 있도록 읽어온다?
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            int datacount = parseDocument(document);
            return datacount;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseDocument(Document document) {

        Element docEle = document.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if((nodelist != null) && (nodelist.getLength() > 0)){
            for(int i = 0; i < nodelist.getLength(); i++){
                String newsItem = getTagData(nodelist,i);
                if(newsItem != null){
                    data.add((newsItem));
                    count++;
                }
            }
        }
        return count;
    }

    private String getTagData(NodeList nodelist, int i) {
        String newsItem = null;
        try{
            Element entry = (Element)nodelist.item(i);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            if(title != null){
                Node firstChild = title.getFirstChild();
                if(firstChild != null) titleValue = firstChild.getNodeValue();
            }

            String pubDateValue = null;
            if(pubDate != null){
                Node firstChild = pubDate.getFirstChild();
                if(firstChild != null) pubDateValue = firstChild.getNodeValue();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = new Date();
            newsItem = titleValue+"-"+simpleDateFormat.format(date.parse(pubDateValue));
        }
        catch (DOMException e){
            e.printStackTrace();
        }
        return  newsItem;
    }

    public void onClick(View view){
        thread.start();
    }
}
