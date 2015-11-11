package com.blue.leaves.util;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.blue.leaves.util.io.FileUtil;
import com.blue.leaves.util.log.Log;


public class AsyncOperationUtil {
    public static final String TAG = "AsyncOperationUtil";

    public static final int OPERATION_NONE_TAG = -1;

    public static void asyncStoreCache(
            final Object dataObj, final String fileName) {
        asyncStoreCache(dataObj, fileName, null,
                OPERATION_NONE_TAG);
    }

    public static void asyncStoreCache(
            final Object dataObj, final String fileName,
            final AsyncOperationListner listener) {
        asyncStoreCache(dataObj, fileName, listener,
                OPERATION_NONE_TAG);
    }

    public static void asyncStoreCache(
            final Object dataObj, final String fileName,
            final AsyncOperationListner listener,
            final int tag) {
        AsyncTask<Void, Void, Object> tTask = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    if (dataObj != null
                            && !TextUtils.isEmpty(fileName)) {
                        FileUtil.writeObjectToPath(dataObj,
                                fileName, true);
                    }
                } catch (Exception e) {
                    Log.e(TAG,
                            "write object exception: tag, "
                                    + tag + ", exception: "
                                    + e);
                }
                return dataObj;
            }

            @Override
            protected void onPostExecute(Object tData) {
                if (listener != null) {
                    listener.onOperationComplete(tData, tag);
                }
            }
        };
        tTask.execute();
    }

    public static void asyncReadCache(
            final String fileName,
            final AsyncOperationListner listener) {
        asyncReadCache(fileName, listener,
                OPERATION_NONE_TAG);
    }

    public static void asyncReadCache(
            final String fileName,
            final AsyncOperationListner listener,
            final int tag) {

        AsyncTask<Void, Void, Object> tTask = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                Object tData = null;
                try {
                    if (!TextUtils.isEmpty(fileName)) {
                        tData = FileUtil
                                .readObjectFromPath(fileName);
                    }
                } catch (Exception e) {
                    Log.e(TAG,
                            "asyncReadCache exception: tag, "
                                    + tag + ", exception: "
                                    + e);
                }
                return tData;
            }

            @Override
            protected void onPostExecute(Object tData) {
                if (listener != null) {
                    listener.onOperationComplete(tData, tag);
                }
            }
        };
        tTask.execute();
    }

    public static void asyncOperation(
            final Runnable runnable) {
        asyncOperation(runnable, null, OPERATION_NONE_TAG);
    }

    public static void asyncOperation(
            final Runnable runnable,
            final AsyncOperationListner listener) {
        asyncOperation(runnable, listener,
                OPERATION_NONE_TAG);
    }

    public static void asyncOperation(
            final Runnable runnable,
            final AsyncOperationListner listener,
            final int tag) {
        AsyncTask<Void, Void, Void> tTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "write object exception: "
                            + e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (listener != null) {
                    listener.onOperationComplete(runnable,
                            tag);
                }
            }
        };
        tTask.execute();
    }

    public static void asyncRemoveCache(
            final String fileName) {
        asyncRemoveCache(null, fileName, null,
                OPERATION_NONE_TAG);
    }

    public static void asyncRemoveCache(final String tail,
            final String fileName) {
        asyncRemoveCache(tail, fileName, null,
                OPERATION_NONE_TAG);
    }

    public static void asyncRemoveCache(final String tail,
            final String fileName,
            final AsyncOperationListner listener) {
        asyncRemoveCache(tail, fileName, listener,
                OPERATION_NONE_TAG);
    }

    public static void asyncRemoveCache(final String tail,
            final String fileName,
            final AsyncOperationListner listener,
            final int tag) {
        AsyncTask<Void, Void, Object> tTask = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    if (tail != null
                            && !EmptyUtils.isEmpty(tail)) {
                        FileUtil.removeFilesAtDirPath(
                                fileName, tail);
                    } else {
                        FileUtil.removeFileAtPath(fileName);
                    }
                } catch (Exception e) {
                    Log.e(TAG,
                            "write object exception: tag, "
                                    + tag + ", exception: "
                                    + e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object tData) {
                if (listener != null) {
                    listener.onOperationComplete(tData, tag);
                }
            }
        };
        tTask.execute();
    }

    public static interface AsyncOperationListner {
        public void onOperationComplete(Object mData,
                                        int tag);
    }

}
