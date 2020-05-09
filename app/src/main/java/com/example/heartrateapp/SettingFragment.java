package com.example.heartrateapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    String brand = android.os.Build.BRAND;
    String sdk = android.os.Build.VERSION.SDK; // SDK号
    String model = android.os.Build.MODEL; // 手机型号
    String release = android.os.Build.VERSION.RELEASE; // android系统版本号
    private Button appPerMan;// 打开应用权限设置
    private Button notifyStart;// 启动通知服务
    private Button notifyStop;// 停止通知服务
    private Context context = getActivity();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_setting, container, false);
        appPerMan = (Button)rootView.findViewById(R.id.appPermissionManagement);
        appPerMan.setOnClickListener(mOpenSetting);
        notifyStart = (Button)rootView.findViewById(R.id.starService);
        notifyStart.setOnClickListener(mStartListener);
        notifyStop = (Button)rootView.findViewById(R.id.stopService);
        notifyStop.setOnClickListener(mStopListener);
        return rootView;
    }
    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission() {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", getContext().getPackageName());  //context.getPackageName()
            startActivity(localIntent);
        } catch (Exception e) {
            try { // 低版本MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname",getContext().getPackageName());//context.getPackageName()
                startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
              startActivity(getAppDetailSettingIntent());
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }
    }

    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package",getContext().getPackageName(), null));// ,context.getPackageName(),
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getContext().getPackageName());//,context.getPackageName()
        }
        return localIntent;
    }
    /*
     *点击判断
     */

    private View.OnClickListener mOpenSetting = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                gotoMiuiPermission();//小米
            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                gotoMeizuPermission();
            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                gotoHuaweiPermission();
            } else {
                startActivity(getAppDetailSettingIntent());
            }
        }
    };
    /*
     *通知服务
     */

    private View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            // 启动Notification对应Service
            getActivity().startService(new Intent(getActivity(),
                    NotifyingService.class));
        }
    };
    private View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            // 停止Notification对应Service
            getActivity().stopService(new Intent(getActivity(),
                    NotifyingService.class));
        }
    };
}
