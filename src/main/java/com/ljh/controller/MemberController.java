package com.ljh.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ljh.domain.MemberVO;
import com.ljh.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService memberService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void getRegister()throws Exception{
		logger.info("get register");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception{
		logger.info("post register");
		memberService.register(vo);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception{
		logger.info("post login");
		HttpSession session = req.getSession();
		MemberVO login = memberService.login(vo);
		if (login == null) {
			session.setAttribute("member", null);
			rttr.addAttribute("msg", false);
		}else {
			session.setAttribute("member", login);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		logger.info("get logout");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify() throws Exception{
		logger.info("get modify");
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(HttpSession session, MemberVO vo) throws Exception{
		logger.info("post modify");
		memberService.modify(vo);
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
	public void getWithdrawal() throws Exception{
		logger.info("get withdrawal");
	}
	
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
	public String postWithdrawal(HttpSession session, MemberVO vo, RedirectAttributes rttr) throws Exception{
		logger.info("post withdrawal");
		MemberVO member = (MemberVO)session.getAttribute("member");
		String oldPass = member.getUserPass();
		String newPass = vo.getUserPass();
		if (!(oldPass.equals(newPass))) {
			rttr.addAttribute("msg", false);
			return "redirect:/member/withdrawal";
		}
		memberService.withdrawal(vo);
		session.invalidate();
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int postIdCheck(HttpServletRequest req) throws Exception{
		logger.info("post idCheck");
		String userId = req.getParameter("userId");
		MemberVO idCheck = memberService.idCheck(userId);
		int result = 0;
		if (idCheck != null) {
			result = 1;
		}
		return result;
	}
}
