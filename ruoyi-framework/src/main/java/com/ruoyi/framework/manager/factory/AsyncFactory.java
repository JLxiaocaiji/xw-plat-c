package com.ruoyi.framework.manager.factory;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.util.StringUtil;
import com.ruoyi.common.util.spring.SpringUtils;
import com.ruoyi.system.domain.SysLogininfor;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import com.ruoyi.common.util.LogUtils;
import com.ruoyi.common.util.ServletUtils;
import com.ruoyi.common.util.ip.AddressUtils;
import com.ruoyi.common.util.ip.IpUtils;
import com.ruoyi.system.service.ISysLogininforService;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {
    // 静态常量 sys_user_logger，它是一个 Logger 实例，用于记录日志信息
    // 调用 LoggerFactory 的 getLogger 方法，并传递了一个字符串 "sys-user" 作为参数。这个字符串是日志记录器的名称，它允许你在配置文件中为不同的记录器设置不同的日志级别和格式
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        // 解析用户代理字符串
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取IP地址
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());

        // 创建并返回一个TimerTask
        return new TimerTask() {
            @Override
            public void run() {
                // 通过 ip 找真实地址
                String address = AddressUtils.getRealAddressByIp(ip);
                // 构建日志信息
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));

                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装登录信息对象
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);

                // 日志状态
                // StringUtil.equalsAny 方法通常用于检查一个字符串是否与提供的任何一个字符串匹配,是否为 Success，Logout，Register 中的某一个
                if (StringUtil.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)){
                    logininfor.setStatus(Constants.LOGIN_SUCCESS);
                }else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }

                // 插入数据
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }
}
