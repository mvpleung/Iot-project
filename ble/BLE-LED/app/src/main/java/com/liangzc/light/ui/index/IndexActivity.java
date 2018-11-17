package com.liangzc.light.ui.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.liangzc.light.R;
import com.liangzc.light.base.BaseFragmentActivity;
import com.liangzc.light.tools.FragmentPoolProxy;
import com.liangzc.light.ui.me.MeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class IndexActivity extends BaseFragmentActivity {

    Unbinder mUnbinder;
    FragmentPoolProxy mFragmentProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mUnbinder = ButterKnife.bind(this);
        mFragmentProxy = new FragmentPoolProxy(getSupportFragmentManager(), R.id.fm_content, IndexFragment.class, true);
    }

    @OnClick({R.id.index, R.id.me})
    public void LayoutClick(View view) {
        switch (view.getId()) {
            case R.id.index:
                setCurrentTab(IndexFragment.class);
                break;
            case R.id.me:
                setCurrentTab(MeFragment.class);
                break;
        }
    }

    public void setCurrentTab(Class<? extends Fragment> mFragmentClass) {
        mFragmentProxy.setCurrentTab(mFragmentClass);
    }

    public void setCurrentTab(Class<? extends Fragment> mFragmentClass,
                              Bundle mBundle) {
        mFragmentProxy.setCurrentTab(mFragmentClass, mBundle);
    }

    public void showPreFragment() {
        mFragmentProxy.showPreFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
