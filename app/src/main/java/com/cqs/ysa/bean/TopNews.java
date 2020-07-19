package com.cqs.ysa.bean;

import java.util.List;

/**
 * 头条新闻
 */
public class TopNews {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"214c8897831330885c8d18ed24dedc49","title":"爆笑GIF图片：凡是能做出这种高难度动作的人，一般都是练瑜伽的！","date":"2020-07-18 15:29","category":"头条","author_name":"逗开心","url":"https://mini.eastday.com/mobile/200718152959893.html","thumbnail_pic_s":"https://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_4_mwpm_03200403.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"uniquekey":"214c8897831330885c8d18ed24dedc49","title":"爆笑GIF图片：凡是能做出这种高难度动作的人，一般都是练瑜伽的！","date":"2020-07-18 15:29","category":"头条","author_name":"逗开心","url":"https://mini.eastday.com/mobile/200718152959893.html","thumbnail_pic_s":"https://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09imgmini.eastday.com/mobile/20200718/20200718152959_a4869ca27ce45036f1cd9268473a81e4_4_mwpm_03200403.jpg"}]
         */

        private String stat;
        private List<News> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<News> getData() {
            return data;
        }

        public void setData(List<News> data) {
            this.data = data;
        }
    }
}
