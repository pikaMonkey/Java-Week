package com.pkh.configuraton.shiro;

import com.auth0.jwt.JWT;
import com.pkh.commons.jwt.JWTCredentialMatcher;
import com.pkh.commons.jwt.JWTToken;
import com.pkh.dao.po.User;
import com.pkh.service.UserService;
import com.pkh.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Resource
    UserService userService;

    @Resource
    RedisUtil redisUtil;

    /**
     * 指定凭证匹配器，匹配器工作在认证后，授权前
     */
    public UserRealm() {
        this.setCredentialsMatcher(new JWTCredentialMatcher());
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken Shiro认证token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 验证jwt缓存是否命中，即token是否失效
        JWTToken jwtToken = (JWTToken)authenticationToken;
        String token = jwtToken.getToken();
        if (ObjectUtils.isEmpty(redisUtil.get(token))) {
            return null;
        }
        // 解析token，账号是否存在，账号是否被锁定
        String username = JWT.decode(token).getClaim("username").asString();
        User user = userService.getByUserId(username);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        //将用户名和密码发送要密码匹配器中
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, token,"userRealm");
        return authenticationInfo;
    }

    /**
     * 判断token是否为JWTToken，必须重写
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
