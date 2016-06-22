package com.xavier.spikedroid.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xavier.spikedroid.R;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 21/06/2016.
 */
public class DebugDrawer extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.debug_drawer, container, false);
        return view;

    }
}
