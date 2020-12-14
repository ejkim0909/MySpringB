package com.kh.myspringb.board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.myspringb.board.model.domain.Board;
import com.kh.myspringb.board.model.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService bService;   
	
	// 게시글 작성 페이지
	@RequestMapping(value="/writeForm.do", method = RequestMethod.GET)
	public String boardInsertForm(ModelAndView mv) {
		return "board/writeForm";	  // View페이지에서 작성 후 form action = "bInsert.do" 로 들어오도록 함.
	}
	

	// 작성된 글을 insert
	@RequestMapping(value="/bInsert.do", method =  RequestMethod.POST)
	public ModelAndView boardInsert(Board b, @RequestParam(name="upfile") MultipartFile report, HttpServletRequest request, ModelAndView mv) {
		// 첨부파일 저장
		if(report!=null && !report.equals("")) {
			saveFile(report, request);
		}		
		b.setBoard_file(report.getOriginalFilename()); 		// 저장된 파일명을 vo에 set		
		
		bService.insertBoard(b);
		mv.setViewName("redirect:bList.do");   // insertBoard에 성공했다면   !!! View페이지로 이동하는 것이 아니라 컨트롤러 url 중 "게시글 리스트 select로 이동" 하는 "/bList.do"
		return mv;
		// 실패했다면
//		mv.setViewName("errorPage");   // errorPage 페이지로 이동
	}	
	
	// 게시글 리스트 select
	@RequestMapping(value="/bList.do")
	public ModelAndView boardListService(ModelAndView mv) {
		mv.addObject("list", bService.selectList());
		mv.setViewName("board/blist");    // board/blist View페이지가 보여짐
		return mv;
	}

	private void saveFile(MultipartFile report, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdirs(); // 폴더가 없다면 생성한다.
		}
		String filePath = null;
		try {
			// 파일 저장
			System.out.println(report.getOriginalFilename() + "을 저장합니다.");
			System.out.println("저장 경로 : " + savePath);

			filePath = folder + "\\" + report.getOriginalFilename();
			report.transferTo(new File(filePath)); // 파일을 저장한다
			System.out.println("파일 명 : " + report.getOriginalFilename());
			System.out.println("파일 경로 : " + filePath);
			System.out.println("파일 전송이 완료되었습니다.");
		} catch (Exception e) {
			System.out.println("파일 전송 에러 : " + e.getMessage());
		}
	}
	
	private void removeFile(String board_file, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		String filePath = savePath + "\\" + board_file;
		try {
			System.out.println(board_file + "을 삭제합니다.");
			System.out.println("기존 저장 경로 : " + savePath);
			File delFile = new File(filePath);
			delFile.delete();
			System.out.println("파일 삭제가 완료되었습니다.");
		} catch (Exception e) {
			System.out.println("파일 삭제 에러: "+ e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/bDetail.do")
	public ModelAndView boardDetail(@RequestParam(name ="board_num") String board_num, 
			@RequestParam(name ="page", defaultValue = "1") int page,  ModelAndView mv) {
		mv.addObject("board", bService.selectOne(board_num));
		mv.setViewName("board/boardDetail");
		return mv;
	}
	
	@RequestMapping(value="/bRenew.do")
	public ModelAndView boardDetail(@RequestParam(name ="board_num") String board_num, ModelAndView mv) {
		mv.addObject("board", bService.selectOne(board_num));
		mv.setViewName("board/boardRenew");
		return mv;
	}
	
	@RequestMapping(value="/bUpdate.do", method=RequestMethod.POST)
	public ModelAndView boardUpdate(Board b, @RequestParam(name="upfile") MultipartFile report, HttpServletRequest request, ModelAndView mv) {
		// 첨부파일 저장
		if(report!=null && !report.equals("")) {
			removeFile(b.getBoard_file(), request);
			saveFile(report, request);
			b.setBoard_file(report.getOriginalFilename()); 		// 저장된 파일명을 vo에 set		
		}		
		if(bService.updateBoard(b)!=null) {
			mv.addObject("board_num", bService.updateBoard(b).getBoard_num());
			mv.setViewName("redirect:bDetail.do");	
		} else {
			// 이전화면으로 이동
		}
		return mv;
		// 실패했다면
//		mv.setViewName("errorPage");   // errorPage 페이지로 이동
	}
	
	
	
	
//	int updateBoard(Board d);
//
//	int deleteBoard(String board_num);
}




















