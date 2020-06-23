package com.zhq.exclusivememory.ui.activity.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.zhq.exclusivememory.R;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/22.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private int progress = 0;

    public MyAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle("AsyncTask测试");
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        while (true) {
            try {
                Thread.sleep(1000);
                progress=progress+10;
                publishProgress(progress);
                if (progress >= 100) {
                    break;
                }
            } catch (InterruptedException e) {

                return false;
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressDialog.setProgress(progress);
        mProgressDialog.setMessage("已完成 " + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mProgressDialog.dismiss();
        progress = 0;
        if (aBoolean) {
            Toast.makeText(mContext, "完成", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
        }
    }
}
