package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by peter on 11/26/16.
 */

public class Device implements Disposable{

    public Disposer disposer;
    public SpriteBatch spriteBatch;
    public BitmapFont bitmapFont;
    public ShapeRenderer shapeRenderer;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public AssetManager assetManager;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Assets assets;

    public Device(){
        disposer=new Disposer("Device");
        assetManager=new AssetManager();
        assets=new Assets();
        assets.setAssetManager(assetManager);
        disposer.add(assetManager,"assetManager");
        camera=new OrthographicCamera();
    }

    public void setLogging(boolean logging){
        disposer.setLogging(logging);
    }

    public Device createSpriteBatch(){
        if (spriteBatch==null) {
            spriteBatch=new SpriteBatch();
            disposer.add(spriteBatch,"spriteBatch");
        }
        return  this;
    }

    public Device createDefaultBitmapFont(){
        if (bitmapFont==null) {
            bitmapFont = new BitmapFont();
            disposer.add(bitmapFont,"defaultBitmapFont");
        }
        return  this;
    }

    public Device createShapeRenderer(){
        if (shapeRenderer==null) {
            shapeRenderer=new ShapeRenderer();
            disposer.add(shapeRenderer,"shapeRenderer");
        }
        return  this;
    }

    public void createOrthogonalTiledMapRenderer(Viewport viewport, TiledMap tiledMap){
        if (orthogonalTiledMapRenderer==null) {
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
            viewport.apply(true);
            orthogonalTiledMapRenderer.setView((OrthographicCamera)viewport.getCamera());
            disposer.add(orthogonalTiledMapRenderer,"tileMapRenderer");
        }
    }

    @Override
    public void dispose(){
        disposer.dispose();

    }

}
