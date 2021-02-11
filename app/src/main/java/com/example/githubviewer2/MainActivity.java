package com.example.githubviewer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        EditText txtName = (EditText) findViewById(R.id.txtName);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String requisicao = new HttpService(txtName.getText().toString()).execute().get();
                    Intent novaTela = new Intent(MainActivity.this, ProfileDetail.class);
                    novaTela.putExtra("JSON", requisicao);
                    startActivity(novaTela);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}