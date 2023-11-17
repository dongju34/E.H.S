package com.example.ehs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ehs.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private TextView textDay;
    private ImageButton btnLeft;
    private ImageButton btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dash);

        textDay = findViewById(R.id.text_day);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);

        // text_day TextView의 클릭 리스너 설정
        textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // btn_left 버튼의 클릭 리스너 설정
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate(-1); // -1은 하루 전을 의미
            }
        });

        // btn_right 버튼의 클릭 리스너 설정
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate(1); // 1은 하루 후를 의미
            }
        });

        // 이 부분에 주어진 코드를 추가합니다.
        // ...

    }

    // DatePickerDialog를 보여주는 메서드
    private void showDatePickerDialog() {
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 생성 및 리스너 설정
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 선택된 날짜 처리
                        String selectedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        updateTextView(selectedDate);
                    }
                },
                year, month, day);

        // 다이얼로그 표시
        datePickerDialog.show();
    }

    // 날짜 업데이트 메서드
    private void updateDate(int daysToAdd) {
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

        try {
            // 현재 날짜를 Date 객체로 파싱
            Date currentDate = sdf.parse(textDay.getText().toString());

            // daysToAdd에 따라 날짜를 조절
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

            // 업데이트된 날짜를 TextView에 설정
            String updatedDate = sdf.format(calendar.getTime());
            updateTextView(updatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // TextView 업데이트 메서드
    private void updateTextView(String date) {
        textDay.setText(date);
    }
}
