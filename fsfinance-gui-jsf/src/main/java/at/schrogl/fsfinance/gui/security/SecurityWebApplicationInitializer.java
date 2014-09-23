package at.schrogl.fsfinance.gui.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer
      extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
        super(SpringSecurityConfiguration.class);
    }
	
}