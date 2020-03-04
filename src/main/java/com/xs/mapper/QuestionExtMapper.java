package com.xs.mapper;

import com.xs.model.Question;
import org.apache.ibatis.annotations.Param;

public interface QuestionExtMapper {
    int incView(@Param("question") Question question);

    int incCommentCount(@Param("question") Question question);
}
