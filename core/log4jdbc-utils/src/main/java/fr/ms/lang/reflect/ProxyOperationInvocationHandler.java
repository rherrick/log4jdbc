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
package fr.ms.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @see <a href="http://marcosemiao4j.wordpress.com">Marco4J</a>
 *
 *
 * @author Marco Semiao
 *
 */
public class ProxyOperationInvocationHandler implements InvocationHandler {

	private final Object implementation;

	private final TimeInvocationHandler invocationHandler;

	private final ProxyOperationFactory factory;

	public ProxyOperationInvocationHandler(final Object implementation, final ProxyOperationFactory factory) {
		this.implementation = implementation;
		this.invocationHandler = new TimeInvocationHandler(this.implementation);
		this.factory = factory;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final TimeInvocation timeInvocation = (TimeInvocation) invocationHandler.invoke(proxy, method, args);

		final Throwable targetException = timeInvocation.getTargetException();

		final ProxyOperation operationContext = factory.newOperation(timeInvocation, proxy, method, args);

		final boolean buildOperation = preProcess();
		if (buildOperation) {
			postProcess(operationContext, timeInvocation, proxy, method, args);
		}

		if (targetException != null) {
			throw targetException;
		}

		final Object wrapInvoke = operationContext.getInvoke();

		return wrapInvoke;
	}

	public boolean preProcess() {
		return false;
	}

	public void postProcess(final ProxyOperation operationContext, final TimeInvocation timeInvocation,
			final Object proxy, final Method method, final Object[] args) {
		// NOOP
	}

	public Object getImplementation() {
		return implementation;
	}

}
