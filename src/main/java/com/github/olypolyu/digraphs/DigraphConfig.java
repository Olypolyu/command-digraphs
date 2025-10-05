package com.github.olypolyu.digraphs;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.github.olypolyu.digraphs.DigraphMod.LOGGER;
import static com.github.olypolyu.digraphs.DigraphMod.MOD_ID;

public class DigraphConfig {

	private static TomlConfigHandler cfg;

	public static final Set<Character> digraphs = new HashSet<>();


	static void setup() {
		LOGGER.info("Initializing config..");

		Toml props = new Toml("digraph_config.toml");

		digraphs.add('~');
		digraphs.add('#');

		props.addEntry(
			"digraphs",
			"You can use this to modify the digraphs. \nThey should separated by commas, example: \"e, r, #, ~\"",
				serialize(digraphs)
			);

		cfg = new TomlConfigHandler(MOD_ID, props);

		if (cfg.getConfigFile().exists()) {
			cfg.loadConfig();

			digraphs.clear();
			digraphs.addAll(deserialize(cfg.getString("digraphs")));
		}

		else {
			try { cfg.getConfigFile().createNewFile(); }
			catch (IOException e) { throw new RuntimeException(e); }

			cfg.writeConfig();
		}
	}

	private static String serialize(Set<Character> chars) {
		if (chars.isEmpty()) return "";

		StringBuilder result = new StringBuilder();

		Iterator<Character> it = chars.iterator();
		result.append(it.next());

		while (it.hasNext()) {
			result.append(", ");
			result.append(it.next());
		}

		return result.toString();
	}

	private static Set<Character> deserialize(String chars) {
		Set<Character> result = new HashSet<>();

		String[] split = chars.split(",");
		for (String c : split) {
			String trimmed = c.trim();
			if (trimmed.length() > 1) throw new RuntimeException("Invalid syntax for configuration! Please only one character per entry!");
			result.add(trimmed.toCharArray()[0]);
		}

		return result;
	}
}
