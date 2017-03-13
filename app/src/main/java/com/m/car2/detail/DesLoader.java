package com.m.car2.detail;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.m.car2.mode.CarDetail;
import com.m.car2.utility.AsyncDataLoader;
import com.m.car2.utility.FileHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by hubing on 2017/3/11.
 */

public class DesLoader extends AsyncDataLoader<CarDetail> {

    private String url;
    public DesLoader(Context context , String url) {
        super(context);
        this.url = url;
    }

    @Override
    public CarDetail loadInBackground() {
        try {
            String fileName = "test";
            CarDetail carDetail = null;
            fileName = url.replace("/","");
            byte[] bytes = FileHelper.readDataFromInternalFile(getContext(),fileName);
            if (bytes != null){
                Log.d("hb","save  = " + new String(bytes).toString());
                carDetail = JSONObject.parseObject(bytes,CarDetail.class);
            }
            if (carDetail != null && !TextUtils.isEmpty(carDetail.getDes()) && !TextUtils.isEmpty(carDetail.getTitle())){
                return carDetail;
            }
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div.story-con");
            for (Element element : elements){
                String title = element.select("i.story-span").text();
                String des = element.select("p").outerHtml();

                Log.d("hb","title  =" + title);
                Log.d("hb"," des = " + des);
                if (carDetail == null){
                    carDetail = new CarDetail();
                }
                carDetail.setDes(des);
                carDetail.setTitle(title);

                Log.d("hb","string = " + JSONObject.toJSONString(carDetail).toString());

                FileHelper.saveDataToInternalFile(getContext(),fileName, com.alibaba.fastjson.JSONObject.toJSON(carDetail).toString().getBytes());

                return carDetail;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
