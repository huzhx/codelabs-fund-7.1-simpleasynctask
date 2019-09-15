package com.huzhx.mobile.simpleasynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private static final String TAG = SimpleAsyncTask.class.getName();
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 200;
        Log.d(TAG, "About to sleep " + s + "milliseconds");
        for (int count = 0; count < s; count++) {
            try {
                Thread.sleep(1);
                publishProgress((int) ((count + 1) / (float) s * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Awake at last after sleeping for " + s + " milliseconds";
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mProgressBar.get().setProgress(progress[0]);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
