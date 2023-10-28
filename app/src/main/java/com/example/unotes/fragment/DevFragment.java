package com.example.unotes.fragment;

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
import com.google.android.material.tabs.TabLayout;

/**
 * dev片段
 *
 * @author witer
 * @date 2023/07/29
 */
public class DevFragment extends Fragment {

    private Button buttonGenerateTab;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devfragment, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {

        buttonGenerateTab = view.findViewById(R.id.button_generate_tab);
        tabLayout = view.findViewById(R.id.tab_layout);

        buttonGenerateTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建新的Fragment对象
                NoteFragment newFragment = new NoteFragment();

                // 获取FragmentManager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // 开始一个新的Fragment事务
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // 将新的Fragment添加到TabLayout中
                fragmentTransaction.add(R.id.tab_layout, newFragment);

                // 提交事务
                fragmentTransaction.commit();
            }
        });


    }


}
