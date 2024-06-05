package com.generation.blogpessoal.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * Objetivo da classe criar e confirmar
 */

@Component
public class JwtService {
	
	//é uma constante que gera uma chave para encodar as inforamações do token 
	public static final String SECRET = "f1bd5e50e84d1b0cefc99865501f8a3d9c9c5254afd51360aa511d9c3d9904b3";
	
	// O token é composto pelo email do usuario ex: matheus@gmail.com 2024-06-04 9:40 assinatura
	
	//assinatura
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET); //encodando a Secret 
		return Keys.hmacShaKeyFor(keyBytes); //retorna a chave já tratada 
	}
	/*
	 * claims - declarações usuario | declaração data que expira | declaração da assinatura
	 * nesse caso assinatura 
	 */

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}

	// pega a assinatura extraida e trata ela para tornar ela entendivel 
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/*
	 * recuperar os dados da parte sub do claim onde encontramos o email (usuario)
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	/*
	 * data que o token expira
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/*
	 * valida se a data que p token expira esta dentro da validade ou seja a data atual ainda não atingiu essa data 
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/*
	 * validar se o usuario que foi extraido do token condiz com o usuario que a userDetails tem e se está dentro da 
	 * data de validade ainda o token 
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	/*
	 * objetivo oe calcular o tempo de validade do token, formar o claim com as informações do token
	 */
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	/*
	 * gerar o token puxando os claims formados no metodo anterior 
	 */
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
	
	
}
