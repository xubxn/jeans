package com.project.jeans.service.member;

import com.project.jeans.domain.common.paging.dto.PageDTO;
import com.project.jeans.domain.member.dao.MemberDAO;
import com.project.jeans.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Autowired
    private final MemberDAO memberDAO;

    /*회원가입 관련------------------------------------------------------*/
    //수강생 회원가입
    @Override
    public int insertStudent(MemberDTO memberDTO) {
        return memberDAO.insertStudent(memberDTO);
    }

    //강사님 회원가입
    @Override
    public int insertTeacher(MemberDTO memberDTO) {
        return memberDAO.insertTeacher(memberDTO);
    }

    //아이디 중복체크
    @Override
    public int idDuplicate(String member_id) {
        return memberDAO.idDuplicate(member_id);
    }

    //전화번호 중복체크
    @Override
    public String phoneDuplicate(String member_phone) {
        int result = memberDAO.phoneDuplicate(member_phone);
        if (result > 0) {
            return "duplicate"; // 전화번호 중복
        } else {
            return "available"; // 전화번호 사용 가능
        }
    }

    //강사코드 중복 체크
    @Override
    public int codeDuplicate(String member_code) {
        return memberDAO.codeDuplicate(member_code);
    }

    //강사코드 조회
    @Override
    public int codeCheck(String code) {
        return memberDAO.codeCheck(code);
    }


    /*로그인 관련---------------------------------------------------------*/
    //회원 타입 조회
    @Override
    public String memberType(String member_id) {
        return memberDAO.memberType(member_id);
    }

    //수강생 로그인 처리
    @Override
    public int loginStudent(MemberDTO memberDTO) {
        return memberDAO.loginStudent(memberDTO);
    }

    //요청 처리중인 회원 조회
    @Override
    public int noAccept(String member_id) {
        return memberDAO.noAccept(member_id);
    }

    //id찾기
    @Override
    public String findId(MemberDTO memberDTO) {
        return memberDAO.findId(memberDTO);
    }

    //비밀번호 찾기
    @Override
    public String findPw(MemberDTO memberDTO) {
        return memberDAO.findPw(memberDTO);
    }

    //비밀번호 수정
    @Override
    public int updatePw(MemberDTO memberDTO) {
        return memberDAO.updatePw(memberDTO);
    }

    //강사님 로그인 처리
    @Override
    public int loginTeacher(MemberDTO memberDTO) {
        return memberDAO.loginTeacher(memberDTO);
    }

    //정보 가져오기(세션)
    @Override
    public MemberDTO getMemberInfo(String member_id) {
        return memberDAO.getMemberInfo(member_id);
    }


    /*관리자 관련---------------------------------------------------------*/
    //현재 회원 조회

    @Override
    public List<MemberDTO>getShowMember(int pageNum, int size) {
        int offset = (pageNum - 1) * size;
        return memberDAO.getShowMember(offset, size);
    }
    //현재 회원 수
    @Override
    public int getShowMemberCount() {
        return memberDAO.getShowMemberCount();
    }

    //탈퇴 회원 조회
    @Override
    public List<MemberDTO> getHiddenMember(PageDTO pageDTO) {
        return memberDAO.getHiddenMember(pageDTO);
    }

    //탈퇴회원 수
    @Override
    public int getHiddenMemberCount(PageDTO pageDTO) {
        return memberDAO.getHiddenMemberCount(pageDTO);
    }

    //회원 가입 요청 회원 조회
    @Override
    public List<MemberDTO> getNoAcceptMember(PageDTO pageDTO) {
        return memberDAO.getNoAcceptMember(pageDTO);
    }

    //요청 회원 수
    @Override
    public int getNoAcceptMemberCount(PageDTO pageDTO) {
        return memberDAO.getNoAcceptMemberCount(pageDTO);
    }

    //회원 가입 요청 처리
    @Override
    public int updateAccept(Map<String, Object> map) {
        return memberDAO.updateAccept(map);
    }
}
