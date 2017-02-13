package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WritingManager extends AppCompatActivity implements View.OnClickListener{

    Button drawingBtn, imageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_manager);
        drawingBtn = (Button)findViewById(R.id.drawingBtn);
        imageBtn  = (Button)findViewById(R.id.imageBtn);
        drawingBtn.setOnClickListener(this);
        imageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.drawingBtn:
                startActivity(new Intent(this, WritingDrawingManager.class));
                break;
            case R.id.imageBtn:
                startActivity(new Intent(this, WritingImageManager.class));
                break;
        }
    }
}
