package com.example.ui_components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 实验3-5 RecyclerView 的适配器：
 * 持有数据集合，负责创建 / 复用 ViewHolder 并绑定数据。
 */
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    /** 列表项点击回调 */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final List<Animal> animals;
    private final OnItemClickListener listener;

    public AnimalAdapter(List<Animal> animals, OnItemClickListener listener) {
        this.animals = animals;
        this.listener = listener;
    }

    /** 创建 ViewHolder：把 item_animal.xml 解析成 View 并包装成 ViewHolder */
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    /** 把第 position 条数据绑定到 ViewHolder 上 */
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.ivAnimal.setImageResource(animal.getImageRes());
        holder.tvName.setText(animal.getName());
        holder.tvDesc.setText(animal.getEnglishName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    /** ViewHolder：缓存列表项中的控件引用，避免重复 findViewById */
    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivAnimal;
        final TextView tvName;
        final TextView tvDesc;

        AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAnimal = itemView.findViewById(R.id.iv_animal);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
