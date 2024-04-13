package midnadimple.survivallevelling.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetLoginHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetLoginHandler.class, remap = false)
public class NetLoginHandlerMixin {
	@Inject(method = "doLogin", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/server/entity/player/EntityPlayerMP;func_20057_k()V"
	, shift = At.Shift.AFTER)
	)
	private void doLogin(Packet1Login packet1Login, CallbackInfo ci, @Local EntityPlayerMP entityPlayerMP) {
		IEntityPlayerMixin player = (IEntityPlayerMixin) entityPlayerMP;

		System.out.println("Sending PacketExp with exp: " +  player.survival_levelling$getExp() + ", level: " + player.survival_levelling$getLevel());

		PacketSendExp packet = new PacketSendExp(player.survival_levelling$getExp(), player.survival_levelling$getLevel());
		entityPlayerMP.playerNetServerHandler.sendPacket(packet);
	}
}
