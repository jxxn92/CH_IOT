package com.example.ch_iot;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private String name;

    private Button statisBtn1;
    private Button statisBtn2;
    private TextView statisText;
    private TextView statisLabel1;
    private TextView statisLabel2;
    private LinearLayout statisLayout1;
    private LinearLayout statisLayout2;
    private TextView statisLabelName;
    private LineChart statisLineChart;
    private TextView statisText1;
    private TextView statisText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statis);

        statisBtn1 = findViewById(R.id.statis_btn1);
        statisBtn2 = findViewById(R.id.statis_btn2);
        statisText = findViewById(R.id.statis_text);
        statisLabelName = findViewById(R.id.statis_name);
        statisLineChart = findViewById(R.id.statis_chart);
        statisLayout1 = findViewById(R.id.statis_layout_text1);
        statisLayout2 = findViewById(R.id.statis_layout_text2);
        statisLabel1 = findViewById(R.id.statis_label1);
        statisLabel2 = findViewById(R.id.statis_label2);
        statisText1 = findViewById(R.id.statis_text_1);
        statisText2 = findViewById(R.id.statis_text_2);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        name = sharedPreferences1.getString("userName", null);

        SharedPreferences sharedPreferences2 = getSharedPreferences("TempPrefs", MODE_PRIVATE);
        gender = sharedPreferences2.getString("gender", null);
        frequency = sharedPreferences2.getInt("fre", 0);
        average = sharedPreferences2.getInt("aver", 0);

        statisLineChart.setVisibility(View.VISIBLE);
        statisLineChart.invalidate();
        setBtn1(gender, average);
        statisLayout1.setVisibility(View.VISIBLE);
        statisLabelName.setText(name);
        statisLabel1.setText(String.valueOf(average));
        statisLayout2.setVisibility(View.VISIBLE);

        if (gender.equals("남")) {
            if (average <= 2) {
                statisLabel2.setText(String.valueOf(14.9)+"%");
            } else if (average <= 4) {
                statisLabel2.setText(String.valueOf(26.2)+"%");
            } else if (average <= 6) {
                statisLabel2.setText(String.valueOf(21.3)+"%");
            } else if (average <= 9) {
                statisLabel2.setText(String.valueOf(17.9)+"%");
            } else {
                statisLabel2.setText(String.valueOf(19.8)+"%");
            }
        } else if (gender.equals("여")) {
            if (average <= 2) {
                statisLabel2.setText(String.valueOf(30.6)+"%");
            } else if (average <= 4) {
                statisLabel2.setText(String.valueOf(30.2)+"%");
            } else if (average <= 6) {
                statisLabel2.setText(String.valueOf(17.4)+"%");
            } else if (average <= 9) {
                statisLabel2.setText(String.valueOf(10.6)+"%");
            } else {
                statisLabel2.setText(String.valueOf(11.2)+"%");
            }
        }

        statisBtn1.setOnClickListener(v -> {
            statisLineChart.setVisibility(View.VISIBLE);
            statisLineChart.invalidate();
            setBtn1(gender, average);
            statisText1.setText("님은 술자리에서 평균 약 ");
            statisText2.setText("잔의 술을 마시며");
            statisLayout1.setVisibility(View.VISIBLE);
            statisLabelName.setText(name);
            statisLabel1.setText(String.valueOf(average));
            statisLayout2.setVisibility(View.VISIBLE);

            if (gender.equals("남")) {
                if (average <= 2) {
                    statisLabel2.setText(String.valueOf(14.9)+"%");
                } else if (average <= 4) {
                    statisLabel2.setText(String.valueOf(26.2)+"%");
                } else if (average <= 6) {
                    statisLabel2.setText(String.valueOf(21.3)+"%");
                } else if (average <= 9) {
                    statisLabel2.setText(String.valueOf(17.9)+"%");
                } else {
                    statisLabel2.setText(String.valueOf(19.8)+"%");
                }
            } else if (gender.equals("여")) {
                if (average <= 2) {
                    statisLabel2.setText(String.valueOf(30.6)+"%");
                } else if (average <= 4) {
                    statisLabel2.setText(String.valueOf(30.2)+"%");
                } else if (average <= 6) {
                    statisLabel2.setText(String.valueOf(17.4)+"%");
                } else if (average <= 9) {
                    statisLabel2.setText(String.valueOf(10.6)+"%");
                } else {
                    statisLabel2.setText(String.valueOf(11.2)+"%");
                }
            }
        });

        statisBtn2.setOnClickListener(v -> {
            statisLineChart.setVisibility(View.VISIBLE);
            statisLineChart.invalidate();
            setBtn2(gender, frequency);
            statisLayout1.setVisibility(View.VISIBLE);
            statisText1.setText("님은 한달에 평균 약 ");
            statisText2.setText("번의 술자리를 가지며");
            statisLabelName.setText(name);
            statisLabel1.setText(String.valueOf(frequency));
            statisLayout2.setVisibility(View.VISIBLE);

            if (gender.equals("남")) {
                if (frequency == 0) {
                    statisLabel2.setText(String.valueOf(18.1) + "%");
                } else if (frequency <= 3) {
                    statisLabel2.setText(String.valueOf(29.5) + "%");
                } else if (frequency <= 6) {
                    statisLabel2.setText(String.valueOf(30.9) + "%");
                } else if (frequency <= 9) {
                    statisLabel2.setText(String.valueOf(20.2) + "%");
                } else {
                    statisLabel2.setText(String.valueOf(1.3) + "%");
                }
            } else if (gender.equals("여")) {
                if (frequency == 0) {
                    statisLabel2.setText(String.valueOf(24.7) + "%");
                } else if (frequency <= 3) {
                    statisLabel2.setText(String.valueOf(33.5) + "%");
                } else if (frequency <= 6) {
                    statisLabel2.setText(String.valueOf(26.9) + "%");
                } else if (frequency <= 9) {
                    statisLabel2.setText(String.valueOf(13.8) + "%");
                } else {
                    statisLabel2.setText(String.valueOf(1.1) + "%");
                }
            }
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
        manyMan.add(new Entry(3F, 29.5F));
        manyMan.add(new Entry(6F, 30.9F));
        manyMan.add(new Entry(9F, 20.2F));
        manyMan.add(new Entry(12F, 1.3F));

        LineDataSet set1 = new LineDataSet(manyMan, "남성");
        set1.setColor(Color.rgb(103, 132, 145));
        set1.setValueTextColor(Color.BLACK);
        set1.setValueTextSize(13);
        set1.setLineWidth(2);
        set1.setCircleColor(Color.BLACK);
        set1.setCircleRadius(3);


        List<Entry> manyWoman = new ArrayList<>();
        manyWoman.add(new Entry(0F, 24.7F));
        manyWoman.add(new Entry(3F, 33.5F));
        manyWoman.add(new Entry(6F, 26.9F));
        manyWoman.add(new Entry(9F, 13.8F));
        manyWoman.add(new Entry(12F, 1.1F));

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
            } else if (frequency <= 3) {
                temp.add(new Entry(3F, 29.5F));
            } else if (frequency <= 6) {
                temp.add(new Entry(6F, 30.9F));
            } else if (frequency <= 9) {
                temp.add(new Entry(9F, 20.2F));
            } else {
                temp.add(new Entry(12F, 1.3F));
            }
        } else if (gender.equals("여")) {
            if (frequency == 0) {
                temp.add(new Entry(0F, 24.7F));
            } else if (frequency <= 3) {
                temp.add(new Entry(3F, 33.5F));
            } else if (frequency <= 6) {
                temp.add(new Entry(6F, 26.9F));
            } else if (frequency <= 9) {
                temp.add(new Entry(9F, 13.8F));
            } else {
                temp.add(new Entry(12F, 1.1F));
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
        xAxis.setAxisMaximum(12F);
        xAxis.setGranularity(3F);
        xAxis.setLabelCount(10);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "회 이하";
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
