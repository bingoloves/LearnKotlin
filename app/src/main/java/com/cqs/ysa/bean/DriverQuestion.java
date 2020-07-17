package com.cqs.ysa.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/7/17 0017.
 */

public class DriverQuestion {

    /**
     * reason : ok
     * result : [{"id":"6","question":"这个标志是何含义？","answer":"4","item1":"右转车道","item2":"掉头车道","item3":"左转车道","item4":"直行车道","explains":"表示只准一切车辆直行。此标志设在直行的路口以前适当位置。","url":"http://images.juheapi.com/jztk/c1c2subject1/6.jpg"},{"id":"21","question":"这个标志是何含义？","answer":"4","item1":"平面交叉路口","item2":"环行平面交叉","item3":"注意交互式道路","item4":"注意分离式道路","explains":"注意分离式道路：用以警告车辆驾驶人注意前方平面交叉的被交道路是分离式道路。","url":"http://images.juheapi.com/jztk/c1c2subject1/21.jpg"},{"id":"24","question":"指示标志的作用是什么？","answer":"4","item1":"告知方向信息","item2":"警告前方危险","item3":"限制车辆、行人通行","item4":"指示车辆、行人行进","explains":"指示标志是交通标志中主要标志的一种，用以指示车辆和行人按规定方向、地点行驶。 指示标志的颜色为蓝底、白色图案；形状分为圆形、长方形和正方形。","url":""}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<Question> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }
}
