package com.example.ui_components;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 实验三：Android 界面组件。
 * 主界面：通过 5 个按钮分别进入 5 个实验任务，分别是
 * 任务1 ListView + SimpleAdapter + 通知、
 * 任务2 自定义布局的 AlertDialog、
 * 任务3 XML 定义菜单、
 * 任务4 ActionMode 上下文菜单、
 * 任务5 RecyclerView 列表。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EdgeInsetHelper.apply(findViewById(R.id.root));

        findViewById(R.id.btn_task1).setOnClickListener(v ->
                startActivity(new Intent(this, ListViewActivity.class)));
        findViewById(R.id.btn_task2).setOnClickListener(v ->
                startActivity(new Intent(this, AlertDialogActivity.class)));
        findViewById(R.id.btn_task3).setOnClickListener(v ->
                startActivity(new Intent(this, MenuActivity.class)));
        findViewById(R.id.btn_task4).setOnClickListener(v ->
                startActivity(new Intent(this, ActionModeActivity.class)));
        findViewById(R.id.btn_task5).setOnClickListener(v ->
                startActivity(new Intent(this, RecyclerViewActivity.class)));
    }
}
