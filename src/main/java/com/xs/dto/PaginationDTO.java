package com.xs.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;

    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer pageIndex) {
        this.totalPage=totalPage;
        //当前页数，用于按钮高亮判断
        currentPage=pageIndex;
        //生成按钮数量
        pages.add(pageIndex);
        for (int i = 1; i <= 3; i++) {
            if (pageIndex - i > 0){
                pages.add(0,pageIndex-i);
            }
            if (pageIndex + i <= totalPage){
                pages.add(pageIndex+i);
            }
        }
        //是否展示上一页按钮
        showPrevious = pageIndex != 1;
        //是否展示下一页按钮
        showNext = pageIndex != totalPage;
        //是否展示第一页按钮
        showFirstPage = !pages.contains(1);
        //是否展示最后一页按钮
        showEndPage = !pages.contains(totalPage);
    }
}
