package com.netposa.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netposa.model.User;
import com.netposa.service.HbaseConn;
import com.netposa.service.HelloworldService;

@RequestMapping("/")
@Controller
public class QueryController {
	
	private HelloworldService helloworldService;
	
	
	

	public QueryController() {
		HbaseConn conn = new HbaseConn();
		
		helloworldService = new HelloworldService(conn, "test");
		System.out.println("---------------");
	}
	
	public String indexname(String key){
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "1");
		map.put("age", "2");
		map.put("agename","3");
		map.put("heightname", "4");
		
		return map.get(key);
	}

	@RequestMapping("query.api")
	public ModelAndView query(String querystr) throws Throwable{
		Map<String,String> param = new HashMap<String,String>();
		//name=zhang3,age=23
		if(querystr!=null){
			String[] kvarr = querystr.split(",");
			for(String kvstr:kvarr){
				String[] kv = kvstr.split("=");
				if(kv.length==2){
					String k = kv[0].trim();
					String v = kv[1].trim();
					param.put(k, v);
				}
			}
		}
		
		Set<String> keySet = param.keySet();
		
		String keystr = this.listToString(keySet);
		
		String indexname = this.indexname(keystr);
		Map<String,Object> data = new HashMap<String,Object>();
		if(indexname!=null){
			StringBuilder values = new StringBuilder();
			
			for(String key:keySet){
				values.append(param.get(key));
			}
			String queryLine = indexname+"-"+values;
			long start = System.currentTimeMillis();
			
			data.put("querystr", querystr);
			
			List<User> userlist = this.helloworldService.queryByCondition(queryLine);
			
			data.put("userlist", userlist);
			long time = System.currentTimeMillis()-start;
			
			data.put("time", time);
			data.put("count", userlist.size());
		}
		
		
		return new ModelAndView("query",data);
	}
	
	public String listToString(Collection<String> keys){
		StringBuilder str = new StringBuilder();
		if(keys!=null){
			for(String key:keys){
				str.append(key);
			}
		}
		return str.toString();
	}

	public HelloworldService getHelloworldService() {
		return helloworldService;
	}

	public void setHelloworldService(HelloworldService helloworldService) {
		this.helloworldService = helloworldService;
	}
}
