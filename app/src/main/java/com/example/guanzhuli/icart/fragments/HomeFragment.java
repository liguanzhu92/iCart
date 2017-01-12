package com.example.guanzhuli.icart.fragments;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.guanzhuli.icart.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class HomeFragment extends Fragment {
    private ViewPager mViewPager, mViewTop;
    private TabLayout mTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int[] mResources = {
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3,
    };
    private int count = 0;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("iCart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSectionsPagerAdapter =  new SectionsPagerAdapter(getChildFragmentManager());
        // set top view pager
        mViewTop = (ViewPager) view.findViewById(R.id.imagePage);
        mViewTop.setAdapter(new TopPagerAdapter(getContext()));
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewTop, true);
        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(count<3){
                            mViewTop.setCurrentItem(count);
                            count++;
                        }else{
                            count = 0;
                            mViewTop.setCurrentItem(count);
                        }
                    }
                });
            }
        }, 500, 3000);


        //set up tabs
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        return view;
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    HomeTopSellerFragment tab1 = new HomeTopSellerFragment();
                    return tab1;
                case 1:
                    HomeNewArrivalFragment tab2 = new HomeNewArrivalFragment();
                    return tab2;
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Top Seller";
                case 1:
                    return "New Arrival";
                default:
                    break;
            }
            return null;
        }
    }

    class TopPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public TopPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}

