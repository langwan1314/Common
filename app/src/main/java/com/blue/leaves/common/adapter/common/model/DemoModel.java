package com.blue.leaves.common.adapter.common.model;


/**
 * @author Jack Tony
 * @date 2015/5/15
 * 超级简单的model，没啥特别的
 */
public class DemoModel {

    public String content;

    public String type;


    /**
     * 这个model中决定数据类型的字段
     */
    public Object getDataType() {
        return type;
    }

}
