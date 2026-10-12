package com.example.ui_components;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实验3-5：使用 RecyclerView 实现列表视图效果
 * - 使用实验1的动物图片资源，列表项包含图片、名称
 * - 使用 RecyclerView、Adapter 与 ViewHolder 展示数据
 * - 单击显示名称，增加一条记录后列表立即更新（notifyItemInserted）
 * - 验证实际效果：滚动正常、点击对象正确、更新后无错位
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private final List<Animal> animals = new ArrayList<>();
    private AnimalAdapter adapter;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recyclerview);
        EdgeInsetHelper.apply(findViewById(R.id.root));

        tvCount = findViewById(R.id.tv_count);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始数据：实验1的动物图片资源
        animals.add(new Animal("猫", "Cat", R.drawable.cat));
        animals.add(new Animal("狗", "Dog", R.drawable.dog));
        animals.add(new Animal("大象", "Elephant", R.drawable.elephant));
        animals.add(new Animal("狮子", "Lion", R.drawable.lion));
        animals.add(new Animal("猴子", "Monkey", R.drawable.monkey));
        animals.add(new Animal("老虎", "Tiger", R.drawable.tiger));

        adapter = new AnimalAdapter(animals, position -> {
            Animal animal = animals.get(position);
            // 1. 单击显示名称
            Toast.makeText(this, "你单击了：" + animal.getName() + "（" + animal.getEnglishName() + "）",
                    Toast.LENGTH_SHORT).show();
            // 2. 增加一条记录，列表立即更新（局部刷新，无错位）
            animals.add(new Animal(animal.getName() + "·副本",
                    animal.getEnglishName() + " (copy)", animal.getImageRes()));
            adapter.notifyItemInserted(animals.size() - 1);
            recyclerView.smoothScrollToPosition(animals.size() - 1);
            updateCount();
        });
        recyclerView.setAdapter(adapter);
        updateCount();
    }

    private void updateCount() {
        tvCount.setText(getString(R.string.recycler_view_count, animals.size()));
    }
}
