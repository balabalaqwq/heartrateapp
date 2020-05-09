package com.example.heartrateapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

/**
 * 实现发一条状态栏通知的Service
 *
 */
public class NotifyingService extends Service {
    // 状态栏通知的管理类对象，负责发通知、清楚通知等
    private NotificationManager mNM;
    // 使用Layout文件的对应ID来作为通知的唯一识别
    private static int MOOD_NOTIFICATIONS = 10;
    /**
     * Android给我们提供ConditionVariable类，用于线程同步。提供了三个方法block()、open()、close()。 void
     * block() 阻塞当前线程，直到条件为open 。 void block(long timeout)阻塞当前线程，直到条件为open或超时
     * void open()释放所有阻塞的线程 void close() 将条件重置为close。
     */
    private ConditionVariable mCondition;
    @Override
    public void onCreate() {
        // 状态栏通知的管理类对象，负责发通知、清楚通知等
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("normal_notify",
                    "定时提醒用户心率检测通知服务", NotificationManager.IMPORTANCE_DEFAULT);
            mNM.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this, "normal_notify")
                .setContentTitle("心率检测通知").setContentText("今天还没有来检测哦！快来检测一下吧~")
                .setSmallIcon(R.drawable.app_icon).setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.notify)).build();
        startForeground(2, notification);
        Toast.makeText(this, "通知服务已开启", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        // 取消通知功能
        mNM.cancel(MOOD_NOTIFICATIONS);
        // 停止线程进一步生成通知
        Toast.makeText(this, "通知服务已关闭", Toast.LENGTH_SHORT).show();
    }
    /**
     * 生成通知的线程任务
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    @SuppressWarnings("deprecation")
    private void showNotification(int moodId, int textId) {
        // 自定义一条通知内容
        CharSequence text = getText(textId);
        // 当点击通知时通过PendingIntent来执行指定页面跳转或取消通知栏等消息操作
        Notification notification = new Notification(moodId, null,
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        // 在此处设置在nority列表里的该norifycation得显示情况。

        /**
         * 注意,我们使用出来。incoming_message ID 通知。它可以是任何整数,但我们使用 资源id字符串相关
         * 通知。它将永远是一个独特的号码在你的 应用程序。
         */
        mNM.notify(MOOD_NOTIFICATIONS, notification);
    }
    // 这是接收来自客户端的交互的对象. See
    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                                     int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };
}

