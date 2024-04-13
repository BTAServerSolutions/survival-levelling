package midnadimple.survivallevelling.commands;

import turniplabs.halplibe.helper.CommandHelper;

public class CommandManager {
	public void init() {
		LevelCommand levelCommand = new LevelCommand();
		ExpCommand expCommand = new ExpCommand();
		CommandHelper.Core.createCommand(levelCommand);
		CommandHelper.Core.createCommand(expCommand);
	}
}
