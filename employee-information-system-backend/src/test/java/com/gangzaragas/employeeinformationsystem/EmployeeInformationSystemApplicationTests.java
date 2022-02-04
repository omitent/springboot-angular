package com.gangzaragas.employeeinformationsystem;

import com.gangzaragas.employeeinformationsystem.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeInformationSystemApplicationTests {

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	void contextLoads() {
	}


	}

