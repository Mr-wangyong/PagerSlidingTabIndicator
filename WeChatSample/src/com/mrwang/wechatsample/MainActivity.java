package com.mrwang.wechatsample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.mrwang.wechatsample.fragment.ChatFragment;
import com.mrwang.wechatsample.fragment.ContactsFragment;
import com.mrwang.wechatsample.fragment.FoundFragment;

public class MainActivity extends FragmentActivity  {
	 /** 
     * 聊天界面的Fragment 
     */  
    private ChatFragment chatFragment;  
  
    /** 
     * 发现界面的Fragment 
     */  
    private FoundFragment foundFragment;  
  
    /** 
     * 通讯录界面的Fragment 
     */  
    private ContactsFragment contactsFragment;  
  
    /** 
     * PagerSlidingTabStrip的实例 
     */  
    private PagerSlidingTabStrip tabs;  
  
    /** 
     * 获取当前屏幕的密度 
     */  
    private DisplayMetrics dm; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOverflowShowingAlways();
		//获取屏幕密度
		dm=getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);  
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //让tab与viewpager相关联
		tabs.setViewPager(pager);
		//设置tab数据
		setTabsValue();
	}

	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的  
        tabs.setShouldExpand(true);  
        // 设置Tab的分割线是透明的  
        tabs.setDividerColor(Color.TRANSPARENT);  
        // 设置Tab底部线的高度  
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));  
        // 设置Tab Indicator的高度  
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));  
        // 设置Tab标题文字的大小  
        tabs.setTextSize((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_SP, 16, dm));  
        // 设置Tab Indicator的颜色  
        tabs.setIndicatorColor(Color.parseColor("#45c01a"));  
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)  
        tabs.setSelectedTextColor(Color.parseColor("#45c01a"));  
        // 取消点击Tab时的背景色  
        tabs.setTabBackground(0); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * 让隐藏菜单的图标能够显示出来
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
                try {  
                    Method m = menu.getClass().getDeclaredMethod(  
                            "setOptionalIconsVisible", Boolean.TYPE);  
                    m.setAccessible(true);  
                    m.invoke(menu, true);  
                } catch (Exception e) {  
                }  
            }  
        }  
        return super.onMenuOpened(featureId, menu);  
	}
	/**
	 * 统一风格 让隐藏总菜单都显示出来
	 */
	private void setOverflowShowingAlways() {  
        try {  
            ViewConfiguration config = ViewConfiguration.get(this);  
            Field menuKeyField = ViewConfiguration.class  
                    .getDeclaredField("sHasPermanentMenuKey");  
            menuKeyField.setAccessible(true);  
            menuKeyField.setBoolean(config, false);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
	
	public class MyPagerAdapter extends FragmentPagerAdapter {  
		  
        public MyPagerAdapter(FragmentManager fm) {  
            super(fm);  
        }  
  
        private final String[] titles = { "聊天", "发现", "通讯录" };  
  
        @Override  
        public CharSequence getPageTitle(int position) {  
            return titles[position];  
        }  
  
        @Override  
        public int getCount() {  
            return titles.length;  
        }  
  
        @Override  
        public Fragment getItem(int position) {  
            switch (position) {  
            case 0:  
                if (chatFragment == null) {  
                    chatFragment = new ChatFragment();  
                }  
                return chatFragment;  
            case 1:  
                if (foundFragment == null) {  
                    foundFragment = new FoundFragment();  
                }  
                return foundFragment;  
            case 2:  
                if (contactsFragment == null) {  
                    contactsFragment = new ContactsFragment();  
                }  
                return contactsFragment;  
            default:  
                return null;  
            }  
        }  
  
    }  

}
