package com.FriendWorldChecker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("FriendWorldChecker")
public interface FriendWorldCheckerConfig extends Config
{
	@ConfigItem(
		keyName = "showSameWorld",
		name = "Show Login to Same World",
		description = "Shows a different login notification if a friend logs in to the same world you are in."
	)
	default boolean showSameWorld()
	{
		return false;
	}
}
