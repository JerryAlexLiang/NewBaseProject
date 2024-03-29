package com.liang.module_base.widget.ratingbar;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

/**
 * @Time: 2023/6/30/0030 on 17:06
 * @User: Jerry
 * @Description: 星级评分控件
 */
interface IRatingBar {

    /**
     * 设置最大的星级数
     *
     * @param numStars
     */
    void setNumStars(int numStars);

    int getNumStars();

    /**
     * 设置当前的星级数
     *
     * @param rating
     */
    void setRating(float rating);

    float getRating();

    /**
     * 设置星星的宽度
     *
     * @param starWidth
     */
    void setStarWidth(@IntRange(from = 0) int starWidth);

    int getStarWidth();

    /**
     * 设置星星的高度
     *
     * @param starHeight
     */
    void setStarHeight(@IntRange(from = 0) int starHeight);

    int getStarHeight();

    /**
     * 设置星星之间的间距
     *
     * @param ratingPadding
     */
    void setStarPadding(int ratingPadding);

    int getStarPadding();

    /**
     * 设置星星为空的图案
     *
     * @param drawable
     */
    void setEmptyDrawable(Drawable drawable);

    void setEmptyDrawableRes(@DrawableRes int res);

    /**
     * 设置星星填充的图案
     *
     * @param drawable
     */
    void setFilledDrawable(Drawable drawable);

    void setFilledDrawableRes(@DrawableRes int res);

    /**
     * 设置最小星级数
     *
     * @param minimumStars
     */
    void setMinimumStars(@FloatRange(from = 0.0) float minimumStars);

    boolean isIndicator();

    /**
     * 设置是否只用于指示
     *
     * @param indicator
     */
    void setIsIndicator(boolean indicator);

    boolean isScrollable();

    /**
     * 设置是否可以滑动评分
     *
     * @param scrollable
     */
    void setScrollable(boolean scrollable);

    boolean isClickable();

    /**
     * 设置是否可以点击评分
     *
     * @param clickable
     */
    void setClickable(boolean clickable);

    /**
     * 设置重复点击，是否可以清空星级评分
     *
     * @param enabled
     */
    void setClearRatingEnabled(boolean enabled);

    boolean isClearRatingEnabled();

    float getStepSize();

    /**
     * 设置星级评分的进度单位
     *
     * @param stepSize
     */
    void setStepSize(@FloatRange(from = 0.1, to = 1.0) float stepSize);


}
