package com.cqs.ysa.retrofit;

import com.cqs.ysa.bean.CityList;
import com.cqs.ysa.bean.DriverQuestion;
import com.cqs.ysa.bean.Jokes;
import com.cqs.ysa.bean.Stock;
import com.cqs.ysa.bean.TopNews;
import com.cqs.ysa.bean.Weather;
import com.cqs.ysa.bean.WeatherWids;

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
    /**
     *  获取驾校考题接口
        名称  	    必填  	类型	    说明
        key         是	    string	您申请的appKey
        subject	    是	    int	    选择考试科目类型，1：科目1；4：科目4
        model	    是	    string	驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略
        testType	否	    string	测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）
     */
    @GET("http://v.juhe.cn/jztk/query?key=da894efab9897c2b9566639a7aa3acd7")
    Observable<DriverQuestion> getDriverQuestions(@Query("subject") int subject,@Query("model") String model,@Query("testType") String testType);

    /**
     * 获取新闻接口
     * @param type
     * 	名称  	必填  	类型	    说明
        key	    是	    string	应用APPKEY
        type	否	    string	类型:top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    @GET("http://v.juhe.cn/toutiao/index?key=238ca532342eb15fb820f1f7fe08c2d6")
    Observable<TopNews> getNews(@Query("type") String type);
    /**
     * 获取天气种类类型
     */
    @GET("http://apis.juhe.cn/simpleWeather/wids?key=e0887776b29f6e6ccdcab54fbc7df132")
    Observable<WeatherWids> getWeatherWids();
    /**
     * 获取城市列表
     */
    @GET("http://apis.juhe.cn/simpleWeather/cityList?key=e0887776b29f6e6ccdcab54fbc7df132")
    Observable<CityList> getCityList();
    /**
     * 获取天气预报接口
     * @param city 要查询的城市名称/id，城市名称如：温州、上海、北京，需要utf8 urlencode
     */
    @GET("http://apis.juhe.cn/simpleWeather/query?key=e0887776b29f6e6ccdcab54fbc7df132")
    Observable<Weather> getWeather(@Query("city") String city);
    /**
     * 获取股票数据接口(深圳股市列表)
     *  名称	    必填	    类型	    说明
        key	    是	    string	您申请的APPKEY
        stock	否	    string	a表示A股，b表示B股,默认所有
        page	否	    int	第几页,默认第1页
        type	否	    int	每页返回条数,1(20条默认),2(40条),3(60条),4(80条)
     */
    @GET("http://web.juhe.cn:8080/finance/stock/szall?key=894303de5ebc96490ad22506f96f8d1c")
    Observable<Stock> getStock(@Query("page") int page,@Query("type") int type);

    /**
     * 获取笑话大全
     * @param page
     * @param pagesize
        名称  	    必填	    类型	    说明
        sort	    是	    string	desc:指定时间之前发布的，asc:指定时间之后发布的
        page	    否	    int	    当前页数,默认1,最大20
        pagesize	否	    int	    每次返回条数,默认1,最大20
        time	    是	    string	时间戳（10位）秒为单位，如：1418816972
     */
    @GET("http://v.juhe.cn/joke/content/list.php?key=7e7404aae472a996f8df537ad5ceea55&sort=desc")
    Observable<Jokes> getJokeList(@Query("page") int page, @Query("pagesize") int pagesize, @Query("time") long time);

    @POST("/login")
    Observable<String> login(@Query("loginCode") String loginCode, @Query("password") String password);

}