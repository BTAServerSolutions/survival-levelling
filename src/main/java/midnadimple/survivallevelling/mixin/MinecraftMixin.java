package midnadimple.survivallevelling.mixin;

import midnadimple.survivallevelling.gui.FirstSetupMenu;
import midnadimple.survivallevelling.SurvivalLevelling;
import midnadimple.survivallevelling.mixininterface.ILevelingSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Shadow
	public abstract void displayGuiScreen(GuiScreen guiScreen);
	@Shadow
	public GameSettings gameSettings;

	@Inject(method = "startGame", at = @At("TAIL"))
	private void startGame(CallbackInfo ci) {
		SurvivalLevelling.options = (ILevelingSettings) gameSettings;

		if (!SurvivalLevelling.options.survival_levelling$firstSetupFinished().value) {
			displayGuiScreen(new FirstSetupMenu());
			System.out.println("First time setup for survival leveling");
		}
	}
}
