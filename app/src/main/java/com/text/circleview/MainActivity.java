package com.text.circleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名： MainActivity
 * 时间：2017/11/7 11:58
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author Liu_xg
 */
public class MainActivity extends AppCompatActivity {

    private List<DataBean> listData;
    private CircleView circleView;
    private float[] value = {10f, 20f, 30f, 10f, 60f, 50f, 15f,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView = (CircleView) findViewById(R.id.circleview);
        initData();
    }

    private void initData() {
        listData = new ArrayList<>();
        for (int i = 0; i < value.length; i++) {
            listData.add(new DataBean("测试数据" + i, value[i]));
        }
        circleView.setListData(listData);
    }
}
