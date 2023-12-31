package com.kirti.restwebservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloWorldController {

    private MessageSource messageSource;
	
    ////Constructor Injection
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource=messageSource;
	}
	
	@GetMapping("/hello-world") //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	public String helloWorld() {
		return "Hello World Kirti";
	}
	
	@GetMapping("/hello-world-bean") //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World-Bean");
	}
	
	@GetMapping("/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello, %s - Bean Path Variable ", name) );		
	}
	
	@GetMapping("/hello-world-internationlized")
	public String helloWorldInternationlized() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Kirti - Default Message", locale) ;
	}
}
