package midnadimple.survivallevelling.commands;

import turniplabs.halplibe.helper.CommandHelper;

public class CommandManager {
	public void init() {
		CommandHelper.Core.createCommand(new LevelCommand());
		CommandHelper.Core.createCommand(new ExpCommand());
	}
}
