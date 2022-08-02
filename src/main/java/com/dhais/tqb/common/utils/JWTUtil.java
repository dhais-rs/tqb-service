package com.dhais.tqb.common.utils;

import cn.hutool.json.JSONUtil;
import com.dhais.tqb.common.exception.ServiceException;
import com.dhais.tqb.common.model.HttpCode;
import com.dhais.tqb.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    private final static Map<String,String> TOKEN_MAP = new HashMap<>();

    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();
    /**
     * jwt私钥
     */
    final static String BASE64_ENCODED_SECRET_KEY = "YWRtaW4=";
    //过期时间,测试使用十分钟
    final static long TOKEN_EXP = 1000 * 60 * 10;

    public static User getUserInfo(){
        return USER_THREAD_LOCAL.get();
    }


    public static String getToken(User user) {
        String userStr = JSONUtil.toJsonStr(user);

        String token = Jwts.builder()

                        .setSubject(userStr)

                        .claim("roles", "user")

                        .setIssuedAt(new Date())
                        /*过期时间*/
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP))

                        .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_SECRET_KEY)

                        .compact();
        TOKEN_MAP.put(user.getUserName(),token);
        return token;
    }

    /**
     * 检查token,只要不正确就会抛出异常
     **/

    public static Claims checkToken(String token) throws ServiceException {

        try {

            final Claims claims = Jwts.parser().setSigningKey(BASE64_ENCODED_SECRET_KEY).parseClaimsJws(token).getBody();
            if (!TOKEN_MAP.containsValue(token)) {
                throw new ExpiredJwtException(null,null,null);
            }
            User user = JSONUtil.toBean(claims.getSubject(), User.class);
            USER_THREAD_LOCAL.set(user);
            return claims;

        } catch (ExpiredJwtException e1) {

            throw new ServiceException(HttpCode.FORBIDDEN,"登录信息过期，请重新登录");

        } catch (Exception e) {

            throw new ServiceException(HttpCode.UNAUTHORIZED,"用户未登录，请重新登录");

        }

    }


}
