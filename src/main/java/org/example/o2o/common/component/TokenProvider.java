package org.example.o2o.common.component;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.example.o2o.common.dto.jwt.TokenDto.AccessTokenClaimsInfo;
import org.example.o2o.common.dto.jwt.TokenDto.Payload;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.TokenErrorCode;
import org.example.o2o.config.exception.enums.common.CommonErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {

	private final ObjectMapper objectMapper;
	private static SecretKey SECRET_KEY;
	private static long ACCESS_EXPIRATION;
	private static long REFRESH_EXPIRATION;
	private static final String ACCESS_TOKEN_SUBJECT = "Access Token";
	private static final String REFRESH_TOKEN_SUBJECT = "Refresh Token";

	public TokenProvider(
		@Value("${jwt.secret}") String secreyKey,
		@Value("${jwt.access.expiration}") long accessExpiration,
		@Value("${jwt.refresh.expiration}") long refreshExpiration,
		ObjectMapper objectMapper
	) {
		byte[] secretKeyBytes = Base64.getDecoder().decode(secreyKey);
		SECRET_KEY = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "HmacSHA256");
		ACCESS_EXPIRATION = accessExpiration;
		REFRESH_EXPIRATION = refreshExpiration;
		this.objectMapper = objectMapper;
	}

	public String createAccessToken(AccessTokenClaimsInfo claimsInfo) {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(ACCESS_EXPIRATION);

		return Jwts.builder()
			.issuedAt(Date.from(now.toInstant()))
			.expiration(Date.from(tokenValidity.toInstant()))
			.subject(ACCESS_TOKEN_SUBJECT)
			.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
			.claims(
				Jwts.claims()
					.add("id", claimsInfo.getId())
					.add("accountId", claimsInfo.getAccountId())
					.add("role", claimsInfo.getRole())
					.add("name", claimsInfo.getName())
					.build()
			)
			.compact();
	}

	public String createRefreshToken(Long id) {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(REFRESH_EXPIRATION);

		return Jwts.builder()
			.issuedAt(Date.from(now.toInstant()))
			.expiration(Date.from(tokenValidity.toInstant()))
			.subject(REFRESH_TOKEN_SUBJECT)
			.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
			.claims(
				Jwts.claims()
					.add("id", id)
					.build()
			)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(SECRET_KEY).build().parse(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.", e);
		} catch (SignatureException e) {
			log.error("SignatureException: ", e);
		}
		return false;
	}

	public Payload extractPayload(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
			return objectMapper.convertValue(claims, Payload.class);
		} catch (IllegalArgumentException e) {
			throw new ApiException(CommonErrorCode.UNKNOWN_ERROR);
		} catch (JwtException e) {
			throw new ApiException(TokenErrorCode.INVALID_TOKEN);
		}
	}

}
