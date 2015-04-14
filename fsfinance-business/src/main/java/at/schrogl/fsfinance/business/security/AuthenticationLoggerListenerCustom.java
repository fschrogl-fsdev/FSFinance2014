/**
 * This file is part of FSFinance.
 *
 * FSFinance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSFinance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSFinance. If not, see <http://www.gnu.org/licenses/>.
 */
package at.schrogl.fsfinance.business.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.enums.Authorities;

/**
 * Custom implementation of {@link LoggerListener}.
 * <p>
 * Logs all authentication-related application events using SLF4J. Successful
 * authentication events are logged with severity INFO, authentication failures
 * are logged with severity WARN. By default only
 * {@link AbstractAuthenticationFailureEvent}s and
 * {@link AuthenticationSuccessEvent}s are logged, unless
 * {@link #isLogOtherEvents()} is set to <code>true</code> and/or debug logging
 * is enabled.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
/* Bean definition in spring-security.xml */
public class AuthenticationLoggerListenerCustom implements ApplicationListener<AbstractAuthenticationEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationLoggerListenerCustom.class);

	private boolean logOtherEvents = false;

	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		if (event instanceof AbstractAuthenticationFailureEvent) {
			Object[] args = new Object[3];
			args[0] = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();
			args[1] = event.getAuthentication().getName();
			args[2] = ((AbstractAuthenticationFailureEvent) event).getException().getMessage();
			LOGGER.warn("Authentication failure! IP=[{}] User=[{}] Message=[{}]", args);
		} else if (event instanceof AuthenticationSuccessEvent) {
			Object[] args = new Object[3];
			args[0] = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();
			args[1] = ((UserDetailsCustom) event.getAuthentication().getPrincipal()).getPrincipal();
			args[2] = ((User) args[1]).getAuthorities().contains(Authorities.ROLE_ADMIN);
			LOGGER.info("Authentication successfull! IP=[{}] User=[{}] isAdmin=[{}]", args);
		} else if (LOGGER.isDebugEnabled() || logOtherEvents) {
			LOGGER.debug("Authentication event :: {} msg={}", event.getClass().getSimpleName(), event);
		}
	}

	/**
	 * Enable/Disable logging of other event (categories).
	 * 
	 * @param logOtherEvents
	 * 			Enables/disables logging of other events (categories). Defaults to <code>false</code>.
	 */
	public void setLogOtherEvents(boolean logOtherEvents) {
		this.logOtherEvents = logOtherEvents;
	}

	public boolean isLogOtherEvents() {
		return logOtherEvents;
	}

}
