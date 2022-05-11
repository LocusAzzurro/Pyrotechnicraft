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

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<EntityType<FireworkMissileEntity>> SIMPLE_FIREWORK_MISSILE =
            register("firework_missile", () -> EntityType.Builder
                    .<FireworkMissileEntity>of(FireworkMissileEntity::new, MobCategory.MISC).sized(0.1f, 0.1f).build("firework_missile"));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType<T>> type) {
        return ENTITIES.register(name, type);
    }
}
