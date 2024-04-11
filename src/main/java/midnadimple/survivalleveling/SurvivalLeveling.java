package midnadimple.survivalleveling;

import midnadimple.survivalleveling.mixininterface.ILevelingSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class SurvivalLeveling implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "survivalleveling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static GameRuleBoolean ALLOW_SURVIVAL_LEVELING = GameRules.register(new GameRuleBoolean("allowSurvivalLeveling", true));
	public static ILevelingSettings options;
	private static final HudManager hudManager = new HudManager();

    @Override
    public void onInitialize() {
		hudManager.init();
        LOGGER.info("SurvivalLeveling initialized.");
    }

	@Override
	public void beforeGameStart() {
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
