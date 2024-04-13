package midnadimple.survivallevelling.network;

import turniplabs.halplibe.helper.NetworkHelper;

public class NetManager {
	public void init() {
		NetworkHelper.register(PacketSendExp.class, true, true);
		NetworkHelper.register(PacketAddExp.class, false, true);
	}
}
