package com.ruoyi.framework.web.service;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.util.DateUtils;
import com.ruoyi.common.util.ServletUtils;
import com.ruoyi.common.util.StringUtil;
import com.ruoyi.common.util.ip.IpUtils;
import org.springframework.security.authentication.AuthenticationManager;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.util.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

import javax.annotation.Resource;

@Component  // 将此类标记为Spring容器中的一个Bean
public class SysLoginService {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Resource   // 自动注入依赖
    private AuthenticationManager authenticationManager;    // 是Spring Security中的一个核心接口，用于处理认证请求

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    public String login(String username, String password, String code, String uuid) {
        // 为空的话，就是 true,
        boolean captchaEnabled = configService.selectCaptchaEnabled();

        // 验证码开关
        if ( captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }

        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);

            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if ( e instanceof BadCredentialsException) {
                // 类型：密码不匹配
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        // 构建Redis缓存中验证码的键
        // StringUtil.nvl: 用于处理可能的null值
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtil.nvl(uuid, "");
        // 从Redis缓存中获取验证码值
        String captcha = redisCache.getCacheObject(verifyKey);
        // 从Redis缓存中删除验证码条目，通常在验证码使用一次后进行删除
        redisCache.deleteObject(verifyKey);

        // 验证码不存在或已过期
        if (captcha == null) {
            // 异步记录登录信息, 类型：到期，失效
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        // 验证码 不等于 缓存中的验证码
        if (!code.equalsIgnoreCase(captcha)) {
            // 类型：错误
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
