package flashcards;

import java.io.*;
import java.util.*;

public class FlashcardService {
    private Scanner scanner;
    private String action;

    private String outputString;

    private Flashcard flashcard;

    private List<Flashcard> listOfFlashcards = new ArrayList<>();
    private List<String> listOfTerms;
    private List<String> listOfDefinitions;
    private List<Integer> listOfAmountsOfMistakes;
    private List<String> logList;

    private String fileToImport;
    private String fileToExport;

    public FlashcardService(){
        scanner = new Scanner(System.in);
        listOfFlashcards = new ArrayList<>();
        logList = new ArrayList<>();
    }
    public void inputAction(){
        System.out.println("Input the action (add, remove, import, export, ask, exit, log," +
                " hardest card, reset stats)");
        action = scanner.nextLine().toLowerCase().trim();
        logList.add(action);

        if(action.equals("exit")){
            outputMsg("Bye bye!");
            if(fileToExport != null) {
                exportOnExit(fileToExport);
            }
            System.exit(0);
        }
        else if(action.equals("add")){
            addCard();
        }
        else if(action.equals("remove")){
            removeCard();
        }
        else if(action.equals("import")){
            loadFile();
        }
        else if(action.equals("export")){
            exportFile();
        }
        else if(action.equals("ask")){
            askCard();
        }
        else if(action.equals("hardest card")){
            hardestCard();
        }
        else if(action.equals("reset stats")){
            resetStats();
        }
        else if(action.equals("log")){
            log();
        }
        else {
            outputMsg("No such action, try again: ");
        }

    }


    private void log(){
        String fileName;

        outputMsg("File name:");
        fileName = scanner.nextLine();
        logList.add(fileName);

        File file = new File(fileName);

        try(PrintWriter writer = new PrintWriter(file)){
            for(String s : logList){
                writer.println(s);
            }
            outputMsg("The log has been saved.");
        }
        catch(IOException e){
            outputMsg("IOException has been thrown");
        }
    }

    private void exportFile(){
        String fileName;
        String tempString;

        int amountOfCards = listOfFlashcards.size();

        outputMsg("File name:");
        fileName = scanner.nextLine();
        logList.add(fileName);
        File file = new File(fileName);

        try(PrintWriter writer = new PrintWriter(file)){
            for(Flashcard card : listOfFlashcards){
                tempString = card.getTerm() + " - " + card.getDefinition() + " - " + card.getAmountOfMistakes();
                writer.println(tempString);
            }
            outputMsg(amountOfCards + " cards have been saved.");
        }
        catch(IOException e){
            outputMsg("IOException has been thrown");
        }
    }


