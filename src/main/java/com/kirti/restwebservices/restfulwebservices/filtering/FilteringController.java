package com.kirti.restwebservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/static-filtering")
	public SomeBean staticfiltering() {
		return new SomeBean("value1","value2","value3");
	}
	
	@GetMapping("/static-filtering-list")
	public List<SomeBean> staticfilteringList() {
		return Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value4","value5","value6"));
	}
	
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue dynamicfiltering() {
		SomeBean someBean = new SomeBean("value1","value2","value3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("KirtiSomeBeanFilter", filter);
		
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue dynamicfilteringList() {
		List<SomeBean> valueList = Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value4","value5","value6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(valueList);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3","field1");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("KirtiSomeBeanFilter", filter );
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

}
