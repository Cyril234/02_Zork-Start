package ch.bbw.zork;

public class Transformer extends Item{
    private boolean car = false;
    private String descriptionCar;
    private String descriptionDefult;
    /**
     * Konstruktor für ein Item.
     *
     * @param description Die Beschreibung des Items
     * @param name        Der Name des Items
     * @param weight      Das Gewicht des Items
     */
    public Transformer(String description, String descriptionCar, String name, int weight) {
        super(description, name, weight);
        this.descriptionCar = descriptionCar;
        this.descriptionDefult = description;
    }

    public void transform(){
        if(!car){
            super.setDescription(descriptionCar);
            car = true;
        }else {
            super.setDescription(descriptionDefult);
            car = false;
        }
    }
}
