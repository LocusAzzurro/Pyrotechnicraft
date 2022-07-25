package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

@Mod.EventBusSubscriber(modid = Pyrotechnicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    final static String MOD_ID = Pyrotechnicraft.MOD_ID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fh = event.getExistingFileHelper();
        if (event.includeServer()){
            BlockTagsProvider blockTags = new ModBlockTagsProvider(generator, fh);
            generator.addProvider(blockTags);
            generator.addProvider(new ModItemTagsProvider(generator, blockTags, fh));
            generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new ModLootTableProvider(generator));
        }
        if (event.includeClient()){
            generator.addProvider(new ModBlockModelProvider(generator, fh));
            generator.addProvider(new ModItemModelProvider(generator, fh));
        }
    }
}
