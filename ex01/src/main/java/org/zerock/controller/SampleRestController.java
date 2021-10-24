package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleRestController {
	
	//string
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
		//text/plain charset=UTF-8
		
		return "�ȳ��ϼ���";
	}
	
	//string2
	@GetMapping(value = "/getText2", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getText2() {
		
		log.info("MIME TYPE: " + "text/plain charset=ISO-8859-1");
		
		return "Hello";
	}
	
	//string3
	@GetMapping("/getText3")
	public String getText3() {
		
		log.info("MIME TYPE: " + "text/html charset=ISO-8859-1");
		
		return "Hello";
	}

	//object
	@GetMapping(value = "/getSample",
				produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
							 MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		
		return new SampleVO(112, "��Ÿ", "�ε�");
	}
	
	//object2
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		
		return new SampleVO(113, "����", "����");
	}
	
	//collection
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + " First", i + " Last"))
				.collect(Collectors.toList());
	}
	
	//collection2
	@GetMapping(value = "/getList2")
	public List<SampleVO> getList2(){
		
		ArrayList<SampleVO> list = new ArrayList<>();
		
		for(int i=0; i<10; i++) {
			SampleVO obj = new SampleVO(i, "First", "last");
			list.add(obj);
		}
		
		return list;
	}
	
	//collection3
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111,"�׷�Ʈ", "�ִϾ�"));
		
		return map;
	}
	
	//ResponseEntity
	@GetMapping(value = "/check", params = {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); //502
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo); // 200
		}
		
		return result;
		
	}
	
	//@PathVariable
	//�⺻�ڷ��� ��� �Ұ�
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(
		@PathVariable("cat") String cat,
		@PathVariable("pid") Integer pid) {
		
		return new String[] {"category: " + cat, " productid: " + pid };
	}
	
	//@PathVariable2
	@GetMapping(value = "/product2/{cat}/{pid}",                      
				produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String[] getPath2(
		@PathVariable("cat") String cat,
		@PathVariable("pid") Integer pid) {
		
		return new String[] {"category: " + cat, "productid: " + pid };
	}
	
	//@RequestBody
	//Ŭ���̾�Ʈ�� �����ϴ� HTTP ��û�� Body ������ Java Object�� ��ȯ�����ִ� ����.
	//�׷��� ������ Get����� �޼ҵ忡 @RequestBody�� Ȱ���ϴ� ���� �������� �ʴ�.
	//@RequestBody�� Json�̳� XML���� ������ data�� jackson���� MessageConverter�� Ȱ���Ͽ� Java Object�� ��ȯ�Ѵ�.
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert.......ticket" + ticket);
		
		return ticket;
	}
	
	
}

