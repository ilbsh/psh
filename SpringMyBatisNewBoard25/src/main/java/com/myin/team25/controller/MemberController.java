package com.myin.team25.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.myin.team25.domain.MemberVo;
import com.myin.team25.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;

	@RequestMapping(value = "/MemberListController")
	public String memberList(Model model) {

		ArrayList<MemberVo> alist = ms.selectMemberAll();
		model.addAttribute("alist", alist);

		return "member/memberList";
	}

	@RequestMapping(value = "/MemberJoinController")
	public String memberJoin() {

		return "member/memberJoin";
	}

	@RequestMapping(value = "/MemberJoinActionController")
	public String memberJoinAction(@ModelAttribute("mv") MemberVo mv, @RequestParam("memberEmail1") String memberEmail1,
			@RequestParam("memberEmail2") String memberEmail2) throws UnknownHostException {

		String memberEmail = memberEmail1 + "@" + memberEmail2;

		int maxMidx = ms.maxMember();

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String memberWriteday = sdf.format(dt);
		memberWriteday = memberWriteday.substring(2);

		// System.out.println(maxMidx);

		int res = ms.insertMember(maxMidx, mv.getMemberId(), mv.getMemberName(), mv.getMemberPassword(),
				mv.getMemberJumin(), memberEmail, mv.getMemberAddr(), mv.getMemberSex(), memberIp, memberWriteday,
				mv.getBidx());

		String page = null;
		if (res == 1) {
			page = "redirect:/MemberLoginController";
		} else {
			page = "redirect:/MemberJoinController";
		}

		return page;
	}

	@RequestMapping(value = "/MemberLogoutController")
	public String memberLogout() {

		return "member/memberLogout";
	}

	@RequestMapping(value = "/MemberLoginController")
	public String memberLogin() {

		return "member/memberLogin";
	}

	@RequestMapping(value = "/MemberLoginActionController", method = RequestMethod.POST)
	public String memberLoginAction(HttpServletRequest request, @RequestParam("memberId") String memberId,
			@RequestParam("memberPassword") String memberPassword, Model model) throws IOException {
		int res = 0;
		String page = null;
		MemberVo mv = null;

		mv = ms.loginCheck(memberId, memberPassword);

		if (mv != null) {

			model.addAttribute("sMemberId", mv.getMemberId());
			model.addAttribute("sMemberMidx", mv.getMemberMidx());
			model.addAttribute("sMemberName", mv.getMemberName());

			// impl keeplogin 불러서 db입력

			res = 1;
		}

		HttpSession session = request.getSession();
		String dest = (String) session.getAttribute("dest");

		if (res == 1) {
			if (dest != null) {
				page = "redirect:" + dest + "";
			} else {
				page = "redirect:/MemberListController";
			}
		} else {
			page = "redirect:/MemberLoginController";
		}

		return page;
	}

	@RequestMapping(value = "/MemberModifyController")
	public String memberModify(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		String sMemberId = (String) session.getAttribute("sMemberId");

		MemberVo mv = ms.selectMemberOne(sMemberId);
		model.addAttribute("mv", mv);

		return "member/memberModify";
	}

	@RequestMapping(value = "/MemberModifyActionController")
	public String memberModifyAction(HttpServletRequest request, @RequestParam("memberEmail1") String memberEmail1,
			@RequestParam("memberEmail2") String memberEmail2, @RequestParam("memberAddr") String memberAddr,
			@RequestParam("memberPassword") String memberPassword, @RequestParam("bidx") int bidx_int, Model model)
			throws UnknownHostException, UnsupportedEncodingException {

		String memberEmail = memberEmail1 + "@" + memberEmail2;

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);

		ms.updateMember(sMemberMidx, memberPassword, memberEmail, memberAddr, memberIp, Modifyday, bidx_int);

		String page = null;

		// if (res ==1) {
		// page = "redirect:/MemberContentController";
		// }else{
		page = "redirect:/MemberModifyController";
		// }

		return page;
	}

}
