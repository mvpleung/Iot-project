package com.liangzc.light.tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.liangzc.light.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Fragment操作类
 *
 * @author LiangZiChao
 * @Data 2015年7月16日
 * @Package com.exiaobai.library.tools
 */
public class FragmentPoolProxy {

    private Map<Class<? extends Fragment>, Fragment> mFragmentMap;
    private FragmentManager mFragmentManager; // Fragment所属的Activity
    private int mFragmentContentId; // Activity中所要被替换的区域的id
    private Fragment mPreFragment, mCurrentFragment;
    private Class<? extends Fragment> mPreFragmentClass; // 上一个FragmentClassName
    private Class<? extends Fragment> mCurrentFragmentClass; // 当前FragmentClassName
    private OnRgsExtraChangedListener onRgsExtraChangedListener; // 用于让调用者在切换tab时候增加新的功能
    private boolean isNeedAnimation; // 是否需要动画

    /**
     * @param mFragmentManager
     * @param fragmentContentId 填充Fragment的FrameLayoutID
     * @param mCurrentFragment
     * @param isNeedAnimation   是否需要动画
     */
    public FragmentPoolProxy(FragmentManager mFragmentManager,
                             int fragmentContentId, Class<? extends Fragment> mCurrentFragment,
                             boolean isNeedAnimation) {
        this(mFragmentManager, null, fragmentContentId, mCurrentFragment,
                isNeedAnimation);
    }

    /**
     * @param mFragmentManager
     * @param mFragmentMap      Fragment集合，以Class为key
     * @param fragmentContentId 填充Fragment的FrameLayoutID
     * @param mCurrentFragment
     * @param isNeedAnimation   是否需要动画
     */
    public FragmentPoolProxy(FragmentManager mFragmentManager,
                             Map<Class<? extends Fragment>, Fragment> mFragmentMap,
                             int fragmentContentId, Class<? extends Fragment> mCurrentFragment,
                             boolean isNeedAnimation) {
        this.mFragmentMap = mFragmentMap != null ? mFragmentMap
                : new HashMap<Class<? extends Fragment>, Fragment>();
        this.mCurrentFragmentClass = mCurrentFragment;
        this.mFragmentManager = mFragmentManager;
        this.mFragmentContentId = fragmentContentId;
        this.isNeedAnimation = isNeedAnimation;

        if (mCurrentFragment != null) {
            // 默认显示第一页
            FragmentTransaction ft = obtainFragmentTransaction();
            ft.add(fragmentContentId, getFragment(mCurrentFragment));
            ft.commit();
        }
    }

    public void addFragment(Class<? extends Fragment> mClass) {
        mFragmentMap.put(mClass, getFragment(mClass));
    }

    public void addFragment(Fragment baseFragment) {
        mFragmentMap.put(baseFragment.getClass(), baseFragment);
    }

    /**
     * 切换TAB
     */
    public void setCurrentTab(Class<? extends Fragment> mFragmentClass) {
        setCurrentTab(mFragmentClass, null);
    }

    /**
     * 切换TAB
     */
    public void setCurrentTab(Class<? extends Fragment> mFragmentClass,
                              Bundle mBundle) {
        if (mFragmentClass == null)
            throw new NullPointerException("mFragmentClass is null");
        boolean isChangedEnable = true;
        if (null != onRgsExtraChangedListener) {
            isChangedEnable = onRgsExtraChangedListener
                    .onRgsExtraBeforeChanged(mFragmentMap
                            .get(mPreFragmentClass));
        }
        if (isChangedEnable) {

            mPreFragmentClass = mCurrentFragmentClass;
            mPreFragment = mCurrentFragment;
            getCurrentFragment().onPause(); // 暂停当前tab
            // getCurrentFragment().onStop(); // 停止当前tab

            showTab(mFragmentClass); // 显示目标tab
            Fragment fragment = getCurrentFragment();
            if (fragment.isAdded()) {
                if (mBundle != null && fragment.getArguments() != null)
                    fragment.getArguments().putAll(mBundle);
                // fragment.onStart(); // 启动目标tab的onStart()
                fragment.onResume(); // 启动目标tab的onResume()
            } else if (mBundle != null)
                fragment.setArguments(mBundle);
            // 如果设置了切换tab额外功能功能接口
            if (null != onRgsExtraChangedListener)
                onRgsExtraChangedListener.OnRgsExtraChanged(fragment);
        }
    }

    public Fragment getFragment(Class<? extends Fragment> mFragmentClass) {
        Fragment fragment = mFragmentMap.get(mFragmentClass);
        if (fragment == null) {
            try {
                fragment = (Fragment) mFragmentClass.newInstance();
                mFragmentMap.put(mFragmentClass, fragment);
            } catch (Exception e) {

            }
        }
        return fragment;
    }

    /**
     * 显示目标tab
     *
     * @params idx
     */
    private void showTab(Class<? extends Fragment> mFragmentClass) {
        Set<Class<? extends Fragment>> mKeySet = mFragmentMap.keySet();
        Fragment mShowFragment = getFragment(mFragmentClass);
        for (Class<?> mClass : mKeySet) {
            FragmentTransaction ft = obtainFragmentTransaction();
            if (mClass.isAssignableFrom(mFragmentClass)) {
                if (!mShowFragment.isAdded()) {
                    ft.add(mFragmentContentId, mShowFragment);
                }
                ft.show(mShowFragment);
            } else {
                ft.hide(mFragmentMap.get(mClass));
            }
            ft.commitAllowingStateLoss();
        }
        mCurrentFragmentClass = mFragmentClass; // 更新目标tab为当前tab
        mCurrentFragment = mShowFragment;
    }

    public void showPreFragment() {
        setCurrentTab(mPreFragmentClass);
    }

    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @return
     * @params index
     */
    private FragmentTransaction obtainFragmentTransaction() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (isNeedAnimation)
            // 设置切换动画
            ft.setCustomAnimations(getEnterAnimations(), getEndAnimations());
        return ft;
    }

    public int getEnterAnimations() {
        return R.anim.abc_fade_in;
    }

    public int getEndAnimations() {
        return R.anim.abc_fade_out;
    }

    public Fragment getPreFragment() {
        if (mPreFragment == null)
            mPreFragment = mFragmentMap.get(mPreFragmentClass);
        return mPreFragment;
    }

    public Fragment getCurrentFragment() {
        if (mCurrentFragment == null)
            mCurrentFragment = mFragmentMap.get(mCurrentFragmentClass);
        return mCurrentFragment;
    }

    public OnRgsExtraChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(
            OnRgsExtraChangedListener onRgsExtraChangedListener) {
        this.onRgsExtraChangedListener = onRgsExtraChangedListener;
    }

    /**
     * 切换tab额外功能功能接口
     */
    public interface OnRgsExtraChangedListener {
        public boolean onRgsExtraBeforeChanged(Fragment mFragment);

        public void OnRgsExtraChanged(Fragment mFragment);
    }

}
