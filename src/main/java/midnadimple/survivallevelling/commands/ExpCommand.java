package midnadimple.survivallevelling.commands;

import midnadimple.survivallevelling.mixininterface.IEntityPlayerMixin;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class ExpCommand extends Command {
	public ExpCommand() {
		super("exp", (String) null);
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		if (args.length < 1 || args.length > 2) {
			sender.sendMessage("Incorrect number of arguments");
			return false;
		}

		if (!handler.playerExists(args[0])) {
			sender.sendMessage("There is no player named " + args[0]);
			return false;
		}

		IEntityPlayerMixin player = (IEntityPlayerMixin) handler.getPlayer(args[0]);

		if (args.length == 1) {
			sender.sendMessage("Player " + args[0] + " has " + player.survival_levelling$getExp() + " exp");
		} else {
			player.survival_levelling$gainExp(Integer.parseInt(args[1]));
			sender.sendMessage("Player " + args[0] + " gained " + Integer.parseInt(args[1]) + ", now has " + player.survival_levelling$getExp() + " exp");
		}

		return true;
	}

	@Override
	public boolean opRequired(String[] args) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("/exp <player>");
		sender.sendMessage("/exp <player> <newExpGained>");
	}
}
