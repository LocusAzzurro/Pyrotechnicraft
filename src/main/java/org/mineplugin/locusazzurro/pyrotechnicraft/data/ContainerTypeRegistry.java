package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkLauncherStandContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkMissileCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkOrbCraftingTableContainer;

public class ContainerTypeRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<MenuType<FireworkOrbCraftingTableContainer>> FIREWORK_ORB_CRAFTING_TABLE = CONTAINERS.register("firework_orb_crafting_table",
            () -> IForgeMenuType.create((windowId, inv, data) -> new FireworkOrbCraftingTableContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<FireworkMissileCraftingTableContainer>> FIREWORK_MISSILE_CRAFTING_TABLE = CONTAINERS.register("firework_missile_crafting_table",
            () -> IForgeMenuType.create((windowId, inv, data) -> new FireworkMissileCraftingTableContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<FireworkLauncherStandContainer>> FIREWORK_LAUNCHER_STAND = CONTAINERS.register("firework_launcher_stand",
            () -> IForgeMenuType.create((windowId, inv, data) -> new FireworkLauncherStandContainer(windowId, data.readBlockPos(), inv, inv.player)));

}
