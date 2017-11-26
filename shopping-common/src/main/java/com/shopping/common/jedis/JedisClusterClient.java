package com.shopping.common.jedis;

import java.util.List;

import redis.clients.jedis.JedisCluster;

//访问redis集群
public class JedisClusterClient implements JedisClient {
	
	private  JedisCluster jedisCluster;	

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.set(key, value);
		
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.get(key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.exists(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		// TODO Auto-generated method stub
		return jedisCluster.hdel(key, field);
	}

	@Override
	public Boolean hexists(String key, String field) {
		// TODO Auto-generated method stub
		return jedisCluster.hexists(key, field);
	}

	@Override
	public List<String> hvals(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.hvals(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.incr(key);
	}

}
