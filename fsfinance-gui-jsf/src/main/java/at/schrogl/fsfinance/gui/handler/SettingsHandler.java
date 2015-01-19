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
import java.util.Arrays;
import java.util.List;

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

	@ManagedProperty("#{applicationConfig}")
	private ApplicationConfig appConfig;
	
	public List<String> getTabnames() {
		return Arrays.asList(tabnames);
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public void setAppConfig(ApplicationConfig appConfig) {
		this.appConfig = appConfig;
	}
}
