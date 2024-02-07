package com.advance.mydrawing.custom_drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zulfikar on 28 Dec 2023 at 17:31.
 */
public class CustomDrawingView extends View {
    private Path drawPath;
    private static final String PATHS_KEY = "paths";
    private ArrayList<SerializablePath> paths = new ArrayList<>();
    private ArrayList<Path> undonePaths = new ArrayList<>();
    private Bitmap drawingBitmap;
    private Paint drawPaint;
    private Paint canvasPaint;
    private int paintColor = Color.BLACK;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private int paintSize = 20; // Default paint size
    private boolean erase = false;

    public CustomDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(paintSize); // Set the paint size
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        canvasBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    // Set the paint color
    public void setColor(int newColor) {
        erase = false;
        paintColor = newColor;
        drawPaint.setColor(paintColor);
    }

    // Set the paint size
    public void setPaintSize(float newSize) {
        paintSize = (int) newSize;
        drawPaint.setStrokeWidth(paintSize);
    }

    // Set erasing mode
    public void setErase(boolean isErasing) {
        erase = isErasing;
        if (erase) {
            drawPaint.setColor(Color.WHITE);
        } else {
            drawPaint.setColor(paintColor);
        }
    }

    // Clear the drawing
    public void clearDrawing() {
        drawCanvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);
        paths.clear(); // Clear saved paths
        invalidate();
    }

    public Bitmap getBitmap() {
        return canvasBitmap;
    }

    // Set the drawing from a Bitmap
    public void setBitmap(Bitmap bitmap) {
        canvasBitmap = bitmap;
        drawCanvas.setBitmap(canvasBitmap);
        invalidate();
    }

    private void redrawCanvas() {
        drawCanvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);
        for (SerializablePath serializablePath : paths) {
            drawCanvas.drawPath(serializablePath.getPath(), drawPaint);
        }
        invalidate();
    }


    public void undo() {
        if (!paths.isEmpty()) {
            paths.remove(paths.size() - 1);
            redrawCanvas();
        }
    }


    public void redo() {
        if (undonePaths.size() > 0) {
            undonePaths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }

    public Bundle onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PATHS_KEY, paths);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<SerializablePath> savedPaths =
                    (ArrayList<SerializablePath>) savedInstanceState.getSerializable(PATHS_KEY);
            if (savedPaths != null) {
                paths = savedPaths;
                redrawCanvas();
            }
        }
    }

    // Custom SerializablePath class to store Path objects
    private static class SerializablePath implements Serializable {
        private Path path;

        SerializablePath(Path path) {
            this.path = path;
        }

        Path getPath() {
            return path;
        }
    }
}

