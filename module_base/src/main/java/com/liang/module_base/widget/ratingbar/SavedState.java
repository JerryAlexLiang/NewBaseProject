package com.liang.module_base.widget.ratingbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @Time: 2023/6/30/0030 on 17:07
 * @User: Jerry
 * @Description: 状态保存
 */
class SavedState extends View.BaseSavedState {

    private float rating;

    /**
     * Constructor called from {@link RatingBar#onSaveInstanceState()}
     */
    SavedState(Parcelable superState) {
        super(superState);
    }

    /**
     * Constructor called from {@link #CREATOR}
     */
    private SavedState(Parcel in) {
        super(in);
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeFloat(rating);
    }

    public static final Creator<SavedState> CREATOR
            = new Creator<SavedState>() {
        @Override
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        @Override
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
