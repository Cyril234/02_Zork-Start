package ch.bbw.zork;

import java.util.ArrayList;


/**
 * Class Game - the main class of the "Zork" game.
 * <p>
 * Author:  Michael Kolling, 1.1, March 2000
 * refactoring: Rinaldo Lanza, September 2020
 */

public class Game {
    public static boolean finished = false;

    /** Parser zum Einlesen der Benutzereingaben */
    private Parser parser;
    private ArrayList<Room> visitedRooms = new ArrayList<>();
    private Room currentRoom;
    /** Alle Räume des Spiels: Zellen, Gänge, Kreuzungen, spezielle Räume und Freiheitsräume */
    private Room zelle1, zelle2, zelle3, zelle4, zelle5, zelle6, zelle7, zelle8, zelle9, zelle10, zelle11, zelle12, gangNordSued1, gangNordSued2, gangWestEast1, gangWestEast2, gangWestEast3, gangWestEast4, gangWestEast5, gangWestEast6, kreuzung1, kreuzung2, kreuzung3, wachraum, waschraum, innenhof, kueche, esssaal, waffenkammer, checkpoint, freiheit1, freiheit2, freiheit3, freiheit4;

    private ArrayList<Item> inventory = new ArrayList<>();
    private final int maxWight = 10;
    /** Flag, ob aktuell ein Gespräch aktiv ist */
    private boolean confersationActiv = false;
    /** Handler zur Verwaltung von Gesprächen */
    private ConfersationHandler confersationHandler = new ConfersationHandler();

    private String map = """
                   		 [Zelle]   [Waschraum]  [Zelle]
                   			|           |          |
                 [Zelle]---[Gang]---[Kreuzung]---[Gang]---[Zelle]---[Freiheit]
                   			|           |          |
                   		 [Zelle]        |        [Zelle]
                   					  [Gang]
                   		    [Zelle]     |           [Zelle]
                   			   |        |              |
               [Freiheit]---[Zelle]---[Kreuzung]---[Kreuzung]---[Gang]---[Zelle]
                   			   |        |              |
                   		    [Zelle]     |            [Zelle]
                   		 		        |
                   		 		        |       [Küche(Leiter]
                   		 		        |          |
[Waffenkammer(Uniform, Sprengstoff---[Gang]---[Kreuzung]---[Gang]---[Innenhof]---[Freiheit]
                            |           |         |
                        [Wachraum]   [Gang]---[Esssahl(Löffel)]
                            			|
                            	  [Checkpoint]
                            		    |
                            		[Freiheit]
            """;

    /**
     * Standard-Konstruktor, verwendet System.in als Eingabequelle.
     */
    public Game() {
        this(new Parser(System.in));
    }

