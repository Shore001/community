package com.xs.dto;

import com.xs.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModify;

    private Integer likeCount;

    private String content;
    private User user;
}
