package com.zhq.exclusivememory.ui.activity.data_save.file;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class FileSaveActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.et_save_content)
    EditText mEtSaveContent;
    @BindView(R.id.iv_save_image)
    ImageView mIvSaveImage;
    @BindView(R.id.tv_read_content)
    TextView mTvReadContent;
    @BindView(R.id.iv_read_image)
    ImageView mIvReadImage;
    @BindView(R.id.btn_save_content)
    Button mBtnSaveContent;
    @BindView(R.id.btn_read_content)
    Button mBtnReadContent;
    @BindView(R.id.btn_save_image)
    Button mBtnSaveImage;
    @BindView(R.id.btn_read_image)
    Button mBtnReadImage;
    private String image = "http://img5.imgtn.bdimg.com/it/u=639238630,2179659181&fm=26&gp=0.jpg";
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static List<String> permissionList = new ArrayList<>();
    private static final int REQUEST_PERMISSION_CODE_SAVE_IMAGE = 1003;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("文件存储");
        RequestOptions options = new RequestOptions();

        Glide.with(FileSaveActivity.this).load(image).into(mIvSaveImage);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_file_save;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_save_content, R.id.btn_read_content, R.id.btn_save_image, R.id.btn_read_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_save_content:
                saveContent();
                break;
            case R.id.btn_read_content:
                String content = readContent();
                mTvReadContent.setText(content);
                break;
            case R.id.btn_save_image:
                if (permissionList.size() > 0) {
                    permissionList.clear();
                }
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(FileSaveActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(permissions[i]);
                    }
                }

                if (!permissionList.isEmpty()) {
                    String[] permission_need = permissionList.toArray(new String[permissionList.size()]);
                    ActivityCompat.requestPermissions(FileSaveActivity.this, permission_need, REQUEST_PERMISSION_CODE_SAVE_IMAGE);
                } else {
                    saveImage();
                }


                break;
            case R.id.btn_read_image:
                readImage();
                break;
        }
    }

    private void readImage() {
        try {
            FileInputStream fileInputStream = new FileInputStream("image.jpg");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            mIvReadImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveImage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = Glide.with(FileSaveActivity.this).asBitmap().load(image).into(500, 500).get();
                    saveToFile(bitmap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void saveToFile(Bitmap bitmap) {
        FileOutputStream output = null;
        File appDir = new File(Environment.getExternalStorageDirectory(), "测试的文件夹");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "image.jpg";
        File file = new File(appDir, fileName);
        try {
            output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(getApplication().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        getApplication().sendBroadcast(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FileSaveActivity.this, "保存图片成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_SAVE_IMAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                }
                break;
        }
    }

    /**
     * 读取文本
     */
    private String readContent() {
        FileInputStream fileInput = null;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileInput = openFileInput("content");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInput));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (!TextUtils.isEmpty(content.toString())) {
            Toast.makeText(this, "读取文本成功", Toast.LENGTH_SHORT).show();
        }
        return content.toString();
    }

    /**
     * 保存文本
     */
    private void saveContent() {
        String content = mEtSaveContent.getText().toString().trim();
        FileOutputStream fileOutput = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutput = openFileOutput("content", Context.MODE_APPEND);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutput));
            bufferedWriter.write(content);
            Toast.makeText(this, "保存文本成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
