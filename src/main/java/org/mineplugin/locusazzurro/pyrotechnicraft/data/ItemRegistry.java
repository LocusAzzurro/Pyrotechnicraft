package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.*;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", BasicMaterialItem::new);
    public static final RegistryObject<Item> SALTPETER = ITEMS.register("saltpeter", BasicMaterialItem::new);
    public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_MIXTURE = ITEMS.register("firework_mixture", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_ORB_CORE = ITEMS.register("firework_orb_core", BasicMaterialItem::new);
    public static final RegistryObject<Item> SPHERE_EXPLOSION_PATTERN = ITEMS.register("sphere_explosion_pattern",
            () -> new ExplosionShapePattern(ExplosionShape.SPHERE));
    public static final RegistryObject<Item> RING_EXPLOSION_PATTERN = ITEMS.register("ring_explosion_pattern",
            () -> new ExplosionShapePattern(ExplosionShape.RING));
    public static final RegistryObject<Item> BURST_EXPLOSION_PATTERN = ITEMS.register("burst_explosion_pattern",
            () -> new ExplosionShapePattern(ExplosionShape.BURST));
    public static final RegistryObject<Item> PLANE_EXPLOSION_PATTERN = ITEMS.register("2d_explosion_pattern",
            () -> new ExplosionShapePattern(ExplosionShape.PLANE));
    public static final RegistryObject<Item> MATRIX_EXPLOSION_PATTERN = ITEMS.register("3d_explosion_pattern",
            () -> new ExplosionShapePattern(ExplosionShape.MATRIX));
    public static final RegistryObject<Item> FIREWORK_ORB = ITEMS.register("firework_orb", FireworkOrb::new);
    public static final RegistryObject<Item> INSTANT_FUSE = ITEMS.register("instant_fuse",
            () -> new FireworkFuse(FireworkFuse.FuseType.INSTANT));
    public static final RegistryObject<Item> REGULAR_FUSE = ITEMS.register("regular_fuse",
            () -> new FireworkFuse(FireworkFuse.FuseType.REGULAR));
    public static final RegistryObject<Item> EXTENDED_FUSE = ITEMS.register("extended_fuse",
            () -> new FireworkFuse(FireworkFuse.FuseType.EXTENDED));
    public static final RegistryObject<Item> CUSTOM_FUSE = ITEMS.register("custom_fuse",
            () -> new FireworkFuse(FireworkFuse.FuseType.CUSTOM));
    public static final RegistryObject<Item> FIREWORK_WRAPPING_PAPER = ITEMS.register("firework_wrapping_paper", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_HOMING_MODULE = ITEMS.register("firework_homing_module", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_MISSILE = ITEMS.register("firework_missile", FireworkMissile::new);
    public static final RegistryObject<Item> COMPOSITE_FIREWORK_ORB = ITEMS.register("composite_firework_orb", CompositeFireworkOrb::new);
    public static final RegistryObject<Item> FIREWORK_LAUNCHER = ITEMS.register("firework_launcher", FireworkLauncher::new);
    public static final RegistryObject<Item> HOMING_ARRAY = ITEMS.register("homing_array", HomingArray::new);
    public static final RegistryObject<Item> HOMING_ARRAY_SCRIPT = ITEMS.register("homing_array_script", HomingArrayScript::new);
    public static final RegistryObject<Item> TACTICAL_SCRIPT = ITEMS.register("tactical_script", TacticalScript::new);
    public static final RegistryObject<Item> FIREWORK_ORB_SHOOTER = ITEMS.register("firework_orb_shooter", FireworkOrbShooter::new);
    public static final RegistryObject<Item> FLICKER_STICK = ITEMS.register("flicker_stick", FlickerStick::new);
    public static final RegistryObject<Item> FIRECRACKER = ITEMS.register("firecracker", Firecracker::new);
    public static final RegistryObject<Item> FIREWORK_REPLICATION_KIT = ITEMS.register("firework_replication_kit", FireworkReplicationKit::new);
    public static final RegistryObject<Item> CREATIVE_FIREWORK_TEST_KIT = ITEMS.register("creative_firework_test_kit", CreativeFireworkTestKit::new);

    public static final RegistryObject<Item> FIREWORK_ORB_CRAFTING_TABLE = fromBlock(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE);
    public static final RegistryObject<Item> FIREWORK_MISSILE_CRAFTING_TABLE = fromBlock(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE);
    public static final RegistryObject<Item> FIREWORK_LAUNCHER_STAND = fromBlock(BlockRegistry.FIREWORK_LAUNCHER_STAND);
    public static final RegistryObject<Item> COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND = fromBlock(BlockRegistry.COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND);

    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB)));
    }

}
