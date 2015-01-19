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
package at.schrogl.fsfinance.gui.handler;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.schrogl.fsfinance.gui.ApplicationConfig;

@ManagedBean
@ViewScoped
public class SettingsHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsHandler.class);

	private static final String[] tabnames = { "Profile", "Accounts", "Labels", "Delete Profile" };
	
	private String activeTab = null; 

	@ManagedProperty("#{applicationConfig}")
	private ApplicationConfig appConfig;
	
	public String[] getTabnames() {
		return tabnames;
	}
	
	public String setActiveTab(String activeTab) {
		this.activeTab = activeTab;
		LOGGER.debug(activeTab);
		return null;
	}
	
	public String getActiveTab() {
		return activeTab;
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public void setAppConfig(ApplicationConfig appConfig) {
		this.appConfig = appConfig;
	}
}
