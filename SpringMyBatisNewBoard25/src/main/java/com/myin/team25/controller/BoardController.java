package com.myin.team25.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.PageMaker;
import com.myin.team25.domain.SearchCriteria;
import com.myin.team25.service.BoardService;
import com.myin.team25.util.MediaUtils;
import com.myin.team25.util.UploadFileUtiles;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;

	@Resource(name = "uploadPath")
	private String uploadPath;

	@RequestMapping(value = "/BoardListController")
	public String boardList(SearchCriteria scri, Model model) {

		int cnt = 0;
		cnt = bs.totalRecordCount(scri);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		pageMaker.setTotalCount(cnt);

		ArrayList<BoardVo> alist = bs.SelectBoardAll(scri);
		model.addAttribute("alist", alist);
		model.addAttribute("pageMaker", pageMaker);

		return "board/boardList";
	}

	// Json파일 가져오기
	@RequestMapping(value = "/BoardListAjaxController/{page}")
	public @ResponseBody HashMap<String, Object> boardListAjax(@PathVariable("page") int page, SearchCriteria scri,
			Model model) {

		scri.setPage(page);

		int cnt = 0;
		cnt = bs.totalRecordCount(scri);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		pageMaker.setTotalCount(cnt);

		ArrayList<BoardVo> alist = bs.SelectBoardAll(scri);
		// model.addAttribute("alist", alist);
		// model.addAttribute("pageMaker", pageMaker);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("alist", alist);
		map.put("pm", pageMaker);

		return map;
	}

	// Ajax로 제작된 게시판리스트 페이지 가져오기
	@RequestMapping(value = "/BoardList_AjaxController")
	public String boardList_Ajax(SearchCriteria scri, Model model) {

		// int cnt=0;
		// cnt=bs.totalRecordCount(scri);
		//
		// PageMaker pageMaker = new PageMaker();
		// pageMaker.setScri(scri);
		// pageMaker.setTotalCount(cnt);
		//
		// ArrayList<BoardVo> alist = bs.SelectBoardAll(scri);
		// model.addAttribute("alist", alist);
		// model.addAttribute("pageMaker", pageMaker);

		return "board/boardList_Ajax";
	}

	@RequestMapping(value = "/BoardContentController")
	public String boardContent(SearchCriteria scri, @RequestParam("bbidx") int bbidx, Model model) {

		BoardVo bv = bs.SelectBoardOne(bbidx);
		model.addAttribute("bv", bv);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);

		return "board/boardContent";
	}

	@RequestMapping(value = "/BoardDeleteController")
	public String boardDelete(SearchCriteria scri, @ModelAttribute("bbidx") int bbidx, Model model) {

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);

		return "board/boardDelete";
	}

	@RequestMapping(value = "/BoardDeleteActionController")
	public String boardDeleteAction(SearchCriteria scri, HttpServletRequest request,
			@RequestParam("password") String password, @RequestParam("bbidx") int bbidx, RedirectAttributes rttr)
			throws UnknownHostException {

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);

		int res = bs.deleteBoard(password, memberIp, bbidx, sMemberMidx);

		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		String page = null;
		if (res == 1) {
			rttr.addFlashAttribute("msg", "삭제되었습니다.");
			page = "redirect:/BoardListController";
		} else {
			rttr.addAttribute("bbidx", bbidx);
			page = "redirect:/BoardDeleteController";
		}

		return page;
	}

	@RequestMapping(value = "/BoardWriteController")
	public String boardWrite(SearchCriteria scri, Model model) {

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);

		return "board/boardWrite";
	}

	@RequestMapping(value = "/BoardWriteActionController")
	public String boardWriteAction(SearchCriteria scri, HttpServletRequest request,
			@RequestParam("subject") String subject, 
			@RequestParam("content") String content,
			@RequestParam("writer") String writer, 
			@RequestParam("password") String password,
			@RequestParam("uploadfile") String uploadfile, Model model) throws Exception {

		//MultipartFile file = uploadfile;
	//	System.out.println("원본파일이름:" + file.getOriginalFilename());

		// 복사하고 저장된 파일이름찾기
		// String savedName =
		// uploadFile(file.getOriginalFilename(),file.getBytes());
		// System.out.println("savedName:"+savedName);
		// model.addAttribute("savedName", savedName);

		//String uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String memberWriteday = sdf.format(dt);
		memberWriteday = memberWriteday.substring(2);

		int res = bs.insertBoard(subject, content, writer, password, memberIp, sMemberMidx, uploadfile);

		model.addAttribute("page", scri.getPage());
		model.addAttribute("searchType", scri.getSearchType());
		model.addAttribute("keyword", scri.getKeyword());

		String page = null;
		if (res == 1) {
			page = "redirect:/BoardListController";
		} else {
			page = "redirect:/BoardWriteController";
		}

		return page;
	}

	// 파일 업로드하는 메소드
	private String uploadFile(String originalName, byte[] fileData) throws Exception {

		// System.out.println("uploadFile");

		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalName;

		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);

		return savedName;
	}

	@RequestMapping(value = "/BoardModifyController")
	public String boardModify(SearchCriteria scri, HttpServletRequest request, @RequestParam("bbidx") int bbidx,
			Model model) {

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);

		BoardVo bv = bs.SelectBoardOne(bbidx);

		model.addAttribute("bv", bv);
		model.addAttribute("pageMaker", pageMaker);

		return "board/boardModify";
	}

	@RequestMapping(value = "/BoardModifyActionController")
	public String boardModifyAction(SearchCriteria scri, HttpServletRequest request, @RequestParam("bbidx") int bbidx,
			@RequestParam("subject") String subject, @RequestParam("content") String content,
			@RequestParam("writer") String writer, @RequestParam("password") String password, RedirectAttributes rttr)
			throws UnknownHostException {

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);

		int res = bs.updateBoard(subject, content, writer, password, memberIp, bbidx, sMemberMidx);

		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		rttr.addAttribute("bbidx", bbidx);

		String page = null;
		if (res == 1) {
			rttr.addFlashAttribute("msg", "수정되었습니다.");
			page = "redirect:/BoardContentController";
		} else {
			page = "redirect:/BoardModifyController";
		}

		return page;
	}

	@RequestMapping(value = "/BoardReplyController")
	public String boardReply(SearchCriteria scri, @ModelAttribute("bbidx") int bbidx, @ModelAttribute("oidx") int oidx,
			@ModelAttribute("updown") int updown, @ModelAttribute("leftright") int leftright, Model model) {

		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);

		model.addAttribute("pageMaker", pageMaker);

		return "board/boardReply";
	}

	@RequestMapping(value = "/BoardReplyActionController")
	public String boardReplyAction(SearchCriteria scri, HttpServletRequest request, @ModelAttribute("bv") BoardVo bv,
			RedirectAttributes rttr) throws UnknownHostException {

		HttpSession session = request.getSession();
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");

		InetAddress local = InetAddress.getLocalHost();
		String memberIp = local.getHostAddress();

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String memberWriteday = sdf.format(dt);
		memberWriteday = memberWriteday.substring(2);

		int res = bs.replyBoard(bv.getBbidx(), bv.getOidx(), bv.getUpdown(), bv.getLeftright(), sMemberMidx,
				bv.getSubject(), bv.getContent(), bv.getWriter(), bv.getPassword(), memberIp);

		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		String page = null;
		if (res == 1) {
			rttr.addFlashAttribute("msg", "등록되었습니다.");
			page = "redirect:/BoardListController";
		} else {
			rttr.addAttribute("bbidx", bv.getBbidx());
			rttr.addAttribute("oidx", bv.getOidx());
			rttr.addAttribute("updown", bv.getUpdown());
			rttr.addAttribute("leftright", bv.getLeftright());

			page = "redirect:/BoardReplyController";
		}

		return page;
	}

	@RequestMapping(value = "/BoardMemberInfoController")
	public String boardMemberList(SearchCriteria scri, Model model) {

		// 향후에 페이징 처리할때 쓰도록함.
		// int cnt=0;
		// cnt=bs.totalRecordCount(scri);
		//
		// PageMaker pageMaker = new PageMaker();
		// pageMaker.setScri(scri);
		// pageMaker.setTotalCount(cnt);

		ArrayList<HashMap<String, Object>> alist = bs.boardMemberInfo(scri);
		System.out.println("확인:" + alist);
		System.out.println("확인:" + alist.get(0));
		System.out.println("확인:" + alist.get(0).get("BBIDX"));
		model.addAttribute("alist", alist);
		// model.addAttribute("pageMaker", pageMaker);

		return "board/boardMemberInfo";
	}

	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {

		System.out.println("원본이름:" + file.getOriginalFilename());

		String uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());

		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String>(uploadedFileName, HttpStatus.CREATED);

		return entity;
	}

	@ResponseBody
	@RequestMapping(value="/displayFile", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		System.out.println("fileName:"+fileName);
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
//		logger.info("FILE NAME : "+fileName);
				
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath+fileName);
			
			if(mType != null){
				headers.setContentType(mType);
				}else{
					
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
				}
		entity=new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
				headers,
				HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity=new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close();
		}
		return entity;
	}

}
