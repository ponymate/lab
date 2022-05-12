package com.mjw.lab.utils;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    // token时效：24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 根据用户id和昵称生成token
     * @param id  用户id
     * @param name 用户昵称
     * @return JWT规则生成的token
     */
    public static String getJwtToken(Long id, String name,String type){

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //自定义
                .setSubject("pony")
                //设置签发时间
                .setIssuedAt(new Date())
                //设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //body数据，自行设置
                .claim("id", id)
                .claim("name", name)
                .claim("type",type)
                // HS256算法实际上就是MD5加盐值，此时APP_SECRET就代表盐值
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            //推测：测试得到如果claim中只有一个值的话不能使用parseClaimsJws
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request Http请求对象
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            // 从http请求头中获取token字符串
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Long getIdByToken(String token){
        Jwt parse = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Object temp = claims.get("id");

        return Long.valueOf(temp.toString());
    }


    public static String getTypeByToken(String token) {
        Jwt parse = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("type");
    }

    /**
     * 根据token获取会员id
     * @param request Http请求对象
     * @return 解析token后获得的用户id
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }


}