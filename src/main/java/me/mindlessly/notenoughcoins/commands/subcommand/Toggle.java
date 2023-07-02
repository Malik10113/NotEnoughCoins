package me.mindlessly.notenoughcoins.commands.subcommand;

import com.google.gson.JsonObject;

import me.mindlessly.notenoughcoins.configuration.ConfigHandler;
import me.mindlessly.notenoughcoins.utils.Utils;
import me.mindlessly.notenoughcoins.websocket.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Toggle implements Subcommand {
	public Toggle() {

	}

	public static void updateConfig() {

	}

	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String getCommandUsage() {
		return "";
	}

	@Override
	public String getCommandDescription() {
		return "Toggles the flipper on or off";
	}

	@Override
	public boolean processCommand(ICommandSender sender, String[] args) {
		JsonObject config = ConfigHandler.getConfig();
		if (config.get("flipper").getAsBoolean()) {
			ConfigHandler.write("flipper", Utils.gson.toJsonTree(false));
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Flipper disabled."));
		} else {
			ConfigHandler.write("flipper", Utils.gson.toJsonTree(true));
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Flipper enabled."));
		}
		Client.start();
		return true;
	}
}