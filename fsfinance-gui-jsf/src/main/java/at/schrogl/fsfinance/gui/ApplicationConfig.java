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
import java.util.Properties;
import java.util.PropertyResourceBundle;

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
	private String appVersion;
	
	@ManagedProperty("#{msg}")
	private PropertyResourceBundle msg;
	
	@ManagedProperty("#{propertyPlaceholder}")
	private transient PropertySourcesPlaceholderConfigurer propertyPlaceholder;
	private Properties properties;

	@PostConstruct
	public void initialize() {
		// Check if application properties file was configured/found
		if (properties == null) {
			LOGGER.error("No application properties file found! Application won't function properly!");
		} else {
			String configType = (String) properties.getProperty("app.config");
			LOGGER.info("Found application properties! app.config={}", configType);
		}

		// Determine the configured javax.faces.PROJECT_STAGE
		projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
		LOGGER.info("Application PROJECT_STAGE: {}", projectStage);

		// Extract project's version from Messages.properties
		appVersion = msg.getString("application_version");
		appVersion = (appVersion == null) ? "<undefined>" : appVersion;
		LOGGER.info("Application version: {}", appVersion);
	}

	public String getProperty(String key) {
		return (String) properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return (String) properties.getProperty(key, defaultValue);
	}
	
	public String getMsg(String key) {
		return (msg.containsKey(key) ? msg.getString(key) : null);
	}
	
	public String getMsg(String key, String defaultValue) {
		String value = msg.containsKey(key) ? msg.getString(key) : null;
		return (value != null) ? value : defaultValue;
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================
	
	public void setPropertyPlaceholder(PropertySourcesPlaceholderConfigurer propertyPlaceholder) {
		PropertySources sources = propertyPlaceholder.getAppliedPropertySources();
		PropertySource<?> source = (sources != null) ? sources.get("localProperties") : null;
		this.properties = (source != null) ? (Properties) source.getSource() : new Properties();
		this.propertyPlaceholder = propertyPlaceholder;
	}
	
	public void setMsg(PropertyResourceBundle msg) {
		this.msg = msg;
	}

	public boolean isDevModeActive() {
		return (ProjectStage.Development == projectStage);
	}

	public String getAppVersion() {
		return appVersion;
	}
}
