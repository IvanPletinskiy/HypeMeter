package com.handen.hypemeter;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    TextView urlTV, coverageTV, hypeTV, resultTV;
    Button backButton;
    double value;
    double percent;
    String url;

    ArrayList<String> coverage = new ArrayList<>(Arrays.asList("Очень маленький", "Маленький", "Средний", "Большой", "Очень большой", "Невероятно большой", "СВЕРХВЫСОКИЙ"));
    ArrayList<String> result = new ArrayList<>(Arrays.asList("Уровень хайпа предельно низкий. Может лучше удалить пост?(шутка)","Хайп настолько маленький, что, по-моему, его нет :)","Уровень хайпа невелик. Не бойтесь, все так начинали.","Хайпометр показывает средний уровень хайпа, ещё чуть-чуть и вы станете хайповым!","Внимание! Обнаружен большой уровень хайпа! Хайп-защита на пределе!","Хайпометр зашкаливает! Вызывайте хайп-полицию!", "Нет слов!Вы просто БОГ хайпа!"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        String appKey = "c1c6d623017585c33da91cf07a36eea1fb709c890ef183de";
        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, appKey, Appodeal.BANNER);
        Log.d("appodeal", Boolean.toString(Appodeal.isLoaded(Appodeal.BANNER_BOTTOM)));
        Appodeal.show(this, Appodeal.BANNER);

        value = getIntent().getDoubleExtra("value", 0);
        url = getIntent().getStringExtra("URL");



        urlTV = (TextView) findViewById(R.id.urlTextView);
        urlTV.setText(url);
        coverageTV = (TextView) findViewById(R.id.coverageTextView);
        hypeTV = (TextView) findViewById(R.id.hypeLevel);
        resultTV = (TextView) findViewById(R.id.resultTextView);
        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MainActivity.isDeleteLink = true;
            }
        });

        Log.d("Value", "value:" + value);


            if(value <= 50.00)
            {
                coverageTV.setText("Уровень охвата: " + coverage.get(0));
                double d = value / 5000.0;
                d = d * 10000;
                Log.d("D", "d: " + d);
                int i = (int) Math.round(d);
                d = (double)i / 100;
                hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                resultTV.setText(result.get(0));
            }
            else
                if(value <=100.00)
                {
                    coverageTV.setText("Уровень охвата: " + coverage.get(1));
                    double d = value / 5000.0;
                    Log.d("D", "d: " + d);
                    d = d * 10000;
                    int i = (int) Math.round(d);
                    d = (double)i / 100;
                    hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                    resultTV.setText(result.get(1));
                }
                else
                    if(value <= 500.00)
                    {
                        coverageTV.setText("Уровень охвата: " + coverage.get(2));
                        double d = value / 5000.0;
                        Log.d("D", "d: " + d);
                        d = d * 10000;
                        int i = (int) Math.round(d);
                        d = (double)i / 100;
                        hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                        resultTV.setText(result.get(2));
                    }
                    else
                        if(value <= 1000.00)
                        {
                            coverageTV.setText("Уровень охвата: " + coverage.get(3));
                            double d = value / 5000.0;
                            Log.d("D", "d: " + d);
                            d = d * 10000;
                            int i = (int) Math.round(d);
                            d = (double)i / 100;
                            hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                            resultTV.setText(result.get(3));
                        }
                        else
                            if(value <= 5000.00)
                            {
                                coverageTV.setText("Уровень охвата: " + coverage.get(4));
                                double d = value / 5000.0;
                                Log.d("D", "d: " + d);
                                d = d * 10000;
                                int i = (int) Math.round(d);
                                d = (double)i / 100;
                                hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                                resultTV.setText(result.get(4));
                            }
                            else
                                if(value <= 10000.00)
                                {
                                    coverageTV.setText("Уровень охвата: " + coverage.get(5));
                                    double d = value / 5000.0;
                                    Log.d("D", "d: " + d);
                                    d = d * 10000;
                                    int i = (int) Math.round(d);
                                    d = (double)i / 100;
                                    hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                                    resultTV.setText(result.get(5));
                                }
                                else
                                {
                                    coverageTV.setText("Уровень охвата: " + coverage.get(6));
                                    double d = value / 5000.0;
                                    Log.d("D", "d: " + d);
                                    d = d * 10000;
                                    int i = (int) Math.round(d);
                                    d = (double)i / 100;
                                    hypeTV.setText("Уровень хайпа: " + Double.toString(d) + "%");
                                    resultTV.setText(result.get(6));
                                }



    }

    public void onBackPressed()
    {
        finish();
        MainActivity.isDeleteLink = true;
    }

}
