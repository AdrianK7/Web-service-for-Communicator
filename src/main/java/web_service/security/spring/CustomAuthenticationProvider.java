package web_service.security.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import web_service.DAO.DatabaseDAO;
import web_service.SpringConf.DBConf;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String passwordDatabase = DAO.select("select e.password from Employees e where e.login='" + name + "'");
		context.close();
		
		if(BCrypt.checkpw(password, passwordDatabase)) {
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority("LOGIN"));
			Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
			return auth;
		} 
		else {
			
			Authentication auth = new UsernamePasswordAuthenticationToken(null, null, null);
			return auth;
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}