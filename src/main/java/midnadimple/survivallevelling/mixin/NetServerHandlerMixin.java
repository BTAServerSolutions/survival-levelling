package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import midnadimple.survivallevelling.network.INetHandler;
import midnadimple.survivallevelling.network.PacketAddExp;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin extends NetHandler implements INetHandler {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Override
	public void survival_leveling$handleSendExpPacket(PacketSendExp packet) {
		((IEntityPlayerMixin)playerEntity).survival_levelling$setExp(packet.exp);
		((IEntityPlayerMixin)playerEntity).survival_levelling$setLevel(packet.level);
	}

	@Override
	public void survival_leveling$handleAddExpPacket(PacketAddExp packet) {

	}

	@Override
	public boolean isServerHandler() {
		return true;
	}
}
