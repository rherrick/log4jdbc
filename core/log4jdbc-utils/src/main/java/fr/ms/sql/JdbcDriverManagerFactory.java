/*
 * This file is part of Log4Jdbc.
 *
 * Log4Jdbc is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Log4Jdbc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Log4Jdbc.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package fr.ms.sql;

import fr.ms.lang.SystemPropertyUtils;

/**
 *
 * @see <a href="http://marcosemiao4j.wordpress.com">Marco4J</a>
 *
 *
 * @author Marco Semiao
 *
 */
public final class JdbcDriverManagerFactory {

	private final static boolean driverManagerExtended = SystemPropertyUtils.getProperty("jdbc.manager.extended", true);

	private final static JdbcDriverManager driverManager;

	static {
		if (driverManagerExtended) {
			driverManager = new JdbcDriverManagerExtended();
		} else {
			driverManager = new JdbcDriverManagerImpl();
		}
	}

	public static JdbcDriverManager getInstance() {
		return driverManager;
	}
}
