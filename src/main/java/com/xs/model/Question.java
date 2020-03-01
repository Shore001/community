package com.xs.model;

import lombok.Data;

@Data
public class Question {

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
}
