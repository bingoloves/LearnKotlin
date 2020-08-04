package com.cqs.ysa.retrofit.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by bingo on 2020/8/4 0004.
 * 自定义解析器
 */
public abstract class BaseResponseConverter<T> implements Converter<ResponseBody,T> {

    @Override
    public T convert(ResponseBody value) throws IOException {
        return parserJson(value.string());
    }

    public abstract T parserJson(String json);
}
