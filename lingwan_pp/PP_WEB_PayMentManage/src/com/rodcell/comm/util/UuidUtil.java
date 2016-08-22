package com.rodcell.comm.util;

import java.util.UUID;

import org.safehaus.uuid.UUIDGenerator;

public class UuidUtil {

	public static UUIDGenerator generator=UUIDGenerator.getInstance();
	public static void main(String[] args) {
		
//		UUIDGenerator generator=UUIDGenerator.getInstance();
//		org.safehaus.uuid.UUID uuid=generator.generateRandomBasedUUID();
//		System.out.println(uuid.toString());
//		uuid=generator.generateTimeBasedUUID();
//		System.out.println(uuid.toString());
		UuidUtil.generateNumUuid();
		long s = System.currentTimeMillis();
				for (int i = 0; i < 300000; i++) {
					UuidUtil.generateNumUuid();
				}
				long s1 = System.currentTimeMillis();
				System.out.println(s1-s);
				System.out.println(UuidUtil.generateNumUuid());
				System.out.println(UuidUtil.generateShortUuid());
	}
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String generateNumUuid() {
		StringBuffer shortBuffer = new StringBuffer();
//		String uuid = StringUtil.replaceAll(UuidUtil.generator.generateTimeBasedUUID().toString(), "-", "");
		String uuid = StringUtil.replaceAll(UuidUtil.generator.generateTimeBasedUUID().toString(), "-", "");
//		System.out.println(uuid);
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(x);// (chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = StringUtil.replaceAll(UUID.randomUUID().toString(), "-", "");
//		System.out.println(uuid);
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	 
	
}
