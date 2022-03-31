package org.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

// 启动类
@SpringBootApplication
// 开启eureka 在服务启动后自动将服务注册到eureka中
@EnableEurekaClient
// 服务发现及DeptController中的discovery方法
@EnableDiscoveryClient
// 添加对熔断的支持 
//Enable 启用 ，Circuit- 电路，Breaker 断路器
@EnableCircuitBreaker
public class DeptProviderHystrix_8081 {

	public static void main(String[] args) {
		SpringApplication.run(DeptProviderHystrix_8081.class, args);
	}

	@Bean
	public ServletRegistrationBean getServlet() {
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/actuator/hystrix.stream");
		registrationBean.setName("hystrix.stream");
		return registrationBean;

	}

}
