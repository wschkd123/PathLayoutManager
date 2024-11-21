package com.wuyr.pathlayoutmanagertest;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 通用型工具
 */
public class YWCommonUtil {

    /**
     * dp 转 px 工具
     *
     * @param dipValue dp 值
     * @return px 值
     */
    public static int dp2px(Context context, float dipValue) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue
                , resources.getDisplayMetrics());
    }

}
