package com.rodcell.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
 
public class RedisDao {
	
	
	private  ShardedJedisPool jedisPool;

	public ShardedJedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	
	public  void set(String key,String value){
		ShardedJedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		jedisPool.returnResource(jedis);
	}

	
	public  String get(String key){
		ShardedJedis jedis = jedisPool.getResource();
		String s =jedis.get(key);
		jedisPool.returnResource(jedis);
		return s;
	}
	
	
	public  void del(String key){
		ShardedJedis jedis = jedisPool.getResource();
		jedis.del(key);
		jedisPool.returnResource(jedis);
		
	}
	
	
	public static void main(String[] args) {
		redis.clients.jedis.JedisPoolConfig p=new redis.clients.jedis.JedisPoolConfig();
//		p.setMaxTotal(maxTotal);
	}
	
}
