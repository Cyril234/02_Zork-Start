package ch.bbw.zork;

/**
 * Klasse zur Repräsentation eines Items im Spiel.
 * Items können vom Spieler aufgenommen, getragen und verwendet werden.
 */
public class Item {
    private final String description;
    private final String name;
    private final int weight;

    /**
     * Konstruktor für ein Item.
     * @param description Die Beschreibung des Items
     * @param name Der Name des Items
     * @param weight Das Gewicht des Items
     */
    public Item(String description, String name, int weight) {
        this.description = description;
        this.name = name;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
