package com.example.ch_iot;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class StatisActivity extends AppCompatActivity {

    private int frequency; // 음주 빈도
    private int average; // 평균 주량
    private String gender;

    private Button statisBtn1;
    private Button statisBtn2;
    private TextView statisText;
    private LineChart statisLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statis);

        statisBtn1 = findViewById(R.id.statis_btn1);
        statisBtn2 = findViewById(R.id.statis_btn2);
        statisText = findViewById(R.id.statis_text);
        statisLineChart = findViewById(R.id.statis_chart);

        SharedPreferences sharedPreferences = getSharedPreferences("TempPrefs", MODE_PRIVATE);
        gender = sharedPreferences.getString("gender", null);
        frequency = sharedPreferences.getInt("fre", 0);
        average = sharedPreferences.getInt("aver", 0);

        statisBtn1.setOnClickListener(v -> {
            statisLineChart.setVisibility(View.VISIBLE);
            statisLineChart.invalidate();
            setBtn1(gender, average);
        });

        statisBtn2.setOnClickListener(v -> {
            statisLineChart.setVisibility(View.VISIBLE);
            statisLineChart.invalidate();
            setBtn2(gender, frequency);
        });

    }

    public void setBtn1(String gender, int average) { // 음주 정도
        statisText.setText("음주 정도 통계");

        List<Entry> amtMan = new ArrayList<>();
        amtMan.add(new Entry(1.5F, 14.9F));
        amtMan.add(new Entry(3.5F, 26.2F));
        amtMan.add(new Entry(5.5F, 21.3F));
        amtMan.add(new Entry(9F, 17.9F));
        amtMan.add(new Entry(11F, 19.8F));

        LineDataSet dataSet1 = new LineDataSet(amtMan, "남성");
        dataSet1.setColor(Color.rgb(103, 132, 145));
        dataSet1.setValueTextColor(Color.BLACK);
        dataSet1.setValueTextSize(13);
        dataSet1.setLineWidth(2);
        dataSet1.setCircleColor(Color.BLACK);
        dataSet1.setCircleRadius(3);

        List<Entry> amtWoman = new ArrayList<>();
        amtWoman.add(new Entry(1.5F, 30.6F));
        amtWoman.add(new Entry(3.5F, 30.2F));
        amtWoman.add(new Entry(5.5F, 17.4F));
        amtWoman.add(new Entry(9F, 10.6F));
        amtWoman.add(new Entry(11F, 11.2F));

        LineDataSet dataSet2 = new LineDataSet(amtWoman, "여성");
        dataSet2.setColor(Color.rgb(255, 165, 0));
        dataSet2.setValueTextColor(Color.BLACK);
        dataSet2.setValueTextSize(13);
        dataSet2.setLineWidth(2);
        dataSet2.setCircleColor(Color.BLACK);
        dataSet2.setCircleRadius(3);

        List<Entry> temp = new ArrayList<>();

        if (gender.equals("남")) {
            if (average <= 2) {
                temp.add(new Entry(1.5F, 14.9F));
            } else if (average <= 4) {
                temp.add(new Entry(3.5F, 26.2F));
            } else if (average <= 6) {
                temp.add(new Entry(5.5F, 21.3F));
            } else if (average <= 9) {
                temp.add(new Entry(9F, 17.9F));
            } else {
                temp.add(new Entry(11F, 19.8F));
            }
        } else if (gender.equals("여")) {
            if (average <= 2) {
                temp.add(new Entry(1.5F, 30.6F));
            } else if (average <= 4) {
                temp.add(new Entry(3.5F, 30.2F));
            } else if (average <= 6) {
                temp.add(new Entry(5.5F, 17.4F));
            } else if (average <= 9) {
                temp.add(new Entry(9F, 10.6F));
            } else {
                temp.add(new Entry(11F, 11.2F));
            }
        }

        LineDataSet dataSet3 = new LineDataSet(temp, "");
        dataSet3.setCircleColor(Color.rgb(255,0,0));
        dataSet3.setValueTextSize(0);
        dataSet3.setValueTextColor(Color.WHITE);
        dataSet3.setCircleRadius(7);
        dataSet3.setLineWidth(0);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);
        statisLineChart.setData(lineData);

        XAxis xAxis = statisLineChart.getXAxis();
        xAxis.setAxisMinimum(0F);
        xAxis.setAxisMaximum(12F);
        xAxis.setGranularity(2F);
        xAxis.setLabelCount(6);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "잔 이상";
            }
        });

        YAxis yAxisLeft = statisLineChart.getAxisLeft();
        YAxis yAxisRight = statisLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setAxisMinimum(0F);
        yAxisLeft.setAxisMaximum(35);
        yAxisLeft.setGranularity(5);
        yAxisLeft.setLabelCount(7);
        yAxisLeft.setDrawLabels(true);

        statisLineChart.invalidate();

        Description description = new Description();
        description.setText("X축: 평균 잔 수");
        description.setTextSize(12f);
        statisLineChart.setDescription(description);
    }

    public void setBtn2(String gender, int frequency) { // 과음 빈도
        statisText.setText("과음 빈도 통계");

        List<Entry> manyMan = new ArrayList<>();
        manyMan.add(new Entry(0F, 18.1F));
        manyMan.add(new Entry(1F, 29.5F));
        manyMan.add(new Entry(2F, 30.9F));
        manyMan.add(new Entry(4F, 20.2F));
        manyMan.add(new Entry(5F, 1.3F));

        LineDataSet set1 = new LineDataSet(manyMan, "남성");
        set1.setColor(Color.rgb(103, 132, 145));
        set1.setValueTextColor(Color.BLACK);
        set1.setValueTextSize(13);
        set1.setLineWidth(2);
        set1.setCircleColor(Color.BLACK);
        set1.setCircleRadius(3);


        List<Entry> manyWoman = new ArrayList<>();
        manyWoman.add(new Entry(0F, 24.7F));
        manyWoman.add(new Entry(1F, 33.5F));
        manyWoman.add(new Entry(2F, 26.9F));
        manyWoman.add(new Entry(4F, 13.8F));
        manyWoman.add(new Entry(5F, 1.1F));

        LineDataSet set2 = new LineDataSet(manyWoman, "여성");
        set2.setColor(Color.rgb(255, 165, 0));
        set2.setValueTextColor(Color.BLACK);
        set2.setValueTextSize(13);
        set2.setLineWidth(2);
        set2.setCircleColor(Color.BLACK);
        set2.setCircleRadius(3);

        List<Entry> temp = new ArrayList<>();

        if (gender.equals("남")) {
            if (frequency == 0) {
                temp.add(new Entry(0F, 18.1F));
            } else if (frequency <= 1) {
                temp.add(new Entry(1F, 29.5F));
            } else if (frequency <= 3) {
                temp.add(new Entry(2F, 30.9F));
            } else if (frequency <= 5) {
                temp.add(new Entry(4F, 20.2F));
            } else {
                temp.add(new Entry(5F, 1.3F));
            }
        } else if (gender.equals("여")) {
            if (frequency == 0) {
                temp.add(new Entry(0F, 24.7F));
            } else if (frequency <= 1) {
                temp.add(new Entry(1F, 33.5F));
            } else if (frequency <= 3) {
                temp.add(new Entry(2F, 26.9F));
            } else if (frequency <= 5) {
                temp.add(new Entry(4F, 13.8F));
            } else {
                temp.add(new Entry(5F, 1.1F));
            }
        }

        LineDataSet set3 = new LineDataSet(temp, "");
        set3.setCircleColor(Color.rgb(255,0,0));
        set3.setValueTextSize(0);
        set3.setValueTextColor(Color.WHITE);
        set3.setCircleRadius(7);
        set3.setLineWidth(0);

        LineData lineData = new LineData(set1, set2, set3);
        statisLineChart.setData(lineData);

        XAxis xAxis = statisLineChart.getXAxis();
        xAxis.setAxisMinimum(0F);
        xAxis.setAxisMaximum(5F);
        xAxis.setGranularity(1F);
        xAxis.setLabelCount(10);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "회 이상";
            }
        });

        YAxis axisLeft = statisLineChart.getAxisLeft();
        YAxis yAxisRight = statisLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        axisLeft.setEnabled(false);
        axisLeft.setAxisMinimum(0F);
        axisLeft.setAxisMaximum(35);
        axisLeft.setGranularity(5);
        axisLeft.setLabelCount(7);
        axisLeft.setDrawLabels(true);

        statisLineChart.invalidate();

        Description description = new Description();
        description.setText("X축: 평균 음주 횟수");
        description.setTextSize(12f);
        statisLineChart.setDescription(description);
    }

}
