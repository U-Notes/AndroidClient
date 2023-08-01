package com.example.unotes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.unotes.fragment.DevFragment;
import com.example.unotes.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于在欢迎界面后的主界面
 *
 * @author witer
 * @date 2023/07/29
 */
public class MainPagerAdapter extends FragmentStateAdapter  {
    private List<Fragment> fragmentList;

    public MainPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentList = new ArrayList<>();
        fragmentList.add(new DevFragment());
        fragmentList.add(new UserFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
