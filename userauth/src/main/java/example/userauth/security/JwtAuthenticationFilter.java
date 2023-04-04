package example.userauth.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service	
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		
		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		String userId = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				userId = this.jwtTokenHelper.getUserIdFromToken(token); 	
			}
			catch(IllegalArgumentException ex) {
				System.out.println("Unable to Get JWT Token");
			}
			catch(ExpiredJwtException ex) {
				System.out.println("JWT Token has expired");
			}
			catch(MalformedJwtException ex) {
				System.out.println("Invalid JWT");
			} 
			
		}
		else {
			System.out.println("JWT Token Does not begin with Bearer");
		}
		
		if(userId != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userId);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Invalid JWT Token");
			}
		}
		else {
			System.out.println("UserId is null or context is not null");
		}
			
		filterChain.doFilter(request, response);
	}

	
}
