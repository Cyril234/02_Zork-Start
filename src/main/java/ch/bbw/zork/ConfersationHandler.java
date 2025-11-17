package ch.bbw.zork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConfersationHandler {
    private ConversationPice conversationPice;
    private ArrayList<Item> inventory;

    public boolean getGetPlayerInput(Parser parser) {
        String inputLine;




        try {
            if(!conversationPice.getAlreadyExecuted()){
                if(conversationPice.getAktion() == null){
                    System.out.println(conversationPice.getNpcAusage());
                    System.out.println("1: "+conversationPice.getAnswer(0).getAnswerPlayer());
                    System.out.println("2: "+conversationPice.getAnswer(1).getAnswerPlayer());
                    System.out.println("3: "+conversationPice.getAnswer(2).getAnswerPlayer());
                    String desision = parser.getCommand(true).getCommandWord();
                    if(desision.trim().equals("1")){
                        conversationPice.setAlreadyExecuted(true);
                        conversationPice = conversationPice.getAnswer(0);
                    } else if (desision.trim().equals("2")) {
                        conversationPice.setAlreadyExecuted(true);
                        conversationPice = conversationPice.getAnswer(1);
                    }else if (desision.trim().equals("3")) {
                        conversationPice.setAlreadyExecuted(true);
                        conversationPice = conversationPice.getAnswer(2);
                    }else{
                        System.out.println("Your Answer has to be 1, 2 or 3!");
                    }
                    return true;
                } else if (conversationPice.getAktion() == ConversationAktion.END_NORMAL) {
                    System.out.println(conversationPice.getNpcAusage());
                    return false;
                }else if (conversationPice.getAktion() == ConversationAktion.END_GAME) {
                    System.out.println("You lost");
                    Game.finished = true;
                    return false;
                }else {
                    System.out.println(conversationPice.getNpcAusage());
                    inventory.add(conversationPice.getItem());
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void setupConversation(ConversationPice conversationPice, ArrayList<Item> inventory){
        this.conversationPice = conversationPice;
        this.inventory = inventory;
    }
}
