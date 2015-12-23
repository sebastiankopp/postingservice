package de.sebikopp.ownjodel.helpers.intercept;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ExceptionHandlerRest {
	@AroundInvoke
	public Object intercept (InvocationContext ctx) throws Exception {
		try {
			return ctx.proceed();
		} catch (ValidationException exc){
			throw new WebApplicationException(exc, Status.BAD_REQUEST);
		} catch (NullPointerException npe){
			throw new WebApplicationException("Die gesuchte Ressource ist leider nicht vorhanden!", npe, Status.NOT_FOUND);
		}
	}
}
