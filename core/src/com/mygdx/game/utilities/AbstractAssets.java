package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by peter on 1/22/17.
 */

public class AbstractAssets {
    AssetManager assetManager;
    public String soundFileType="wav";
    public String musicFileType="mp3";
    private HashMap<String,Sound> sounds=new HashMap<String, Sound>();
    private HashMap<String,Music> music=new HashMap<String, Music>();
    private HashMap<String,TextureAtlas.AtlasRegion> atlasRegions=new HashMap<String, TextureAtlas.AtlasRegion>();
    private HashMap<String,Array<TextureAtlas.AtlasRegion>> atlasRegionArrays=new HashMap<String,Array<TextureAtlas.AtlasRegion>>();
    private Array<String> textureAtlases=new Array<String>();
    private Sound noSound=null;
    private Music noMusic=null;
    private boolean soundIsOn=true;


    // general

    public void setAssetManager(AssetManager assetManager){
        this.assetManager=assetManager;
    }

    public void finishLoading(){
        assetManager.finishLoading();
    }

    public boolean update(){
        return assetManager.update();
    }

    public float getProgress(){
        return assetManager.getProgress();
    }

    public void loadTexture(String name){
        assetManager.load(name, Texture.class);
    }

    public void loadSound(String name){
        assetManager.load(name, Sound.class);
    }

    public  void loadMusic(String name){
        assetManager.load(name, Music.class);
    }

    // special items


    // load texture atlas
    public void loadAtlases(String... names){
        for (String name: names) {
            assetManager.load(name + ".atlas", TextureAtlas.class);
            textureAtlases.add(name);
        }
    }
    // get texture atlases and extract the texture regions
    // simple atlas region or array of atlas regions (animations)
    // note that atlas region extends texture region
    public void getAtlases(){
        TextureAtlas atlas;
        Array<TextureAtlas.AtlasRegion>newRegions;
        String regionName;
        for (String atlasName:textureAtlases){
            atlas=assetManager.get(atlasName+".atlas",TextureAtlas.class);
            newRegions=atlas.getRegions();
            for (TextureAtlas.AtlasRegion newRegion:newRegions){
                regionName=newRegion.name;
                if (newRegion.index==-1){                       // a single image of given name
                    atlasRegions.put(regionName,newRegion);
                }
                else{                     // multiple images nameUnderscoreFramenumber.png such as animation_01.png, etc.
                    if (!atlasRegionArrays.containsKey(regionName)){
                        atlasRegionArrays.put(regionName,atlas.findRegions(regionName));
                    }
                }
            }
        }
    }

    public TextureAtlas.AtlasRegion getAtlasRegion(String name){
        return atlasRegions.get(name);
    }


    public Array<TextureAtlas.AtlasRegion> getAtlasRegionArray(String name){
        return atlasRegionArrays.get(name);
    }

    public Animation createAnimation(float frameDuration,String name,Animation.PlayMode playMode){
        return new Animation(frameDuration,getAtlasRegionArray(name),playMode);
    }


    public Sprite createSprite(String regionName){
        return new Sprite(getAtlasRegion(regionName));
    }

    public Sprite createCenteredSprite(String regionName){
        Sprite sprite=createSprite(regionName);
        sprite.setOriginCenter();
        return sprite;
    }


    // tiled map

    public void loadTmxMap(String name){
        if (assetManager.getLoader(TiledMap.class)==null) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        }
        assetManager.load(name,TiledMap.class);
    }
    // sounds and music

    public void setSoundIsOn(boolean value){
        soundIsOn=value;
    }

    // sounds
    public void addSounds(String... names){
        for (String name: names) {
            assetManager.load(name + "." + soundFileType, Sound.class);
            sounds.put(name, noSound);
        }

    }

    public void getSounds(){
        Set<String> names=sounds.keySet();
        for (String name:names) {
            sounds.put(name,assetManager.get(name+"."+soundFileType,Sound.class));
        }
    }

    public void playSound(String name){
        if (soundIsOn){
            sounds.get(name).play();
        }
    }

    // music

    // sounds
    public void addMusics(String... names){
        for (String name: names) {
            assetManager.load(name + "." + musicFileType, Music.class);
            music.put(name, noMusic);
        }
    }

    public void getMusics(){
        Set<String> names=music.keySet();
        for (String name:names) {
            music.put(name,assetManager.get(name+"."+musicFileType,Music.class));
        }
    }

    public Music getMusic(String name){
        return music.get(name);
    }


}
