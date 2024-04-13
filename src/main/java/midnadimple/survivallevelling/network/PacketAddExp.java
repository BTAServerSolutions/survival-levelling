package midnadimple.survivallevelling.network;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAddExp extends Packet {
	public int exp;

	public PacketAddExp(int exp) {
		this.exp = exp;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		exp = dataInputStream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeInt(exp);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((INetHandler)netHandler).survival_leveling$handleAddExpPacket(this);
	}

	@Override
	public int getPacketSize() {
		return 4;
	}
}
