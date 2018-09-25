package me.djin.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "me.djin.study.frame.dao")
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println("SpringBoot start...");
    	SpringApplication.run(App.class, args);
    	System.out.println("SpringBoot end!");
    }
}