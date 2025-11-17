package ch.bbw.zork;

import java.util.ArrayList;

public class ConfersationHandler {
    private ConversationPice conversationPice;
    private ArrayList<Item> inventory;

    public boolean getGetPlayerInput(Parser parser) {
        // Früher: getGetPlayerInput
        if (conversationPice == null) {
            throw new IllegalStateException("conversationPice must not be null");
        }

        // Wenn dieses Gesprächsstück schon abgehandelt wurde, ist hier Schluss
        if (conversationPice.getAlreadyExecuted()) {
            return false;
        }

        // Kein spezieller Aktions-Typ → normale Auswahl mit Antworten
        if (conversationPice.getAktion() == null) {
            return handleDialogueChoice(parser);
        }

        // Es gibt eine Aktion → je nach Typ behandeln
        switch (conversationPice.getAktion()) {
            case END_NORMAL:
                System.out.println(conversationPice.getNpcAusage());
                return false;

            case END_GAME:
                System.out.println(conversationPice.getNpcAusage());
                System.out.println("You lost");
                Game.finished = true;
                return false;

            case GIVE_ITEM:
                System.out.println(conversationPice.getNpcAusage());
                inventory.add(conversationPice.getItem());
                return false;

            default:
                throw new IllegalStateException(
                        "Unexpected ConversationAktion: " + conversationPice.getAktion()
                );
        }
    }

    /**
     * Behandelt die normale Dialog-Situation mit 3 auswählbaren Antworten.
     */
    private boolean handleDialogueChoice(Parser parser) {
        // NPC spricht
        System.out.println(conversationPice.getNpcAusage());

        // Antwortmöglichkeiten Player
        System.out.println("1: "+conversationPice.getAnswer(0).getAnswerPlayer());
        System.out.println("2: "+conversationPice.getAnswer(1).getAnswerPlayer());
        System.out.println("3: "+conversationPice.getAnswer(2).getAnswerPlayer());

        // Eingabe holen
        String decision = parser.getCommand(true).getCommandWord();
        if (decision == null) {
            System.out.println("Your answer has to be 1, 2 or 3!");
            return true; // Dialog läuft weiter
        }

        decision = decision.trim();

        switch (decision) {
            case "1":
            case "2":
            case "3":
                int index = Integer.parseInt(decision) - 1;
                conversationPice.setAlreadyExecuted(true);
                conversationPice = conversationPice.getAnswer(index);
                break;

            default:
                System.out.println("Your answer has to be 1, 2 or 3!");
                break;
        }

        // true = Dialog läuft weiter
        return true;
    }

    public void setupConversation(ConversationPice conversationPice, ArrayList<Item> inventory){
        this.conversationPice = conversationPice;
        this.inventory = inventory;
    }
}
