package com.ruoyi.framework.manager.factory;

import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import com.ruoyi.common.util.ServletUtils;
import com.ruoyi.common.util.ip.AddressUtils;
import com.ruoyi.common.util.ip.IpUtils;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {
    // 静态常量 sys_user_logger，它是一个 Logger 实例，用于记录日志信息
    // 调用 LoggerFactory 的 getLogger 方法，并传递了一个字符串 "sys-user" 作为参数。这个字符串是日志记录器的名称，它允许你在配置文件中为不同的记录器设置不同的日志级别和格式
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());

        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIp(ip);
            }
        }
    }
}
