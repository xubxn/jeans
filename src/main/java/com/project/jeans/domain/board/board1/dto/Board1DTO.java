package com.project.jeans.domain.board.board1.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
public class Board1DTO {

    private int board1_no;
    private String board1_title;
    private String board1_content;
    private Timestamp board1_regdate;
    private String board1_isshow;
    private String member_id;
    private String member_name;
    private String member_type;

}