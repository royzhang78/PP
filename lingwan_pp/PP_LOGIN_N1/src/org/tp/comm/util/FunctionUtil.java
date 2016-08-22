package org.tp.comm.util;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
class test{
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

public class FunctionUtil {
	static List l = new ArrayList();
	
	public static void main(String[] args) { 
		test t =new test();
		t.setId(1);
		t.setName("a");
		
		test t2 =new test();
		t2.setId(2);
		t2.setName("b");
		
		test t3 =new test();
		t3.setId(3);
		t3.setName("c");
		
		
		l.add(t);
		l.add(t2);
		l.add(t3);
		
		Function function =new Function<test, test>() {
			@Override
			public test apply(test input) {
//				if(input.getId()==1)
				
				//System.out.println(1);
				input.setId(input.getId()*10);
				
				return input;
			}
		};
		
		Iterable  iterable = FunctionUtil.objectListExcFunction(l, function); 
		Object []tt= FunctionUtil.iterableToArray(iterable.iterator());
		
		for (int i = 0; i < tt.length; i++) {
			System.out.println(((test)tt[i]).getId());
		}
		//=======================================================================================================
//		Range<Integer> range = Ranges.closed(1, 12);
//		List l = new ArrayList();
//		l.add(1);
//		l.add(2);
//		l.add(3);
//		Iterable<Integer> iterable = Iterables.transform(
//				l,
//				new Function<Integer, Integer>() {
//					@Override
//					public Integer apply(Integer input) {
//						//System.out.println(1);
//						return input * 2;
//					}
//
//				});
//		
//		Iterator iterator =iterable.iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}
		
		
	}
	
	/**
	 * 过滤List对象
	 * @param list
	 * @param function 遍历对象所执行的方法
	 * @return
	 */
	public static Iterable objectListExcFunction(List list,Function function){
		return Iterables.transform(list,function);
	}
	
	/**
	 * 过滤set对象
	 * @param set
	 * @param function 遍历对象所执行的方法
	 * @return
	 */
	public static Iterable objectSetExcFunction(Set set,Function function){
		return Iterables.transform(set,function);
	}
	
	
	/**
	 * 将Iterator对象转换为数组
	 * @param iterator
	 * @return
	 */
	public static Object[] iterableToArray(Iterator iterator){		
		return  iterableToList(iterator).toArray();
	}
	
	
	/**
	 * 将Iterator对象转换为List
	 * @param iterator
	 * @return
	 */
	public static List iterableToList(Iterator iterator){
		return  Lists.newArrayList(iterator);
	}

}
