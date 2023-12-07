package me.lewboski.GeneralHelper;

import jline.internal.Nullable;
import me.lewboski.SSMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityHelper {
    public static EntitySelection selectedEntity;
    public enum EntitySelection {
        GOLEM
    }
    public static double lastAttackTime;
    public static @org.jetbrains.annotations.Nullable Entity searchForEntity(@NotNull EntityPlayer player, @NotNull EntitySelection selectedEntity) {
        double distance = 64.0D;
        AxisAlignedBB boundingBox = player.getEntityBoundingBox().expand(distance, 128.0D, distance);
        List<Entity> entity;

        switch (selectedEntity) {
            case GOLEM:
                entity = player.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, boundingBox);
                break;
            default:
                return null;
        }

        if (entity.isEmpty()) return null;
        return entity.get(0);
    }

    public static void aimAtEntity(@NotNull EntityPlayer player, @NotNull Entity entity) {
        double dX = entity.posX - player.posX;
        double dY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (player.posY + player.getEyeHeight());
        double dZ = entity.posZ - player.posZ;

        double horizontalDistance = Math.sqrt(dX * dX + dZ * dZ);

        float yaw = (float) (Math.atan2(dZ, dX) * 180.0 / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(dY, horizontalDistance) * 180.0 / Math.PI);

        player.rotationYaw = yaw;
        player.rotationPitch = pitch;
    }

    public static void walkToEntity(@NotNull EntityPlayer player) {
        @Nullable Entity entity = searchForEntity(player, selectedEntity);

        if (entity != null) {
            double distanceAway = player.getDistanceToEntity(entity);

            if (distanceAway > 2.99) {
                aimAtEntity(player, entity);
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
            } else {
                SSMod.currentState = SSMod.PlayerState.ATTACKING_ENTITY;
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
                player.addChatMessage(new ChatComponentText("I am next to the golem baby bro"));
            }
        }
    }

    public static void attackEntity(@NotNull EntityPlayer player) {
        long currentTime = System.currentTimeMillis();
        Entity entity = searchForEntity(player, selectedEntity);

        if (entity != null && (currentTime - lastAttackTime > 100)) {  // 500ms delay between attacks
            player.inventory.currentItem = 0;
            aimAtEntity(player, entity);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), true);
            KeyBinding keyBindAttack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
            if (!keyBindAttack.isKeyDown()) {
                KeyBinding.onTick(keyBindAttack.getKeyCode());
                lastAttackTime = currentTime;
            }
        } else if (entity == null) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
            player.addChatMessage(new ChatComponentText("I killed a golem"));
            SSMod.currentState = SSMod.PlayerState.IDLE;
        }
    }
}