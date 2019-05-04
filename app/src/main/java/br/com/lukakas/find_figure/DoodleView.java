package br.com.lukakas.find_figure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lukakas.find_figure.R;

public class DoodleView extends View {

    private static final int RADIUS = 200;

    private TextView chooseTextView;

    private Bitmap bitmap;

    private Canvas canvasBitmap;

    private Paint paintScreen;

    private Paint paintLine;

    private int correct;

    private int sqr;

    private int circle;

    private int triangle;

    private int rect;

    private Rect sqrObj;

    private Rect rectObj;

    private int circleX;

    private int circleY;

    List<LatLng> trian;

    private int score;

    private TextView scoreTextView;

    private int round;

    public DoodleView (Context context, AttributeSet set){
        super (context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.argb(255,255,5,5));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        score = 0;
        round = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ArrayList<Point> posicoes = new ArrayList<>();
        posicoes = gerarPosicoes();


        Paint paint          = new Paint();
        Paint paintClear     = new Paint();
        TextPaint textPaint  = new TextPaint();
        int width            = getWidth();
        int height           = getHeight();
        int x                = 300;
        int y                = 300;
        float radius           = (float) (width*1.7/6);

        PorterDuff.Mode mode        = PorterDuff.Mode.ADD;      // mode Mode.ADD

        paintClear.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100 * getResources().getDisplayMetrics().density);
        textPaint.setColor(Color.GREEN);
        textPaint.setStrokeWidth(3);

        // ** clear canvas background to white**
        paintClear.setColor(Color.WHITE);
        canvas.drawPaint(paintClear);
        //canvas.save();

        Bitmap compositeBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas compositeCanvas = new Canvas(compositeBitmap);
        paintClear.setColor(Color.TRANSPARENT);
        compositeCanvas.drawPaint(paintClear);

        // ** draw destination circle in red **
        paint.setColor(Color.RED);
        compositeCanvas.drawCircle(posicoes.get(0).x, posicoes.get(0).y, radius, paint);

        // ** set Xfermode **
        paint.setXfermode(new PorterDuffXfermode(mode));
        textPaint.setXfermode(new PorterDuffXfermode(mode));

        // ** draw source circle in blue **
        paint.setColor(Color.BLUE);
        compositeCanvas.drawCircle(posicoes.get(1).x, posicoes.get(1).y, radius, paint);
        paint.setColor(Color.GREEN);
        compositeCanvas.drawCircle(posicoes.get(2).x, posicoes.get(2).y, radius, paint);
        paint.setColor(Color.YELLOW);
        compositeCanvas.drawCircle(posicoes.get(3).x, posicoes.get(3).y, radius, paint);

        // ** draw text in Green **
        compositeCanvas.save();
        compositeCanvas.rotate(-45, x, y+150);
        compositeCanvas.drawText("- 65,6", x, y+150, textPaint);
        compositeCanvas.restore();

