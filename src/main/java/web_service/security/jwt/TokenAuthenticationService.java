package web_service.security.jwt;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web_service.DAO.DatabaseDAO;
import web_service.SpringConf.DBConf;
import web_service.model.Lockouts;
import web_service.security.model.AuthenticatedUser;

@PropertySource(value = { "classpath:other.properties" })
public class TokenAuthenticationService {

    @Autowired
    private Environment env;
	
    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String headerString = "Authorization";
    public void addAuthentication(HttpServletResponse response, String username)
    {
    	if(!username.isEmpty() && !isUserLocked(username)) {
	    	Map<String,Object> claims = new HashMap<>();
	    	List<String> roles = new ArrayList<>();
	    	roles.add("LOGIN");
	    	claims.put("roles", roles);
	    	claims.put("username", username);
	        String JWT = Jwts.builder()
                    	.setClaims(claims)
	                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	                    .setAudience("test")
	                    .signWith(SignatureAlgorithm.HS512, (String) env.getProperty("jwt.secret"))
	                    .compact();
	        response.addHeader(headerString, JWT);
	        response.addHeader("State", "true");
    	}
    	else {
            response.addHeader("State", "false");
    	}
    }

    public Authentication getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(headerString);
        if(token != null)
        {         
        	Claims claims = getClaims(token);
        	if(claims != null) {
	        	String username = claims.get("username").toString();
	            if(username != null)
	            {
	        		if(claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
	        			return null;
	        		}
	        		if(isUserLocked(username)) {
	        			return null;	        		
	        		}
	        		else {
	        			return new AuthenticatedUser(username);	        		
	        		}
	            }
	            else {
	            	return null;
	            }
        	}
        }
        return null;
    }

    private Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey((String) env.getProperty("jwt.secret"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    
    private boolean isUserLocked(String username) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Lockouts> DAO = context.getBean(DatabaseDAO.class);
		List<Lockouts> lock = new ArrayList<>();
		lock = DAO.list("select e from Lockouts e where e.login_employee='" + username + "'");
		context.close();
		if(lock.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
    }
}
