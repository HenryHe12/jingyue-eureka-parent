package com.jingyue.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDataPojo implements Serializable {
    private int page;//当前页面

    private int perPage;//每页多少条记录

    private int totalPage;

    private int totalNum;

    private List<?> list=new ArrayList<Object>();
}
