package com.outercloud.reworked_sleep_to_night;

import com.google.gson.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;

public class Mod implements ModInitializer {
	public enum TimeAdvanceMode {
		Absolute,
		Relative,
	}

	public static final Logger LOGGER = LoggerFactory.getLogger("reworked_sleep_to_night");
	public static TimeAdvanceMode timeAdvanceMode = TimeAdvanceMode.Absolute;
	public static long sunrise = 23000L;
	public static long sunset = 13000L;
	public static long advance = 12000L;

	@Override
	public void onInitialize() {
		FileReader fileReader = null;

		String path = FabricLoader.getInstance().getConfigDir().resolve("reworked_sleep_to_night.json").toString();

		try {
			fileReader = new FileReader(path);
		} catch (Exception e) {
			try {
				FileWriter fileWriter = new FileWriter(path);
				fileWriter.write("{\n\"mode\": \"absolute\", // This determines how the time will be handled. Set to absolute for specific times or relative to have the day advance by a certain amount each time you sleep.\n\"sunrise\": 23000,\n\"sunset\": 13000,\n\"advance\": 12000 // This is the time advanced when the mode is set to relative\n}");
				fileWriter.close();

				fileReader = new FileReader(path);
			} catch (Exception e2) {}
		}

		if(fileReader == null) {
			LOGGER.error("Failed to read config file!");

			return;
		}

		JsonElement json = null;
		try {
			json = JsonParser.parseReader(fileReader);

			fileReader.close();
		} catch (Exception e) {}

		if(json == null) {
			LOGGER.error("Failed to parse config file!");

			return;
		}

		try {
			if(json.getAsJsonObject().get("mode").getAsString().equals("relative")) {
				timeAdvanceMode = TimeAdvanceMode.Relative;
			}
		} catch (Exception e) {}

		try {
			sunrise = json.getAsJsonObject().get("sunrise").getAsLong();
		} catch (Exception e) {}

		try {
			sunset = json.getAsJsonObject().get("sunset").getAsLong();
		} catch (Exception e) {}

		try {
			advance = json.getAsJsonObject().get("advance").getAsLong();
		} catch (Exception e) {}
	}
}