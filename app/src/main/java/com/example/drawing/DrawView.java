package com.example.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DrawView extends View {

    private Paint mPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();
    private List<Draw> mList = new ArrayList<>();
    private Draw mCurrentDraw;

    private int mColor = getResources().getColor(R.color.colorRed);
    private Instrument mInstrument = Instrument.LINE;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
        initPaint();
    }

    public void setColor(int color){
        mColor = color;
    }

    public void setInstrument(Instrument instrument){
        mInstrument = instrument;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (Draw draw : mList) {
            mPaint.setColor(draw.getColor());
            switch (draw.getInstrument()){
                case POIT:
                    canvas.drawPoint(draw.getOrigin().x, draw.getOrigin().y, mPaint);
                    break;
                case LINE:
                    canvas.drawLine(draw.getCurrent().x, draw.getCurrent().y, draw.getOrigin().x, draw.getOrigin().y, mPaint);
                    break;
                case CURVE:
                    canvas.drawPath(draw.getPath(), mPaint);
                    break;
                case SQUARE:
                    float left = Math.min(draw.getCurrent().x, draw.getOrigin().x);
                    float right = Math.max(draw.getCurrent().x, draw.getOrigin().x);
                    float top = Math.min(draw.getCurrent().y, draw.getOrigin().y);
                    float bottom = Math.max(draw.getCurrent().y, draw.getOrigin().y);
                    canvas.drawRect(left, top, right, bottom, mPaint);
                    break;
                case POLYGON:
                    SparseArray<PointF> activePoints = draw.getActivePoints();
                    if(activePoints.size() == 1)
                        canvas.drawPoint(activePoints.get(0).x, activePoints.get(0).y, mPaint);
                    else
                        for (int i = 0; i < activePoints.size(); i++) {
                            float x = activePoints.get(i).x;
                            float y = activePoints.get(i).y;
                            float nextX = (i+1 == activePoints.size())? activePoints.get(0).x : activePoints.get(i+1).x;
                            float nextY = (i+1 == activePoints.size())? activePoints.get(0).y : activePoints.get(i+1).y;
                            canvas.drawLine(x, y, nextX, nextY, mPaint);
                        }
            }
        }
    }

    public void clean(){
        mList.clear();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        PointF current = new PointF(event.getX(), event.getY());

        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mCurrentDraw = new Draw(current, mColor, mInstrument);
                mList.add(mCurrentDraw);
                int pointId = event.getPointerId(event.getActionIndex());
                mCurrentDraw.addActivePoint(pointId, current);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF p = new PointF();
                int pointIndex = event.getActionIndex();
                int pId = event.getPointerId(pointIndex);
                p.x = event.getX(pointIndex);
                p.y = event.getY(pointIndex);
                mCurrentDraw.addActivePoint(pId, p);
                break;
            case MotionEvent.ACTION_MOVE:
                if(mCurrentDraw != null){
                    mCurrentDraw.setCurrent(current);
                }
                if(mInstrument == Instrument.CURVE){
                    mList.get(mList.size()-1).getPath().lineTo(event.getX(), event.getY());
                }
                if(mInstrument == Instrument.POLYGON){
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        int id = event.getPointerId(i);
                        PointF point = mCurrentDraw.getActivePoints().get(id);
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                return super.onTouchEvent(event);
        }
        invalidate();
        return true;
    }

    private void initPaint() {
        mPaint.setColor(mColor);
    }

    private void setUpPaint(){
        mBackgroundPaint.setColor(Color.WHITE);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
    }
}
