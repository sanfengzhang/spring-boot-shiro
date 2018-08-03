package com.han.springboot.shiro.core;

import java.util.ArrayList;
import java.util.List;

public class DynamicPermissionParams
{
	private List<String> paramsList = new ArrayList<String>();

	public void addPermissonParams(String permission)
	{
		paramsList.add(permission);
	}

	public List<String> getParamsList()
	{
		return paramsList;
	}

	public void setParamsList(List<String> paramsList)
	{
		this.paramsList = paramsList;
	}

}
