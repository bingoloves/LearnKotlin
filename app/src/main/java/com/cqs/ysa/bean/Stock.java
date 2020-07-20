package com.cqs.ysa.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/7/20 0020.
 */

public class Stock {

    /**
     * error_code : 0
     * reason : SUCCESSED!
     * result : {"totalCount":"2268","page":"1","num":"20","data":[{"symbol":"sz000001","name":"平安银行","trade":"14.570","pricechange":"0.430","changepercent":"3.041","buy":"14.570","sell":"14.580","settlement":"14.140","open":"14.230","high":"14.650","low":"14.100","volume":1178742,"amount":1697410583,"code":"000001","ticktime":"11:24:39"}]}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

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

    public static class ResultBean {
        /**
         * totalCount : 2268
         * page : 1
         * num : 20
         * data : [{"symbol":"sz000001","name":"平安银行","trade":"14.570","pricechange":"0.430","changepercent":"3.041","buy":"14.570","sell":"14.580","settlement":"14.140","open":"14.230","high":"14.650","low":"14.100","volume":1178742,"amount":1697410583,"code":"000001","ticktime":"11:24:39"}]
         */

        private String totalCount;
        private String page;
        private String num;
        private List<DataBean> data;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * symbol : sz000001
             * name : 平安银行
             * trade : 14.570
             * pricechange : 0.430
             * changepercent : 3.041
             * buy : 14.570
             * sell : 14.580
             * settlement : 14.140
             * open : 14.230
             * high : 14.650
             * low : 14.100
             * volume : 1178742
             * amount : 1697410583
             * code : 000001
             * ticktime : 11:24:39
             */

            private String symbol;  /*代码*/
            private String name;    /*名称*/
            private String trade;   /*最新价*/
            private String pricechange; /*涨跌额*/
            private String changepercent;/*涨跌幅*/
            private String buy;/*买入*/
            private String sell;/*卖出*/
            private String settlement;/*昨收*/
            private String open;/*今开*/
            private String high;/*最高*/
            private String low;/*最低*/
            private int volume;/*成交量*/
            private long amount;/*成交额*/
            private String code;
            private String ticktime;/*时间*/

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTrade() {
                return trade;
            }

            public void setTrade(String trade) {
                this.trade = trade;
            }

            public String getPricechange() {
                return pricechange;
            }

            public void setPricechange(String pricechange) {
                this.pricechange = pricechange;
            }

            public String getChangepercent() {
                return changepercent;
            }

            public void setChangepercent(String changepercent) {
                this.changepercent = changepercent;
            }

            public String getBuy() {
                return buy;
            }

            public void setBuy(String buy) {
                this.buy = buy;
            }

            public String getSell() {
                return sell;
            }

            public void setSell(String sell) {
                this.sell = sell;
            }

            public String getSettlement() {
                return settlement;
            }

            public void setSettlement(String settlement) {
                this.settlement = settlement;
            }

            public String getOpen() {
                return open;
            }

            public void setOpen(String open) {
                this.open = open;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
            }

            public long getAmount() {
                return amount;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTicktime() {
                return ticktime;
            }

            public void setTicktime(String ticktime) {
                this.ticktime = ticktime;
            }
        }
    }
}
