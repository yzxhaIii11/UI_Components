package com.example.ui_components;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 工具类：在 Edge-to-Edge（沉浸式）模式下，为根视图叠加系统栏 / 刘海屏边距，
 * 避免内容被状态栏遮挡（Android 15+ 强制 Edge-to-Edge）。
 */
public final class EdgeInsetHelper {

    private EdgeInsetHelper() {
    }

    /**
     * 给内容视图叠加系统栏边距。
     *
     * @param contentView 需要处理边距的根视图
     */
    public static void apply(View contentView) {
        final int left = contentView.getPaddingLeft();
        final int top = contentView.getPaddingTop();
        final int right = contentView.getPaddingRight();
        final int bottom = contentView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(contentView, (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(left + bars.left, top + bars.top, right + bars.right, bottom + bars.bottom);
            return insets;
        });
    }
}
