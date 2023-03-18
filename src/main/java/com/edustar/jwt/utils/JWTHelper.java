package com.edustar.jwt.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;
import java.util.Map;

public class JWTHelper {

    private JWTHelper() {
    }

    public static String createJWT(Map<String, Object> claims, String issuer, String secretKey, long ttl) {

        final JwtBuilder builder = Jwts.builder();

        final String id = (String) claims.get("userId");
        final String subject = (String) claims.get("UserInfo");

        final long nowMillis = System.currentTimeMillis();
        final Date now = new Date(nowMillis);
        // TODO: PERFORMANCE
        final byte[] signingKey = TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");

        builder.setId(id).setIssuedAt(now).setIssuer(issuer).setSubject(subject).addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signingKey);
        System.out.println("OCC!!! => " + ttl);
        // TODO: ELSE SCENARIO
        if (ttl >= 0) {
            long expMillis = nowMillis + ( ttl * 60 * 1000);
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        System.out.println("builder => " + builder);

        return builder.compact();
    }

}
