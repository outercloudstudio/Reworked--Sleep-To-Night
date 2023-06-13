package com.outercloud.reworked;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reworked implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("reworked");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}