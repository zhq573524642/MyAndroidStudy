package com.zhq.exclusivememory.ui.activity.third_party.image_select;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.CsvFormatStrategy;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.widget.BottomPopupOption;
import com.zhq.exclusivememory.widget.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/6.
 */

public class ImageSelectActivity extends BaseSimpleActivity {

    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.btn_select_iamge)
    Button mBtnSelectIamge;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("图片选择框架");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_image_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_select_iamge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_select_iamge:
                final BottomPopupOption popupOption = new BottomPopupOption(this);
                popupOption.setItemText("拍照","从手机相册选择");
                popupOption.setColors(Color.parseColor("#404040"),Color.parseColor("#404040"));
                popupOption.setItemClickListener(new BottomPopupOption.OnPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                openGallery();
                                break;
                        }
                        popupOption.dismiss();
                    }
                });
                popupOption.showPopupWindow(R.id.layout_popup_add,R.layout.popup_window_sub_item);
                break;
        }


    }

    private void openCamera(){
        PictureSelector.create(ImageSelectActivity.this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    private void openGallery(){
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(ImageSelectActivity.this)
                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16,9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String image="";
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = localMedia.get(0);
                    if(media.isCompressed()){
                        image=media.getCompressPath();
                    }else if (media.isCut()){
                        image=media.getCutPath();
                    }else {
                        image=media.getPath();
                    }
                    Glide.with(this).load(image).into(mIvImage);
                    break;
                    case PictureConfig.REQUEST_CAMERA:
                        List<LocalMedia> localMedia1 = PictureSelector.obtainMultipleResult(data);
                        LocalMedia media1 = localMedia1.get(0);
                        if(media1.isCompressed()){
                            image=media1.getCompressPath();
                        }else if (media1.isCut()){
                            image=media1.getCutPath();
                        }else {
                            image=media1.getPath();
                        }
                        Glide.with(this).load(image).into(mIvImage);
                        break;
            }
        }
    }
}
