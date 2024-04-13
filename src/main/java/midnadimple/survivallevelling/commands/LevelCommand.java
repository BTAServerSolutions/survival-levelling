package midnadimple.survivallevelling.commands;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class LevelCommand extends Command {

	public LevelCommand() {
		super("level");
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		if (args.length < 1 || args.length > 2) {
			return false;
		}

		if (!handler.playerExists(args[0])) {
			throw new CommandError("There is no player named " + args[0]);
		}

		IEntityPlayerMixin player = (IEntityPlayerMixin) handler.getPlayer(args[0]);

		if (args.length == 1) {
			sender.sendMessage("Player " + args[0] + " is level " + player.survival_levelling$getLevel());
		} else {
			player.survival_levelling$setLevel(Integer.parseInt(args[1]));
			sender.sendMessage("Player " + args[0] + " is now level " + player.survival_levelling$getLevel());
		}

		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("/level <player>");
		sender.sendMessage("/level <player> <newLevel>");
	}
}
