package com.project.jeans.domain.admin.notice.dao;

import com.project.jeans.domain.admin.notice.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {

    List<NoticeDTO> selectAll();

    //공지사항 INSERT
    NoticeDTO insertNotice();
}
