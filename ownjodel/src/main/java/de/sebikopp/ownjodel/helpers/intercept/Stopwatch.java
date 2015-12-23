package de.sebikopp.ownjodel.helpers.intercept;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class Stopwatch {
	@AroundInvoke
	public Object logTime (InvocationContext ctx) throws Exception{
		long start = System.currentTimeMillis();
		try {
			return ctx.proceed();
		} finally {
			long diff = System.currentTimeMillis() - start;
			Logger logger = Logger.getLogger(ctx.getClass().getName());
			logger.log(Level.INFO, "Zeitverbrauch der Methode " + ctx.getMethod().getName() + ": " + diff + " ms.");
		}
	}
}
