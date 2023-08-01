package com.example.unotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.unotes.adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this);
        viewPager2.setAdapter(mainPagerAdapter);
    }
}