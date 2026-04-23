package com.example.netfriquis; 

import org.junit.jupiter.api.Test; 
import org.springframework.boot.test.context.SpringBootTest; 
import static org.assertj.core.api.Assertions.assertThat; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.ApplicationContext; 

@SpringBootTest 
class NetfriquisApplicationTests { 

	@Autowired 
	private ApplicationContext context; 

	@Test 
	void contextLoads() { 
		assertThat(context).isNotNull(); 
	} 

} 