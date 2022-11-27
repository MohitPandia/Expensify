package com.expensify.expensify.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.expensify.expensify.entity.User;
import com.expensify.expensify.service.UserService;
import com.expensify.expensify.utility.JWTUtility;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtility jwtUtility;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		String token = null;
		String userName = null;

		if (authorization != null && authorization.startsWith("Bearer ")) {
			token = authorization.substring(7);
			userName = jwtUtility.getUsernameFromToken(token);
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User userDetails = userService.loadUserByUserName(userName);

			if (jwtUtility.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, userDetails, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
