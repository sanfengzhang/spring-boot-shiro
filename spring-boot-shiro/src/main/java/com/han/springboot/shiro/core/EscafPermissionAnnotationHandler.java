package com.han.springboot.shiro.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

public class EscafPermissionAnnotationHandler extends AuthorizingAnnotationHandler
{

	public EscafPermissionAnnotationHandler()
	{
		super(RequiresPermissions.class);
	}

	protected String[] getAnnotationValue(Annotation a)
	{
		RequiresPermissions rpAnnotation = (RequiresPermissions) a;
		return rpAnnotation.value();
	}

	public void assertAuthorized(Annotation a, String... params) throws AuthorizationException
	{
		if (null == params || params.length == 0)
		{
			assertAuthorized(a);
		} else
		{
			if (!(a instanceof RequiresPermissions))
				return;

			RequiresPermissions rpAnnotation = (RequiresPermissions) a;
			List<String> permsList = new ArrayList<String>();
			String[] permsPrefixs = getAnnotationValue(a);
			for (String permsPrefix : permsPrefixs)
			{

				for (String param : params)
				{
					permsList.add(permsPrefix + param);
				}

			}

			String[] perms = new String[permsList.size()];
			assertAuthorized(permsList.toArray(perms), rpAnnotation);

		}

	}

	@Override
	public void assertAuthorized(Annotation a) throws AuthorizationException
	{

		if (!(a instanceof RequiresPermissions))
			return;

		RequiresPermissions rpAnnotation = (RequiresPermissions) a;
		String[] perms = getAnnotationValue(a);
		assertAuthorized(perms, rpAnnotation);

	}

	private void assertAuthorized(String[] perms, RequiresPermissions rpAnnotation) throws AuthorizationException
	{
		Subject subject = getSubject();

		if (perms.length == 1)
		{
			subject.checkPermission(perms[0]);
			return;
		}
		if (Logical.AND.equals(rpAnnotation.logical()))
		{
			getSubject().checkPermissions(perms);
			return;
		}
		if (Logical.OR.equals(rpAnnotation.logical()))
		{
			// Avoid processing exceptions unnecessarily - "delay" throwing the exception by
			// calling hasRole first
			boolean hasAtLeastOnePermission = false;
			for (String permission : perms)
				if (getSubject().isPermitted(permission))
					hasAtLeastOnePermission = true;
			// Cause the exception if none of the role match, note that the exception
			// message will be a bit misleading
			if (!hasAtLeastOnePermission)
				getSubject().checkPermission(perms[0]);

		}
	}

}
