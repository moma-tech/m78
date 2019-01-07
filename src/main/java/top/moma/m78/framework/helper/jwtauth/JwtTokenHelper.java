package top.moma.m78.framework.helper.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import top.moma.m78.framework.constants.ApiConstants;
import top.moma.m78.framework.constants.enumeration.HttpStatusCodeEnum;
import top.moma.m78.framework.customizer.exception.exceptions.ApiException;
import top.moma.m78.framework.helper.TypeHelper;

/**
 * JwtTokenHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:58 PM.
 */
public class JwtTokenHelper {
  private static final long EXPIRE = 60 * 60 * 1000;

  public static String create(String claimKey, long hours) {
    String secret = ApiConstants.TOKEN_SECRET;
    Date now = new Date();
    Date expire = new Date(now.getTime() + hours * EXPIRE * 1000);
    Map<String, Object> claims = new HashMap<>(2);
    claims.put(ApiConstants.CLAIM_KEY, claimKey);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expire)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  static Claims getClams(String token) {
    String secret = ApiConstants.TOKEN_SECRET;
    Claims claims = null;
    try {
      claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      throw new ApiException(HttpStatusCodeEnum.TOKEN_INVALID);
    }
    return claims;
  }

  public static String getClaimValue(String token) {
    return TypeHelper.castToString(getClams(token).get(ApiConstants.CLAIM_KEY));
  }
}
