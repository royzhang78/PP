package com.rodcell.comm.util;

import java.util.List;

public class RandomUtil {
	/**
	 * 获取随即数
	 * @param percent
	 * @return
	 */
	public static int Random(int min,int max){
		try{
			max++;
			int k = (int)(min+(max-min)*Math.random());
			return k;
		}catch(RuntimeException e){
			throw e;
		}
	}
	
	/**
	 * 获取随即数
	 * @param percent
	 * @return
	 */
	public static float Random(float min, float max){
		try{
			max++;
			float k = (float)(min+(max-min)*Math.random());
			if (k < min) {
				k = min;
			}
			if (k > max) {
				k = max;
			}
			return k;
		}catch(RuntimeException e){
			throw e;
		}
	}
	
	public static int[] random (List<Integer> indexList, int num) {
		if (indexList.size() <= num) {
			int[] index = new int[indexList.size()];
			for (int i = 0; i < indexList.size(); i++) {
				index[i] = indexList.get(i);
			}
			return index;
		} else {
			int[] index = new int[num];
			for (int i = 0; i < index.length; i++) {
				int k = (int) (0 + (indexList.size() - 0) * Math.random());
				index[i] = indexList.get(k);
				indexList.remove(k);
			}
			return index;
		}
	}

	public static void main(String[] args) {
		float min = 0.98f;
		float max = 1.02f;
		float a = min+(max-min);
		
		for (int i1 = 0; i1 < 10; i1++) {
			System.out.println(Math.random());
			System.out.println(Math.random() * a);
//			float i = RandomUtil.Random(0.98f, 1.02f);
//			System.out.println(i);
		}
		
	}
}
