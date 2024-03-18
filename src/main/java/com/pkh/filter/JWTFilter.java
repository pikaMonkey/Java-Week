package com.pkh.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkh.commons.jwt.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
    private static String LOGIN_SIGN = "X-Token";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = ((HttpServletRequest) request).getHeader(LOGIN_SIGN);
        if (!StringUtils.hasLength(token)) {
            try {
                setReturnInfo((HttpServletResponse) response, 401, "请求token为空");
            } catch (IOException e) {
                log.info("isAccessAllowed 出错: {}", e.getMessage());
            }
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)   {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader(LOGIN_SIGN);

        JWTToken jwtToken = new JWTToken(token);
        // 提交给realm进行登录，如果错误他会抛出异常并被捕获
        try {
            this.getSubject(request, response).login(jwtToken);
        } catch (Exception e) {
            try {
                setReturnInfo(httpServletResponse,401,"登录认证出现异常");
            } catch (IOException ex) {
                log.info(e.getMessage());
            }
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }

    private static void setReturnInfo(HttpServletResponse response, int status, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, String> result = new HashMap<>();
        result.put("status", String.valueOf(status));
        result.put("msg", msg);

        ObjectMapper mapper = new ObjectMapper();
        String jsonRes = mapper.writeValueAsString(result);
        response.getWriter().write(jsonRes);
    }
}
