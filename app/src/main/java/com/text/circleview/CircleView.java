package com.text.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 类名： CircleView
 * 时间：2017/11/7 10:18
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author Liu_xg
 */
public class CircleView extends View {

    /**
     * 外部轮廓
     */
    private Paint mPaintOut;
    /**
     * 内部扇形
     */
    private Paint mPaintCurrent;
    /**
     * 字体
     */
    private Paint mPaintText;
    /**
     * 每块扇形的面积显示颜色
     */
    private int[] colors = {
            getResources().getColor(R.color.red),
            getResources().getColor(R.color.blue),
            getResources().getColor(R.color.green),
            getResources().getColor(R.color.yellow),
            getResources().getColor(R.color.pink),
            getResources().getColor(R.color.olive),
            getResources().getColor(R.color.olivedrab)};
    /**
     * 画笔宽度
     */
    private float outPaintWidth;
    /**
     * 同心圆的间距
     */
    private float concentricCircleSpace;
    /**
     * 外圈圆和屏幕的间距
     */
    private float outScreenSpace;
    /**
     * 字体大小
     */
    private float textSize;
    /**
     * 当前结束角度
     */
    private float currentAngle;
    /**
     * 当前起始角度
     */
    private float startAngle = 0;
    /**
     * 总数
     */
    private float totalNum = 0;

    private List<DataBean> listData;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        textSize=array.getDimension(R.styleable.CircleView_circle_text_size,
                        dip2px(context, 14));
        concentricCircleSpace=array.getDimension(R.styleable.CircleView_concentric_circle_space,
                dip2px(context, 120));
        outScreenSpace=array.getDimension(R.styleable.CircleView_out_screen_space,
                dip2px(context, 110));
        outPaintWidth=array.getDimension(R.styleable.CircleView_out_paint_width,
                dip2px(context, 2));
        array.recycle();


        /**
         * 初始化数据
         */

        //外层弧
        mPaintOut = new Paint();
        mPaintOut.setAntiAlias(true);
        mPaintOut.setColor(getResources().getColor(R.color.gray));
        mPaintOut.setStyle(Paint.Style.STROKE);
        mPaintOut.setStrokeCap(Paint.Cap.ROUND);
        mPaintOut.setStrokeWidth(outPaintWidth);

        //内部扇形
        mPaintCurrent = new Paint();
        mPaintCurrent.setAntiAlias(true);
        //设置为填充
        mPaintCurrent.setStyle(Paint.Style.FILL);
        mPaintCurrent.setStrokeCap(Paint.Cap.ROUND);

        //字体
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 测量所需区域的宽和高
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //正方形
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float radius = getWidth() / 2 - concentricCircleSpace - outPaintWidth;
        float point = (getWidth()) / 2;
        //规定所需的区域大小，内层
        RectF rCurrent = new RectF(concentricCircleSpace, concentricCircleSpace,
                getWidth() - concentricCircleSpace, getHeight() - concentricCircleSpace);
        mPaintCurrent.setColor(getResources().getColor(R.color.white));
        canvas.drawRect(rCurrent, mPaintCurrent);
        for (int i = 0; i < listData.size(); i++) {
            mPaintCurrent.setColor(colors[i % colors.length]);
            currentAngle = 360 * listData.get(i).getItemValue() / totalNum;
            //画内部的扇形
            canvas.drawArc(rCurrent, startAngle, currentAngle, true, mPaintCurrent);
            //
            calCoor(canvas, radius, point, currentAngle, startAngle,
                    colors[i % colors.length], listData.get(i).getItemName());

            startAngle = currentAngle + startAngle;
        }
        //规定所需的区域大小,外层
        RectF rOut = new RectF(outScreenSpace, outScreenSpace,
                getWidth() - outScreenSpace, getHeight() - outScreenSpace);
        mPaintOut.setColor(Color.GRAY);
        //画外层弧
        canvas.drawArc(rOut, 0, 360, false, mPaintOut);
    }

    /**
     * 计算每个扇形弧的中点坐标
     *
     * @param canvas
     * @param radius
     * @param point
     */
    private void calCoor(Canvas canvas, float radius,
                         float point, float cAngle, float sAngle
            , int color, String title) {
        //当前圆弧对应的角度
        float angle = cAngle / 2 + sAngle;
        float dx = (float) (Math.cos(2 * angle * Math.PI / 360) * radius);
        float dy = (float) (Math.sin(2 * angle * Math.PI / 360) * radius);

        mPaintOut.setColor(color);
        mPaintText.setColor(color);

        float startX = 0, startY = 0;
        float endX = 0, endY = 0;
        //平行线坐标
        float parX = 0, parY = 0;
        //文字坐标
        float textX = 0, textY = 0;
        //偏差量
        float offsetX = 40f, offsetY = 20f, offset = 30f, offsetText = 10f;
        //文字宽度
        float textWidth = mPaintText.measureText(title, 0, title.length());

        /**
         * 第一象限 x > 0 && y < 0
         * 第二象限 x < 0 && y < 0
         * 第三象限 x < 0 && y > 0
         * 第四象限 x > 0 && y > 0
         *
         * &&  并且
         * ||  或者
         */
        if (dx > 0 && dy < 0) {
            //右上
            startX = point + Math.abs(dx);
            startY = point - Math.abs(dy);

            endX = startX + offsetX;
            endY = startY - offsetY;

            parX = endX + offset;
            parY = endY;

            textX = parX + offsetText;
            textY = parY;
        } else if (dx < 0 && dy < 0) {
            //左上
            startX = point - Math.abs(dx);
            startY = point - Math.abs(dy);

            endX = startX - offsetX;
            endY = startY - offsetY;

            parX = endX - offset;
            parY = endY;

            textX = parX - offsetText - textWidth;
            textY = parY;
        } else if (dx < 0 && dy > 0) {
            //左下
            startX = point - Math.abs(dx);
            startY = point + Math.abs(dy);

            endX = startX - offsetX;
            endY = startY + offsetY;

            parX = endX - offset;
            parY = endY;

            textX = parX - offsetText - textWidth;
            textY = parY;
        } else if (dx > 0 && dy > 0) {
            //右下
            startX = point + Math.abs(dx);
            startY = point + Math.abs(dy);

            endX = startX + offsetX;
            endY = startY + offsetY;

            parX = endX + offset;
            parY = endY;

            textX = parX + offsetText;
            textY = parY;
        }
        // 画线
        canvas.drawLine(startX, startY, endX, endY, mPaintOut);
        //画水平线
        canvas.drawLine(endX, endY, parX, parY, mPaintOut);
        //画文字
        canvas.drawText(title, textX, textY, mPaintText);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public List<DataBean> getListData() {
        return listData;
    }

    public void setListData(List<DataBean> listData) {
        this.listData = listData;
        for (DataBean dataBean : listData) {
            totalNum += dataBean.getItemValue();
        }
        invalidate();
    }

}
