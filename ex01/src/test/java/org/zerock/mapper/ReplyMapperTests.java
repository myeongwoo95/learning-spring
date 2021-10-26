package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//테스트 전에 해당 번호의 게시물이 존재하는지 반드시 확인할 것
	private Long[] bnoArr = {1573304L, 1573303L, 1573302L, 1573301L, 1573300L};
	
	
	public void testMapper() {
		
		IntStream.range(0, 10).forEach(i -> {
			
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i % 5]); //it's creative idea LoL
			vo.setReply("댓글 테스트");
			vo.setReplyer("replyer" + (i+1));
			
			mapper.insert(vo);
		});
	}
	
	public void testRead() {
		
		Long targetRno = 99L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
	
	public void testDelete() {
		
		Long targetRno = 99L;
		
		mapper.delete(targetRno);
	}
	
	public void testUpdate() {
		
		Long targetRno = 98L;
		
		ReplyVO vo = mapper.read(targetRno); // it's good idea
		
		vo.setReply("Update Reply");
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT: " + count);
	}
	
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testList2() {
		
		Criteria cri = new Criteria(2, 10);
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 1573305L);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	
	
	
}
