package ch.bbw.zork;

/*
 * author:  Michael Kolling, Version: 1.0, Date: July 1999
 * refactoring: Rinaldo Lanza, September 2020
 */

import java.util.Arrays;
import java.util.List;

public class CommandWords {

	/** Liste aller gültigen Kommandowörter im Spiel */
	private List<String> validCommands = Arrays.asList("go", "quit", "help", "back", "investigate", "take", "drop", "inv", "map");

	/**
	 * Prüft, ob ein gegebenes Wort ein gültiges Kommando ist.
	 * @param commandWord Das zu prüfende Wort
	 * @return true, wenn das Wort ein gültiges Kommando ist
	 */
	public boolean isCommand(String commandWord) {
		return validCommands.stream()
				.filter( c -> c.equals(commandWord) )
				.count()>0;
	}

	/**
	 * Gibt alle gültigen Kommandowörter als String zurück.
	 * @return Ein String mit allen gültigen Kommandos, durch Leerzeichen getrennt
	 */
	public String showAll() {
		return String.join(" ", validCommands);
	}

}





