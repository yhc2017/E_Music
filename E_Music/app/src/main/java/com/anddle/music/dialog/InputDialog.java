package com.anddle.music.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anddle.music.R;
import com.anddle.music.uitl.BindView;

/**
 * Created by HUAHUA on 2017/8/29.
 */

public class InputDialog extends Dialog{

    /**
     * 实例化对象
     */
//    @BindView(R.id.dialog_input_tips)TextView mTips;
//    @BindView(R.id.dialog_input_image)ImageView mImage;
//    @BindView(R.id.dialog_input_message)EditText mMessage;
//    @BindView(R.id.dialog_input_no)Button mBtnNo;
//    @BindView(R.id.dialog_input_yes)Button mBtnYes;
//    @BindView(R.id.column_line)View columnLineView;

    TextView mTips;
    ImageView mImage;
    EditText mMessage;
    Button mBtnNo,mBtnYes;
    View columnLineView;
    /**
     * 设置对话框风格
     */
    public InputDialog(Context mContext) {
        super(mContext, R.style.InputDialog);
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String tips;
    private String btn1,btn2 ;
    private int imageResId = -1 ;

    private boolean isSingle = false;//底部是否只有一个按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局
        setContentView(R.layout.inputdialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //引入资源
        setview();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        setEvent();

    }

    /**
     * 引入资源
     */
    public void setview(){
         mTips = (TextView)findViewById(R.id.dialog_input_tips);
         mImage = (ImageView)findViewById(R.id.dialog_input_image);
         mMessage = (EditText)findViewById(R.id.dialog_input_message);
         mBtnNo = (Button)findViewById(R.id.dialog_input_no);
         mBtnYes = (Button)findViewById(R.id.dialog_input_yes);
         columnLineView = (View)findViewById(R.id.column_line);
    }


    /**
     * 初始化界面的确定和取消监听器
     */
    public void setEvent() {
        //设置确定按钮被点击后，向外界提供监听
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化页面显示
     */
    public void refreshView() {
        //设置tips
        if (!TextUtils.isEmpty(tips)){
            mTips.setText(tips);
        }
        //设置image
        if (imageResId!=-1){
            mImage.setImageResource(imageResId);
        }else {
            mImage.setVisibility(View.GONE);
        }
        //传达输入值
        if (TextUtils.isEmpty(message)){
            mMessage.setText(message);
        }
        //动态设置按钮文字
        if (!TextUtils.isEmpty(btn1)){
            mBtnNo.setText(btn1);
        }else {
            mBtnNo.setText("取消");
        }
        if (!TextUtils.isEmpty(btn2)){
            mBtnYes.setText(btn1);
        }else {
            mBtnYes.setText("确定");
        }
   /**
    * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
    */
        if (isSingle){
            columnLineView.setVisibility(View.GONE);
            mBtnYes.setVisibility(View.GONE);
        }else {
            mBtnNo.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;
    public InputDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    /**
     * 按钮的监听接口
     */
    public interface OnClickBottomListener{
        /**
         * 点击确定按钮事件
         */
        public void onYesClick();
        /**
         * 点击取消按钮事件
         */
        public void onNoClick();
    }

    /**
     * 内容数据的get set
     */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getBtn1() {
        return btn1;
    }

    public void setBtn1(String btn1) {
        this.btn1 = btn1;
    }

    public String getBtn2() {
        return btn2;
    }

    public void setBtn2(String btn2) {
        this.btn2 = btn2;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public InputDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }
}
