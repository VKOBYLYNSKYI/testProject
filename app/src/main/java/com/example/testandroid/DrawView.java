package com.example.testandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DrawView extends View {

    private Paint p;
    private Bitmap mBitmap;
    private Bitmap mCoint;
    private Bitmap moneyMove;

    public static int MONEY = 0;
    private static int speed = 150;
    private static int allClickButton = 0;
    private int iCurStep = 0;

    private List<PointF> aPoints = new ArrayList<PointF>();
    private Path ptCurve = new Path();
    private PathMeasure pm;
    private float fSegmentLen;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        p = setPaint(Paint.Style.FILL);
        Resources res = this.getResources();

        mBitmap = BitmapFactory.decodeResource(res, R.drawable.user);
        mCoint = BitmapFactory.decodeResource(res, R.drawable.coin, setBitmapSize(19));
        moneyMove = BitmapFactory.decodeResource(res, R.drawable.money_move, setBitmapSize(1));

        setLayoutParams(new ViewGroup.LayoutParams(mBitmap.getWidth(), mBitmap.getHeight()));

        aPoints.add(new PointF(700f, 0f));
        aPoints.add(new PointF(700f, 500f));
        aPoints.add(new PointF(700f, 1000f));
        aPoints.add(new PointF(700f, 1500f));

        PointF point = aPoints.get(0);
        ptCurve.moveTo(point.x, point.y);

        for(int i = 0; i < aPoints.size() - 1; i++){
            point = aPoints.get(i);
            PointF next = aPoints.get(i+1);
            ptCurve.quadTo(point.x, point.y, (next.x + point.x) / 2, (point.y + next.y) / 2);
        }

        pm = new PathMeasure(ptCurve, false);
        fSegmentLen = pm.getLength() / speed;

    }


    private BitmapFactory.Options setBitmapSize(@Nullable  int size){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        return options;
    }

    private Paint setPaint(@Nullable Paint.Style style){
        Paint px = new Paint();
        px.setTextSize(12);
        px.setStyle(style);
        px.setColor(Color.BLACK);
        return px;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        String StringMoney = String.valueOf(MONEY);
        p.setTextSize(32);
        p.setColor(Color.GREEN);
        canvas.drawText(StringMoney, canvas.getWidth() - getWidth() + 65, 45,p);
        p.setColor(Color.BLACK);
        canvas.drawBitmap(mBitmap,  (getDisplay().getWidth() - getWidth())+100,   getDisplay().getHeight() - getHeight(), p);
        canvas.drawBitmap(mCoint, canvas.getWidth() - getWidth() + 10, 10, p);

        if(allClickButton != 0) {
            for(int i = 0; i < allClickButton; i++) {
                Matrix mxTransform = new Matrix();
                if (iCurStep <= speed) {
                    pm.getMatrix(fSegmentLen * iCurStep, mxTransform,
                            PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG);
                    canvas.drawBitmap(moneyMove, mxTransform, null);
                    iCurStep++;
                    invalidate();
                } else {
                    allClickButton--;
                    iCurStep = 0;
                }
            }
        }
        invalidate();
    }

    public static void init(int money){
        MONEY = money;
        allClickButton++;
    }

}
