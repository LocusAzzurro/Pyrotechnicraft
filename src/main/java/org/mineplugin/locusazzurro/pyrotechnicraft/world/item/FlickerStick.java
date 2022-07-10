package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;
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
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
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
        ItemStack itemOffhand = pPlayer.getOffhandItem();
        int type = item.getOrCreateTag().getInt("Type");


        if (pLevel.isClientSide()){

            if (itemOffhand.is(ItemRegistry.FIREWORK_ORB.get())){
                CompoundTag tag = itemOffhand.getOrCreateTag();
                float size = tag.contains("Force") ? tag.getFloat("Force") : 0.5f;
                int sparks = tag.contains("Sparks") ? tag.getInt("Sparks") : 50;
                boolean trail = tag.contains("Trail") && tag.getBoolean("Trail");
                boolean sparkle = tag.contains("Sparkle") && tag.getBoolean("Sparkle");
                int[] colors = tag.contains("Colors") ? tag.getIntArray("Colors") : new int[]{0xffffff};
                boolean hasFade = tag.contains("FadeColors");
                int[] fadeColors = colors;
                if (hasFade) fadeColors = tag.getIntArray("FadeColors");

                SphereExplosion exp = new SphereExplosion(size, sparks);
                SparkPointsVisitor visitor = new SparkPointsVisitor(pLevel.random);
                List<Vec3> points = exp.accept(visitor);
                int[] finalFadeColors = fadeColors;
                points.forEach((p) ->{
                    int color = colors[pLevel.random.nextInt(colors.length)];
                    int fadeColor = hasFade ? finalFadeColors[pLevel.random.nextInt(finalFadeColors.length)] : color;
                    pLevel.addParticle(new FireworkSparkParticleOption(color, fadeColor, trail, sparkle),
                            pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                            p.x(), p.y(), p.z());
                });
            }

            /*
            for (int i = -49; i < 50; i++){
                for (int j = -49; j < 50; j++){
                    Vec3 point = new Vec3(0.01f * i, 0.01f * j, 0).xRot(Mth.PI / 4);
                    pLevel.addParticle(new FireworkSparkParticleOption(0xffeeee, 0xffffff, false, false),
                            pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                            point.x(), point.y(), point.z());
                }
            }
            */

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

        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
