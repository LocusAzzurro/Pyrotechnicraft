package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

public class FireworkFuse extends BasicMaterialItem{

    private final FuseType type;

    public FireworkFuse(FuseType type){
        super();
        this.type = type;
    }

    public enum FuseType{
        INSTANT(0), REGULAR(2), EXTENDED(5), CUSTOM(0);

        private final int delay;

        FuseType(int delay) {
            this.delay = delay;
        }

        public int getFuseDelay(){
           return this.delay;
        }
    }

}
