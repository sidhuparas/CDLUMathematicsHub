package com.parassidhu.cdlumaths.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView,
                "translationY",goesDown ? 200 : -200,0);
        animatorTranslateY.setDuration(500);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
    }
}
