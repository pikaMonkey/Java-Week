package com.pkh.commons.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pkh.dao.po.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JWTCredentialMatcher extends HashedCredentialsMatcher {

    private static final String HEXITS = "0123456789abcdef";
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        String token = String.valueOf(authenticationToken.getCredentials());
        User user =(User) info.getPrincipals().getPrimaryPrincipal();
        try {
//            return JWTUtil.verify(token, user.getUserName(), user.getPassword());
            return true;
        } catch (TokenExpiredException e) {

            throw new RuntimeException("token过期");
        }
    }
    /**
     * 检查用户输入的密码，加密后是否和数据库存储的密码一致
     * 1. 截取随机密码
     * 2. 根据随机密码生成sha1摘要
     * 3. 根据sha1摘要和模式串生成乱码密钥
     * 4. 对比新生成的乱码密钥和数据库中存储的乱码密钥是否一致
     *
     * sha1$raYr1$da39a3ee5e6b4b0d3255bfef95601890afd80709
     *
     * @param password 密码
     * @param encryptPassword 存储的加密后的密码
     * @return
     * @throws Exception
     */
    public static boolean checkPassword(String password, String encryptPassword) throws Exception {
        String[] pwdList = encryptPassword.split("\\$");
        if (pwdList.length == 3) {
            if (pwdList[0].equals("sha1")) {
                MessageDigest sha1 = createSha1(pwdList[1] + password);
                String pwdHash = getPwdHash(sha1, HEXITS);
                if (pwdList[2].equals(pwdHash)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 对明文密码进行加密
     * 1. 密码+随机数组成随机字符串
     * 2. 对随机字符串生成sha1摘要
     * 3. 根据sha1摘要和模式串生成乱码密钥
     *
     * 加密后密码样例：sha1$raYr1$da39a3ee5e6b4b0d3255bfef95601890afd80709
     *
     * @param password 明文密码
     * @return
     * @throws Exception
     */
//    public static String encryptPassword(String password) throws Exception {
//        String random = SysTool.getRandomString(5);
//        String value = random + password;
//        MessageDigest sha1 = createSha1(value);
//        String pwdHash = getPwdHash(sha1, HEXITS);
//        return "sha1$" + random + "$" + pwdHash;
//    }

    /**
     * 根据明文密码生成sha1摘要
     *
     * @param value 明文密码
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static MessageDigest createSha1(String value) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("sha1");
        sha1.reset();
        sha1.update(value.getBytes());
        return sha1;
    }

    /**
     * 根据sha1摘要 和 模式串生成密码
     *
     * @param sha1
     * @param hexits
     * @return
     */
    private static String getPwdHash(MessageDigest sha1, String hexits) {
        byte[] pwd = sha1.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : pwd) {
            sb.append(hexits.charAt((b >>> 4) & 0xf));
            sb.append(hexits.charAt(b & 0xf));
        }
        return sb.toString();
    }
}
