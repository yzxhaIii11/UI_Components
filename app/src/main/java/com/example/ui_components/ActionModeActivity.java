package com.example.ui_components;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实验3-4：创建上下文操作模式（ActionMode）的上下文菜单
 * - 使用 ListView 创建列表
 * - choiceMode 设置为 multipleChoiceModal（多选模态）
 * - 长按列表项进入 ActionMode，上下文菜单在 res/menu/menu_context.xml 中定义
 */
public class ActionModeActivity extends AppCompatActivity {

    private final String[] names = {"猫", "狗", "大象", "狮子", "猴子", "老虎"};
    private final String[] englishNames = {"Cat", "Dog", "Elephant", "Lion", "Monkey", "Tiger"};
    private final int[] images = {R.drawable.cat, R.drawable.dog, R.drawable.elephant,
            R.drawable.lion, R.drawable.monkey, R.drawable.tiger};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actionmode);
        EdgeInsetHelper.apply(findViewById(R.id.root));

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(createAdapter());
        // 多选模态：长按列表项即进入 ActionMode 上下文操作模式
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new ActionModeCallback(listView));
    }

    /** 构建 SimpleAdapter：把数据源的 image / name / desc 绑定到列表项布局的三个控件 */
    private SimpleAdapter createAdapter() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images[i]);
            item.put("name", names[i]);
            item.put("desc", englishNames[i] + " · 长按进入 ActionMode");
            data.add(item);
        }
        return new SimpleAdapter(this, data, R.layout.item_actionmode,
                new String[]{"image", "name", "desc"},
                new int[]{R.id.iv_icon, R.id.tv_name, R.id.tv_desc});
    }

    /** ActionMode 回调：加载 XML 上下文菜单、维护已选数量、响应菜单项点击 */
    private final class ActionModeCallback implements AbsListView.MultiChoiceModeListener {

        private final ListView listView;

        ActionModeCallback(ListView listView) {
            this.listView = listView;
        }

        /** 列表项选中状态变化：更新 ActionMode 标题显示已选数量 */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            mode.setTitle("已选择 " + listView.getCheckedItemCount() + " 项");
        }

        /** 进入 ActionMode：加载 XML 定义的上下文菜单 */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /** 点击上下文菜单项：执行对应操作（Toast 演示），完成后退出 ActionMode */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int count = listView.getCheckedItemCount();
            int id = item.getItemId();
            if (id == R.id.action_copy) {
                Toast.makeText(ActionModeActivity.this, "复制：共选中 " + count + " 项", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.action_share) {
                Toast.makeText(ActionModeActivity.this, "分享：共选中 " + count + " 项", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.action_delete) {
                Toast.makeText(ActionModeActivity.this, "删除：共选中 " + count + " 项", Toast.LENGTH_SHORT).show();
            } else {
                return false;
            }
            mode.finish(); // 操作完成后关闭 ActionMode
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
