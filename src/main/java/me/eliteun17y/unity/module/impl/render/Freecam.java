    package me.eliteun17y.unity.module.impl.render;

    import com.mojang.authlib.GameProfile;
    import me.eliteun17y.unity.event.Direction;
    import me.eliteun17y.unity.event.Subscribe;
    import me.eliteun17y.unity.event.impl.*;
    import me.eliteun17y.unity.module.Category;
    import me.eliteun17y.unity.module.Module;
    import me.eliteun17y.unity.util.player.PlayerUtil;
    import me.eliteun17y.unity.util.setting.impl.BooleanValue;
    import me.eliteun17y.unity.util.setting.impl.NumberValue;
    import net.minecraft.client.entity.EntityOtherPlayerMP;
    import net.minecraft.network.play.client.*;
    import net.minecraft.util.math.BlockPos;
    import org.lwjgl.input.Keyboard;

    public class Freecam extends Module {
        public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
        public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);
        public BooleanValue cancelPackets = new BooleanValue(this, "Cancel Packets", true);

        public EntityOtherPlayerMP fakeEntity;
        public BlockPos oldPosition;

        public Freecam() {
            super("Freecam", "Allows you to fly through blocks (client sided).", Category.RENDER, Keyboard.KEY_NONE);
        }

        @Override
        public void onEnable() {
            super.onEnable();
            fakeEntity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
            fakeEntity.copyLocationAndAnglesFrom(mc.player);
            fakeEntity.prevRotationYaw = mc.player.rotationYaw;
            fakeEntity.rotationYawHead = mc.player.rotationYawHead;
            fakeEntity.inventory.copyInventory(mc.player.inventory);
            mc.world.addEntityToWorld(-100, fakeEntity);
            oldPosition = mc.player.getPosition();
        }

        @Override
        public void onDisable() {
            super.onDisable();
            mc.world.removeEntity(fakeEntity);
            mc.player.setPosition(oldPosition.getX(), oldPosition.getY(), oldPosition.getZ());
        }

        @Subscribe
        public void onMove(EventMove event) {
            mc.player.noClip = true;
        }

        @Subscribe
        public void onBlockPush(EventBlockPush event) {
            event.setCancelled(true);
        }

        @Subscribe
        public void onUpdate(EventUpdate event) {
            mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
            PlayerUtil.strafe(speed.getDouble());
        }

        @Subscribe
        public void onPacket(EventPacket event) {
            if(event.getDirection() == Direction.OUTGOING) {
                if(!cancelPackets.getObject()) return;

                if ((event.getPacket() instanceof CPacketPlayer)
                        || (event.getPacket() instanceof CPacketVehicleMove)
                        || (event.getPacket() instanceof CPacketChatMessage))
                {
                    event.setCancelled(true);
                }
            }
        }
    }
