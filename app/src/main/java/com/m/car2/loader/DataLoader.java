package com.m.car2.loader;

import android.app.ListActivity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.m.car2.mode.CarData;
import com.m.car2.utility.AsyncDataLoader;
import com.m.car2.utility.FileHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubing on 2017/3/11.
 */

public class DataLoader extends AsyncDataLoader<List<CarData>> {

    private final static String CAR_DARA_URL = "http://brand.emao.com/";

    public DataLoader(Context context) {
        super(context);
    }

    @Override
    public List<CarData> loadInBackground() {
        List<CarData> carDataList = new ArrayList<>();
        try {
            String fileName = CAR_DARA_URL.replace("/", "");
            byte[] bytes = FileHelper.readDataFromInternalFile(getContext(), fileName);
            if (bytes != null) {
                carDataList = JSON.parseArray(new String(bytes), CarData.class);
            }
            if (carDataList != null && carDataList.size() > 0) {
                return carDataList;
            }

            Document document = Jsoup.connect(CAR_DARA_URL).get();
            Elements elements = document.select("div.brand-list-box");
            for (Element element : elements) {
                String id = element.attr("id");
                Elements idElments = element.select("ul.brand-list-con").select("li");
                for (Element idElment : idElments) {
                    String name = idElment.select("div.brand-list-img").select("span").text();
                    String simpDes = idElment.select("div.brand-list-text").select("p.brand-list-article").select("a").text();
                    String iconUrl = idElment.select("img.lazy").attr("data-original");
                    String desUrl = idElment.select("p.brand-list-article").select("a").attr("href");
                    if (TextUtils.isEmpty(name) && TextUtils.isEmpty(iconUrl) && TextUtils.isEmpty(desUrl)) {
                        continue;
                    }
                    Log.d("hb", "name = " + name);
                    Log.d("hb", "iconUrl = " + iconUrl);
                    Log.d("hb", "desUrl = " + desUrl);
                    Log.d("hb", "simple des  = " + simpDes);
                    CarData carData = new CarData();
                    carData.setCarName(name);
                    carData.setCarIconUrl(iconUrl);
                    carData.setCarDesUrl(desUrl);
                    carData.setCarId(id);
                    carData.setCarSimpDes(simpDes);
                    carDataList.add(carData);
                }
            }

            Log.d("hb","json = " + JSONObject.toJSONString(carDataList));

            if (carDataList != null && carDataList.size() > 0) {
                FileHelper.saveDataToInternalFile(getContext(), fileName, JSONObject.toJSONString(carDataList).getBytes());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return carDataList;
    }
}
