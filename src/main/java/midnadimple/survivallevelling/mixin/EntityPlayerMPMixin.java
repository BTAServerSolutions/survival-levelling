package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMPMixin;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerMP.class, remap = false)
public class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayerMPMixin {
	@Shadow
	public NetServerHandler playerNetServerHandler;

	public EntityPlayerMPMixin(World world) {
		super(world);
	}

	@Inject(method = "onDeath", at = @At("TAIL"))
	private void onDeath(Entity entity, CallbackInfo ci) {
		PacketSendExp packet = new PacketSendExp(0, 1);
		survival_leveling$serverPacketExp(packet);
	}

	@Override
	public void func_6420_o() {

	}

	@Override
	public void survival_leveling$serverPacketExp(Packet packet) {
		playerNetServerHandler.sendPacket(packet);
	}
}
