package com.m.car2.glide.loader;

import android.content.pm.ApplicationInfo;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;

/**
 * Created by zhenyu on 17/2/16.
 */

public class PackageIconLoader implements ModelLoader<ApplicationInfo, ApplicationInfo> {

    @Override
    public DataFetcher<ApplicationInfo> getResourceFetcher(final ApplicationInfo model, int width, int height) {
        return new DataFetcher<ApplicationInfo>() {
            @Override
            public ApplicationInfo loadData(Priority priority) throws Exception {
                return model;
            }

            @Override
            public void cleanup() {

            }

            @Override
            public String getId() {
                return PackageIconLoader.class.getName() + model.packageName;
            }

            @Override
            public void cancel() {

            }
        };
    }
}
