package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticleOption;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.SparkPointsVisitor;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.SphereExplosion;

import java.util.List;

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

            for (int i = -49; i < 50; i++){
                for (int j = -49; j < 50; j++){
                    Vec3 point = new Vec3(0.01f * i, 0.01f * j, 0).xRot(Mth.PI / 4);
                    pLevel.addParticle(new FireworkSparkParticleOption(0xffeeee, 0xffffff, false, false),
                            pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                            point.x(), point.y(), point.z());
                }
            }

            /*
            SphereExplosion exp = new SphereExplosion(0.1f, 100);
            SparkPointsVisitor visitor = new SparkPointsVisitor(pLevel.random);
            List<Vec3> points = exp.accept(visitor);
            points.forEach((p) ->{
                pLevel.addParticle(new FireworkSparkParticleOption(0xffeeee, 0xffffff, false, false),
                        pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        p.x(), p.y(), p.z());
            });

             */

            /*
            for (int i = 0; i < 50; i++) {
                pLevel.addParticle(new FireworkSparkParticleOption(0xffeeee, 0xffffff, true, false),
                        pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        (pLevel.random.nextFloat() - 0.5) * 0.8,
                        (pLevel.random.nextFloat() - 0.5) * 0.8,
                        (pLevel.random.nextFloat() - 0.5) * 0.8);
            }
            */
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
