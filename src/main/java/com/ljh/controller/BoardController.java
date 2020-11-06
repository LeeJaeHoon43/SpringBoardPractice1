package com.ljh.controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ljh.domain.BoardVO;
import com.ljh.domain.Criteria;
import com.ljh.domain.PageMaker;
import com.ljh.domain.ReplyVO;
import com.ljh.domain.SearchCriteria;
import com.ljh.service.BoardService;
import com.ljh.service.ReplyService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	BoardService service;

	@Inject
	ReplyService replyService;

	// 글 작성 페이지.
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite(HttpSession session, Model model) throws Exception{
		logger.info("get Write");
		Object loginInfo = session.getAttribute("member");
		if (loginInfo == null) {
			model.addAttribute("msg", "login_error");
		}
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
	public void getRead(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("get read");
		BoardVO vo = service.read(bno);
		model.addAttribute("read", vo);
		model.addAttribute("scri", scri);

		List<ReplyVO> repList = replyService.readReply(bno);
		model.addAttribute("repList", repList);
	}

	// 글 수정.
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("get modify");
		BoardVO vo = service.read(bno);
		model.addAttribute("modify", vo);
		model.addAttribute("scri", scri);
	}

	// 글 삭제.
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void getDelete(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception {
		logger.info("get delete");
		model.addAttribute("delete", bno);
		model.addAttribute("scri", scri);
	}

	// 글 수정  POST.
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("post modify");
		service.update(vo);
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/listSearch";
	}

	// 글 삭제  POST.
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String postDelete(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("post delete");
		service.delete(bno);
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/listSearch";
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

	// 댓글 작성.
	@RequestMapping(value = "/replyWrite", method = RequestMethod.POST)
	public String replyWrite(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("reply write");
		replyService.writeReply(vo);
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/read";
	}
	
	// 댓글 수정 GET.
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.GET)
	public void getReplyUpdate(@RequestParam("rno") int rno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("reply update");
		ReplyVO vo = null;
		vo = replyService.readReplySelect(rno); // 댓글 번호(rno)를 이용하여 선택한 댓글 하나만 가져온다.
		model.addAttribute("readReply", vo);
		model.addAttribute("scri", scri); // SearchCriteria를 이용하여 이전 페이지의 정보를 가져온다.
		// 이 정보로 수정/삭제가 완료되거나 취소되었을 때 원래의 게시물로 이동할 수 있도록 한다.
	}
	
	// 댓글 삭제 GET.
	@RequestMapping(value = "/replyDelete", method = RequestMethod.GET)
	public void getReplyDelete(@RequestParam("rno") int rno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("reply delete");
		ReplyVO vo = null;
		vo = replyService.readReplySelect(rno); // 댓글 번호(rno)를 이용하여 선택한 댓글 하나만 가져온다.
		model.addAttribute("readReply", vo);
		model.addAttribute("scri", scri); // SearchCriteria를 이용하여 이전 페이지의 정보를 가져온다.
		// 이 정보로 수정/삭제가 완료되거나 취소되었을 때 원래의 게시물로 이동할 수 있도록 한다.
	}

	// 댓글 수정.
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.POST)
	public String replyUpdate(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("reply update");
		replyService.replyUpdate(vo);
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/read";
	}

	// 댓글 삭제.
	@RequestMapping(value = "/replyDelete", method = RequestMethod.POST)
	public String replyDelete(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("reply delete");
		replyService.replyDelete(vo);
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/read";
	}
}
