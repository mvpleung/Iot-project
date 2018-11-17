package com.liangzc.light.ui.me;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangzc.light.R;
import com.liangzc.light.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.imgback)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initControl();
        return view;
    }

    public void initControl() {
        mImgBack.setVisibility(View.GONE);
        mTitle.setText("我的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
