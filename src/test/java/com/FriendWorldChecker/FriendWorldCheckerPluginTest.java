package com.FriendWorldChecker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class FriendWorldCheckerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FriendWorldCheckerPlugin.class);
		RuneLite.main(args);
	}
}