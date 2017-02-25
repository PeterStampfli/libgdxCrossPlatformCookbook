package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by peter on 1/22/17.
 */

public class AbstractAssets {
    AssetManager assetManager;
    public String soundFileType="wav";
    public String musicFileType="mp3";
    private ObjectMap<String,Sound> soundMap =new ObjectMap<String, Sound>();
    private ObjectMap<String,Music> musicMap =new ObjectMap<String, Music>();
    private ObjectMap<String,TextureAtlas.AtlasRegion> atlasRegionMap =new ObjectMap<String, TextureAtlas.AtlasRegion>();
    private ObjectMap<String,Array<TextureAtlas.AtlasRegion>> atlasRegionArrayMap =new ObjectMap<String, Array<TextureAtlas.AtlasRegion>>();
    private ObjectMap<String,TextureAtlas> atlasMap =new ObjectMap<String, TextureAtlas>();
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

    public void loadBitmapFont(String name) {assetManager.load(name,BitmapFont.class);}

    // special items


    // load texture atlas
    public void loadAtlases(String... names){
        for (String name: names) {
            assetManager.load(name + ".atlas", TextureAtlas.class);
            atlasMap.put(name,null);
        }
    }
    // get texture atlases and extract the texture regions
    // simple atlas region or array of atlas regions (animations)
    // note that atlas region extends texture region
    public void getAtlases(){
        TextureAtlas atlas;
        Array<TextureAtlas.AtlasRegion>newRegions;
        String regionName;
        ObjectMap.Keys<String> atlasNames=atlasMap.keys();
        for (String atlasName:atlasNames){
            atlas=assetManager.get(atlasName+".atlas",TextureAtlas.class);
            newRegions=atlas.getRegions();
            for (TextureAtlas.AtlasRegion newRegion:newRegions){
                regionName=newRegion.name;
                if (newRegion.index==-1){                       // a single image of given name
                    atlasRegionMap.put(regionName,newRegion);
                }
                else{                     // multiple images nameUnderscoreFramenumber.png such as animation_01.png, etc.
                    if (!atlasRegionArrayMap.containsKey(regionName)){
                        atlasRegionArrayMap.put(regionName,atlas.findRegions(regionName));
                    }
                }
            }
        }
    }

    public TextureAtlas.AtlasRegion getAtlasRegion(String name){
        return atlasRegionMap.get(name);
    }

    // for animations
    public Array<TextureAtlas.AtlasRegion> getAtlasRegionArray(String name){
        return atlasRegionArrayMap.get(name);
    }

    public Animation createAnimation(float frameDuration,String name,Animation.PlayMode playMode){
        return new Animation(frameDuration,getAtlasRegionArray(name),playMode);
    }

    // for particle effects
    public TextureAtlas getAtlas(String name){
        return atlasMap.get(name);
    }

    // create particle effect:
    //  effectPath is path relative to android assets
    // atlasName is name of atlas that contains images, together with other,
    //  particleEffect.dispose() not needed because does not own texture
    //  without atlasprefix ??
    //  not particleEffectPool !!!
    public ParticleEffect createParticleEffect(String effectPath, String atlasName){
        ParticleEffect particleEffect=new ParticleEffect();
        particleEffect.load(Gdx.files.internal(effectPath),getAtlas(atlasName));
        return particleEffect;
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
    // soundHashMap and musicMap

    public void setSoundIsOn(boolean value){
        soundIsOn=value;
    }

    // soundHashMap
    public void addSounds(String... names){
        for (String name: names) {
            assetManager.load(name + "." + soundFileType, Sound.class);
            soundMap.put(name, null);
        }

    }

    public void getSounds(){
        ObjectMap.Keys<String> names=soundMap.keys();
        for (String name:names) {
            soundMap.put(name,assetManager.get(name+"."+soundFileType,Sound.class));
        }
    }

    public void playSound(String name){
        if (soundIsOn){
            soundMap.get(name).play();
        }
    }

    // musicMap

    // soundHashMap
    public void addMusics(String... names){
        for (String name: names) {
            assetManager.load(name + "." + musicFileType, Music.class);
            musicMap.put(name, null);
        }
    }

    public void getMusics(){
        ObjectMap.Keys<String> names=musicMap.keys();
        for (String name:names) {
            musicMap.put(name,assetManager.get(name+"."+musicFileType,Music.class));
        }
    }

    public Music getMusic(String name){
        return musicMap.get(name);
    }


}
