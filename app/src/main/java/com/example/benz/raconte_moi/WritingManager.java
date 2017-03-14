package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class WritingManager extends AppCompatActivity implements View.OnClickListener{

    ImageButton drawingBtn, imageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_manager);
        drawingBtn = (ImageButton)findViewById(R.id.drawingBtn);
        imageBtn  = (ImageButton)findViewById(R.id.imageBtn);
        drawingBtn.setOnClickListener(this);
        imageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent2;
        String idChild;
        Intent intent;
        switch (view.getId()){
            case R.id.drawingBtn:
               intent2 = getIntent();
                 idChild = intent2.getStringExtra("idChild");

               intent = new Intent(this, WritingDrawingManager.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);
                break;
            case R.id.imageBtn:
                intent2 = getIntent();
                idChild = intent2.getStringExtra("idChild");
                 intent = new Intent(this, WritingImageManager.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);

                break;
        }
    }
}
