package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public record FlightProperties(int flightTime, double speed, double power, boolean homing) {

    public static final int DEFAULT_FLIGHT_TIME = 10;
    public static final double DEFAULT_SPEED = 1;
    public static final double DEFAULT_POWER = 1;
    public static final String FLIGHT_TIME = "FlightTime";
    public static final String SPEED = "Speed";
    public static final String POWER = "Power";
    public static final String HOMING = "Homing";
    private static final String FLIGHT = "Flight";

    public static FlightProperties deserialize(CompoundTag tag){
        int flightTime = (tag.contains(FLIGHT_TIME)) ? tag.getInt(FLIGHT_TIME) : DEFAULT_FLIGHT_TIME;
        double speed = (tag.contains(SPEED)) ? tag.getDouble(SPEED) : DEFAULT_SPEED;
        double power = (tag.contains(POWER)) ? tag.getDouble(POWER) : DEFAULT_POWER;
        boolean homing = tag.contains(HOMING) && tag.getBoolean(HOMING);
        return new FlightProperties(flightTime, speed, power, homing);
    }

    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putInt(FLIGHT_TIME, flightTime);
        tag.putDouble(SPEED, speed);
        tag.putDouble(POWER, power);
        tag.putBoolean(HOMING, homing);
        return tag;
    }

    public static FlightProperties createFromVanilla(CompoundTag tag){
        int flightTime = (tag.contains(FLIGHT)) ? Byte.toUnsignedInt(tag.getByte(FLIGHT)) * 10 : DEFAULT_FLIGHT_TIME;
        return new FlightProperties(flightTime, DEFAULT_SPEED, DEFAULT_POWER, false);
    }

    public static FlightProperties createFromVanilla(ItemStack firework){
        return createFromVanilla(firework.getOrCreateTag());
    }

    public static FlightProperties createDefault(){
        return new FlightProperties(DEFAULT_FLIGHT_TIME, DEFAULT_SPEED, DEFAULT_POWER, false);
    }

}
