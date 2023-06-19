package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

import java.util.List;

public class FireworkFuse extends BasicMaterialItem{

    public static final Component TEXT_FUSE_TITLE = Component.translatable("item." + Pyrotechnicraft.MOD_ID + ".firework_fuse.delay");

    private final FuseType type;

    public FireworkFuse(FuseType type){
        super();
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getItem() instanceof FireworkFuse fuseItem){
            FuseType fuseType = fuseItem.getType();
            if (fuseType == FuseType.CUSTOM){
                if (pStack.getOrCreateTag().contains("FuseDelay"))
                    pTooltipComponents.add(TEXT_FUSE_TITLE.copy()
                            .append(String.valueOf(pStack.getOrCreateTag().getInt("FuseDelay"))).withStyle(ChatFormatting.GRAY));
            }
            else {
                pTooltipComponents.add(TEXT_FUSE_TITLE.copy().append(String.valueOf(fuseType.delay)).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public FuseType getType() {
        return type;
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
