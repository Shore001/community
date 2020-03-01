package com.xs.dto;


import com.xs.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
    private String tags;
    private User user;

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", creator=" + creator +
                ", tags='" + tags + '\'' +
                ", user=" + user +
                '}';
    }
}
