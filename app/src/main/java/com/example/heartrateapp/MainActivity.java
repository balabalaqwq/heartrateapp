package com.example.heartrateapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.SparseArray;
import android.view.View;

import android.widget.RadioGroup;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.today_tab, BlankFragment.newInstance("本APP可仅通过手机摄" +
                "像头及闪光灯达到测量实时的人体心率数据。" +
                "注意： 该功能测得的数据仅供参考，因设备、灯光等环境因素影响与实际心率略有偏差。" +
                "若发现身体不适建议及时就医！"));
        mFragmentSparseArray.append(R.id.record_tab, ListFragment.newInstance("心率","记录"));
        mFragmentSparseArray.append(R.id.contact_tab, WebFragment.newInstance("dxy", "heart"));
        mFragmentSparseArray.append(R.id.settings_tab, BlankFragment.newInstance("设置"));



        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        mFragmentSparseArray.get(checkedId)).commit();


                switch (checkedId)  {
                    case R.id.today_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
                        break;
                    case R.id.record_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                mFragmentSparseArray.get(checkedId)).commit();
                        break;
                    case R.id.contact_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                mFragmentSparseArray.get(checkedId)).commit();
                        break;

                    case R.id.settings_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                mFragmentSparseArray.get(checkedId)).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                                mFragmentSparseArray.get(R.id.today_tab)).commit();
                        break;

                }


            }
        });
        // 默认显示第一个


        findViewById(R.id.test_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeartRateActivity.class));
            }
        });
    }
}
