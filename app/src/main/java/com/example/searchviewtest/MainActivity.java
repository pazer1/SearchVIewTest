package com.example.searchviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button partOneButton;
    private Button partTwoButton;
    private Button partThreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        partOneButton = (Button) findViewById(R.id.partOneButton);
        partTwoButton = (Button) findViewById(R.id.partTwoButton);
        partThreeButton = (Button) findViewById(R.id.partThreeButton);

        partOneButton.setOnClickListener(this);
        partTwoButton.setOnClickListener(this);
        partThreeButton.setOnClickListener(this);

    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        if(v.equals(PartThreeActivity.class)){
            Intent intent = new Intent(this,PartThreeActivity.class);
            startActivity(intent);
        }
    }
}
