package com.registerWithJWT.registerWithJWT.servlet;

import com.registerWithJWT.registerWithJWT.entity.UserAuthToken;
import com.registerWithJWT.registerWithJWT.exception.AuthorizationFailedException;
import com.registerWithJWT.registerWithJWT.exception.RestErrorCode;
import com.registerWithJWT.registerWithJWT.exception.UnauthorizedException;
import com.registerWithJWT.registerWithJWT.provider.BearerAuthDecoder;
import com.registerWithJWT.registerWithJWT.service.AuthTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.registerWithJWT.registerWithJWT.constants.ResourceConstants.BASIC_AUTH_PREFIX;


@Component
public class AuthFilter extends ApiFilter {

	@Autowired
	private AuthTokenService authTokenService;

	@Override
	public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		if (servletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}

		final String pathInfo = servletRequest.getRequestURI();
		if (!pathInfo.contains("register") && !pathInfo.contains("actuator") && !pathInfo.contains("doctors")) {
			final String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			if (StringUtils.isEmpty(authorization)) {
				throw new UnauthorizedException(RestErrorCode.ATH_001);
			}

			if (pathInfo.contains("login") && !authorization.startsWith(BASIC_AUTH_PREFIX)) {
				throw new UnauthorizedException(RestErrorCode.ATH_002);
			}

			if (!pathInfo.contains("login")) {
				final String accessToken = new BearerAuthDecoder(authorization).getAccessToken();
				try {
					final UserAuthToken userAuthTokenEntity = authTokenService.validateToken(accessToken);
					servletRequest.setAttribute(HttpHeaders.AUTHORIZATION, userAuthTokenEntity.getUser().getEmailId());
				} catch (AuthorizationFailedException e) {
					servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
					return;
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

}
