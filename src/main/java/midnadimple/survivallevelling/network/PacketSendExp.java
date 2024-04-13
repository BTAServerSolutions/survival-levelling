package midnadimple.survivallevelling.network;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketSendExp extends Packet {
	public int level;
	public int exp;

	public PacketSendExp(int exp, int level) {
		this.exp = exp;
		this.level = level;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		exp = dataInputStream.readInt();
		level = dataInputStream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeInt(exp);
		dataOutputStream.writeInt(level);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((INetHandler)netHandler).survival_leveling$handleSendExpPacket(this);
	}

	@Override
	public int getPacketSize() {
		return 8;
	}
}
