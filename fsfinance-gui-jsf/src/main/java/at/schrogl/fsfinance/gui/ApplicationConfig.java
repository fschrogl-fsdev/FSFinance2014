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
package at.schrogl.fsfinance.gui;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

@ManagedBean
@ApplicationScoped
public class ApplicationConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	private ProjectStage projectStage;

	@ManagedProperty("#{msg.application_version}")
	private String appVersion;

	@ManagedProperty("#{propertyPlaceholder}")
	private PropertySourcesPlaceholderConfigurer propertyPlaceholder;
	private PropertySource<?> properties;

	@PostConstruct
	public void initialize() {
		PropertySources sources = propertyPlaceholder.getAppliedPropertySources();
		properties = sources.get("localProperties");

		if (properties == null) {
			LOGGER.error("No user-supplied localProperties found and fallback properties not configured!");
		} else {
			String configType = (String) properties.getProperty("app.config");
			LOGGER.info("Found user-supplied localProperties! app.config={}", configType);
		}

		// Determine the configured javax.faces.PROJECT_STAGE
		projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
		LOGGER.info("Application PROJECT_STAGE: {}", projectStage);

		// Extract project's version from Messages.properties
		LOGGER.info("Application version: {}", appVersion);
	}

	public String getProperty(String key) {
		return (String) properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		Object property = properties.getProperty(key);
		return (property != null) ? property.toString() : defaultValue;
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================
	
	public void setPropertyPlaceholder(PropertySourcesPlaceholderConfigurer propertyPlaceholder) {
		this.propertyPlaceholder = propertyPlaceholder;
	}

	public boolean isDevModeActive() {
		return (ProjectStage.Development == projectStage);
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}
}
