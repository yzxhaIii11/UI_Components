package com.example.ui_components;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实验3-1：Android ListView 的用法
 * - 利用 SimpleAdapter 实现列表（注意列表项的布局：图片 + 名称 + 描述）
 * - 图片使用课程 QQ 群附件资源
 * - 使用 Toast 显示选中的列表项信息
 * - 单击列表项后发送一条通知：Icon 为应用程序图标，Title 为列表项内容，通知内容自拟
 */
public class ListViewActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "list_click_channel";
    private static final int NOTIFICATION_ID = 0x01;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;

    // 动物数据（图片来自课程 QQ 群附件资源）
    private final String[] names = {"猫", "狗", "大象", "狮子", "猴子", "老虎"};
    private final String[] englishNames = {"Cat", "Dog", "Elephant", "Lion", "Monkey", "Tiger"};
    private final int[] images = {R.drawable.cat, R.drawable.dog, R.drawable.elephant,
            R.drawable.lion, R.drawable.monkey, R.drawable.tiger};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listview);
        EdgeInsetHelper.apply(findViewById(R.id.root));

        // Android 13（API 33）及以上：发送通知前需要动态申请 POST_NOTIFICATIONS 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION);
        }

        // SimpleAdapter 的数据源：List<Map<String, Object>>
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images[i]);   // key "image" 绑定到列表项的 ImageView
            item.put("name", names[i]);     // key "name" 绑定到列表项的名称 TextView
            item.put("desc", englishNames[i] + " · 动物"); // key "desc" 绑定到描述 TextView
            data.add(item);
        }

        // 三个 key 依次对应 item_simple_list.xml 中的三个控件
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_simple_list,
                new String[]{"image", "name", "desc"},
                new int[]{R.id.iv_icon, R.id.tv_name, R.id.tv_desc});

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // 单击列表项：Toast 显示选中信息，并发送一条通知
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String name = names[position];
            Toast.makeText(this, "你单击了列表项：" + name + "（" + englishNames[position] + "）",
                    Toast.LENGTH_SHORT).show();
            sendNotification(name);
        });
    }

    /**
     * 发送通知：
     * - 通知的图片（Icon）为应用程序图标
     * - 显示的 Title 为列表项内容
     * - 通知内容自拟
     */
    private void sendNotification(String itemName) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        // Android 13+ 未开启通知权限时给出提示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !manager.areNotificationsEnabled()) {
            Toast.makeText(this, "通知权限未开启，请在系统设置中允许通知", Toast.LENGTH_SHORT).show();
            return;
        }

        // Android 8.0（API 26）及以上必须先创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "列表项点击通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("单击列表项时发送的通知");
            manager.createNotificationChannel(channel);
        }

        // 点击通知回到本界面
        Intent intent = new Intent(this, ListViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)    // Icon：应用程序图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(itemName)             // Title：列表项内容
                .setContentText("你刚刚单击了「" + itemName + "」。这是实验三任务1的通知，内容自拟。")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            boolean granted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            Toast.makeText(this, granted ? "通知权限已开启" : "通知权限被拒绝，通知将无法显示",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
