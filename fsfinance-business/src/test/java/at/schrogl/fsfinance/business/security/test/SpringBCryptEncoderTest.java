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
package at.schrogl.fsfinance.business.security.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This class is more a documentation on how to use Spring Security's BCrypt
 * encoder class, than a proper unit test.
 * <p>
 * Spring Security's BCrypt encoder uses OpenBSD-style Blowfish password hashing
 * using the scheme described in "A Future-Adaptable Password Scheme" by Niels
 * Provos and David Mazieres. The hashed password string contains the version of
 * the BCrypt algorithm used, the number of log_rounds, the salt used and the
 * actual hashed password. Hence it is not necessary to store the used salt
 * separately.
 * <p>
 * A hashed password using the raw password 'password' may look like this:
 * <p>
 * <code>$2a$10$nEXxppo9a/lBFuh7Qg8ax.PRS0BrT029VdexYiliuTEqdeLsE/FoC</code>
 * <p>
 * The BCrypt version is 2a, the log_rounds used is 10, the salt used is
 * 'nEXxppo9a/lBFuh7Qg8ax.', the hashed password is
 * 'PRS0BrT029VdexYiliuTEqdeLsE/FoC'.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public class SpringBCryptEncoderTest {

	private static final String rawPasssword = "password";
	private static final int rounds = 10;

	@Test
	public void saltImplicit_test() {
		// Create a hashed password with an embedded salt
		String hashedPassword = BCrypt.hashpw(rawPasssword, BCrypt.gensalt(rounds));

		// Check a raw password against a hashed password with embedded salt
		assertTrue(BCrypt.checkpw(rawPasssword, hashedPassword));
	}

	@Test
	public void saltExplicit_test() {
		// Generate a random salt
		String salt = BCrypt.gensalt(rounds);
		String hashedPassword = BCrypt.hashpw(rawPasssword, salt);

		// Check a raw password against a hashed password with embedded salt
		assertTrue(BCrypt.checkpw(rawPasssword, hashedPassword));
	}
}
