package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.*;

import java.util.function.Supplier;

public class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<EntityType<FireworkMissileEntity>> FIREWORK_MISSILE =
            register("firework_missile", () -> EntityType.Builder
                    .<FireworkMissileEntity>of(FireworkMissileEntity::new, MobCategory.MISC).sized(0.1f, 0.1f).build("firework_missile"));

    public static final RegistryObject<EntityType<FireworkStarter>> FIREWORK_STARTER =
            register("firework_starter", () -> EntityType.Builder
                    .<FireworkStarter>of(FireworkStarter::new, MobCategory.MISC).sized(0.1f, 0.1f).build("firework_starter"));

    public static final RegistryObject<EntityType<FirecrackerEntity>> FIRECRACKER =
            register("firecracker", () -> EntityType.Builder
                    .<FirecrackerEntity>of(FirecrackerEntity::new, MobCategory.MISC).sized(0.1f, 0.1f).build("firecracker"));

    public static final RegistryObject<EntityType<ThrownFireworkOrbEntity>> FIREWORK_ORB =
            register("firework_orb", () -> EntityType.Builder
                    .<ThrownFireworkOrbEntity>of(ThrownFireworkOrbEntity::new, MobCategory.MISC).sized(0.1f, 0.1f).build("firework_orb"));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType<T>> type) {
        return ENTITIES.register(name, type);
    }
}
