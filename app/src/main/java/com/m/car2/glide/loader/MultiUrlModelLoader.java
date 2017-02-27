package com.m.car2.glide.loader;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;

/**
 * Created by zhenyu on 17/2/15.
 */

public class MultiUrlModelLoader<Y> implements ModelLoader<MultiUrl, Y> {


    @Override
    public DataFetcher<Y> getResourceFetcher(MultiUrl model, int width, int height) {
        return new DataFetcher<Y>() {
            @Override
            public Y loadData(Priority priority) throws Exception {
                return null;
            }

            @Override
            public void cleanup() {

            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public void cancel() {

            }
        };
    }
}
