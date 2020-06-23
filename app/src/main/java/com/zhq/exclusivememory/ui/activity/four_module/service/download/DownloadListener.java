package com.zhq.exclusivememory.ui.activity.four_module.service.download;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/23.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
