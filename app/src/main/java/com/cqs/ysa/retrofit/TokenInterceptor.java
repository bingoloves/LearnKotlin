package com.cqs.ysa.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        String token = SPUtils.getString(MyApp.getAppContext(), Constants.TOKEN);
//        if (!TextUtils.isEmpty(token)){
//            Request updateRequest = request.newBuilder().header(Constants.TOKEN, token).build();
//            return chain.proceed(updateRequest);
//        }
        return chain.proceed(request);
    }
}
