package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import midnadimple.survivallevelling.network.INetHandler;
import midnadimple.survivallevelling.network.PacketAddExp;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.handler.NetHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetClientHandler.class, remap = false)
public class NetClientHandlerMixin extends NetHandler implements INetHandler {
	@Shadow
	@Final
	private Minecraft mc;

	@Override
	public void survival_leveling$handleSendExpPacket(PacketSendExp packet) {
		IEntityPlayerMixin player = (IEntityPlayerMixin) this.mc.thePlayer;
		player.survival_levelling$setExp(packet.exp);
		player.survival_levelling$setLevel(packet.level);

		System.out.println("Exp: " + packet.exp + ", Level: " + packet.level);
	}

	@Override
	public void survival_leveling$handleAddExpPacket(PacketAddExp packet) {
		IEntityPlayerMixin playerMixin = (IEntityPlayerMixin) this.mc.thePlayer;
		playerMixin.survival_levelling$gainExp(packet.exp);
	}

	@Override
	public boolean isServerHandler() {
		return false;
	}
}
