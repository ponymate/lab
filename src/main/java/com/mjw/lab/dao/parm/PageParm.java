package com.mjw.lab.dao.parm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 0:40 2022/4/13
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParm {

    private String page;

    private String pageSize;
}
