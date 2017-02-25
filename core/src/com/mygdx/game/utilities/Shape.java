package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

import java.nio.ByteBuffer;

/**
 * Created by peter on 2/25/17.
 */

public class Shape {
    IntArray verticesX;
    IntArray verticesY;
    Array<Line> lines;

    public Shape(){
        verticesX=new IntArray();
        verticesY=new IntArray();
        lines=new Array<Line>();
    }

    public Shape reset(){
        verticesX.clear();
        verticesY.clear();
        lines.clear();
        return this;
    }

    public Shape addVertex(int i,int j){
        verticesX.add(i);
        verticesY.add(j);
        return  this;
    }

    public Shape makeLines(){
        lines.clear();
        int lengthM1=verticesX.size-1;
        for (int i=0;i<lengthM1;i++){
            lines.add(new Line(verticesX.get(i),verticesY.get(i),verticesX.get(i+1),verticesY.get(i+1)));
        }
        lines.add(new Line(verticesX.get(lengthM1),verticesY.get(lengthM1),verticesX.get(0),verticesY.get(0)));
        return this;
    }

    public void showDiagnostics(Pixmap pixmap,int i,int j){
        for (Line line:lines){
            line.showDiagnostics(pixmap,i,j);
        }
    }

    public void draw(Pixmap pixmap){
        int width=pixmap.getWidth();
        int height=pixmap.getHeight();
        ByteBuffer pixels=pixmap.getPixels();
        int i,j;
        int jWidth;
        float d;
        for (j=0;j<height;j++){
            jWidth=j*width;
            for (i=0;i<width;i++) {
                d = 10f;
                for (Line line : lines) {
                        d = Math.min(d, line.distance(i, j));
                }
                    d += 0.5f;
                    if (d > 0) {
                        d = Math.min(d, 1f);
                        //d=1;
                        pixels.put(4 * (i + jWidth) +2, (byte) Math.round((255 * d)));
                    }

            }

        }
        pixels.rewind();

    }


}
