package com.sergivonavi.materialbanner.app.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.sergivonavi.materialbanner.app.R;
import com.sergivonavi.materialbanner.app.utils.SnackbarHelper;

public class FromLayoutActivity extends BaseSampleActivity {

    @Override
    protected int setLayoutView() {
        return R.layout.sample_activity_from_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ViewGroup rootView = findViewById(R.id.root);

        mBanner = findViewById(R.id.banner);

        mBanner.setLeftButtonListener(banner -> SnackbarHelper.show(rootView, R.string.msg_banner_btnleft));
        mBanner.setRightButtonListener(banner -> SnackbarHelper.show(rootView, R.string.msg_banner_btnright));
        mBanner.setOnShowListener(() -> SnackbarHelper.show(rootView, R.string.msg_banner_onshow));
        mBanner.setOnDismissListener(() -> SnackbarHelper.show(rootView, R.string.msg_banner_ondismiss));
    }
}
