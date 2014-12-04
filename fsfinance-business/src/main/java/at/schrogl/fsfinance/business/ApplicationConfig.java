package at.schrogl.fsfinance.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private boolean devModeActive;
	private String appVersion;

	private PropertySourcesPlaceholderConfigurer propertyPlaceholder;
	private PropertySource<?> properties;

	@PostConstruct
	public void initialize() {
		PropertySources sources = propertyPlaceholder.getAppliedPropertySources();
		properties = sources.get("localProperties");

		if (properties == null) {
			log.error("No localProperties found and fallback properties not configured!");
		} else {
			String configType = (String) properties.getProperty("app.config");
			log.info("Found localProperties! app.config={}", configType);
		}

		// Determine the configured application mode
		String appMode = (String) properties.getProperty("app.mode");
		devModeActive = ("DEVELOPMENT".equalsIgnoreCase(appMode));
		log.info("Application is in mode: {}", (devModeActive) ? "DEVELOPMENT" : "PRODUCTION");

		/*
		 * Extract project's version from Messages.properties
		 * 
		 * Info: Don't want to add JSF as a dependency, therefore don't using FacesContext
		 * for extracting from Messages.properties.
		 */
		appVersion = "UNKNOWN";
		try (InputStream msgPropsIns = getClass().getResourceAsStream("/localized-messages/Messages.properties")) {
			Properties msgProperties = new Properties();
			msgProperties.load(msgPropsIns);
			appVersion = msgProperties.getProperty("application_version", appVersion);
		} catch (IOException ioe) {
			log.warn("Unable to load application version from Messages.properties", ioe);
		}
		log.info("Application version is {}", appVersion);
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

	@Autowired(required = true)
	public void setProperties(PropertySourcesPlaceholderConfigurer propertyPlaceholder) {
		this.propertyPlaceholder = propertyPlaceholder;
	}

	public boolean isDevModeActive() {
		return devModeActive;
	}

	public String getAppVersion() {
		return appVersion;
	}

}
