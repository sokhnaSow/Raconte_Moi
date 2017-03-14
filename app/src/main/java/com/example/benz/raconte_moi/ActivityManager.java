package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityManager extends AppCompatActivity implements View.OnClickListener {

    Button writingBtn, readingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        writingBtn = (Button)findViewById(R.id.writingBtn);
        readingBtn  = (Button)findViewById(R.id.readingBtn);
        writingBtn.setOnClickListener(this);
        readingBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.writingBtn:
                Intent intent2 = getIntent();
                String idChild = intent2.getStringExtra("idChild");

                Intent intent = new Intent(this, WritingManager.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);

                break;
            case R.id.readingBtn:

                startActivity(new Intent(this, ReadingManager.class));
                break;
        }
    }
}
