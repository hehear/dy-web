package com.dy.s.basic.rest;

import com.dy.s.basic.common.CommonRest;
import com.dy.s.basic.common.SimpleMessage;
import com.dy.s.basic.common.SimpleObjectMessage;
import com.dy.s.basic.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dxy
 * @date 2019-08-22
 * @describe RedisRest
 */
@RestController
@RequestMapping("/redis")
public class RedisRest extends CommonRest<Object>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisRest.class);
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * String-->get
	 * @param key
	 * @return
	 */
	@RequestMapping(value="/get",method = RequestMethod.GET)
    public SimpleMessage<Object> get(String key){
        
		SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();
    	
    	try {
    		LOGGER.info("string-->get:key={};",key);
			Object rs = redisUtil.get(key);
			LOGGER.info("string-->get:value={};",rs);
			//查询
    		sm.setRecord(rs);
    		
		} catch (Exception e) {
    			e.printStackTrace();
    			return error(e);
		}
        return sm;
    }

	/**
	 * string-->set
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value="/set",method = RequestMethod.GET)
	public SimpleMessage<Object> set(String key,String value){

		SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

		try {
			LOGGER.info("string-->set:key={};value={};",key,value);
			redisUtil.set(key,value);
			//查询
			sm.setRecord("插入string成功");

		} catch (Exception e) {
			e.printStackTrace();
			return error(e);
		}
		return sm;
	}

	@RequestMapping(value="/lGet",method = RequestMethod.GET)
	public SimpleMessage<Object> lGet(String key,long start,long end){

		SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

		try {
			LOGGER.info("list-->get:key={};strtIndex={};endIndex={};",key,start,end);
			List<Object> list = redisUtil.lGet(key, start, end);
			//查询
			sm.setRecord(list);

		} catch (Exception e) {
			e.printStackTrace();
			return error(e);
		}
		return sm;
	}

	@RequestMapping(value="/lSet",method = RequestMethod.POST)
	public SimpleMessage<Object> lSet(String key, @RequestParam(value = "list") List<Object> list, long time){

		SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

		try {
			LOGGER.info("list-->set:key={};list={};endIndex={};",key,list.size(),time);
			redisUtil.lSet(key,list,time);
			//查询
			sm.setRecord("插入list成功！");

		} catch (Exception e) {
			e.printStackTrace();
			return error(e);
		}
		return sm;
	}



}
