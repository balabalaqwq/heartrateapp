package com.example.heartrateapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean DELETE_CODE;

    private MyDatabaseHelper dbHelper;
    private List<HeartRate> recordsList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        //读出SQLite数据库数据
        dbHelper = new MyDatabaseHelper(getActivity(), "HeartRateRecords.db",
                null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Records", null, null,
                null , null, null , null);
        recordsList.clear();
        if (cursor.moveToFirst())   {
            do {
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String heartrate = cursor.getString(cursor.getColumnIndex("heartrate"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                HeartRate heartRate = new HeartRate(time,heartrate,status);
                recordsList.add(heartRate);
            }   while (cursor.moveToNext());
        }
        cursor.close();
        final HeartRateAdapter adapter = new HeartRateAdapter(getActivity(), R.layout.records_item,
                recordsList);
        //显示到页面中
        final ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //长按删除对应数据
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                final String data_time = recordsList.get(position).getTime();
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除该数据？");
                builder.setTitle("提示");
                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DELETE_CODE = true;
                        Snackbar.make(view, "数据已删除. 请刷新页面", Snackbar.LENGTH_SHORT).show();
                        dbHelper = new MyDatabaseHelper(getActivity(), "HeartRateRecords.db",
                                null, 1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        if (DELETE_CODE == true) {
                            db.delete("Records", "time == ?", new String[]{data_time});
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();


                return false;
            }
        });


        return rootView;
    }
}
