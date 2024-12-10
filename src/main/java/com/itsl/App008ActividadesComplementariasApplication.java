package com.itsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class, 
//									UserDetailsServiceAutoConfiguration.class
								 })*/
//@EntityScan(basePackages = {
//		"com.itsl.entity",
//		"com.itsl.springSecurity.entity"
//})
//@ComponentScan("com.itsl.springSecurity.repository.UserRepository")
public class App008ActividadesComplementariasApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(App008ActividadesComplementariasApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
				.addMapping("/**")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Accept", "X-Requested-With", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin")
				.exposedHeaders("Access-Control-Expose-Headers", "Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin")
				.allowedMethods("HEAD","GET","PUT","POST","DELETE","PATCH","OPTIONS");
			}
			
		};
	}

}
