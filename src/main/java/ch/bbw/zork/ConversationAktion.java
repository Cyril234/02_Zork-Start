package ch.bbw.zork;

/**
 * Enum für verschiedene Aktionstypen, die während eines Gesprächs ausgelöst werden können.
 */
public enum ConversationAktion {
    /** Beendet das Spiel (Spieler hat verloren) */
    END_GAME, 
    /** Gibt dem Spieler ein Item */
    GIVE_ITEM, 
    /** Beendet das Gespräch normal */
    END_NORMAL
}
