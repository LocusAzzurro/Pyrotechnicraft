package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticleOption;

public class FlickerStick extends Item{

    public FlickerStick()
    {
        super(new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        int type = item.getOrCreateTag().getInt("Type");

        if (pLevel.isClientSide()){
            for (int i = 0; i < 50; i++) {
                pLevel.addParticle(new FireworkSparkParticleOption(0xffeeee, 0xffffff, true, false),
                        pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        (pLevel.random.nextFloat() - 0.5) * 0.8,
                        (pLevel.random.nextFloat() - 0.5) * 0.8,
                        (pLevel.random.nextFloat() - 0.5) * 0.8);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
