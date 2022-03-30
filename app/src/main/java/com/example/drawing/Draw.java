package com.example.drawing;

import android.graphics.Path;
import android.graphics.PointF;
import android.util.SparseArray;


public class Draw {
    private PointF origin;
    private PointF current;
    private int color;
    private Instrument instrument;
    private Path path;
    private SparseArray<PointF> activePoints;


    public Draw(PointF origin, int color, Instrument instrument){
        this.origin = origin;
        this.current = origin;
        this.color = color;
        this.instrument = instrument;
        this.path = new Path();
        path.moveTo(origin.x, origin.y);
        activePoints = new SparseArray<>();
    }

    public int getColor(){
        return color;
    }

    public PointF getCurrent() {
        return current;
    }

    public void setCurrent(PointF current) {
        this.current = current;
    }

    public PointF getOrigin() {
        return origin;
    }

    public void setOrigin(PointF origin) {
        this.origin = origin;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Path getPath(){
        return path;
    }

    public SparseArray<PointF> getActivePoints() {
        return activePoints;
    }

    public void addActivePoint(int id, PointF point){
        activePoints.put(id, point);
    }
}
