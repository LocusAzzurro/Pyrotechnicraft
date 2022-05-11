package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.AbstractFireworkMissileEntity;

import java.util.HashMap;
import java.util.Map;

public record FlightProperties(int flightTime, double speed, double power, HomingPolicy homingPolicy) {

    public static final int DEFAULT_FLIGHT_TIME = 10;
    public static final double DEFAULT_SPEED = 1;
    public static final double DEFAULT_POWER = 1;
    public static final String FLIGHT_TIME = "FlightTime";
    public static final String SPEED = "Speed";
    public static final String POWER = "Power";
    public static final String HOMING_POLICY = "HomingPolicy";
    private static final String FLIGHT = "Flight";

    public static FlightProperties deserialize(CompoundTag tag){
        int flightTime = (tag.contains(FLIGHT_TIME)) ? tag.getInt(FLIGHT_TIME) : DEFAULT_FLIGHT_TIME;
        double speed = (tag.contains(SPEED)) ? tag.getDouble(SPEED) : DEFAULT_SPEED;
        double power = (tag.contains(POWER)) ? tag.getDouble(POWER) : DEFAULT_POWER;
        HomingPolicy homingPolicy = (tag.contains(HOMING_POLICY)) ? HomingPolicy.table.get(tag.getShort(HOMING_POLICY)) : HomingPolicy.NONE;
        return new FlightProperties(flightTime, speed, power, homingPolicy);
    }

    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putInt(FLIGHT_TIME, flightTime);
        tag.putDouble(SPEED, speed);
        tag.putDouble(POWER, power);
        tag.putShort(HOMING_POLICY, homingPolicy.getCode());
        return tag;
    }

    public static FlightProperties createFromVanilla(CompoundTag tag){
        int flightTime = (tag.contains(FLIGHT)) ? Byte.toUnsignedInt(tag.getByte(FLIGHT)) * 10 : DEFAULT_FLIGHT_TIME;
        return new FlightProperties(flightTime, DEFAULT_SPEED, DEFAULT_POWER, HomingPolicy.NONE);
    }

    public static FlightProperties createFromVanilla(ItemStack firework){
        return createFromVanilla(firework.getOrCreateTag());
    }

    public static FlightProperties createDefault(){
        return new FlightProperties(DEFAULT_FLIGHT_TIME, DEFAULT_SPEED, DEFAULT_POWER, HomingPolicy.NONE);
    }

    enum HomingPolicy {
        NONE((short)0), MANUAL_TARGET((short)1), NEAREST((short)2);

        public final static Map<Short, HomingPolicy> table = new HashMap<>();

        private short code;
        HomingPolicy(short code){
            this.code = code;
        }

        public short getCode(){
            return code;
        }

        static {
            table.put((short)0, NONE);
            table.put((short)1, MANUAL_TARGET);
            table.put((short)2, NEAREST);
        }
    }
}
