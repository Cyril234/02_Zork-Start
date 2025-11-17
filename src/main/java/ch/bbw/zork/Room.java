package ch.bbw.zork;
/**
 * Author:  Michael Kolling, Version: 1.1, Date: August 2000
 * refactoring: Rinaldo Lanza, September 2020
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Room {
	
	private String description;
	private HashMap<String, Room> exits;
	private boolean winnRoom = false;
	private ArrayList<Item> items = new ArrayList<>();
	private Item itemToEnter;
	private boolean isOpen;
	private ConversationPiece conversationPiece;

	/**
	 * Konstruktor für einen Raum.
	 * @param description Die Beschreibung des Raums
	 */
	public Room(String description) {
		this.description = description;
		this.exits = new HashMap<>();
	}

	/**
	 * Setzt die Ausgänge des Raums in alle vier Himmelsrichtungen.
	 * @param north Der Raum im Norden (null = kein Ausgang)
	 * @param east Der Raum im Osten (null = kein Ausgang)
	 * @param south Der Raum im Süden (null = kein Ausgang)
	 * @param west Der Raum im Westen (null = kein Ausgang)
	 */
	public void setParameter(Room north, Room east, Room south, Room west) {
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
	}

	/**
	 * Setzt die Ausgänge und Items des Raums.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param items Liste der Items in diesem Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, ArrayList<Item> items) {
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.items.clear();
		for (Item item : items){
			this.items.add(item);
		}
	}

	/**
	 * Setzt die Ausgänge und markiert den Raum als Gewinnraum.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param winnRoom true, wenn dies ein Gewinnraum ist
	 */
	public void setParameter(Room north, Room east, Room south, Room west, boolean winnRoom) {
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.winnRoom = winnRoom;
	}

	/**
	 * Setzt die Ausgänge und das benötigte Item zum Betreten.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 */
	public void setParameter(Room north, Room east, Room south, Room west, Item itemToEnter) {
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
	}

	/**
	 * Setzt die Ausgänge, Items und das benötigte Item zum Betreten.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param items Liste der Items in diesem Raum
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 */
	public void setParameter(Room north, Room east, Room south, Room west, ArrayList<Item> items, Item itemToEnter) {
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.items.clear();
		for (Item item : items){
			this.items.add(item);
		}
	}

	/**
	 * Setzt die Ausgänge, markiert als Gewinnraum und setzt das benötigte Item.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param winnRoom true, wenn dies ein Gewinnraum ist
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 */
	public void setParameter(Room north, Room east, Room south, Room west, boolean winnRoom, Item itemToEnter) {
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.winnRoom = winnRoom;
	}

	/**
	 * Setzt die Ausgänge und das Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
	}

	/**
	 * Setzt die Ausgänge, Items und das Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param items Liste der Items in diesem Raum
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, ArrayList<Item> items, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.items.clear();
		for (Item item : items){
			this.items.add(item);
		}
	}

	/**
	 * Setzt die Ausgänge, markiert als Gewinnraum und setzt das Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param winnRoom true, wenn dies ein Gewinnraum ist
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, boolean winnRoom, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.winnRoom = winnRoom;
	}

	/**
	 * Setzt die Ausgänge, das benötigte Item und das Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, Item itemToEnter, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
	}

	/**
	 * Setzt die Ausgänge, Items, benötigtes Item und Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param items Liste der Items in diesem Raum
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, ArrayList<Item> items, Item itemToEnter, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.items.clear();
		for (Item item : items){
			this.items.add(item);
		}
	}

	/**
	 * Setzt alle Parameter: Ausgänge, Gewinnraum, benötigtes Item und Gesprächsstück.
	 * @param north Der Raum im Norden
	 * @param east Der Raum im Osten
	 * @param south Der Raum im Süden
	 * @param west Der Raum im Westen
	 * @param winnRoom true, wenn dies ein Gewinnraum ist
	 * @param itemToEnter Das Item, das benötigt wird, um diesen Raum zu betreten
	 * @param conversationPiece Das Gesprächsstück für diesen Raum
	 */
	public void setParameter(Room north, Room east, Room south, Room west, boolean winnRoom, Item itemToEnter, ConversationPiece conversationPiece) {
		this.conversationPiece = conversationPiece;
		this.itemToEnter = itemToEnter;
		exits.put("north", north);
		exits.put("east", east);
		exits.put("south", south);
		exits.put("west", west);
		this.winnRoom = winnRoom;
	}

	public String longDescription() {
        return "You are in " + description + ".\n" + exitString();
	}

	private String exitString() {
		return "Exits:" + String.join(" ", exits.keySet());
	}

	public Room nextRoom(String direction) {
		return exits.get(direction);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public String getDescription() {
		return description;
	}

	public boolean isWinnRoom() {
		return winnRoom;
	}

	public Item getItemToEnter() {
		return itemToEnter;
	}

	public void setItemToEnter(Item itemToEnter) {
		this.itemToEnter = itemToEnter;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public ConversationPiece getConversationPiece() {
		return conversationPiece;
	}
}