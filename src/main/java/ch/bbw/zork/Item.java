package ch.bbw.zork;

public class Item {
    private final String description;
    private final String name;
    private final int weight;

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
