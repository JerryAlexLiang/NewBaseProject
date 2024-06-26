package com.liang.module_base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import com.liang.module_base.R;
import com.liang.module_base.base.BaseApp;
import com.liang.module_base.utils.ToastUtil;

/**
 * 创建日期：2018/1/16 on 下午6:55
 * 描述:自定义iOS样式风格搜索框(带删除按钮及动画效果)
 * 作者:yangliang
 */
public class SearchEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    /**
     * 图标是否默认在左边
     */
    private boolean isIconLeft = false;
    /**
     * 是否点击软键盘搜索
     */
    private boolean pressSearch = false;
    /**
     * 软键盘搜索键监听
     */
    private OnSearchClickListener listener;

    private onSearchTextChangedListener changeListener;

    private Drawable[] drawables; // 控件的图片资源
    private Drawable drawableLeft, drawableDel; // 搜索图标和删除按钮图标
    private int eventX, eventY; // 记录点击坐标
    private Rect rect; // 控件区域

    public void setOnSearchTextChangedListener(onSearchTextChangedListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface onSearchTextChangedListener {
        void onSearchTextChangedListener(Editable s);
    }

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(View view);
    }

    public SearchEditText(Context context) {
        this(context, null);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
        addTextChangedListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isIconLeft) { // 如果是默认样式，直接绘制
            if (length() < 1) {
                drawableDel = null;
            }
            this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableDel, null);
            super.onDraw(canvas);
        } else { // 如果不是默认样式，需要将图标绘制在中间
            if (drawables == null) drawables = getCompoundDrawables();
            if (drawableLeft == null) drawableLeft = drawables[0];
            float textWidth = getPaint().measureText(getHint().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawableLeft.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
            super.onDraw(canvas);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 被点击时，恢复默认样式
//        if (!pressSearch && TextUtils.isEmpty(getText().toString())) {
//            isIconLeft = hasFocus;
//            System.out.println("====>llllll:  >>>>>Base " + isIconLeft);
//        }
        if (TextUtils.isEmpty(getText().toString())) {
            isIconLeft = hasFocus;
            System.out.println("====>llllll:  >>>>>isIconLeft " + isIconLeft);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressSearch = (keyCode == KeyEvent.KEYCODE_ENTER);
        // 完美解决editText监听键盘回车会执行两次的解决方法，原因：引文onkey事件包含了down和up事件，所以只需要加入一个判断即可
        if (pressSearch && event.getAction() == KeyEvent.ACTION_UP) {
            System.out.println("====>SearchEditText:  >>>>>pressSearch " + pressSearch);
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            if (!TextUtils.isEmpty(getText().toString())) {
                if (listener != null) {
                    listener.onSearchClick(v);
                }
            } else {
                ToastUtil.INSTANCE.showFailToast(BaseApp.Companion.getAppContext(), true, "请输入搜索内容！", false);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 清空edit内容
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        // 删除按钮被按下时改变图标样式
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY)) {
                //  drawableDel = this.getResources().getDrawable(R.drawable.core_iocn_edit_delete);
                // getResources().getDrawable()方法过时后的替代方法
                //Use ResourcesCompat.getDrawable()
                // 1）使用drawable资源但不为其设置theme主题
                //ResourcesCompat.getDrawable(getResources(), R.drawable.name, null);
                // 2）使用默认的activity主题
                // ContextCompat.getDrawable(getActivity(), R.drawable.name);
                // 3）使用自定义主题
                drawableDel = ContextCompat.getDrawable(getContext(), R.drawable.core_iocn_edit_delete);
            }
        } else {
            drawableDel = ContextCompat.getDrawable(getContext(), R.drawable.core_iocn_edit_delete);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (this.length() < 1) {
            drawableDel = null;
        } else {
//            drawableDel = this.getResources().getDrawable(R.drawable.core_iocn_edit_delete);
            drawableDel = ContextCompat.getDrawable(getContext(), R.drawable.core_iocn_edit_delete);

        }
        if (changeListener != null) {
            changeListener.onSearchTextChangedListener(s);
        }
    }
}
