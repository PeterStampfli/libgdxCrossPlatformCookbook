package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by peter on 2/24/17.
 */

//  make it a private class of something ...
public class Line {
    private int x1,y1,x2,y2;
    private float slope;
    private boolean isHorizontal;
    private boolean invertedOrder;

    public Line(int x1p,int y1p,int x2p,int y2p){
        x1=x1p;
        y1=y1p;
        x2=x2p;
        y2=y2p;
        isHorizontal=(Math.abs(x2-x1)>Math.abs(y2-y1));
        if (isHorizontal){
            invertedOrder=(x1>x2);
            exchangeEndpointsIfnotOriginalOrder();
            slope=((float)(y2-y1))/((float)(x2-x1));
            Gdx.app.log("invertedOrder",""+invertedOrder);
            Gdx.app.log("slope",""+slope);
        }
        else {
            invertedOrder = (y1 > y2);
            exchangeEndpointsIfnotOriginalOrder();
            slope = ((float) (x2 - x1)) / ((float) (y2 - y1));
            Gdx.app.log("invertedOrder",""+invertedOrder);
            Gdx.app.log("slope",""+slope);
        }
    }

    private void exchangeEndpointsIfnotOriginalOrder(){
        if (invertedOrder) {
            int h = x1;
            x1 = x2;
            x2 = h;
            h = y1;
            y1 = y2;
            y2 = h;
        }
    }

    // check if line is relevant for given point
    public boolean isRelevant(int x,int y){
        boolean result;
        if (isHorizontal){
            result= (x>=x1)&&(x<=x2);
        }
        else {
            result= (y>=y1)&&(y<=y2);
        }
        return result;
    }

    // get smaller manhattan distance from line, positive for inside
    public float distance(int x,int y){
        float result;
        if (isHorizontal){
            result=y-slope*(x-x1)-y1;
            Gdx.app.log("**",""+(slope*(x-x1)+y1));
        }
        else {
            result=-(x-slope*(y-y1)-x1);
        }
        if (invertedOrder){
            result=-result;
        }
        return result;
    }

    public void showDiagnostics(Pixmap pixmap,int x,int y){
        pixmap.setColor(Color.BLUE);

        pixmap.drawLine(x1,y1,x2,y2);
        if (isRelevant(x,y)) {
            pixmap.setColor(Color.GREEN);
            int d = Math.round(distance(x, y));
            if (d<0){
                pixmap.setColor(Color.RED);
            }
            if (isHorizontal) {
                if (invertedOrder){
                    pixmap.drawLine(x, y,x,y+d );

                }
                else {
                    pixmap.drawLine(x, y, x,y-d);
                }
            }
            else {
                if (invertedOrder){
                    pixmap.drawLine(x,y,x-d,y);
                }
                else {
                    pixmap.drawLine(x,y,x+d,y);

                }

            }
        }
        pixmap.setColor(Color.BLACK);
        pixmap.drawPixel(x,y);
        if (invertedOrder){
            pixmap.drawPixel(x2, y2);

        }
        else {
            pixmap.drawPixel(x1, y1);
        }
    }
}
