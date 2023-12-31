package com.project.jeans.controller.board.board3;

import com.project.jeans.LoginCheckSession;
import com.project.jeans.domain.board.board3.dto.Board3DTO;
import com.project.jeans.domain.board.board3.dto.Comment3DTO;
import com.project.jeans.domain.member.dto.MemberDTO;
import com.project.jeans.service.board.board3.Board3Service;
import com.project.jeans.service.board.board3.Comment3Service;
import com.project.jeans.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping("/board3")
@RequiredArgsConstructor
@Controller
public class Board3Controller {
    private final MemberService memberService;
    private final Board3Service board3Service;
    private final Comment3Service comment3Service;

    //반별 게시판 목록 조회
    @GetMapping("/list")
    public String getBoard3List(HttpSession session, Model model){
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);
        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return "/member/login";
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return "/main/main";
        }

        String category = "board3";
        model.addAttribute("category", category);
        model.addAttribute("member_id", memberInfo.getMember_id());
        model.addAttribute("member_name",memberInfo.getMember_name());
        model.addAttribute("member_class",memberInfo.getMember_class());
        model.addAttribute("member_type",memberInfo.getMember_type());


        List<Board3DTO> board3DTOList = board3Service.getBoard3List();
        List<Board3DTO> board3DTOBYTutor = board3Service.findBoard3ByTutor();

        model.addAttribute("board3List", board3DTOList);
        model.addAttribute("board3DTOBYTutor", board3DTOBYTutor);

        return "/board/board3/board3List";
    }


    //반별 게시글 상세 조회 및 게시글 관련 댓글 조회
    //페이지 연결할 때 수정 가능성 있음
    @GetMapping("/detail/{board3_no}")
    public String readBoard3(@PathVariable("board3_no") int board3_no, HttpSession session, Model model) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);
        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return "/member/login";
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return "/main/main";
        }

        String category = "board3";
        model.addAttribute("category", category);
        model.addAttribute("member_id", memberInfo.getMember_id());
        model.addAttribute("member_name",memberInfo.getMember_name());
        model.addAttribute("member_class",memberInfo.getMember_class());
        model.addAttribute("member_type",memberInfo.getMember_type());

        Board3DTO board3DTO = board3Service.getBoard3Detail(board3_no);
        model.addAttribute("board3DTO", board3DTO);
        List<Comment3DTO> comment3DTO = comment3Service.getComment3List(board3_no);
        model.addAttribute("comment3DTO", comment3DTO);
        return "/board/board3/board3Detail";
    }

    //반별 게시글 작성(폼)
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeBoard3Form(HttpSession session, Model model) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);

        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return "/member/login";
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return "/main/main";
        }

        String category = "board3";
        model.addAttribute("category", category);
        model.addAttribute("member_id", memberInfo.getMember_id());
        model.addAttribute("member_name", memberInfo.getMember_name());
        model.addAttribute("member_class", memberInfo.getMember_class());
        model.addAttribute("member_type", memberInfo.getMember_type());

        return "/board/board3/board3Write";
    }

    //반별 게시글 작성(로직)
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public ModelAndView writeBoard3(HttpSession session, Model model, ModelAndView modelAndView, @RequestParam Map<String, Object> map) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);
        if (memberInfo == null) {
            System.out.println("테스트");
            // 로그인이 필요한 경우 리디렉션
            return new ModelAndView("redirect:/member/login");
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return  new ModelAndView("redirect:/main/main");
        }

        if(map.isEmpty()){
            return new ModelAndView("redirect:/board3/list");
        }
        if (map.get("board3_title") == "" || map.get("board3_title") == null){
            return new ModelAndView("redirect:/board3/list");
        }
        if (map.get("board3_content") == "" || map.get("board3_content") == null){
            return new ModelAndView("redirect:/board3/list");
        }

        int writeInt = board3Service.writeBoard3(map);
        if (writeInt == 1) {
            modelAndView.setViewName("redirect:/board3/list");
        } else {
            modelAndView.setViewName("redirect:/board3/write");
        }
        return modelAndView;
    }

    //반별 게시글 수정(폼)
    @GetMapping("/modify")
    public String modifyBoard3Form(@RequestParam int board3_no, Model model, HttpSession session) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);
        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return "redirect:member/login";
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return "/main/main";
        }

        String category = "board3";
        model.addAttribute("category", category);
        model.addAttribute("member_id", memberInfo.getMember_id());
        model.addAttribute("member_name", memberInfo.getMember_name());
        model.addAttribute("member_class", memberInfo.getMember_class());
        model.addAttribute("member_type", memberInfo.getMember_type());

        Board3DTO board3DTO = board3Service.getBoard3Detail(board3_no);
        model.addAttribute("board3DTO", board3DTO);
        return "/board/board3/board3Modify";
    }

    //반별 게시글 수정
    @PostMapping("/modify")
    public ModelAndView modifyBoard3(HttpSession session, Model model, ModelAndView modelAndView, @RequestParam int board3_no, @RequestParam Map<String, Object> map) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);
        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return new ModelAndView("redirect:member/login");
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return  new ModelAndView("redirect:/main/main");
        }

        if(map.isEmpty()){
            return new ModelAndView("redirect:/board3/list");
        }
        if (map.get("board3_title") == "" || map.get("board3_title") == null){
            return new ModelAndView("redirect:/board3/list");
        }
        if (map.get("board3_content") == "" || map.get("board3_content") == null){
            return new ModelAndView("redirect:/board3/list");
        }

        int modifyInt = board3Service.modifyBoard3(map);
        if (modifyInt == 1) {
            modelAndView.setViewName("redirect:/board3/detail/" + board3_no);
        } else {
            modelAndView.setViewName("redirect:/board3/list");
        }
        return modelAndView;
    }

    //반별 게시글 삭제
    @GetMapping("/delete")
    public ModelAndView deleteBoard3(HttpSession session, Model model, ModelAndView modelAndView,
                                     @RequestParam Map<String, Object> map) {
        LoginCheckSession loginCheck = new LoginCheckSession(memberService);
        MemberDTO memberInfo = loginCheck.getLoginCheckSession(session, model);

        if (memberInfo == null) {
            // 로그인이 필요한 경우 리디렉션
            return new ModelAndView("redirect:member/login");
        }

        String memberClass = memberInfo.getMember_class();
        String memberType = memberInfo.getMember_type();
        if (!memberClass.equals("3반") && memberType.equals("수강생")) {
            return  new ModelAndView("redirect:/main/main");
        }

        int deletedBoard3Int = board3Service.deleteBoard3(map);
        if (deletedBoard3Int == 1) {
            modelAndView.setViewName("redirect:/board3/list");
        } else {
            modelAndView.setViewName("redirect:/board3/list");
        }
        return modelAndView;
    }
}