package com.FriendWorldChecker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Friend World Checker"
)
public class FriendWorldCheckerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private FriendWorldCheckerConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("FriendWorldChecker started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("FriendWorldChecker stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
	}

	@Subscribe
	public void onChatMessage(ChatMessage message)
	{
		if (message.getType() == ChatMessageType.LOGINLOGOUTNOTIFICATION && config.showSameWorld())
		{
			MessageNode messageNode = message.getMessageNode();
			// get the player name out of the notification
			String name = messageNode.getValue()
					.substring(0, messageNode.getValue().indexOf(" "));
			ChatPlayer player = findFriend(name);
			String friendMessage = messageNode.getValue();

			if (player != null && player.getWorld() > 0)
			{
				if (player.getWorld() == client.getWorld()) {
					friendMessage = friendMessage.substring(0,friendMessage.length()-1) + " on your world.";
					messageNode
							.setValue(friendMessage);
				} else{
					friendMessage = friendMessage.substring(0,friendMessage.length()-1) + " on a different world.";
					messageNode
							.setValue(friendMessage);
				}
			}
		}
	}

	private ChatPlayer findFriend(String name)
	{
		NameableContainer<Friend> friendContainer = client.getFriendContainer();
		if (friendContainer != null)
		{
			String cleanName = Text.removeTags(name);
			return friendContainer.findByName(cleanName);
		}

		return null;
	}

	@Provides
	FriendWorldCheckerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendWorldCheckerConfig.class);
	}
}
