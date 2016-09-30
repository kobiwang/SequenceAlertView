package wang.kobi.sequencealertview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by MacBook on 16/8/25.
 */
public class AlertView extends LinearLayout {


    private final int WIDTH = 320;

    public interface OnAlertViewBtnClickLister{
        void onAlertViewBtnClick(AlertView view);
    }


    private TextView titleTextView;
    private TextView msgTextView;
    public TextView msgTextView2;
    private TextView leftBtn;
    private TextView rightBtn;

    private ViewGroup rootView;
    private ViewGroup decorView;
    private ViewGroup maskView;

    private ImageButton closeBtn;

    private boolean isCancelable;


    private OnAlertViewBtnClickLister onCloseBtnClickLister;



    public AlertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AlertView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AlertView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView().findViewById(android.R.id.content);

        View v = View.inflate(context, R.layout.view_alert_view, null);
        addView(v);

        titleTextView = (TextView) v.findViewById(R.id.title_tv);
        msgTextView = (TextView) v.findViewById(R.id.msg_tv);
        msgTextView2 = (TextView) v.findViewById(R.id.msg_tv2);

        leftBtn = (TextView) v.findViewById(R.id.left_btn);
        rightBtn = (TextView) v.findViewById(R.id.right_btn);
        closeBtn = (ImageButton) v.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCloseBtnClickLister != null){
                    onCloseBtnClickLister.onAlertViewBtnClick(AlertView.this);
                }
                dismiss();
            }
        });

    }


    public static class Builder {
        private AlertView view;

        public Builder(Context context) {
            view = new AlertView(context);
        }

        public Builder setTitle(String title) {
            view.titleTextView.setVisibility(VISIBLE);
            view.titleTextView.setText(title);
            return this;
        }

        public Builder setMessage(String msg) {
            view.msgTextView.setVisibility(VISIBLE);
            view.msgTextView.setText(msg);
            return this;
        }

        public Builder setMessage2(String msg) {
            view.msgTextView2.setVisibility(VISIBLE);
            view.msgTextView2.setText(msg);
            return this;
        }

        public Builder setLeftButtonTitle(String leftButtonTitle, final OnAlertViewBtnClickLister onClickListener) {
            view.leftBtn.setVisibility(VISIBLE);
            view.leftBtn.setText(leftButtonTitle);
            view.leftBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.onAlertViewBtnClick(view);
                    }
                }
            });
            return this;
        }

        public Builder setOnCloseBtnClickLister(OnAlertViewBtnClickLister onCloseBtnClickLister){
            view.closeBtn.setVisibility(VISIBLE);
            view.onCloseBtnClickLister = onCloseBtnClickLister;
            return this;
        }

        public Builder setRightButtonTitle(String rightButtonTitle, final OnAlertViewBtnClickLister onClickListener) {
            view.rightBtn.setVisibility(VISIBLE);
            view.rightBtn.setText(rightButtonTitle);
            view.rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.onAlertViewBtnClick(view);
                    }
                }
            });
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            view.isCancelable = cancelable;
            return this;
        }

        public AlertView create() {
            return view;
        }

    }



    public void showAndInTurn(){

        //当前页面有其他的alertView 正在显示
        if(decorView != null && decorView.findViewById(R.id.hud_root) != null){
            AlertViewManager.add(this);
        }else {
            show();
        }
    }

    public void show() {
        if (getContext() == null) return;
        if (this.isShown()) return;



        rootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.hud_root, null, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        maskView = (ViewGroup) rootView.findViewById(R.id.hud_mask_container);

        maskView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isCancelable) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        decorView.addView(rootView);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
        );
        params.gravity = Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL;
        params.leftMargin = (DensityUtil.px2dip(getContext(), width ) - WIDTH)/2;

        this.setLayoutParams(params);

        rootView.addView(this);
    }


    public void dismiss(){
        if(rootView == null) return;
        rootView.removeAllViews();
        decorView.removeView(rootView);
        rootView = null;

        AlertViewManager.remove(this);

        if(AlertViewManager.alertViewList.size() > 0){
            AlertViewManager.getNext().show();
        }
    }


}
