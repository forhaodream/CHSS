package com.sheepshop.businessside.net.progress;

/**
 * Created by  on 2017/9/10.
 */

public interface ProgressListener {
    void onProgress(long soFarBytes, long totalBytes);

    void onError(Throwable throwable);
}
