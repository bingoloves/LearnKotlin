package com.cqs.ysa.retrofit;

import com.cqs.ysa.bean.DriverQuestion;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
     String BASE_URL = "http://ysa.cncqs.cn:8087";
//   String BASE_URL = "http://ysatest.cncqs.cn:8091";
    //头条新闻api
    String TOP_NEWS_URL = "http://v.juhe.cn/toutiao/index?type=top&key=238ca532342eb15fb820f1f7fe08c2d6";
    //驾校题库api
    String DRIVER_QUESTION_URL = "http://v.juhe.cn/jztk/query?subject=1&model=c1&key=da894efab9897c2b9566639a7aa3acd7&testType=rand";

    @GET(DRIVER_QUESTION_URL)
    Observable<DriverQuestion> getQuestions();

    @POST("/login")
    Observable<String> login(@Query("loginCode") String loginCode,
                             @Query("password") String password,
                             @Query("lng") double longitude,
                             @Query("lat") double latitude);

}