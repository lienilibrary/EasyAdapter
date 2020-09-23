package com.lieni.library.easyadapter.util;

import android.content.Context;

public class UnitUtil {
    public static int dpToPx(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
