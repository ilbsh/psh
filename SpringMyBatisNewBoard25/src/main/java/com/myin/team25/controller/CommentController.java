package com.myin.team25.controller;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myin.team25.domain.CommentCriteria;
import com.myin.team25.domain.CommentVo;
import com.myin.team25.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService cs;

	// 처음 10개 가져오기
	@RequestMapping(value = "/comments/all/{bbidx}")
	public ArrayList<CommentVo> commentList(@PathVariable("bbidx") int bbidx) {

		ArrayList<CommentVo> alist = cs.SelectCommentAll(bbidx);

		return alist;
	}

	// 추가 부분 가져오기
	@RequestMapping(value = "/comments/{block}/{bbidx}")
	public HashMap<String, Object> moreCommentList(@PathVariable("bbidx") int bbidx, @PathVariable("block") int block) {

		CommentCriteria cc = new CommentCriteria();

		int defaultBlock = cc.getBlock();
		int perBlockNum = cc.getPerBlockNum();
		// int nextBlock = cc.getNextBlock();

		if (block == 0) {
			block = defaultBlock;
		}
		ArrayList<CommentVo> alist = cs.getCommentMore(bbidx, block, perBlockNum);

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("alist", alist);
		hm.put("nextBlock", block + 1);

		System.out.println("hm:" + hm);
		System.out.println("hm nextBlock:" + hm.get("nextBlock"));

		return hm;

	}

	@RequestMapping(value = "/comments/{cidx}/{bbidx}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> CommentDelete(@PathVariable("cidx") int cidx, @PathVariable("bbidx") int bbidx,
			HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String ip = local.getHostAddress();

		CommentVo cv = new CommentVo();
		cv.setCidx(cidx);
		cv.setMemberMidx(sMemberMidx);
		cv.setIp(ip);

		ResponseEntity<String> entity = null;
		String msg = null;

		try {
			cs.deleteComment(cv);

			msg = "삭제되었습니다.";
			entity = new ResponseEntity<String>(msg, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> CommentWrite(@RequestBody CommentVo cv, HttpServletRequest request) throws Exception {

		System.out.println("ggg:" + cv.getBbidx());

		InetAddress local = InetAddress.getLocalHost();
		String ip = local.getHostAddress();

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		cv.setIp(ip);
		cv.setMemberMidx(sMemberMidx);

		ResponseEntity<String> entity = null;
		String msg = null;
		try {
			cs.insertComment(cv);
			msg = "등록되었습니다.";
			entity = new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (Exception e) {

			msg = e.getMessage();
			entity = new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		System.out.println("등록시 entity:" + entity.getBody());

		return entity;
	}

}
