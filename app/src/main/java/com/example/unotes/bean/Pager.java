package com.example.unotes.bean;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author WTL
 * @date 2023/11/03
 */
@Data
@Builder
public class Pager {
    private int id;
    private String pagerName;
    private int pagerNum;
    private Date createTime;
    private Date updateTime;
}
