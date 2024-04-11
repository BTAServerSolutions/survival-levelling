package midnadimple.survivalleveling.mixin;

import midnadimple.survivalleveling.mixininterface.ILevelingSettings;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements ILevelingSettings
{
	@Unique
	private final GameSettings thisAs = (GameSettings)((Object)this);
	@Unique
	public BooleanOption firstSetupFinished =
		new BooleanOption(thisAs, "survivalleveling.options.firstSetupFinished", false);

	@Override
	public BooleanOption survival_leveling$firstSetupFinished()
	{
		return firstSetupFinished;
	}
}
