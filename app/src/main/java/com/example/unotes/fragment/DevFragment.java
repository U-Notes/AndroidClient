package com.example.unotes.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.unotes.R;
import com.example.unotes.database.PagerSqlite;
import com.google.android.material.tabs.TabLayout;

/**
 * dev片段
 *
 * @author witer
 * @date 2023/07/29
 */
public class DevFragment extends Fragment {
    PagerSqlite pagerSqlite;
    SQLiteDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devfragment, container, false);
        initview(view);
        initEvent();

        return view;
    }

    private void initEvent() {
        db = pagerSqlite.getReadableDatabase();
    }

    private void initview(View view) {


    }


}
