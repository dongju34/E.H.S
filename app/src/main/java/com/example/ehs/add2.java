package com.example.ehs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add2 extends AppCompatActivity {

    private EditText edtDate, edtAmount, edtContent;
    private Button saveBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDate = findViewById(R.id.edtDate);
        edtAmount = findViewById(R.id.edtAmount);
        edtContent = findViewById(R.id.edtContent);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = edtDate.getText().toString();
                String amount = edtAmount.getText().toString();
                String content = edtContent.getText().toString();

                // 데이터 처리 로직을 작성하세요.
                // 예를 들어, 데이터를 저장하거나 다른 작업을 수행할 수 있습니다.

                Toast.makeText(add2.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
