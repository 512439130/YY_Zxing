package com.yy.zxing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends Activity {
    private TextView mTvResult;
    private EditText mEditText;
    private ImageView mImageView;
    private CheckBox mCheckBox;
    private static final int requestCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mTvResult = (TextView) findViewById(R.id.id_tv_result);
        mEditText = (EditText) findViewById(R.id.id_et_text);
        mImageView = (ImageView) findViewById(R.id.id_iv_image);
        mCheckBox = (CheckBox) findViewById(R.id.id_cb_logo);
    }

    /**
     * 扫描二维码
     * @param view
     */
    public void scan(View view){
        //打开摄像头扫码(调用)
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),requestCode);
    }

    /**
     * 获取startActivityForResult返回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){  //返回成功
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");//获取扫描出的数据
            mTvResult.setText(result);
        }
    }

    /**
     * 生成二维码
     * @param view
     */
    public void createQRCode(View view){
        String text = mEditText.getText().toString();
        if(text.equals("")){
            Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }else{
            Bitmap bitmap = EncodingUtils.createQRCode(text,1000,1000,mCheckBox.isChecked()? BitmapFactory.decodeResource(getResources(),R.mipmap.logo):null);
            mImageView.setImageBitmap(bitmap);
        }
    }
    /**
     * 生成条形码
     * @param view
     */
    public void createBarCode(View view){
        String text = mEditText.getText().toString();
        if(text.equals("")){
            Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }else{
            Bitmap bitmap = EncodingUtils.createBarDCode(
                    getApplicationContext(),
                    text,
                    1000,
                    400,
                    mCheckBox.isChecked()? BitmapFactory.decodeResource(getResources(),R.mipmap.logo):null,
                    true);
            mImageView.setImageBitmap(bitmap);
        }
    }

}
