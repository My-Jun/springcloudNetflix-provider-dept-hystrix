package org.springcloud.controller;

import org.springcloud.pojo.Dept;
import org.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author My 提供RestFul 风格服务
 */
@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;

	// 使用@HystrixCommand 设置fallbackMethod当出现失败调用方法回调hystrixGet方法
	@GetMapping("/dept/queryById/{deptno}")
	@HystrixCommand(fallbackMethod = "hystrixGet")
	public Dept queryById(@PathVariable("deptno") Long deptno) {
		Dept dept = deptService.queryById(deptno);
		// 添加实际业务逻辑，如果查询id为空，则抛出异常
		if (dept == null) {
			throw new RuntimeException("id=》" + deptno + ",不存在该条信息，或查找不到~~");
		}

		return dept;
	}
	
	
	@GetMapping("/dept/queryById1/{deptno}")
	public Dept queryById1(@PathVariable("deptno") Long deptno) {
		return deptService.queryById(deptno);
	}

	// 备选方案，熔断
	public Dept hystrixGet(@PathVariable("deptno") Long deptno) {
		return new Dept().setDeptno(deptno).setDname("id=》" + deptno + ",没有对于信息，null----@hystrix")
				.setDb_source(" 没有 这个数据库 ");
	}

}
