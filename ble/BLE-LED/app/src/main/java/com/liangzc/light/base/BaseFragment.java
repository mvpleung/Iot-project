package com.liangzc.light.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Matches on 15/11/23.
 */
public class BaseFragment extends Fragment {

    public static final String imgWidth = "720";
    public static final String imgHeight = "480";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public void initData() {

    }

    /**
     * 跳转界面
     *
     * @param paramClass
     */
    protected void openActivity(Class<?> paramClass) {
        openActivity(paramClass, null);
    }

    protected void openActivity(Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent(getActivity(), paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    protected void openActivityForResult(Class<?> paramClass, Bundle paramBundle, int requestCode) {
        Intent localIntent = new Intent(getActivity(), paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivityForResult(localIntent, requestCode);
    }

    protected void openActivityForResult(Class<?> paramClass, int requestCode) {
        openActivityForResult(paramClass, null, requestCode);
    }

    protected void openActivity(String paramString) {
        openActivity(paramString, null);
    }

    protected void openActivity(String paramString, Bundle paramBundle) {
        Intent localIntent = new Intent(paramString);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
