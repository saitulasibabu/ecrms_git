package com.vwoa.ecrms.action;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This is a base action that every action class should inherit from.
 * Currently, this serves two purposes:
 * <ol>
 * 	<li>Makes parent package of the application to be the ecrms-default.<br>
 * ecrms package provides timer interceptor as well as global exception handling.</li>
 * <li>Provides a default error result, if one does not explicitely defines one on their action.</li>
 * </ol>
 * 
 * @author vijay dharap
 */
@ParentPackage("ecrms-default")
@Results({
	@Result(name="error", location="/WEB-INF/content/error.jsp")		
})
public abstract class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
