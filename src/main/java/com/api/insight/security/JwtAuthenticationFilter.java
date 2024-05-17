package com.api.insight.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		// Bearer 2352345235sdfrsfgsdfsdf

		String userEmail = null;
		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			// looking good
			token = requestToken.substring(7);
			try {

				userEmail = this.jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				System.out.println("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				System.out.println("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				System.out.println("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			System.out.println("Invalid Header Value !! ");
		}

		// once we get the token validate it
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {

				// set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				System.out.println("Validation fails !!");
			}

		}
		filterChain.doFilter(request, response);
	}

}
