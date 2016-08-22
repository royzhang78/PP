package com.rodcell.service;

import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.rodcell.entity.TestBean;
import com.rodcell.exception.SException;

public interface TestDB {
	public void test1() throws SException;

	public void saveString(String String);

//	public TestBean getById(@ParameterValueKeyProvider String StringId);
// 
//
//	public void deleteString(String StringId);
//
//	void updateString(@ParameterValueKeyProvider @ParameterDataUpdateContent TestBean s);
}
