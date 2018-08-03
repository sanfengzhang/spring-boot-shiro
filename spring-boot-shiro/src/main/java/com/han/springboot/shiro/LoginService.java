package com.han.springboot.shiro;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import com.han.springboot.shiro.core.DynamicPermissionParams;

@Service
public class LoginService
{

	@RequiresRoles(value = { "admin" })
	@RequiresPermissions(value = { "query" })
	public String queryData(DynamicPermissionParams params)
	{

		return "SUCCESS";
	}

}
