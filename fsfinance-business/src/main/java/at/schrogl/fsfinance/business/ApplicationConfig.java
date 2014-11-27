package at.schrogl.fsfinance.business;

import java.io.Serializable;

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

	private boolean isDevModeActive;
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
		isDevModeActive = ("DEVELOPMENT".equalsIgnoreCase(appMode));
		log.info("Application is in mode: {}", (isDevModeActive) ? "DEVELOPMENT" : "PRODUCTION");

		// Extract application version from Maven's pom.properties
		appVersion = "UNKNOWN";
		/* TODO Make this work :)
		 
		Resource pomFile = new ClassPathResource("classpath:META-INF/maven/at.schrogl.fsfinance/fsfinance-gui-jsf/pom.properties");
		Resource pomFile = new FileSystemResource("META-INF/maven/at.schrogl.fsfinance/fsfinance-gui-jsf/pom.properties");
		
		
		if (pomFile.exists() && pomFile.isReadable()) {
			try (InputStream pomFileIns = new FileInputStream(pomFile.getFile())) {
				Properties pomProperties = new Properties();
				pomProperties.load(pomFileIns);
				appVersion = pomProperties.getProperty("version", appVersion);
			} catch (IOException ioe) {
				log.warn("Unable to load application version from pom.properties", ioe);
			}
		}
		*/
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
		return isDevModeActive;
	}

	public String getAppVersion() {
		return appVersion;
	}

}
