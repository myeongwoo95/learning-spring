package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Component
@ToString
@Getter
//@AllArgsConstructor //if using this Annotation you dont need to make constructor
public class SampleHotel {
	
	private Chef chef;
	
	//after spring4.3 it's possible that constructor Autowiring without @Autowired Annotation 
	public SampleHotel(Chef chef) {
		
		this.chef = chef;
		
	}
	
}
