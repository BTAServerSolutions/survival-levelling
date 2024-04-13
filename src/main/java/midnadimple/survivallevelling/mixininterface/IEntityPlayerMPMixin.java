package midnadimple.survivallevelling.mixininterface;

import net.minecraft.core.net.packet.Packet;

public interface IEntityPlayerMPMixin {
	void survival_leveling$serverPacketExp(Packet packet);
}
