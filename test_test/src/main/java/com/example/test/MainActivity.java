package com.example.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;
import java.util.List;

@Route(path = "/test_test/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String args[]) {
//            List<String> list = Arrays.asList("as", "ac", "a", "bv");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        double temp = list.stream()
                .filter(s -> s > 1)
                .mapToInt(s -> s)
                .summaryStatistics().getAverage();
        System.out.print(temp + "");
    }

}
