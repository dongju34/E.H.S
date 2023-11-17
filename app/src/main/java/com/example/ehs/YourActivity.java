package com.example.ehs;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// YourActivity.java
public class YourActivity extends AppCompatActivity {
    private ListView incomeListView, expenseListView;
    private MyAdapter incomeAdapter, expenseAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calender);

        dbHelper = new DBHelper(this);

        // 수입 데이터 조회
        ArrayList<String> incomeDataList = dbHelper.getIncomeData();
        incomeListView = findViewById(R.id.listIncome);
        incomeAdapter = new MyAdapter(this, incomeDataList);
        incomeListView.setAdapter(incomeAdapter);

        // 지출 데이터 조회
        ArrayList<String> expenseDataList = dbHelper.getExpenseData();
        expenseListView = findViewById(R.id.listExpense);
        expenseAdapter = new MyAdapter(this, expenseDataList);
        expenseListView.setAdapter(expenseAdapter);
    }
}