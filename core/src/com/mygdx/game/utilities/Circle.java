package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;


public class Circle {
    SpriteBatch batch;
    TextureRegion img;
    Texture input;
    ShapeRenderer renderer;
    int size;


    public void create () {
        batch = new SpriteBatch();
        renderer=new ShapeRenderer();
        byte b=(byte) 255;
        size=50;
        Pixmap pixmap=new Pixmap(size,size, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        Gdx.app.log("blending",""+pixmap.getBlending());
        pixmap.setColor(1,1f,1,0f);
        pixmap.fill();
        pixmap.setColor(1f,1f,1,1f);

        int nHalf=size/2;
        float radius=nHalf-1;
        int nLines= MathUtils.floor(radius/1.414f);
        float lineLength;
        int intLineLength;

        for (int line=0;line<=nLines;line++){
            lineLength=(float) Math.sqrt(radius*radius-(line+0.5)*(line+0.5));
            intLineLength=MathUtils.floor((lineLength));
            Gdx.app.log(line+" length",lineLength+" "+intLineLength);
            lineLength-=intLineLength;

            pixmap.setColor(1f,1f,1,1f);

            pixmap.drawLine(nHalf+line,nHalf-intLineLength,nHalf+line,nHalf-1+intLineLength);
            pixmap.drawLine(nHalf-1-line,nHalf-intLineLength,nHalf-1-line,nHalf-1+intLineLength);

            pixmap.drawLine(nHalf-intLineLength,nHalf+line,nHalf-1+intLineLength,nHalf+line);
            pixmap.drawLine(nHalf-intLineLength,nHalf-1-line,nHalf-1+intLineLength,nHalf-1-line);
            pixmap.setColor(1f,0f,0,1);
            pixmap.setColor(1,1,1,lineLength);
            // antiblox
            pixmap.drawPixel(nHalf+line,nHalf+intLineLength);
            pixmap.drawPixel(nHalf-1-line,nHalf+intLineLength);
            pixmap.drawPixel(nHalf+line,nHalf-1-intLineLength);
            pixmap.drawPixel(nHalf-1-line,nHalf-1-intLineLength);

            pixmap.drawPixel(nHalf+intLineLength,nHalf+line);
            pixmap.drawPixel(nHalf+intLineLength,nHalf-1-line);
            pixmap.drawPixel(nHalf-1-intLineLength,nHalf+line);
            pixmap.drawPixel(nHalf-1-intLineLength,nHalf-1-line);
        }

        img=new TextureRegion(new Texture(pixmap));
        img.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //img.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        pixmap.dispose();
    }

    public void render () {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        //renderer.rect(0,0);
        renderer.end();

        batch.begin();
        //batch.setColor(Color.BROWN);
        batch.draw(img, 100, 100,0,0,
                4*size,4*size,1,1,0);
        batch.draw(img,0,0);


        batch.end();
    }

    public void dispose () {
        batch.dispose();
        img.getTexture().dispose();
    }
}
