package br.com.lukakas.find_figure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import br.com.lukakas.find_figure.R;

public class DoodleView extends View {

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private static final int RADIUS = 200;

    private float radius;

    private TextView chooseTextView;

    private Bitmap bitmap;

    private Canvas canvasBitmap;

    private Paint paintScreen;

    private Paint paintLine;

    private int correct;

    private int sqr;

    private int currentCircle0Color, currentCircle1Color, currentCircle2Color, currentCircle3Color;

    private int triangle, circle;

    private int rect;

    private Rect sqrObj;

    private Rect rectObj;

    private int circleX;

    private int circleY;

    List<LatLng> trian;

    private int score;

    private TextView scoreTextView;

    private int numCircle;

    private ArrayList<Point> posicoes = new ArrayList<>();

    private Paint paintCircle0,paintCircle1,paintCircle2,paintCircle3;

    private boolean firstTime = true;

    private String  text0 = MainActivity.text0,
                    text1 = MainActivity.text1,
                    text2 = MainActivity.text2,
                    text3 = MainActivity.text3;

//    private ArrayList<String> list;

    public DoodleView (Context context, AttributeSet set){
        super (context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.argb(255,255,5,5));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setStrokeCap(Paint.Cap.ROUND);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("texts");

        score = 0;
//        round = 0;
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

        posicoes = gerarPosicoes();


        Paint paint          = new Paint();
        Paint paintClear     = new Paint();
        TextPaint textPaint  = new TextPaint();
        int width            = getWidth();
        int height           = getWidth();
        int x                = 300;
        int y                = 300;
        radius           = (float) (width*1.5/6);

        PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;      // mode Mode.ADD

        paintClear.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(100 * getResources().getDisplayMetrics().density);
        textPaint.setTextSize(38f);
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(3);

        // ** clear canvas background to white**
        paintClear.setColor(Color.WHITE);
        canvas.drawPaint(paintClear);
        //canvas.save();

        Bitmap compositeBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        Canvas compositeCanvas = new Canvas(compositeBitmap);
        paintClear.setColor(Color.TRANSPARENT);
        compositeCanvas.drawPaint(paintClear);

        // ** draw destination circle in red **
//        paint.setColor(Color.RED);
        if(firstTime) {
            paintCircle0 = gerarPaint(paint);
            currentCircle0Color = paintCircle0.getColor();
        } else {
            paintCircle0.setColor(currentCircle0Color);
        }
        compositeCanvas.drawCircle(posicoes.get(0).x, posicoes.get(0).y, radius, paintCircle0);

        // ** set Xfermode **
        paint.setXfermode(new PorterDuffXfermode(mode));
        textPaint.setXfermode(new PorterDuffXfermode(mode));

        // ** draw source circle in blue **
//        paint.setColor(Color.BLUE);
        if(firstTime){
            paintCircle1 = gerarPaint(paint);
            currentCircle1Color = paintCircle1.getColor();
        } else {
            paintCircle1.setColor(currentCircle1Color);
        }
        compositeCanvas.drawCircle(posicoes.get(1).x, posicoes.get(1).y, radius, paintCircle1);
//        paint.setColor(Color.GREEN);
        if(firstTime) {
            paintCircle2 = gerarPaint(paint);
            currentCircle2Color = paintCircle2.getColor();
        } else {
            paintCircle2.setColor(currentCircle2Color);
        }
        compositeCanvas.drawCircle(posicoes.get(2).x, posicoes.get(2).y, radius, paintCircle2);
//        paint.setColor(Color.YELLOW);
        if(firstTime) {
            paintCircle3 = gerarPaint(paint);
            currentCircle3Color = paintCircle3.getColor();
        } else {
            paintCircle3.setColor(currentCircle3Color);
        }
        compositeCanvas.drawCircle(posicoes.get(3).x, posicoes.get(3).y, radius, paintCircle3);

        compositeCanvas.save();
        mode = PorterDuff.Mode.	SRC_OVER;
        textPaint.setXfermode(new PorterDuffXfermode(mode));
        paint.setTextSize(48f);
        paint.setColor(Color.BLACK);
        compositeCanvas.drawText("IKIGAI",posicoes.get(0).x-60,posicoes.get(2).y-radius/2-30,textPaint);

        compositeCanvas.drawText("MISSÃO",(posicoes.get(0).x+posicoes.get(3).x)/2,(posicoes.get(0).y+posicoes.get(3).y)/2-60,textPaint);

        compositeCanvas.drawText("VOCAÇÃO",(posicoes.get(3).x+posicoes.get(2).x)/2-20,(posicoes.get(3).y+posicoes.get(2).y)/2+60,textPaint);

        compositeCanvas.drawText("PAIXÃO",(posicoes.get(0).x+posicoes.get(1).x)/2-130,(posicoes.get(0).y+posicoes.get(1).y)/2-60,textPaint);

        compositeCanvas.drawText("PROFISSÃO",(posicoes.get(1).x+posicoes.get(2).x)/2-168,(posicoes.get(3).y+posicoes.get(2).y)/2+60,textPaint);

        compositeCanvas.drawText(text0 ,posicoes.get(0).x-100,posicoes.get(0).y-160,textPaint);
        compositeCanvas.drawText(text1 ,posicoes.get(1).x-250,posicoes.get(1).y,textPaint);
        compositeCanvas.drawText(text2 ,posicoes.get(2).x-100,posicoes.get(2).y+200,textPaint);
        compositeCanvas.drawText(text3 ,posicoes.get(3).x+100,posicoes.get(3).y,textPaint);

        compositeCanvas.restore();

        // ** draw text in Green **
        compositeCanvas.save();
        compositeCanvas.rotate(-45, x, y+150);
//        compositeCanvas.drawText("- 65,6", x, y+150, textPaint);
        compositeCanvas.restore();

        //copy compositeCanvas to canvas
        canvas.drawBitmap(compositeBitmap, 0, 0, null);
        //canvas.restore();
        firstTime = false;
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(whichCircle(event) == 0){
//            Toast.makeText(getContext(), "Primeiro", Toast.LENGTH_SHORT).show();
//        } else if (whichCircle(event) == 1) {
//            Toast.makeText(getContext(), "Segundo", Toast.LENGTH_SHORT).show();
//        } else if (whichCircle(event) == 2) {
//            Toast.makeText(getContext(), "Terceiro", Toast.LENGTH_SHORT).show();
//        } else if (whichCircle(event) == 3) {
//            Toast.makeText(getContext(), "Quarto", Toast.LENGTH_SHORT).show();
//        }
//        invalidate();
//
//        return false;
//    }

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

    private int whichCircle(MotionEvent event) {
        // Verificar qual circulo ele tocou
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(0).x, 2) +
                Math.pow(event.getY() - posicoes.get(0).y, 2)) <= radius) {
            return 0;
        } else if (Math.sqrt(Math.pow(event.getX() - posicoes.get(1).x, 2) +
                Math.pow(event.getY() - posicoes.get(1).y, 2)) <= radius) {
            return 1;
        } else if (Math.sqrt(Math.pow(event.getX() - posicoes.get(2).x, 2) +
                Math.pow(event.getY() - posicoes.get(2).y, 2)) <= radius) {
            return 2;
        } else if (Math.sqrt(Math.pow(event.getX() - posicoes.get(3).x, 2) +
                Math.pow(event.getY() - posicoes.get(3).y, 2)) <= radius) {
            return 3;
        }
        return 4;
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
        int y = getWidth();
        Point posicao = new Point(3*x/6,2*y/6);
        Point posicao1 = new Point(2*x/6,3*y/6);
        Point posicao2 = new Point(3*x/6,4*y/6);
        Point posicao3 = new Point(4*x/6,3*y/6);
        Ponteiros.add(posicao);
        Ponteiros.add(posicao1);
        Ponteiros.add(posicao2);
        Ponteiros.add(posicao3);

