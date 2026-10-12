package com.example.ui_components;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 实验3-2：创建自定义布局的 AlertDialog
 * - 自定义布局文件：res/layout/dialog_custom.xml
 * - 调用 AlertDialog.Builder 对象上的 setView() 将布局添加到 AlertDialog
 */
public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alert_dialog);
        EdgeInsetHelper.apply(findViewById(R.id.root));

        Button btnShowDialog = findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(v -> showCustomDialog());
    }

    private void showCustomDialog() {
        // 1. 加载自定义布局 dialog_custom.xml
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        EditText etInput = dialogView.findViewById(R.id.et_input);

        // 2. 通过 setView() 把自定义布局添加到 AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("确定", (d, which) -> {
                    String text = etInput.getText().toString().trim();
                    Toast.makeText(this, text.isEmpty() ? "输入内容为空" : "你输入的内容是：" + text,
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (d, which) -> d.dismiss())
                .create();
        dialog.show();
    }
}
