package com.example.benz.raconte_moi;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReadingManager extends AppCompatActivity {

    Button btnImage, btnDrawing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_manager);
        btnImage= (Button) findViewById(R.id.btnImage);
        btnDrawing= (Button) findViewById(R.id.btnDessin);

        btnDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadingManager.this, ChoiceDrawing.class);
                startActivity(intent);

            }
        });
    }


}






