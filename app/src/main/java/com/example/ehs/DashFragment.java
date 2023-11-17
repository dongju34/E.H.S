package com.example.ehs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashFragment extends Fragment {

    private TextView textDay;
    private ImageButton btnLeft;
    private ImageButton btnRight;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash, container, false);

        textDay = view.findViewById(R.id.text_day);
        btnLeft = view.findViewById(R.id.btn_left);
        btnRight = view.findViewById(R.id.btn_right);
        button = view.findViewById(R.id.button);

        // text_day TextView의 클릭 리스너 설정
        textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDb = new DBHelper(getActivity());

                // dateStr, amountStr, content, classification 변수 선언 및 값 할당
                String dateStr = textDay.getText().toString();
                String amountStr = ""; // 적절한 값으로 초기화
                String content = ""; // 적절한 값으로 초기화
                String classification = ""; // 적절한 값으로 초기화

                if (dateStr.isEmpty() || amountStr.isEmpty() || content.isEmpty()) {
                    // 필수 입력값이 비어있을 경우 사용자에게 알림을 줄 수 있습니다.
                    Toast.makeText(getActivity(), "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 버튼의 태그를 읽어옴
                String tag = (String) v.getTag();

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                    Date date = sdf.parse(dateStr);
                    int amount = Integer.parseInt(amountStr);

                    // 수입인 경우
                    if ("income".equals(tag)) {
                        myDb.addIncome(date, dateStr, classification, amount, content);
                        Toast.makeText(getActivity(), "수입이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    // 지출인 경우
                    else if ("expense".equals(tag)) {
                        myDb.addExpense(date, dateStr, classification, amount, content);
                        Toast.makeText(getActivity(), "지출이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // 숫자 변환 오류 처리
                    Toast.makeText(getActivity(), "금액을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    // 날짜 변환 오류 처리
                    Toast.makeText(getActivity(), "날짜를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                // 사용이 끝난 데이터베이스 닫기
                myDb.close();
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

        // View button = findViewById(R.id.button); 및 클릭 리스너 설정 추가
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), add2.class); // getActivity()를 사용하여 액티비티를 가져옵니다.
                startActivity(intent);
            }
        });

        return view;
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
                requireContext(),
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
