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

    private List<Float> listData;
    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView = (CircleView)findViewById(R.id.circleview);
        initData();
    }

    private void initData() {
        listData = new ArrayList<>();
        listData.add(5f);
        listData.add(10f);
        listData.add(10f);
        listData.add(15f);
        listData.add(30f);
//        listData.add(5f);
//        listData.add(50f);
//        listData.add(30f);
//        listData.add(20f);
//        listData.add(90f);

        circleView.setListData(listData);
    }
}
