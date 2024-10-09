package com.ruoyi.framework.manager;

import com.ruoyi.common.util.spring.SpringUtils;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 异步任务管理器
 *
 * @author ruoyi
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean(("scheduledExecutorService"));

    /**
     * 单例模式
     */
    private AsyncManager() {}

    private static AsyncManager me = new AsyncManager();
}
