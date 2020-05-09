package com.example.heartrateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private int tab;
    private AnimatedBottomBar bottomBar;
    private SparseArray<Fragment> mFragmentSparseArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        final SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
                swipeRefresh.setRefreshing(false);  //刷新页面
            }
        });
    }

    private void refreshView() {
        tab = bottomBar.getSelectedTab().getId();
        bottomBar.selectTabById(R.id.test, true);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        initView();
        bottomBar.selectTabById(tab, true);
    }
    private void initView() {
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.Introduction_tab, BlankFragment.newInstance("1"));
        mFragmentSparseArray.append(R.id.record_tab, ListFragment.newInstance("2","记录"));
        mFragmentSparseArray.append(R.id.test, BlankFragment.newInstance(""));
        mFragmentSparseArray.append(R.id.news_tab, WebFragment.newInstance("3", "heart"));
        mFragmentSparseArray.append(R.id.settings_tab, SettingFragment.newInstance("4","设置"));

        bottomBar = findViewById(R.id.abbar);
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(tab1.getId())).commit();
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

        findViewById(R.id.test_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeartRateActivity.class));
            }
        });
    }
}


//            @Override
//            public void onCheckedChanged(AnimatedBottomBar group, int checkedId) {
//                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
////
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        mFragmentSparseArray.get(checkedId)).commit();
//
//
//
//                switch (checkedId)  {
//                    case R.id.Introduction_tab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        mFragmentSparseArray.get(checkedId)).commit();
//                        break;
//                    case R.id.record_tab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                mFragmentSparseArray.get(checkedId)).commit();
//                        break;
//                    case R.id.news_tab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                mFragmentSparseArray.get(checkedId)).commit();
//                        break;
//
//                    case R.id.settings_tab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                mFragmentSparseArray.get(checkedId)).commit();
//                        break;
//                    default:
//                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
//                                mFragmentSparseArray.get(R.id.Introduction_tab)).commit();
//                        break;
//
//                }
//
//
//            }

//        });
        // 默认显示第一个


