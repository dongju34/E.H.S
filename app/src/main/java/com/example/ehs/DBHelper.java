package com.example.ehs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Account.db";
    private static final int DATABASE_VERSION = 2;
    // 첫 번째 테이블 (수입)
    private static final String TABLE_INCOME = "income";
    private static final String COL_ID_INCOME = "_id";
    private static final String COL_DATE_INCOME = "date";
    private static final String COL_CARD_INCOME = "card";
    private static final String COL_CLASSIFICATION_INCOME = "classification";
    private static final String COL_AMOUNT_INCOME = "amount";
    private static final String COL_CONTENT_INCOME = "content";

    // 두 번째 테이블 (지출)
    private static final String TABLE_EXPENSE = "expense";
    private static final String COL_ID_EXPENSE = "_id";
    private static final String COL_DATE_EXPENSE = "date";
    private static final String COL_CARD_EXPENSE = "card";
    private static final String COL_CLASSIFICATION_EXPENSE = "classification";
    private static final String COL_AMOUNT_EXPENSE = "amount";
    private static final String COL_CONTENT_EXPENSE = "content";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 수입 테이블 생성 쿼리
        String createIncomeTableQuery = "CREATE TABLE " + TABLE_INCOME + " (" +
                COL_ID_INCOME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_INCOME + " STRING, " +
                COL_CARD_INCOME + " STRING, " +
                COL_CLASSIFICATION_INCOME + " STRING, " +
                COL_AMOUNT_INCOME + " INTEGER, " +
                COL_CONTENT_INCOME + " STRING);";
        db.execSQL(createIncomeTableQuery);

        // 지출 테이블 생성 쿼리
        String createExpenseTableQuery = "CREATE TABLE " + TABLE_EXPENSE + " (" +
                COL_ID_EXPENSE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_EXPENSE + " STRING, " +
                COL_CARD_EXPENSE + " STRING, " +
                COL_CLASSIFICATION_EXPENSE + " STRING, " +
                COL_AMOUNT_EXPENSE + " INTEGER, " +
                COL_CONTENT_EXPENSE + " STRING);";
        db.execSQL(createExpenseTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }
    // 수입 데이터 추가 메서드
    public void addIncome(Date date, String card, String classification, int amount, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        cv.put(COL_DATE_INCOME, dateFormat.format(date));
        cv.put(COL_CARD_INCOME, card);
        cv.put(COL_CLASSIFICATION_INCOME, classification);
        cv.put(COL_AMOUNT_INCOME, amount);
        cv.put(COL_CONTENT_INCOME, content);

        try {
            long result = db.insert(TABLE_INCOME, null, cv);
            if (result == -1) {
                throw new Exception("Failed to insert data into income table.");
            }
            Toast.makeText(context, "Income Data Added Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MyDatabaseHelper", "Failed to insert data: " + e.getMessage());
        } finally {
            db.close();
        }
    }


    // 지출 데이터 추가 메서드
    public void addExpense(Date date, String card, String classification, int amount, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        cv.put(COL_DATE_EXPENSE, dateFormat.format(date));
        cv.put(COL_CARD_EXPENSE, card);
        cv.put(COL_CLASSIFICATION_EXPENSE, classification);
        cv.put(COL_AMOUNT_EXPENSE, amount);
        cv.put(COL_CONTENT_EXPENSE, content);

        try {
            long result = db.insert(TABLE_EXPENSE, null, cv);
            if (result == -1) {
                throw new Exception("Failed to insert data into expense table.");
            }
            Toast.makeText(context, "Expense Data Added Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MyDatabaseHelper", "Failed to insert data: " + e.getMessage());

        } finally {
            db.close();
        }
    }

    // 수입 데이터 조회 메서드
    public ArrayList<String> getIncomeData() {
        ArrayList<String> incomeData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME, null);

        while (cursor.moveToNext()) {
            String dateStr = cursor.getString(cursor.getColumnIndex("date"));
            Date date = null;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // 나머지 코드에서 date 객체를 사용할 수 있습니다.

            String card = cursor.getString(cursor.getColumnIndex("card"));
            String classification = cursor.getString(cursor.getColumnIndex("classification"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            String data = "Date: " + dateStr + ", Card: " + card + ", Classification: " + classification + ", Amount: " + amount + ", Content: " + content;
            incomeData.add(data);
        }

        cursor.close();
        db.close();

        return incomeData;
    }

    // 지출 데이터 조회 메서드
    public ArrayList<String> getExpenseData() {
        ArrayList<String> expenseData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE, null);

        while (cursor.moveToNext()) {
            String dateStr = cursor.getString(cursor.getColumnIndex("date"));
            Date date = null;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // 나머지 코드에서 date 객체를 사용할 수 있습니다.

            String card = cursor.getString(cursor.getColumnIndex("card"));
            String classification = cursor.getString(cursor.getColumnIndex("classification"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            String data = "Date: " + dateStr + ", Card: " + card + ", Classification: " + classification + ", Amount: " + amount + ", Content: " + content;
            expenseData.add(data);
        }

        cursor.close();
        db.close();

        return expenseData;
    }
}
