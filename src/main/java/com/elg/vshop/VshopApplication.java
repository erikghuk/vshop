package com.elg.vshop;

import com.elg.vshop.dao.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class VshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(VshopApplication.class, args);
	}

}
