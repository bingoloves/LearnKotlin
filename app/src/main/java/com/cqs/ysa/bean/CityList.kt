package com.cqs.ysa.bean

/**
 * Created by Administrator on 2020/7/20 0020.
 */

class CityList {

    /**
     * reason : 查询成功
     * result : [{"id":"1","province":"北京","city":"北京","district":"北京"},{"id":"2","province":"北京","city":"北京","district":"海淀"},{"id":"3","province":"北京","city":"北京","district":"朝阳"},{"id":"4","province":"北京","city":"北京","district":"顺义"},{"id":"5","province":"北京","city":"北京","district":"怀柔"},{"id":"6","province":"北京","city":"北京","district":"通州"}]
     * error_code : 0
     */

    var reason: String? = null
    var error_code: Int = 0
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * id : 1
         * province : 北京
         * city : 北京
         * district : 北京
         */

        var id: String? = null
        var province: String? = null
        var city: String? = null
        var district: String? = null
    }
}
