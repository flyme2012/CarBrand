package com.m.car2.glide.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhenyu on 17/2/15.
 */

public class MultiUrl extends ArrayList<String> {


    public static MultiUrl create(String[] urls) {
        if (urls != null && urls.length > 0) {
            MultiUrl multiUrl = new MultiUrl(urls.length);
            multiUrl.addAll(Arrays.asList(urls));
            return multiUrl;
        } else {
            return new MultiUrl();
        }
    }


    public MultiUrl() {
        super();
    }

    public MultiUrl(int capacity) {
        super(capacity);
    }

    public MultiUrl(Collection<String> collection) {
        super(collection);
    }

    public MultiUrl(String[] urls) {
        if (urls != null && urls.length > 0) {
            addAll(Arrays.asList(urls));
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (String url : this) {
            sb.append(url);
        }
        return sb.toString();
    }

}
