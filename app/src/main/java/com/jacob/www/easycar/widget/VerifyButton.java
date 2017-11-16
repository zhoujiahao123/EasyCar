package com.jacob.www.easycar.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.jacob.www.easycar.R;

/**
 * Created by 张兴锐 on 2017/11/16.
 */

public class VerifyButton extends android.support.v7.widget.AppCompatButton {

    private Long totalSeconds = Long.valueOf(60 * 1000);//默认60s
    private Long tempSeconds;
    private String beforeSendStr = "VERIFY";//默认为Verify
    private TimeCount timeCount;

    public void setTotalSeconds(Long totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public void setBeforeSendStr(String beforeSendStr) {
        this.beforeSendStr = beforeSendStr;
    }

    public VerifyButton(Context context) {
        this(context, null);
    }

    public Long getTotalSeconds() {
        return totalSeconds;
    }

    public String getBeforeSendStr() {
        return beforeSendStr;
    }

    public VerifyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        timeCount = new TimeCount(totalSeconds, 1000);
    }


    public void startCount() {
        timeCount.start();
        tempSeconds = totalSeconds;
        this.setClickable(false);
        VerifyButton.this.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_small_unclickable, null));
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            totalSeconds = totalSeconds - 1000;
            VerifyButton.this.setText(totalSeconds / 1000 + "s");
        }

        @Override
        public void onFinish() {
            totalSeconds = tempSeconds;
            VerifyButton.this.setText(beforeSendStr);
            VerifyButton.this.setClickable(true);
            VerifyButton.this.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_small_clickable, null));
        }
    }

}
