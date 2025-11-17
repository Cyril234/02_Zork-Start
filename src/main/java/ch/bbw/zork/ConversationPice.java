package ch.bbw.zork;

import java.util.Arrays;
import java.util.List;

/**
 * Klasse zur Repräsentation eines Gesprächsstücks im Spiel.
 * Ein Gesprächsstück kann NPC-Aussagen, Spielerantworten und Aktionen enthalten.
 */
public class ConversationPice {
    private String npcAusage;
    /** Liste der möglichen Antworten (max. 3) */
    private List<ConversationPice> conversationPices;
    private String answerPlayer;
    private ConversationAktion aktion;
    private Item item;
    private Boolean alreadyExecuted = false;

    /**
     * Konstruktor für ein Gesprächsstück mit drei Antwortmöglichkeiten.
     * @param npcAusage Die Aussage des NPCs
     * @param a1 Erste Antwortmöglichkeit
     * @param a2 Zweite Antwortmöglichkeit
     * @param a3 Dritte Antwortmöglichkeit
     */
    public ConversationPice(String npcAusage, ConversationPice a1, ConversationPice a2, ConversationPice a3) {
        this.npcAusage = npcAusage;
        this.conversationPices = Arrays.asList(a1, a2, a3);
    }

    /**
     * Konstruktor für ein Gesprächsstück mit drei Antwortmöglichkeiten und Spielerantwort.
     * @param npcAusage Die Aussage des NPCs
     * @param a1 Erste Antwortmöglichkeit
     * @param a2 Zweite Antwortmöglichkeit
     * @param a3 Dritte Antwortmöglichkeit
     * @param answerPlayer Die Antwort des Spielers
     */
    public ConversationPice(String npcAusage, ConversationPice a1, ConversationPice a2, ConversationPice a3, String answerPlayer) {
        this.npcAusage = npcAusage;
        this.conversationPices = Arrays.asList(a1, a2, a3);
        this.answerPlayer = answerPlayer;
    }

    /**
     * Konstruktor für ein Gesprächsstück mit Aktion.
     * @param npcAusage Die Aussage des NPCs
     * @param answerPlayer Die Antwort des Spielers
     * @param aktion Die auszuführende Aktion
     */
    public ConversationPice(String npcAusage, String answerPlayer, ConversationAktion aktion) {
        this.npcAusage = npcAusage;
        this.answerPlayer = answerPlayer;
        this.aktion = aktion;
    }

    /**
     * Konstruktor für ein Gesprächsstück mit Aktion und Item.
     * @param npcAusage Die Aussage des NPCs
     * @param answerPlayer Die Antwort des Spielers
     * @param aktion Die auszuführende Aktion
     * @param item Das zu übergebende Item
     */
    public ConversationPice(String npcAusage, String answerPlayer, ConversationAktion aktion, Item item) {
        this.npcAusage = npcAusage;
        this.answerPlayer = answerPlayer;
        this.aktion = aktion;
        this.item = item;
    }

    public String getNpcAusage() {
        return npcAusage;
    }

    public void setNpcAusage(String npcAusage) {
        this.npcAusage = npcAusage;
    }

    public List<ConversationPice> getAnswers() {
        return conversationPices;
    }

    public void setAnswers(List<ConversationPice> conversationPices) {
        this.conversationPices = conversationPices;
    }

    public ConversationPice getAnswer(int index){
        return conversationPices.get(index);
    }

    public String getAnswerPlayer() {
        return answerPlayer;
    }

    public void setAnswerPlayer(String answerPlayer) {
        this.answerPlayer = answerPlayer;
    }

    public ConversationAktion getAktion() {
        return aktion;
    }

    public void setAktion(ConversationAktion aktion) {
        this.aktion = aktion;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Boolean getAlreadyExecuted() {
        return alreadyExecuted;
    }

    public void setAlreadyExecuted(Boolean alreadyExecuted) {
        this.alreadyExecuted = alreadyExecuted;
    }
}
