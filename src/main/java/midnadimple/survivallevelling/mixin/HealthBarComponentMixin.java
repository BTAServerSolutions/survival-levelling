package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.SurvivalLevelling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.HealthBarComponent;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static java.lang.Math.ceil;

@Mixin(value = HealthBarComponent.class, remap = false)
public class HealthBarComponentMixin {
	@Unique
	private HealthBarComponent thisAs = (HealthBarComponent)(Object)this;

	@Unique
	private final Random random = new Random();

	// This function is the definition of jank
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {
		if (mc.theWorld.getGameRule(SurvivalLevelling.ALLOW_survival_levelling) && mc.thePlayer.getGamemode() == Gamemode.survival) {
			int x = thisAs.getLayout().getComponentX(mc, thisAs, xSizeScreen);
			int y = thisAs.getLayout().getComponentY(mc, thisAs, ySizeScreen);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glBindTexture(3553, mc.renderEngine.getTexture("/gui/icons.png"));
			GL11.glDisable(3042);
			boolean heartsFlash = mc.thePlayer.heartsFlashTime / 3 % 2 == 1;
			if (mc.thePlayer.heartsFlashTime < 10) {
				heartsFlash = false;
			}

			int health = mc.thePlayer.getHealth();
			int prevHealth = mc.thePlayer.prevHealth;
			this.random.setSeed((long) gui.updateCounter * 312871L);

			// All this shit for one changed line fml
			for (int i = 0; i < (int)ceil(mc.thePlayer.getMaxHealth() / 2.0); ++i) {
				int heartOffset = 0;
				if (heartsFlash) {
					heartOffset = 1;
				}

				int xHeart = x + i * 8;
				int yHeart = y;
				if (health <= 4) {
					yHeart += this.random.nextInt(2);
				}

				gui.drawTexturedModalRect(xHeart, yHeart, 16 + heartOffset * 9, 0, 9, 9);
				if (heartsFlash) {
					if (i * 2 + 1 < prevHealth) {
						gui.drawTexturedModalRect(xHeart, yHeart, 70, 0, 9, 9);
					}

					if (i * 2 + 1 == prevHealth) {
						gui.drawTexturedModalRect(xHeart, yHeart, 79, 0, 9, 9);
					}
				}

				if (i * 2 + 1 < health) {
					gui.drawTexturedModalRect(xHeart, yHeart, 52, 0, 9, 9);
				}

				if (i * 2 + 1 == health) {
					gui.drawTexturedModalRect(xHeart, yHeart, 61, 0, 9, 9);
				}

				if (mc.thePlayer.inventory.getCurrentItem() != null && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) && mc.gameSettings.foodHealthRegenOverlay.value) {
					int healing;
					if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) {
						healing = ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					} else {
						healing = ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					}

					if (i * 2 + 1 >= health) {
						if (i * 2 + 1 == health) {
							gui.drawTexturedModalRect(xHeart, yHeart, 106, 0, 9, 9);
						} else if (i * 2 + 1 < health + healing) {
							gui.drawTexturedModalRect(xHeart, yHeart, 88, 0, 9, 9);
						} else if (i * 2 + 1 == health + healing) {
							gui.drawTexturedModalRect(xHeart, yHeart, 97, 0, 9, 9);
						}
					}
				}
			}
			ci.cancel();
		}
	}
}