        return Ponteiros;

    }

    public Paint gerarPaint(Paint paint){
//        Paint newPaint;
//        newPaint = paint;
        Random random = new Random ();
        int r = random.nextInt(155)+100;
        int g = random.nextInt(155)+100;
        int b = random.nextInt(155)+100;
        // paint.setStyle(Paint.Style.FILL);
        // paint.setColor(Color.WHITE);
        paint.setColor(Color.argb(255, r, g, b));
        return paint;
    }

    public boolean isOnCircle(MotionEvent event, int i){
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(i).x, 2) +
                Math.pow(event.getY() - posicoes.get(i).y, 2)) <= radius) {
            return true;
        }
        return false;
    }

    //Declare this flag globally
    boolean goneFlag = false;
    MotionEvent mainEvent;

    //Put this into the class
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            goneFlag = true;
            //Code for long click
            Toast.makeText(getContext(), "que merda", Toast.LENGTH_SHORT).show();
            whichCircleTouch(mainEvent, 0);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                goneFlag = false;
                handler.postDelayed(mLongPressed, 1000);
                //This is where my code for movement is initialized to get original location.
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(mLongPressed);
                if (mainEvent == null) {
                    mainEvent = event;
                }
                if(!goneFlag) {
                    //Code for single click
                    if(MainActivity.checked) {
                        whichCircleTouch(event, 0);
                    } else {
                        whichCircleTouch(event, 1);
                    }
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                handler.removeCallbacks(mLongPressed);
                //Code for movement here. This may include using a window manager to update the view
                break;
        }
        return true;
    }

    public void whichCircleTouch(MotionEvent event, int option){
        if(isOnCircle(event, 0)&&!isOnCircle(event,1)&&!isOnCircle(event,2)&&!isOnCircle(event,3)){
            if(option == 0) {
                setByColorPicker(0);
            } else {
                DialogText(0);
            }
        }
        else if(!isOnCircle(event,0)&&isOnCircle(event,1)&&!isOnCircle(event,2)&&!isOnCircle(event,3)){
            if(option == 0) {
                setByColorPicker(1);
            } else {
                DialogText(1);
            }
        }
        else if(!isOnCircle(event,0)&&!isOnCircle(event,1)&&isOnCircle(event,2)&&!isOnCircle(event,3)){
            if(option == 0) {
                setByColorPicker(2);
            } else {
                DialogText(2);
            }
        }
        else if(!isOnCircle(event,0)&&!isOnCircle(event,1)&&!isOnCircle(event,2)&&isOnCircle(event,3)){
            if(option == 0) {
                setByColorPicker(3);
            } else {
                DialogText(3);
            }
        }
        //------------------------------------------------------------------------------------------
        else if(isOnCircle(event,0)&& isOnCircle(event,1)&&!isOnCircle(event,2)&& !isOnCircle(event,3)){
        }
        else if(isOnCircle(event,0)&&!isOnCircle(event,1)&&!isOnCircle(event,2)&& isOnCircle(event,3)){
        }
        else if(!isOnCircle(event,0)&&isOnCircle(event,1)&&isOnCircle(event,2)&& !isOnCircle(event,3)){
        }
        else if(!isOnCircle(event,0)&&!isOnCircle(event,1)&&isOnCircle(event,2)&& isOnCircle(event,3)){
        }
    }

    public void setByColorPicker(int numCircle){
        String texto="";
        if(numCircle==0){
            texto="O que você ama?";
        }
        if(numCircle==1){
            texto="Em que voce é bom?";
        }
        if(numCircle==2){
            texto="Pelo que voce pode ser pago?";
        }
        if(numCircle==3){
            texto="O que o mundo precisa?";
        }
        EditText input = new EditText(getContext());
        new ColorPickerDialog.Builder(getContext())
                .setTitle("ColorPicker Dialog")
                .setMessage(texto)
                .setView(input)
                .setPositiveButton("Confirm",
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                switch (numCircle) {
                                    case 0:
                                        currentCircle0Color = envelope.getColor();
                                        text0 = input.getText().toString();

                                        break;
                                    case 1:
                                        currentCircle1Color = envelope.getColor();
                                        text1 = input.getText().toString();
                                        break;
                                    case 2:
                                        currentCircle2Color = envelope.getColor();
                                        text2 = input.getText().toString();
                                        break;
                                    case 3:
                                        currentCircle3Color = envelope.getColor();
                                        text3 = input.getText().toString();
                                        break;
                                }
                                invalidate();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                .show();
    }

    public void DialogText(int n){
        String texto="";
        ArrayList<String> list = new ArrayList<>();
        if(n==0){
            texto="O que você ama?";
            list = MainActivity.listAma;
        }
        if(n==1){
            texto="Em que voce é bom?";
            list = MainActivity.listBom;
        }
        if(n==2){
            texto="Pelo que voce pode ser pago?";
            list = MainActivity.listPago;
        }
        if(n==3){
            texto="O que o mundo precisa?";
            list = MainActivity.listPrecisa;
        }
        EditText input = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(texto)
//                .setMessage(texto)
                .setView(input);

//            String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        ArrayList<String> finalList = list;
        builder.setItems(list.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(n==0) {
                        text0 = finalList.get(which);
                    }
                    if(n==1) {
                        text1 = finalList.get(which);
                    }
                    if(n==2) {
                        text2 = finalList.get(which);
                    }
                    if(n==3) {
                        text3 = finalList.get(which);
                    }
                }
            });

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(n==0) {
                            text0 = input.getText().toString();
                            ref.child("ama").push().setValue(text0);
                        }
                        if(n==1) {
                            text1 = input.getText().toString();
                            ref.child("bom").push().setValue(text1);
                        }
                        if(n==2) {
                            text2 = input.getText().toString();
                            ref.child("pago").push().setValue(text2);
                        }
                        if(n==3) {
                            text3 = input.getText().toString();
                            ref.child("precisa").push().setValue(text3);
                        }
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}