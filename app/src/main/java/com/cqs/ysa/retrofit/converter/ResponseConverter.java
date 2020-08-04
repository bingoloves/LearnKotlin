package com.cqs.ysa.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * Created by bingo on 2020/8/4 0004.
 */

public class ResponseConverter<T> extends BaseResponseConverter<T> {
    @Override
    public T parserJson(String json) {
        T t  = null;
        try {
            Gson gson = new Gson();
            t  = gson.fromJson(json, new TypeToken<T>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
