package com.example.unotes.bean;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author witer330
 * @date 2024/01/11
 */
@Data
@Builder
public class Theme {
    public Theme() {
    }

    private int id;
    private String  type;//主题库,主题,分类,科目
    private String title;
    private int location;
    private int partentId;//父id
    private Date createTime;
    private Date updateTime;
}
