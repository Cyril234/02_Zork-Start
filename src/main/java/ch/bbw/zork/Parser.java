package ch.bbw.zork;
/*
 * author:  Michael Kolling, Version: 1.0, Date: July 1999
 * refactoring: Rinaldo Lanza, September 2020
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {

	private CommandWords validCommandWords;
	private final BufferedReader reader;

	/**
	 * Konstruktor für den Parser.
	 * @param inputStream Der InputStream, aus dem gelesen wird
	 */
	public Parser(InputStream inputStream) {
		this.reader = new BufferedReader(new InputStreamReader(inputStream));
		this.validCommandWords = new CommandWords();
	}

	/**
	 * Liest ein Kommando vom Benutzer ein.
	 * @param inConversation true, wenn sich der Spieler in einem Gespräch befindet (dann sind auch nicht-gültige Kommandos erlaubt)
	 * @return Das geparste Kommando
	 */
	public Command getCommand(Boolean inConversation) {
		System.out.print("> ");

		try {
			String[] tokens = reader.readLine().split(" ");

			switch(tokens.length) {
				case 2:
					if (validCommandWords.isCommand(tokens[0])) {
						return new Command(tokens[0], tokens[1]);
					} else {
						return new Command(null);
					}
				case 1:
					if (inConversation|validCommandWords.isCommand(tokens[0])) {
						return new Command(tokens[0]);
					} else {
						// TODO: refactor this and that ???
						return new Command(null);
					}
				default:
					System.out.println("Input is to long");
					return new Command(null);
					// TODO: handle this error with an exception and non ???
			}
		} catch (java.io.IOException exc) {
			System.out.println("There was an error during reading: " + exc.getMessage());
		}
		// TODO: handle error ???
		return new Command(null);
	}

	/**
	 * Gibt alle gültigen Kommandos als String zurück.
	 * @return String mit allen gültigen Kommandos
	 */
	public String showCommands() {
		return validCommandWords.showAll();
	}
}