    private void loadFile() {
        String term;
        String definition;
        String tempString;

        listOfTerms = getListOfTerms();
        listOfDefinitions = getListOfDefinitions();

        int amountOfMistakes;

        int counter = 0;

        String fileName;
        outputMsg("File name:");
        fileName = scanner.nextLine().toLowerCase();
        logList.add(fileName);
        File file = new File(fileName);

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                tempString = sc.nextLine().trim();
                String[] tempArr = tempString.split(" - ");

                term = tempArr[0];
                definition = tempArr[tempArr.length-2];
                amountOfMistakes = Integer.parseInt(tempArr[tempArr.length - 1]);

                if (listOfTerms.contains(term)){
                    for(Flashcard card : listOfFlashcards){
                        if (term.equals(card.getTerm())){
                            card.setDefinition(definition);
                            counter++;

                            card.setAmountOfMistakes(amountOfMistakes);
                        }
                    }
                }
                else {
                    flashcard = new Flashcard(term, definition);
                    flashcard.setAmountOfMistakes(amountOfMistakes);
                    listOfFlashcards.add(flashcard);
                    counter++;
                }
            }
            if (counter == 1){
                outputMsg("1 card have been loaded");
            }
            else {
                outputMsg(counter + " cards have been loaded.");
            }
        }
        catch(FileNotFoundException e){
            outputMsg("Not found");
        }
    }

    public void loadFileOnStart(String fileName){
        String term;
        String definition;
        String tempString;

        listOfTerms = getListOfTerms();
        listOfDefinitions = getListOfDefinitions();

        int amountOfMistakes;

        int counter = 0;

        File file = new File(fileName);

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                tempString = sc.nextLine().trim();
                String[] tempArr = tempString.split(" - ");

                term = tempArr[0];
                definition = tempArr[tempArr.length-2];
                amountOfMistakes = Integer.parseInt(tempArr[tempArr.length - 1]);

                if (listOfTerms.contains(term)){
                    for(Flashcard card : listOfFlashcards){
                        if (term.equals(card.getTerm())){
                            card.setDefinition(definition);
                            counter++;

                            card.setAmountOfMistakes(amountOfMistakes);
                        }
                    }
                }
                else {
                    flashcard = new Flashcard(term, definition);
                    flashcard.setAmountOfMistakes(amountOfMistakes);
                    listOfFlashcards.add(flashcard);
                    counter++;
                }
            }
            if (counter == 1){
                outputMsg("1 card have been loaded");
            }
            else {
                outputMsg(counter + " cards have been loaded.");
            }
        }
        catch(FileNotFoundException e){
            outputMsg("Not found");
        }
    }

    private void exportOnExit(String fileToExport){
        String tempString;

        int amountOfCards = listOfFlashcards.size();

        File file = new File(fileToExport);

        try(PrintWriter writer = new PrintWriter(file)){
            for(Flashcard card : listOfFlashcards){
                tempString = card.getTerm() + " - " + card.getDefinition() + " - " + card.getAmountOfMistakes();
                writer.println(tempString);
            }
            outputMsg(amountOfCards + " cards have been saved.");
        }
        catch(IOException e){
            outputMsg("IOException has been thrown");
        }
    }


    // done
    private void addCard(){
        listOfTerms = getListOfTerms();
        listOfDefinitions = getListOfDefinitions();

        String term;
        String definition;

        outputMsg("The card:");

        term = scanner.nextLine();
        logList.add(term);
        while(listOfTerms.contains(term)){
            outputMsg("The card \"" + term +"\" already exists.");
            return;
        }

        System.out.println("The definition of the card: ");
        definition = scanner.nextLine();
        logList.add(definition);

        while(listOfDefinitions.contains(definition)){
            outputMsg("The definition \"" + definition +"\" already exists.");
            return;
        }
        flashcard = new Flashcard(term, definition);
        listOfFlashcards.add(flashcard);
        // cards.put(term, definition);
        outputMsg("The pair (\"" + term + "\":" + "\"" + definition + "\") has been added");


    }

    // done
    private void removeCard(){
        String strToRemove;

        listOfTerms = getListOfTerms();

        outputMsg("The card: ");

        strToRemove = scanner.nextLine();
        logList.add(strToRemove);

        if(listOfTerms.contains(strToRemove)){
            for(Flashcard card : listOfFlashcards) {
                if (card.getTerm().equals((strToRemove))) {
                    listOfFlashcards.remove(card);
                    outputMsg("The card has been removed");
                    break;
                }
            }
        }
        else {
            outputMsg("Can't remove \"" + strToRemove + "\":there is no such card.");
        }

    }


    // done
    private void askCard(){
        String answer;
        listOfDefinitions = getListOfDefinitions();

        int timesToAsk;
        int counter = 0;

        outputMsg("How many times to ask?");
        timesToAsk = Integer.parseInt(scanner.nextLine());
        logList.add(Integer.toString(timesToAsk));

        if(listOfFlashcards.isEmpty()){
            outputString = "There is nothing to ask";
            outputMsg(outputString);
        }
        else{
            while(true){
                //for(var entry : cards.entrySet();
                for(Flashcard card : listOfFlashcards){
                    outputMsg("Print the definition of \"" + card.getTerm() + "\":");
                    // System.out.println("Print the definition of \"" + card.getTerm() + "\":");
                    answer = scanner.nextLine();
                    logList.add(answer);

                    if(answer.equals(card.getDefinition())){
                        outputMsg("Correct!");
                    }
                    else {
                        if(listOfDefinitions.contains(answer)) {
                            for (Flashcard tempCard : listOfFlashcards) {
                                if (answer.equals(tempCard.getDefinition())) {
                                    outputMsg("Wrong. The right answer is \"" + card.getDefinition() + "\", but your definition is correct for \""
                                            + tempCard.getTerm() + "\".");
                                    card.setAmountOfMistakes(card.getAmountOfMistakes() + 1);
                                    break;
                                }
                            }
                        }

                        else {
                            outputMsg("Wrong. The right answer is \"" + card.getDefinition() + "\".");
                            card.setAmountOfMistakes(card.getAmountOfMistakes() + 1);
                        }

                    }
                    counter++;
                    if(counter == timesToAsk)
                        return;

                }
            }
        }
    }

    private void hardestCard(){

        listOfAmountsOfMistakes = getListOfAmountsOfMistakes();
        if(listOfAmountsOfMistakes.isEmpty()){
            outputMsg("There are no cards with errors.");
            return;
        }
        int max = Collections.max(listOfAmountsOfMistakes);

        List<String> cardsWithMaxMistakes = new ArrayList<>();

        for(Flashcard card : listOfFlashcards){
            if(card.getAmountOfMistakes() == max){
                cardsWithMaxMistakes.add(card.getTerm());
            }
        }
        if (max == 0){
            outputString = "There are no cards with errors.";
        }
        else if (cardsWithMaxMistakes.size() == 1){
            outputString = "The hardest card is \"" + cardsWithMaxMistakes.get(0) + "\"." +
                    " You have " + max + " errors answering it.";
        }
        else {
            int size = cardsWithMaxMistakes.size();
            int counter = 0;
            outputString = "The hardest cards are ";
            StringBuilder sb = new StringBuilder(outputString);

            for(String term : cardsWithMaxMistakes){
                if(counter == size -1){
                    sb.append("\"" + term + "\".");
                }
                else {
                    sb.append("\"" + term + "\", ");
                    counter++;
                }
            }
            sb.append(" You have " + max + " errors answering them");
            outputString = sb.toString();

        }

        outputMsg(outputString);
    }

    private void resetStats(){
        for(Flashcard card : listOfFlashcards){
            card.setAmountOfMistakes(0);
        }
        outputString = "Card statistics has been reset.";
        outputMsg(outputString);
    }

    private List<String> getListOfDefinitions(){
        List<String> listOfDefinitions = new ArrayList<>();
        for(Flashcard card : listOfFlashcards){
            listOfDefinitions.add(card.getDefinition());
        }

        return listOfDefinitions;
    }

    private List<String> getListOfTerms(){
        List<String> listOfTerms = new ArrayList<>();
        for(Flashcard card : listOfFlashcards){
            listOfTerms.add(card.getTerm());
        }

        return listOfTerms;
    }

    private List<Integer> getListOfAmountsOfMistakes(){
        List<Integer> listOfAmountsOfMistakes = new ArrayList<>();
        for(Flashcard card : listOfFlashcards){
            listOfAmountsOfMistakes.add(card.getAmountOfMistakes());
        }
        return listOfAmountsOfMistakes;
    }

    private void outputMsg(String msg){
        System.out.println(msg);
        logList.add(msg);
    }

    public void setFileToImport(String fileToImport) {
        this.fileToImport = fileToImport;
    }

    public void setFileToExport(String fileToExport) {
        this.fileToExport = fileToExport;
    }
}
