package com.liangzc.light.ui.index;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.liangzc.light.R;
import com.liangzc.light.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    @BindView(R.id.imgback)
    ImageView mImgBack;
    @BindView(R.id.imgsettings)
    ImageView mImgSettings;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.light_bar)
    SeekBar mLightBar;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initControl();
        return view;
    }

    public void initControl() {
        mImgBack.setVisibility(View.GONE);
        mImgSettings.setVisibility(View.VISIBLE);
        mTitle.setText("首页");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
