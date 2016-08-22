package com.rodcell.comm.util.formula;

import java.text.MessageFormat;

public class Formula {
	
	public static void main(String[] args) {
		String i = Formula.computing("{0}+1*2/(1-{1})", new Object[]{2,2});
		System.out.println(Double.valueOf(i));
	}
	
	public static String toStringFormula(String formula,Object[] args){
		return MessageFormat.format(formula,   args); 
	}

	
	/**
	 * 通过公式返回结果如传入 {0}+1*2/1-{1}
	 * @param formula {0}+1*2/1-{1}
	 * @param args   {1,2}
	 * @return
	 */
	public static String computing(String formula,Object[] args){
		return RunStringReg.cacComplex(toStringFormula(formula, args));
	}
	
}
