package ch.bbw.zork;

import java.util.Arrays;
import java.util.List;

public class ConversationPice {
    private String npcAusage;
    private List<ConversationPice> conversationPices;
    private String answerPlayer;
    private ConversationAktion aktion;
    private Item item;
    private Boolean alreadyExecuted = false;

    public ConversationPice(String npcAusage, ConversationPice a1, ConversationPice a2, ConversationPice a3) {
        this.npcAusage = npcAusage;
        this.conversationPices = Arrays.asList(a1, a2, a3);
    }

    public ConversationPice(String npcAusage, ConversationPice a1, ConversationPice a2, ConversationPice a3, String answerPlayer) {
        this.npcAusage = npcAusage;
        this.conversationPices = Arrays.asList(a1, a2, a3);
        this.answerPlayer = answerPlayer;
    }

    public ConversationPice(String npcAusage, String answerPlayer, ConversationAktion aktion) {
        this.npcAusage = npcAusage;
        this.answerPlayer = answerPlayer;
        this.aktion = aktion;
    }

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
