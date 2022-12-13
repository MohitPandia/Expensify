package com.expensify.expensify.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.expensify.expensify.Exception.Filter.AuthenticationFailureException;
import com.expensify.expensify.dto.ErrorResponseDTO;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.service.UserService;
import com.expensify.expensify.utility.JWTUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		System.out.println(authorization);
		boolean find = false;
		try {
			if (authorization != null && authorization.startsWith("Bearer ")) {
				token = authorization.substring(7);
				userName = jwtUtility.getUsernameFromToken(token);
			} else {
				AntPathMatcher path = new AntPathMatcher();
				String requestPath = request.getRequestURI().substring(request.getContextPath().length());
				String[] resources = new String[] { "/user/register", "/user/login" };
				for (String paths : resources) {
					if (path.match(paths, requestPath)) {
						find = true;
						break;
					}
					System.out.println(paths + " " + requestPath);
				}

				System.out.println(requestPath);
				if (find) {
					filterChain.doFilter(request, response);
					return;
				} else
					throw new AuthenticationFailureException("Authorization token is not found");
			}

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null && !find) {
				User userDetails = userService.loadUserByUserName(userName);

				if (jwtUtility.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, userDetails, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} else {
					throw new AuthenticationFailureException("Authentication token is not valid");
				}
			} else {
				throw new AuthenticationFailureException("Authentication token is not valid");
			}
		} catch (Exception ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter out = response.getWriter();
			System.out.println(ex.getMessage());
			out.print(
					mapper.writeValueAsString(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())));
			out.flush();
			return;
		}

		filterChain.doFilter(request, response);
	}

}
