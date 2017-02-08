package com.mygdx.game.utilities;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by peter on 1/22/17.
 */

public class Assets extends AbstractAssets {

    public TiledMap tiledMap;

    public void load(){
        loadTmxMap("map.tmx");

    }

    public void get(){
        tiledMap=assetManager.get("map.tmx");
    }
}
