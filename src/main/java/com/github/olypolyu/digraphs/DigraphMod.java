package com.github.olypolyu.digraphs;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DigraphMod implements ModInitializer{
    public static final String MOD_ID = "digraphs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
    public void onInitialize() {
		LOGGER.info("Digraph mod initialized.");
		DigraphConfig.setup();
	}
}
