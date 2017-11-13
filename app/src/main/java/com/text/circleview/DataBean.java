package com.text.circleview;

/**
 * 类名：com.text.circleview
 * 时间：2017/11/8 19:58
 * 描述：数据类
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author Liu_xg
 */

public class DataBean {

    private String itemName;
    private float itemValue;

    public DataBean(String itemName, float itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemValue() {
        return itemValue;
    }

    public void setItemValue(float itemValue) {
        this.itemValue = itemValue;
    }
}
