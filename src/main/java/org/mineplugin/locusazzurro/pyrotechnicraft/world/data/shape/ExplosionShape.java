package org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ExplosionShape implements Serializable {
    SPHERE("sphere", 20),
    RING("ring", 10),
    BURST("burst", 10),
    PLANE("2d", 16),
    MATRIX("3d", 32);

    private String name;
    private int fireworkMixVal;
    private static final Map<String,ExplosionShape> MAP;
    ExplosionShape(String name, int mixVal){
        this.name = name;
        this.fireworkMixVal = mixVal;
    }

    public String getName(){return name;}

    public int getFireworkMixtureValue(){
        return fireworkMixVal;
    }

    public boolean hasCostByDefault(){
        return this == PLANE || this == MATRIX;
    }

    static {
        Map<String, ExplosionShape> map = new HashMap<>();
        for (ExplosionShape shape : ExplosionShape.values()){
            map.put(shape.name, shape);
        }
        MAP = Collections.unmodifiableMap(map);
    }


}
