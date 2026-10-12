package com.example.ui_components;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 实验3-3：使用 XML 定义菜单
 * 菜单在 res/menu/menu_options.xml 中定义：
 * - 字体大小：小（10号字）/ 中（16号字）/ 大（20号字），点击后设置测试文本的字体大小
 * - 普通菜单项：点击之后弹出 Toast 提示
 * - 字体颜色：红色 / 黑色，点击后设置测试文本的字体颜色
 */
public class MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        tvTest = findViewById(R.id.tv_test);
    }

    /** 加载 XML 定义的选项菜单 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    /** 处理菜单项点击事件 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 字体大小子菜单
        if (id == R.id.size_small) {
            item.setChecked(true);
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            Toast.makeText(this, "字体大小已设置为 10 号字", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.size_medium) {
            item.setChecked(true);
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            Toast.makeText(this, "字体大小已设置为 16 号字", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.size_large) {
            item.setChecked(true);
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            Toast.makeText(this, "字体大小已设置为 20 号字", Toast.LENGTH_SHORT).show();
        }
        // 普通菜单项：点击弹出 Toast 提示
        else if (id == R.id.normal_item) {
            Toast.makeText(this, "你单击了普通菜单项", Toast.LENGTH_SHORT).show();
        }
        // 字体颜色子菜单
        else if (id == R.id.color_red) {
            item.setChecked(true);
            tvTest.setTextColor(Color.RED);
            Toast.makeText(this, "字体颜色已设置为红色", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.color_black) {
            item.setChecked(true);
            tvTest.setTextColor(Color.BLACK);
            Toast.makeText(this, "字体颜色已设置为黑色", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
