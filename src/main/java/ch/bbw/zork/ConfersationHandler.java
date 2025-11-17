package ch.bbw.zork;

import java.util.ArrayList;

/**
 * Handler-Klasse zur Verwaltung von Gesprächen mit NPCs im Spiel.
 * Verarbeitet Dialoge, Spielerantworten und spezielle Aktionen.
 */
public class ConfersationHandler {
    /** Das aktuelle Gesprächsstück, das verarbeitet wird */
    private ConversationPiece conversationPiece;
    /** Das Inventar des Spielers für Item-Interaktionen */
    private ArrayList<Item> inventory;

    /**
     * Verarbeitet die Spielereingabe während eines Gesprächs.
     * @param parser Der Parser zum Einlesen der Spielereingabe
     * @return true, wenn das Gespräch weiterläuft, false wenn es beendet wird
     * @throws IllegalStateException wenn conversationPiece null ist
     */
    public boolean getGetPlayerInput(Parser parser) {
        // Früher: getGetPlayerInput
        if (conversationPiece == null) {
            throw new IllegalStateException("conversationPiece must not be null");
        }

        // Wenn dieses Gesprächsstück schon abgehandelt wurde, ist hier Schluss
        if (conversationPiece.getAlreadyExecuted()) {
            return false;
        }

        // Kein spezieller Aktions-Typ → normale Auswahl mit Antworten
        if (conversationPiece.getAktion() == null) {
            return handleDialogueChoice(parser);
        }

        // Es gibt eine Aktion → je nach Typ behandeln
        switch (conversationPiece.getAktion()) {
            case END_NORMAL:
                System.out.println(conversationPiece.getNpcAusage());
                return false;

            case END_GAME:
                System.out.println(conversationPiece.getNpcAusage());
                System.out.println("You lost");
                Game.finished = true;
                return false;

            case GIVE_ITEM:
                System.out.println(conversationPiece.getNpcAusage());
                inventory.add(conversationPiece.getItem());
                return false;

            default:
                throw new IllegalStateException(
                        "Unexpected ConversationAktion: " + conversationPiece.getAktion()
                );
        }
    }

    /**
     * Behandelt die normale Dialog-Situation mit 3 auswählbaren Antworten.
     */
    private boolean handleDialogueChoice(Parser parser) {
        // NPC spricht
        System.out.println(conversationPiece.getNpcAusage());

        // Antwortmöglichkeiten Player
        System.out.println("1: "+conversationPiece.getAnswer(0).getAnswerPlayer());
        System.out.println("2: "+conversationPiece.getAnswer(1).getAnswerPlayer());
        System.out.println("3: "+conversationPiece.getAnswer(2).getAnswerPlayer());

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
                conversationPiece.setAlreadyExecuted(true);
                conversationPiece = conversationPiece.getAnswer(index);
                break;

            default:
                System.out.println("Your answer has to be 1, 2 or 3!");
                break;
        }

        // true = Dialog läuft weiter
        return true;
    }

    /**
     * Initialisiert ein neues Gespräch mit einem NPC.
     * @param conversationPiece Das Gesprächsstück, das gestartet werden soll
     * @param inventory Das Inventar des Spielers
     */
    public void setupConversation(ConversationPiece conversationPiece, ArrayList<Item> inventory){
        this.conversationPiece = conversationPiece;
        this.inventory = inventory;
    }
}
