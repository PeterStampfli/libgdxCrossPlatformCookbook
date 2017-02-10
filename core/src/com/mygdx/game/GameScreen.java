package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Device;

import java.util.Comparator;

/**
 * Created by peter on 1/19/17.
 */

public class GameScreen extends ScreenAdapter {
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;

    private TheGame theGame;
    private Device device;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private TextureAtlas.AtlasRegion dinosaurRegion;
    private TextureAtlas cavemanAtlas;
    private Animation cavemanWalk;

    private OrthographicCamera camera;
    private Viewport effectsViewport;
    private ParticleEffect[] effects;
    private int currentEffect;
    private Vector3 touchPos;

    private ParticleEffect particleEffect;

    Sprite sprite;


    public GameScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        viewport=device.viewport;
        viewport.apply(true);

    }

     @Override
    public void show(){
         sprite=device.assets.createCenteredSprite("trex");

         sprite.setRotation(60);
         Basic.setCenter(sprite,new Vector2(200,200));
         cavemanAtlas=device.assetManager.get("caveman.atlas",TextureAtlas.class);
         Array<TextureAtlas.AtlasRegion> cavemanRegions=cavemanAtlas.getRegions();
         cavemanRegions.sort(new Comparator<TextureAtlas.AtlasRegion>() {
             @Override
             public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
                 return region1.name.compareTo(region2.name);
             }
         });
         cavemanWalk=new Animation(0.3f,cavemanRegions, Animation.PlayMode.LOOP);

         camera = new OrthographicCamera();
         effectsViewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

         particleEffect=new ParticleEffect();
         particleEffect.load(Gdx.files.internal("test.particle"),Gdx.files.internal(""));
         particleEffect.setPosition(SCENE_WIDTH/2,SCENE_HEIGHT/2);
         particleEffect.start();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }



    public void drawDebug(){
        Vector2 p=Basic.getCenter(sprite,new Vector2());
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(p.x,p.y,20,20);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(sprite.getOriginX(),sprite.getOriginY(),20,20);

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(200,200,10,10);

        shapeRenderer.end();
    }

    public void draw(){
        sprite.rotate(0.2f);
        TextureRegion frame=cavemanWalk.getKeyFrame(Basic.getTime());

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.setColor(Color.YELLOW);

        sprite.draw(spriteBatch);
        spriteBatch.draw(frame,200,200);
        particleEffect.draw(spriteBatch,Gdx.graphics.getDeltaTime());

        spriteBatch.end();

    }

    @Override
    public void render(float delta){
        if (particleEffect.isComplete()){
            particleEffect.reset();
        }

        particleEffect.setPosition(0,0);
        viewport.apply(true);

        Basic.clearBackground(Color.BLACK);

        draw();
        drawDebug();
        effectsViewport.apply(true);


 //       spriteBatch.setProjectionMatrix(effectsViewport.getCamera().combined);
 //       spriteBatch.begin();

 //       spriteBatch.end();


    }

}
