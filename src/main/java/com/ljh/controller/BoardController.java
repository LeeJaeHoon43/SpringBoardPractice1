package com.ljh.controller;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ljh.domain.BoardVO;
import com.ljh.domain.Criteria;
import com.ljh.domain.PageMaker;
import com.ljh.domain.SearchCriteria;
import com.ljh.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	BoardService service;

	// 글 작성 페이지.
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite() throws Exception{
		logger.info("get Write");
	}

	// 글 작성.
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo) throws Exception{
		logger.info("post Write");
		service.write(vo);
		return "redirect:/";
	}

	// 글 목록.
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception{
		logger.info("get list");
		List<BoardVO> list = service.list();
		model.addAttribute("list", list);
	}

	// 글 조회.
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void getRead(@RequestParam("bno") int bno, Model model) throws Exception{
		logger.info("get read");
		BoardVO vo = service.read(bno);
		model.addAttribute("read", vo);
	}

	// 글 수정.
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, Model model) throws Exception{
		logger.info("get modify");
		BoardVO vo = service.read(bno);
		model.addAttribute("modify", vo);
	}

	// 글 삭제.
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void getDelete(@RequestParam("bno") int bno, Model model) throws Exception {
		logger.info("get delete");
		model.addAttribute("delete", bno);
	}

	// 글 수정  POST.
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {
		logger.info("post modify");
		service.update(vo);
		return "redirect:/board/list";
	}

	// 글 삭제  POST.
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String postDelete(@RequestParam("bno") int bno) throws Exception {
		logger.info("post delete");
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	// 글 목록 + 페이징.
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("get list page");
		List<BoardVO> list = service.listPage(cri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCount());
		model.addAttribute("pageMaker", pageMaker);
	}
	
	// 글 목록 + 페이징 + 검색.
	@RequestMapping(value = "/listSearch", method = RequestMethod.GET)
	public void listSearch(@ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("get list search");
		List<BoardVO> list = service.listSearch(scri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount());
		model.addAttribute("pageMaker", pageMaker);
	}
}
