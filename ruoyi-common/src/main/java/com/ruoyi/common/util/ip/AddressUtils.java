package com.ruoyi.common.util.ip;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.util.http.HttpUtils;

public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIp(String ip) {
        // 内网不查询
        if(IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled()) {
            try {
                // get 请求后的响应字符串
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK );
                if (StringUtil.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }

                // 解析JSON字符串
                JSONObject obj = JSON.parseObject(rspStr);
                // getString 方法来获取JSON对象中与指定键（这里是 "pro"）关联的字符串值
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            } catch ( Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
