package com.antony.messageanalizer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.antony.messageanalizer.R;
import com.antony.messageanalizer.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

   private MyPagerAdapter pageAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      List<Fragment> fragments = getFragments();
      pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
      ViewPager pager = (ViewPager) findViewById(R.id.pager);
      pager.setAdapter(pageAdapter);
      pager.setCurrentItem(1);
   }

   private List<Fragment> getFragments() {
      List<Fragment> fList = new ArrayList<Fragment>();
      fList.add(DaysTillSalaryFragment.newInstance());
      fList.add(TotalAmountFragment.newInstance());
      fList.add(PerDayAmountFragment.newInstance());
      return fList;
   }
}
