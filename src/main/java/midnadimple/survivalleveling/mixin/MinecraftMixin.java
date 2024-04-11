package midnadimple.survivalleveling.mixin;

import midnadimple.survivalleveling.FirstSetupMenu;
import midnadimple.survivalleveling.SurvivalLeveling;
import midnadimple.survivalleveling.mixininterface.ILevelingSettings;
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
		SurvivalLeveling.options = (ILevelingSettings) gameSettings;

		if (!SurvivalLeveling.options.survival_leveling$firstSetupFinished().value) {
			displayGuiScreen(new FirstSetupMenu());
			System.out.println("First time setup for survival leveling");
		}
	}
}
