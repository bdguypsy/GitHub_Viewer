package com.example.githubviewer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent novaTela = getIntent();
        String json = novaTela.getStringExtra("JSON");

        try {
            JSONArray jsonArray = new JSONArray(json);
            List<Map<String, String>> listaDados = new ArrayList<Map<String, String>>();
            String owner;
            String name = null;
            String urlImage = null;
            for(int x = 0; x < jsonArray.length(); x++){
                JSONObject jsonObject = jsonArray.getJSONObject(x);
                Map<String, String> dados = new HashMap<String, String>(2);
                dados.put("repoName", jsonObject.getString("name"));
                dados.put("language", jsonObject.getString("language"));
                listaDados.add(dados);
                owner = jsonObject.getString("owner");
                JSONObject ownerObject = new JSONObject(owner);
                name = ownerObject.getString("login");
                urlImage = ownerObject.getString("avatar_url");
            }
            //ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
            TextView txtName = (TextView) findViewById(R.id.txtName);
            ListView listaRepositorios = (ListView) findViewById(R.id.listaRepositorios);
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listaDados, android.R.layout.simple_list_item_2, new String[] {"repoName", "language"}, new int[] {android.R.id.text1, android.R.id.text2});
            //profileImage.setImageBitmap(getImageBitmap(urlImage));
            txtName.setText(name);
            listaRepositorios.setAdapter(simpleAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}