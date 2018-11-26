package me.djin.dcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 * @author djin
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "me.djin.dcore.frame.dao")
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println("SpringBoot start...");
    	SpringApplication.run(App.class, args);
    	System.out.println("SpringBoot end!");
    }
}