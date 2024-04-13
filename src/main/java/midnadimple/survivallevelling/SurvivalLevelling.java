package midnadimple.survivallevelling;

import midnadimple.survivallevelling.commands.CommandManager;
import midnadimple.survivallevelling.gui.HudManager;
import midnadimple.survivallevelling.mixininterface.ILevelingSettings;
import midnadimple.survivallevelling.network.NetManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class SurvivalLevelling implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "survivallevelling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static GameRuleBoolean ALLOW_survival_levelling = GameRules.register(new GameRuleBoolean("allowSurvivalLevelling", true));
	public static ILevelingSettings options;
	private static final HudManager hudManager = new HudManager();
	private static final NetManager netManager = new NetManager();
	private static final CommandManager commandManager = new CommandManager();

    @Override
    public void onInitialize() {
		hudManager.init();
		netManager.init();
		commandManager.init();
        LOGGER.info("Survival Levelling initialized.");
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

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {

	}
}