    /**
     * Konstruktor mit einem benutzerdefinierten Parser (für Tests).
     * @param parser Der Parser zum Einlesen der Eingaben
     */
    public Game(Parser parser) {
        this.parser = parser;
        finished = false;
        Item uniform = new Item("Eine gestohlene Wärteruniform, die dir hilft, unbemerkt durch das Gefängnis zu gelangen.", "Uniform", 2);
        Item explosives = new Item("Ein kleiner Sprengsatz, stark genug, um eine schwache Mauer oder ein Gitter zu sprengen.", "Sprengstoff", 5);
        Item ladder = new Item("Eine improvisierte Leiter, mit der du höhere Mauern überwinden kannst.", "Leiter", 7);
        Item spoon = new Item("Ein unscheinbarer Metalllöffel, perfekt geeignet, um langsam einen Tunnel zu graben.", "Löffel", 1);

        ConversationPice wacheAusgangConversationPice1_1 = new ConversationPice("Die Wache nickt: „Holtz? Ja, der vergisst ständig, Meldung zu machen…“", "Sergeant Holtz. Er war ziemlich in Eile.", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice1_2 = new ConversationPice("Die Wache wird misstrauisch: „Vom Kommandanten selbst? Witzig, der ist heute gar nicht hier.“ Die Wache zieht ihre Pistole und sagt: „Ergib dich, Häftling. Das ist dein Ende!“", "Das kam direkt vom Kommandanten.", ConversationAktion.END_GAME);
        ConversationPice wacheAusgangConversationPice1_3 = new ConversationPice("Die Wache rollt mit den Augen: „Na schön, geh schon, sonst krieg ich wieder Ärger.“", "Du zuckst mit den Schultern, als wüsstest du es selbst nicht genau.", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice1 = new ConversationPice("Aussenposten? Davon hat mir keiner was gesagt. Wer hat dich eingeteilt?", wacheAusgangConversationPice1_1, wacheAusgangConversationPice1_2, wacheAusgangConversationPice1_3, "Ich wurde zum Aussenposten geschickt. Muss schnell raus.");

        ConversationPice wacheAusgangConversationPice2_1 = new ConversationPice("Die Wache schaut kurz und nickt: „Ah ja, die Werkleute wieder…“", "Werkzeug. Die Mechaniker draussen warten.", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice2_2 = new ConversationPice("Die Wache tritt näher, will es sehen. Als sie näher kommt, merkt sie, dass du gar keinen Wärterausweis hast, und nimmt dich fest.", "Papiere vom Quartiermeister. Soll ich sie dir zeigen?", ConversationAktion.END_GAME);
        ConversationPice wacheAusgangConversationPice2_3 = new ConversationPice("Die Wache lacht: „Na dann beeil dich, das Zeug wiegt sicher genug.“", "Du hältst dir den Rücken und tust so, als würdest du etwas Schweres tragen.", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice2 = new ConversationPice("Etwas nach draussen bringen? Was denn?", wacheAusgangConversationPice2_1, wacheAusgangConversationPice2_2, wacheAusgangConversationPice2_3, "Der Hauptmann hat mich beauftragt, etwas nach draussen zu bringen.");

        ConversationPice wacheAusgangConversationPice3_1 = new ConversationPice("Die Wache lacht müde: „Na, das kann ich verstehen. Raus mit dir!“", "Schichtende! Ich will endlich Feierabend!", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice3_2 = new ConversationPice("Die Wache nickt: „Pflichtbewusst, was? Na gut, geh schon.“", "Du bleibst stehen, salutierst knapp, sagst aber nichts.", ConversationAktion.END_NORMAL);
        ConversationPice wacheAusgangConversationPice3_3 = new ConversationPice("Die Wache ruft laut: „He! Bleib sofort stehen!“ Sie greift zum Funkgerät und alarmiert die anderen. Du hast keine Chance mehr zu fliehen.", "Du tust so, als hättest du ihn nicht gehört, und gehst schneller.", ConversationAktion.END_GAME);
        ConversationPice wacheAusgangConversationPice3 = new ConversationPice("He! Bleib stehen! Ich hab gesagt: Halt! Was machst du da?", wacheAusgangConversationPice3_1, wacheAusgangConversationPice3_2, wacheAusgangConversationPice3_3, "Du hebst nur kurz die Hand zum Gruss und gehst weiter.");

        ConversationPice wacheAusgangConversationPice = new ConversationPice("Halt! Stopp! Wohin des Weges?", wacheAusgangConversationPice1, wacheAusgangConversationPice2, wacheAusgangConversationPice3);

        ConversationPice prisonerEsssaalConversationPice1_1 = new ConversationPice("Der Häftling lehnt sich langsam zurück. Ein kurzes, anerkennendes Nicken.\nDann schiebt er seine Hand unter sein Tablett, löst etwas von der Unterseite und legt es vor dich:\nEs ist ein schmaler, verbogener Metalllöffel.\nEr sagt nichts dazu — er tut einfach so, als hätte es keine Bedeutung.", "Ich verstecke mich vor niemandem.", ConversationAktion.GIVE_ITEM, spoon);
        ConversationPice prisonerEsssaalConversationPice1_2 = new ConversationPice("Der Häftling schnauft verächtlich.\n„Wenn du nicht einmal weisst, was du willst, bist du hier nur Ballast.“\nEr steht langsam auf und geht. Die anderen im Raum beachten dich nicht weiter. Du sitzt allein weiter.", "Vielleicht… spielt das eine Rolle?", ConversationAktion.END_GAME);
        ConversationPice prisonerEsssaalConversationPice1_3 = new ConversationPice("Der Häftling packt blitzschnell deinen Kragen und zieht dich herunter.\nMit der freien Hand schlägt er dir kräftig gegen die Schläfe.\nDir wird schwarz vor Augen.\nDu kippst vom Stuhl, bewusstlos.", "Du stehst halb auf, zögerst und setzt dich wieder.", ConversationAktion.END_NORMAL);
        ConversationPice prisonerEsssaalConversationPice1 = new ConversationPice("Platz gibt’s genug, Neuling. Du hast meinen gewählt. Klingt für mich nach einer schlechten Ausrede. Wovor läufst du weg?", prisonerEsssaalConversationPice1_1, prisonerEsssaalConversationPice1_2, prisonerEsssaalConversationPice1_3, "Hier war Platz.");

        ConversationPice prisonerEsssaalConversationPice2_1 = new ConversationPice("Der Häftling mustert dich erneut. Dann nickt er kurz — ein ungewöhnlich ruhiges, fast respektvolles Nicken.\nEr nimmt seinen Löffel, dreht ihn zwischen den Fingern und legt ihn kommentarlos zu dir.", "Ich habe dir nichts getan.", ConversationAktion.GIVE_ITEM, spoon);
        ConversationPice prisonerEsssaalConversationPice2_2 = new ConversationPice("Der Häftling steht langsam auf. Ohne zu schreien oder zu drohen packt er dich an den Haaren und schlägt deinen Kopf hart gegen die Tischkante.\nEin dumpfer Schlag.\nAlles wird schwarz.", "Wenn du Stress willst, sag es einfach.", ConversationAktion.END_GAME);
        ConversationPice prisonerEsssaalConversationPice2_3 = new ConversationPice("Der Häftling schaut kurz irritiert, dann verliert er das Interesse.\nEr setzt sich wieder ans Essen.\nKein Wort mehr.\nDu wirst komplett ignoriert.", "Du zuckst mit den Schultern.", ConversationAktion.END_NORMAL);
        ConversationPice prisonerEsssaalConversationPice2 = new ConversationPice("Gesellschaft? Von mir? Du weisst gar nicht, mit wem du redest. Manche hätten dich jetzt schon am Kragen.", prisonerEsssaalConversationPice2_1, prisonerEsssaalConversationPice2_2, prisonerEsssaalConversationPice2_3, "Ich dachte, du willst Gesellschaft.");

        ConversationPice prisonerEsssaalConversationPice3_1 = new ConversationPice("Der Häftling wirkt zufrieden mit deiner stillen Antwort.\nEr greift in seine Tasche, zieht einen schmalen, leicht verbogenen Löffel heraus und legt ihn vor dich.\nKeine Erklärung.\nKeine Worte.", "Du nickst leicht.", ConversationAktion.GIVE_ITEM, spoon);
        ConversationPice prisonerEsssaalConversationPice3_2 = new ConversationPice("Der Häftling zuckt die Schultern.\n„Dann bleib unsichtbar.“\nEr wendet sich wieder seinem Essen zu.\nEs passiert nichts.", "Ich halte mich aus allem raus.", ConversationAktion.END_NORMAL);
        ConversationPice prisonerEsssaalConversationPice3_3 = new ConversationPice("Der Häftling steht plötzlich auf.\nEr tritt neben dich, packt deinen Hinterkopf und drückt ihn hart gegen die Tischplatte.\nDer Aufprall ist heftig.\nDu verlierst das Bewusstsein.", "Du schweigst weiter.", ConversationAktion.END_NORMAL);
        ConversationPice prisonerEsssaalConversationPice3 = new ConversationPice("Der Häftling starrt dich lange an. Er legt die Stirn schief, als würde er dich analysieren.\n„Stille… Manchmal ist das Respekt. Manchmal Angst. Was ist es bei dir?“", prisonerEsssaalConversationPice3_1, prisonerEsssaalConversationPice3_2, prisonerEsssaalConversationPice3_3, "Du schweigst.");

        ConversationPice prisonerEsssaalConversationPice = new ConversationPice("Du setzt dich an einen Tisch. Der Häftling neben dir sagt: „Du setzt dich einfach hin wie jemand, der nicht weiss, wo er ist. Also sag schon: Warum sitzt du genau hier bei mir?“", prisonerEsssaalConversationPice1, prisonerEsssaalConversationPice2, prisonerEsssaalConversationPice3);

        ConversationPice wacheInnenhofConversationPice1_1 = new ConversationPice("„Du hast recht“, sagt die Wache. „Aber wenn ich herausfinde, dass du lügst, werde ich dich zerstören.“ Mit diesen letzten Worten rennt die Wache Richtung Zellblock 5.", "Wenn ich lügen würde, würdest du es eh herausfinden. Also wieso sollte ich lügen?", ConversationAktion.END_NORMAL);
        ConversationPice wacheInnenhofConversationPice1_2 = new ConversationPice("Die Wache packt dich plötzlich am Kragen und hebt dich hoch. „Bob ist seit drei Tagen im Urlaub“, sagt sie und legt dir Handschellen an.", "Keine Ahnung, aber Bob hat mich geschickt.", ConversationAktion.END_GAME);
        ConversationPice wacheInnenhofConversationPice1_3 = new ConversationPice("„Okay, du hast recht, aber bleib hier“, sagt die Wache und geht schnellen Schrittes Richtung Zellblock 5.", "„Habe ich dich jemals belogen?“, sagst du.", ConversationAktion.END_NORMAL);
        ConversationPice wacheInnenhofConversationPice1 = new ConversationPice("„Wirklich?“, sagt die Wache misstrauisch. „Wieso sollte ich dir glauben?“", wacheInnenhofConversationPice1_1, wacheInnenhofConversationPice1_2, wacheInnenhofConversationPice1_3, "„Eine andere Wache hat mich geschickt“, sagst du nervös. „Ich soll dich informieren, dass du dringend im Zellblock 5 gebraucht wirst!“");

        ConversationPice wacheInnenhofConversationPice2 = new ConversationPice("Die Wache weicht deiner Faust aus und erwidert sie mit einem Schlag voll ins Gesicht. Um dich wird alles schwarz.", "Du holst aus und schlägst die Wache ins Gesicht, damit sie ohnmächtig wird.", ConversationAktion.END_GAME);

        ConversationPice wacheInnenhofConversationPice3_1 = new ConversationPice("Die Wache wird misstrauisch und fragt dich, woher du weisst, dass dort jemand ohnmächtig ist. Als du keine Antwort hast, dreht sie sich um und sagt: „Ich hätte wissen müssen, dass du mich anlügst.“ Die Wache nimmt dich fest.", "Du sagst, dass du nicht in Zellblock 3 gehen kannst, weil dort eine gegnerische Gang ist.", ConversationAktion.END_GAME);
        ConversationPice wacheInnenhofConversationPice3_2 = new ConversationPice("Die Wache zuckt mit den Schultern und ruft dir zu, dass du im Innenhof warten sollst.", "„Ich kann kein Blut sehen“, antwortest du.", ConversationAktion.END_NORMAL);
        ConversationPice wacheInnenhofConversationPice3_3 = new ConversationPice("Die Wache ruft ein zweites Mal, aber als du dich immer noch nicht bewegst, rennt sie Richtung Zellblock 3 und murmelt etwas über „diese dummen, tauben Insassen“.", "Du tust so, als würdest du ihn nicht hören.", ConversationAktion.END_NORMAL);
        ConversationPice wacheInnenhofConversationPice3 = new ConversationPice("Die Wache rennt sofort Richtung Zellblock 3. Als sie merkt, dass du nicht mitkommst, schaut sie dich fragend an: „Wieso kommst du nicht mit?“", wacheInnenhofConversationPice3_1, wacheInnenhofConversationPice3_2, wacheInnenhofConversationPice3_3, "„Wache! Wache!“, rufst du laut. „Im Zellblock 3 ist ein Gefangener ohnmächtig umgefallen! Sie brauchen unbedingt deine Hilfe!“");

        ConversationPice wacheInnenhofConversationPice = new ConversationPice("Du gehst langsam auf die Wache zu, die in der Ecke steht. Sie schaut dich misstrauisch an. (Das ist deine Gelegenheit, sie abzulenken, damit du ausbrechen kannst.)", wacheInnenhofConversationPice1, wacheInnenhofConversationPice2, wacheInnenhofConversationPice3);

        ConversationPice wacheWachraumConversationPice1 = new ConversationPice("Die Wache schaut dich verwirrt an und fängt dann an zu lächeln. „Das ist der schlechteste Ausbruchsversuch, den ich je gesehen habe.“ Dann steht sie auf, holt aus und schlägt dir voll ins Gesicht. Dir wird schwarz vor Augen.", "Mit der Hand, die du in deiner Tasche hast, tust du so, als hättest du eine Pistole. Du sagst: „Wenn du mich nicht sofort rauslässt, werde ich dich erschiessen!“", ConversationAktion.END_GAME);
        ConversationPice wacheWachraumConversationPice2 = new ConversationPice("„Schon gut“, meint die Wache. „Aber jetzt geh raus“, fügt sie noch an.", "„Sorry, falscher Raum“, sagst du.", ConversationAktion.END_NORMAL);
        ConversationPice wacheWachraumConversationPice3 = new ConversationPice("„Lass mich mal nachsehen“, sagt die Wache. „Ah, jetzt sehe ich: Du kommst erst in 253 Jahren raus. Also mach es dir gemütlich.“", "„Ich wollte fragen, wann ich freigelassen werde“, meinst du. „Meine Insassen-ID ist 193-908-296.“", ConversationAktion.END_NORMAL);
        ConversationPice wacheWachraumConversationPice = new ConversationPice("„Was machst du im Wachraum?“, fragt dich die Wache verärgert.", wacheWachraumConversationPice1, wacheWachraumConversationPice2, wacheWachraumConversationPice3);

        zelle1 = new Room("Zelle 1: Eine leere, kahle Zelle. Nur der kalte Betonboden und die grauen Wände begrüssen dich.");
        zelle2 = new Room("Zelle 2: Du betrittst eine karge Zelle. An der Wand steht eine wackelige Pritsche mit einer zerschlissenen Matratze, und aus dem rostigen Wasserhahn tropft unaufhörlich Wasser.");
        zelle3 = new Room("Zelle 3: Ein schwacher Geruch nach Schimmel liegt in der Luft. An der Wand sind Kratzspuren zu erkennen – jemand wollte hier wohl raus.");
        zelle4 = new Room("Zelle 4: Diese Zelle wirkt auf den ersten Blick unscheinbar. Doch in einer Mauerritze entdeckst du ein kleines, sorgfältig verstecktes Päckchen mit weissem Pulver. Der Geruch verrät dir, dass es sich wohl um Kokain handelt.");
        zelle5 = new Room("Zelle 5: Der Raum ist erstaunlich ordentlich. Eine alte Decke liegt sauber gefaltet auf der Pritsche, und an der Wand ist mit Kreide eine Zahl geschrieben – vielleicht ein Hinweis, vielleicht nur Langeweile.");
        zelle6 = new Room("Zelle 6: Die Zelle erscheint unscheinbar. Eine Wand ist leicht eingestürzt. Der Riss zieht sich tief durch den Stein – vielleicht lässt sie sich sprengen?");
        zelle7 = new Room("Zelle 7: Die Wände sind feucht, der Putz bröckelt ab. Eine flackernde Glühbirne wirft unruhige Schatten. In der hinteren Ecke führt ein schmaler, angefangener Tunnel in die Dunkelheit. Der Geruch nach feuchter Erde verrät, dass jemand hier an einem Fluchtversuch gearbeitet hat.");
        zelle8 = new Room("Zelle 8: Der Raum ist fast leer, doch etwas glänzt im Staub: Ein goldener Ring liegt dort, als hätte ihn jemand absichtlich zurückgelassen.");
        zelle9 = new Room("Zelle 9: Es riecht nach Rauch und Metall. An der Wand sind alte Ritzzeichen – vielleicht eine Botschaft?");
        zelle10 = new Room("Zelle 10: Der Raum ist stickig, die Luft schwer von Schweiss. Ein Häftling sitzt auf der Pritsche und beobachtet dich schweigend. Etwas an ihm wirkt unheimlich – als wüsste er mehr, als er zeigt.");
        zelle11 = new Room("Zelle 11: Dunkelheit dominiert den Raum. Nur eine flackernde Glühbirne wirft schwaches Licht auf den feuchten Boden. In der Ferne hörst du das Summen der Stromleitung.");
        zelle12 = new Room("Zelle 12: Eine alte, vergilbte Zeitung liegt auf dem Boden. Die Schlagzeile: „Ausbruch im Hochsicherheitsflügel“.");

        gangNordSued1 = new Room("Gang Nord–Süd 1: Ein schmaler, kalter Gang mit grauen Betonwänden. Das Licht flackert schwach, und irgendwo tropft Wasser von der Decke.");
        gangNordSued2 = new Room("Gang Nord–Süd 2: Der Gang ist breiter und feuchter als andere. In der Ferne hörst du monotones Tropfen.");
        gangWestEast1 = new Room("Gang West–Ost 1: Die Luft ist stickig, rostige Rohre verlaufen an den Wänden. Aus einem Spalt zischt es leise – vielleicht Dampf.");
        gangWestEast2 = new Room("Gang West–Ost 2: Ein enger, düsterer Gang. Staub schwebt im Licht einer flackernden Lampe.");
        gangWestEast3 = new Room("Gang West–Ost 3: Der Boden ist rissig. In der Ferne hörst du leises Metallklirren.");
        gangWestEast4 = new Room("Gang West–Ost 4: Fast völlige Dunkelheit. Nur ein Notlicht flackert und wirft lange Schatten.");
        gangWestEast5 = new Room("Gang West–Ost 5: Ein langer Gang, dessen Ende im Dunkeln verschwindet. Papierfetzen wehen über den Boden.");
        gangWestEast6 = new Room("Gang West–Ost 6: Der Boden ist feucht und deine Schritte hallen. Ein Luftzug lässt die Neonlichter zittern.");

        wachraum = new Room("Wachraum: Ein kleiner, schlecht beleuchteter Raum mit alten Aktenordnern und einem Funkgerät. Eine Wache sitzt vor Monitoren. Der Geruch von kaltem Kaffee liegt in der Luft.");
        waschraum = new Room("Waschraum: Feucht und kalt. Verrostete Waschbecken, übergelaufene Leitungen, Wasser am Boden. Schimmelgeruch liegt schwer in der Luft.");
        innenhof = new Room("Innenhof: Ein weitläufiger Hof mit hohen Mauern und Stacheldraht. Vielleicht könnte man mit einer Leiter hinüber. Eine Wache patrouilliert langsam.");
        kueche = new Room("Küche: Der Geruch von altem Essen hängt in der Luft. Kochtöpfe liegen verstreut. Eine rostige Leiter lehnt an der Wand. Auf dem Tisch liegt ein verbogener Löffel.");
        esssaal = new Room("Esssaal: Lange Metalltische, schweigende Insassen, flackerndes Licht. Das Klirren von Blechgeschirr hallt im Raum.");
        waffenkammer = new Room("Waffenkammer: Metallregale voller Ausrüstung. Munitionskisten, Uniformspinde und Kisten mit Sprengstoff. Der Geruch von Öl und Metall liegt in der Luft.");
        checkpoint = new Room("Checkpoint: Ein massiver Kontrollpunkt mit dicken Türen und Kameras. Eine Wache überprüft jeden Blick. Dahinter beginnt die Freiheit.");

        kreuzung1 = new Room("Kreuzung 1: Mehrere Gänge treffen hier zusammen. Feuchte Wände, flackerndes Licht und lange Schatten.");
        kreuzung2 = new Room("Kreuzung 2: Ein verzweigter Betonpunkt. Wasser tropft rhythmisch. Die Geräusche hallen aus allen Richtungen.");
        kreuzung3 = new Room("Kreuzung 3: Der Gang öffnet sich. Alte Schilder deuten in verschiedene Richtungen. Ein Luftzug weht aus der Tiefe.");

        freiheit1 = new Room("Freiheit 1: Du legst die Sprengladung in den Riss der Wand. Ein Knall, Staub und Splitter. Die Wand bricht ein und gibt den Blick ins Freie frei. Du atmest die kalte Nachtluft – du bist frei.");
        freiheit2 = new Room("Freiheit 2: Mit dem Löffel gräbst du stundenlang. Schliesslich lockert sich die Erde und frische Luft strömt herein. Du schiebst dich nach draussen und siehst den Nachthimmel – du bist frei.");
        freiheit3 = new Room("Freiheit 3: Als die Wache wegsieht, stellst du die Leiter an die Mauer. Jeder Schritt knarrt. Oben greifst du über den Stacheldraht und springst auf die andere Seite. Du bist frei.");
        freiheit4 = new Room("Freiheit 4: Dank der Uniform aus der Waffenkammer wirkst du wie eine echte Wache. Du gehst am Checkpoint vorbei und trittst in den Regen hinaus. Du hast sie getäuscht – du bist frei.");

        zelle1.setParameter(null, gangWestEast1, null, null);
        zelle2.setParameter(null, null, gangWestEast1, null);
        zelle3.setParameter(gangWestEast1, null, null, null);

        zelle4.setParameter(null, null, gangWestEast2, null);
        zelle5.setParameter(gangWestEast2, null, null, null);
        zelle6.setParameter(null, freiheit1, null, gangWestEast2);

        zelle7.setParameter(null, gangWestEast3, null, freiheit2);
        zelle8.setParameter(null, null, gangWestEast3, null);
        zelle9.setParameter(gangWestEast3, null, null, null);

        zelle10.setParameter(null, null, gangWestEast4, null);
        zelle11.setParameter(gangWestEast4, null, null, null);
        zelle12.setParameter(null, null, null, gangWestEast4);

        gangWestEast1.setParameter(zelle2, kreuzung1, zelle3, zelle1);
        gangWestEast2.setParameter(zelle4, zelle6, zelle5, kreuzung1);
        gangWestEast3.setParameter(zelle8, kreuzung2, zelle9, zelle7);
        gangWestEast4.setParameter(zelle10, zelle12, zelle11, kreuzung2);
        gangWestEast5.setParameter(null, kreuzung3, wachraum, waffenkammer);
        gangWestEast6.setParameter(kueche, innenhof, esssaal, kreuzung3);

        gangNordSued1.setParameter(kreuzung1, null, kreuzung2, null);
        gangNordSued2.setParameter(kreuzung3, esssaal, checkpoint, null);

        kreuzung1.setParameter(waschraum, gangWestEast2, gangNordSued1, gangWestEast1);
        kreuzung2.setParameter(gangNordSued1, gangWestEast4, kreuzung3, gangWestEast3);
        kreuzung3.setParameter(kreuzung2, gangWestEast6, gangNordSued2, gangWestEast5);

        innenhof.setParameter(null, freiheit3, null, gangWestEast6, wacheInnenhofConversationPice);
        esssaal.setParameter(gangWestEast6, null, null, gangNordSued2, prisonerEsssaalConversationPice);
        waschraum.setParameter(null, null, kreuzung1, null);
        ArrayList<Item> itemsWaffenkammer = new ArrayList<>();
        itemsWaffenkammer.add(uniform);
        itemsWaffenkammer.add(explosives);
        waffenkammer.setParameter(null, gangWestEast5, null, null, itemsWaffenkammer);
        wachraum.setParameter(gangWestEast5, null, null, null, wacheWachraumConversationPice);
        ArrayList<Item> itemsKueche = new ArrayList<>();
        itemsKueche.add(ladder);
        kueche.setParameter(null, null, gangWestEast6, null, itemsKueche);
        checkpoint.setParameter(gangNordSued2, null, freiheit4, null, uniform, wacheAusgangConversationPice);

        freiheit1.setParameter(null, null, null, null,true, explosives);
        freiheit2.setParameter(null, null, null, null, true, spoon);
        freiheit3.setParameter(null, null, null, null, true, ladder);
        freiheit4.setParameter(null, null, null, null, true);

        // Startposition
        currentRoom = zelle1; // Start in einer Zelle oben links
    }


    /**
     * Hauptspielschleife. Läuft bis zum Ende des Spiels.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        while (!finished) {
            if (confersationActiv) {
                confersationActiv = confersationHandler.getGetPlayerInput(parser);
            } else {
                Command command = parser.getCommand(false);
                finished = processCommand(command);
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Gibt die Willkommensnachricht aus.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Zork!");
        System.out.println("Zork is a simple adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Verarbeitet ein Kommando des Spielers.
     * @param command Das zu verarbeitende Kommando
     * @return true, wenn das Spiel beendet werden soll
     */
    private boolean processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        } else {

            String commandWord = command.getCommandWord();
            if (commandWord.equals("help")) {
                printHelp();

            } else if (commandWord.equals("go")) {
                return goRoom(command);

            } else if (commandWord.equals("quit")) {
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                } else {
                    return true; // signal that we want to quit
                }
            } else if (commandWord.equals("back")) {
                back(command);
            } else if (commandWord.equals("investigate")) {
                if (currentRoom.getItems().isEmpty()) {
                    System.out.println("Der Raum ist lehr");
                } else {
                    for (Item item : currentRoom.getItems()) {
                        System.out.println(item.getName() + "->" + item.getDescription());
                    }
                }
            } else if (commandWord.equals("drop")) {
                dropItem(command);
            } else if (commandWord.equals("take")) {
                takeItem(command);
            } else if (commandWord.equals("inf")) {
                for (Item item : inventory) {
                    System.out.println(item.getName() + "->" + item.getDescription() + "; " + item.getWeight());
                }
            } else if (commandWord.equals("map")) {
                System.out.println(map);
            }
            return false;
        }
    }

    /**
     * Nimmt ein Item aus dem aktuellen Raum auf.
     * @param command Das Kommando mit dem Item-Namen als zweitem Wort
     */
    private void takeItem(Command command) {
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("Der Raum ist lehr");
        } else {
            boolean intemFound = false;
            ArrayList<Item> items = currentRoom.getItems();
            for (Item item : items) {
                if (item.getName().equals(command.getSecondWord())) {
                    intemFound = true;
                    int wight = 0;
                    for (Item itemInInventory : inventory) {
                        wight += itemInInventory.getWeight();
                    }
                    if (wight + item.getWeight() > maxWight) {
                        System.out.println("You don't have enough space in your inventory.");
                    } else {
                        inventory.add(item);
                        currentRoom.getItems().remove(item);
                        System.out.println("you pick up the " + item.getName());
                        break;
                    }
                }
            }
            if (!intemFound) {
                System.out.println("there is no such item in this room");
            }
        }
    }

    /**
     * Gibt die Hilfe mit allen verfügbaren Kommandos aus.
     */
    private void printHelp() {
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Legt ein Item aus dem Inventar im aktuellen Raum ab.
     * @param command Das Kommando mit dem Item-Namen als zweitem Wort
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("please say which item");
        } else {
            boolean itemFound = false;
            for (Item item : inventory) {
                if (item.getName().equals(command.getSecondWord())) {
                    itemFound = true;
                    inventory.remove(item);
                    currentRoom.getItems().add(item);
                    break;
                }
            }
            if (!itemFound) {
                System.out.println("you don't have this item in your inventory");
            }
        }
    }

