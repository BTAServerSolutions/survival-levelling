package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import midnadimple.survivallevelling.network.PacketSendExp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin extends EntityPlayer {
	@Shadow
	protected Minecraft mc;

	public EntityPlayerSPMixin(World world) {
		super(world);
	}

	@Inject(method = "onLivingUpdate()V", at = @At("TAIL"))
	private void onLivingUpdate(CallbackInfo ci) {
		IEntityPlayerMixin player = (IEntityPlayerMixin) this;

		if (player.survival_levelling$getPrevExp() != player.survival_levelling$getExp() ||
			player.survival_levelling$getPrevLevel() != player.survival_levelling$getLevel()) {
			if (mc.getSendQueue() != null) {
				mc.getSendQueue().addToSendQueue(new PacketSendExp(player.survival_levelling$getExp(), player.survival_levelling$getLevel()));
			}
		}
	}

	@Override
	public void func_6420_o() {

	}
}