        //copy compositeCanvas to canvas
        canvas.drawBitmap(compositeBitmap, 0, 0, null);
        //canvas.restore();
    }//onDraw

    private void drawSquare(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                sqrObj = new Rect(getWidth()/4-150, getHeight()/4-200, getWidth()/2-30, getHeight()/2-200);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 1:
                sqrObj = new Rect(getWidth()/4*3-225, getHeight()/4-300, getWidth()/4*3+150, getHeight()/2-300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 2:
                sqrObj = new Rect(getWidth()/4-180, getHeight()/2+300, getWidth()/2-60, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 3:
                sqrObj = new Rect(getWidth()/4*3-220, getHeight()/2+300, getWidth()/4*3+180, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
        }
    }

    private void drawCircle(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                circleX = getWidth()/4;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 1:
                circleX = getWidth()/4*3;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 2:
                circleX = getWidth()/4;
                circleY = getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 3:
                circleX = getWidth()/4*3;
                circleY = getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
        }
    }

    private void drawRect(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                rectObj = new Rect(getWidth()/4-150, getHeight()/4-300, getWidth()/2-210, getHeight()/2-300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 1:
                rectObj = new Rect(getWidth()/4*3-100, getHeight()/4-200, getWidth()/4*3+150, getHeight()/2-200);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 2:
                rectObj = new Rect(getWidth()/4-150, getHeight()/2+300, getWidth()/2-210, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 3:
                rectObj = new Rect(getWidth()/4*3-125, getHeight()/2+300, getWidth()/4*3+125, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
        }
    }

    public void drawTriangle(Canvas canvas, Paint paint, int pos, int width) {
        int x = 0;
        int y = 0;
        switch (pos) {
            case 0:
                x = getWidth()/4;
                y = getHeight()/4;
                break;
            case 1:
                x = getWidth()/4*3;
                y = getHeight()/4;
                break;
            case 2:
                x = getWidth()/4;
                y = getHeight()/4*3;
                break;
            case 3:
                x = getWidth()/4*3;
                y = getHeight()/4*3;
                break;
        }

        int halfWidth = width / 2;

        trian = new ArrayList<>();
        trian.add(new LatLng(x/100, (y-halfWidth)/100));
        trian.add(new LatLng((x -halfWidth)/100, (y + halfWidth)/100));
        trian.add(new LatLng((x + halfWidth)/100, (y + halfWidth)/100));

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }

    private int next(int i) {
        if (i >= 3)
            return 0;
        return i+1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(checkIfPoint(event)){
            addPoint();
        } else {
            decreasePoint();
        }
        invalidate();
        return false;
    }

    private void decreasePoint() {
        if(score != 0) {
            score--;
        }
        scoreTextView.setText("Score: " + score);
    }

    private boolean checkIfPoint(MotionEvent event) {
        // Verifica primeiro qual a figura que foi pedida
        // Passa o evento do touch para verificar no que tocou é a figura requisitada
        if(correct==sqr) {
            if(itsMySqr(event)) {
                return true;
            }
            return false;
        } else if(correct==circle) {
            if(itsMyCir(event)) {
                return true;
            }
        } else if(correct==rect) {
            if(itsMyrRect(event)) {
                return true;
            }
        } else if(correct==triangle) {
            if(itsMyTri(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean itsMyTri(MotionEvent event) {
        float x = event.getX()/100;
        float y = event.getY()/100;
        LatLng point = new LatLng(x, y);
        boolean contains = PolyUtil.containsLocation(point.latitude, point.longitude, trian, true);
        if (contains) {
            return true;
        }
        return false;
    }

    private boolean itsMyCir(MotionEvent event) {
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }

    private boolean itsMyrRect(MotionEvent event) {
        // Returns true if (x,y) is inside the rectangle.
        if(rectObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private boolean itsMySqr(MotionEvent event) {
        // Returns true if (x,y) is inside the square.
        if(sqrObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private void addPoint() {
        score++;
        scoreTextView.setText("Score: " + score);
    }

    public ArrayList<Point> gerarPosicoes(){
        ArrayList<Point> Ponteiros = new ArrayList<>();
        int x = getWidth();
        int y = getHeight();
        Point posição1 = new Point(3*x/6,2*y/6);
        Point posição2 = new Point(2*x/6,3*y/6);
        Point posição3 = new Point(3*x/6,4*y/6);
        Point posição4 = new Point(4*x/6,3*y/6);
        Ponteiros.add(posição1);
        Ponteiros.add(posição2);
        Ponteiros.add(posição3);
        Ponteiros.add(posição4);


        return Ponteiros;

    }

    public Paint gerarPaint(Paint paint){
        // Paint paint = new Paint();
        Random random = new Random ();
        int r = random.nextInt(155)+100;
        int g = random.nextInt(155)+100;
        int b = random.nextInt(155)+100;
        // paint.setStyle(Paint.Style.FILL);
        // paint.setColor(Color.WHITE);
        paint.setColor(Color.argb(255, r, g, b));
        return paint;
    }
}