    /**
     * Geht zum zuvor besuchten Raum zurück.
     * @param command Das "back"-Kommando
     */
    private void back(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("back what?");
        } else {
            if (command.getCommandWord().equals("back")) {
                if (visitedRooms.isEmpty()) {
                    System.out.println("Cant go back");
                } else {
                    currentRoom = visitedRooms.get(visitedRooms.size() - 1);
                    visitedRooms.remove(visitedRooms.size() - 1);
                    System.out.println("You are in " + currentRoom.getDescription());
                }
            }
            System.out.println("Go where?");
        }
    }

    /**
     * Bewegt den Spieler in einen anderen Raum.
     * @param command Das "go"-Kommando mit der Richtung als zweitem Wort
     * @return true, wenn das Spiel gewonnen wurde
     */
    private boolean goRoom(Command command) {
        Room nextRoom = currentRoom.nextRoom(command.getSecondWord());

        if (nextRoom == null) {
            System.out.println("There is no door!");
            return false;
        } else if (nextRoom.getItemToEnter() != null) {
            if (inventory.contains(nextRoom.getItemToEnter()) && !nextRoom.isOpen()) {
                inventory.remove(nextRoom.getItemToEnter());
                nextRoom.setItemToEnter(null);
            } else {
                System.out.println("You need: " + nextRoom.getItemToEnter().getName());
                return false;
            }
        }

        visitedRooms.add(currentRoom);
        currentRoom = nextRoom;
        System.out.println(currentRoom.longDescription());
        if (currentRoom.isWinnRoom()) {
            System.out.println("You winn!!!");
            return true;
        }
        if (currentRoom.getConversationPice() != null) {
            confersationActiv = true;
            confersationHandler.setupConversation(currentRoom.getConversationPice(), inventory);
        }
        return false;
    }
}