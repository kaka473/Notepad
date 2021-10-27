package com.example.notepad.FInput;

import android.app.Activity;
import android.util.Log;

public class FineInput implements KeyboardUtils.OnSoftKeyBoardChangeListener, OnInputListener {

    private Activity mActivity;
    private com.example.notepad.FInput.FineInputView fineInputView;
    private boolean mCancleTouchOutside;
    private boolean mCancleBackPressed;

    /**
     * 输入框的四种状态
     */
    public static final int INITIALIZE = 0;

    public static final int SHOW_NORMAL = 199;
    public static final int SHOW_EMOJI = 198;

    public static final int DISMISSED = 2;
    public static final int DISMISS_OUTSIDE = 100;
    public static final int DISMISS_BACKKEY = 101;


    private int currentState = INITIALIZE;

    /**
     * 初始化监听
     *
     * @param mContext
     */
    public FineInput(Activity mContext) {
        this.mActivity = mContext;
        KeyboardUtils.setOnSoftkeyboardChangeListener(mActivity, this);
    }


    /**
     * 主动调起输入框
     */
    public void showInput() {
        KeyboardUtils.toggleSoftkeyboard(mActivity);
    }


    /***
     * 消失的时候  记录状态
     */
    private void dismiss(int type) {
        Log.e("TAG", "那种方式消失：" + type);
        currentState = type;
        fineInputView.dismiss();
    }

    /***
     * 销毁的时候
     */
    public void destory() {
        if (fineInputView != null) {
            fineInputView.dismiss();
            fineInputView = null;
        }
        currentState = INITIALIZE;
    }

    @Override
    public void onSoftKeyBoardChange(boolean isShow, int keyboadHeight) {
        if (currentState == INITIALIZE) {
            if (isShow) {
                Log.e("TAG", "初始化");
                if (fineInputView == null) {
                    fineInputView = new com.example.notepad.FInput.FineInputView(mActivity);
                    fineInputView.initialize(keyboadHeight);
                    fineInputView.setOnInputListener(this);
                }
                currentState = SHOW_NORMAL;
            }
        } else if (currentState == DISMISSED) {
            Log.e("TAG", "重新显示");
            if (isShow) {
                if (fineInputView != null) {
                    fineInputView.show();
                }
                currentState = SHOW_NORMAL;
            }
        } else if (currentState == SHOW_NORMAL) {
            if (!isShow) {
                if (fineInputView != null) {
                    fineInputView.dismiss();
                }
                currentState = DISMISSED;
            }
        } else if (currentState == SHOW_EMOJI) {
            fineInputView.changeFocus(true);
            if (isShow) {
                if (fineInputView != null) {
                    fineInputView.changeInputState(!isShow);
                }
                currentState = SHOW_NORMAL;
            }

        } else if (isDismissing()) {
            if (!isShow) {
                currentState = DISMISSED;
            }
        }

        Log.e("TAG", "总的监听：" + currentState + isShow);
    }

    /**
     * 软键盘消失有时间和动画，所有这是正在消失过程
     */
    private boolean isDismissing() {
        return currentState == DISMISS_BACKKEY
                || currentState == DISMISS_OUTSIDE;
    }

    @Override
    public void onCanceledOnTouchOutside() {
        //如果正在显示的情况下再去
        if (!mCancleTouchOutside) {
            dismiss(DISMISS_OUTSIDE);
            Log.e("TAG", "点击外部");
        }
    }

    @Override
    public void onBackPressed() {
        //如果正在显示的情况下再去
        if (!mCancleBackPressed) {
            //back键自带取消软键盘
            dismiss(DISMISS_BACKKEY);
            Log.e("TAG", "点击返回");
        }
    }

    @Override
    public void onSwithInputState(boolean isSpecial) {
        //切换软键盘
        KeyboardUtils.toggleSoftkeyboard(mActivity);
        currentState = isSpecial ? SHOW_EMOJI : SHOW_NORMAL;
    }

    @Override
    public void onSwithPager() {
        Log.e("TAG", "切换页面跳转");
    }

    @Override
    public void onSendMessage() {
        Log.e("TAG", "发送消息");
    }


    /**
     * *******************************************************
     * 下面用于用户设置一些参数
     * *******************************************************
     */

    /**
     * 点击区域外部是否消失
     *
     * @param canceledOnTouchOutside
     */
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCancleTouchOutside = canceledOnTouchOutside;
    }


    /**
     * 点击返回键是否取消
     *
     * @param mCancleBackPressed
     */
    public void setCancleBackPressed(boolean mCancleBackPressed) {
        this.mCancleBackPressed = mCancleBackPressed;
    }

}
