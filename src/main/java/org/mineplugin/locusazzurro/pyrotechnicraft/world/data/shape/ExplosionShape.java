package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ExplosionShape implements Serializable {
    SPHERE("sphere"), RING("ring"), BURST("burst"), PLANE("2d"), MATRIX("3d");

    private String name;
    private static final Map<String,ExplosionShape> MAP;
    ExplosionShape(String name){
        this.name = name;
    }

    public String getName(){return name;}

    static {
        Map<String, ExplosionShape> map = new HashMap<>();
        for (ExplosionShape shape : ExplosionShape.values()){
            map.put(shape.name, shape);
        }
        MAP = Collections.unmodifiableMap(map);
    }


}
