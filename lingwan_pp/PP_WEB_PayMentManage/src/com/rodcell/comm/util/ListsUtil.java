package com.rodcell.comm.util;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ListsUtil {
	
	public static List newArrayList(){
		return Lists.newArrayList();
	}
	
	public static List newCopyOnWriteArrayList(){
		return Lists.newCopyOnWriteArrayList();
	}
	
	public static List newLinkedList(){
		return Lists.newLinkedList();
	}
	 

	/**
	 * 创建static final List
	 * @param map
	 * @return
	 */
	public static List newImmutableList(List<Object> list){		
		ImmutableList.Builder<Object> builder = ImmutableList.builder();  
		for (Object o:list) {
			builder.add(o);
		}
		return builder.build();  
	}
}
