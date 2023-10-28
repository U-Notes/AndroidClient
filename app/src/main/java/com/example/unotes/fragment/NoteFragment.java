package com.example.unotes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unotes.R;

public class NoteFragment extends Fragment {
    RecyclerView rl_note_recycle;
    LinearLayout ll_tab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_layout, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        rl_note_recycle = view.findViewById(R.id.rl_note_recycle);
        ll_tab = view.findViewById(R.id.ll_tab);
    }
}
