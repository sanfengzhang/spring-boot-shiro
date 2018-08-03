package com.han.springboot.shiro.core;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class EscafPermissionAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor
{

	public EscafPermissionAnnotationMethodInterceptor()
	{
		super(new EscafPermissionAnnotationHandler());
	}

	public EscafPermissionAnnotationMethodInterceptor(AnnotationResolver resolver)
	{
		super(new EscafPermissionAnnotationHandler(), resolver);
	}

	@Override
	public void assertAuthorized(MethodInvocation mi) throws AuthorizationException
	{

		Object[] objects = mi.getArguments();
		DynamicPermissionParams dynamicPermissionParams = null;
		for (Object object : objects)
		{
			if (object instanceof DynamicPermissionParams)
			{
				dynamicPermissionParams = (DynamicPermissionParams) object;
			}

		}

		if (null == dynamicPermissionParams)
		{
			super.assertAuthorized(mi);
			
		} else
		{
			try
			{
				String dyStrings[] = new String[dynamicPermissionParams.getParamsList().size()];
				((EscafPermissionAnnotationHandler) getHandler()).assertAuthorized(getAnnotation(mi),
						dynamicPermissionParams.getParamsList().toArray(dyStrings));
			} catch (AuthorizationException ae)
			{

				if (ae.getCause() == null)
				{
					ae.initCause(new AuthorizationException("Not authorized to invoke method: " + mi.getMethod()));
				}
				throw ae;
			}
		}

	}

}
