package flashcards;

public class Flashcard {
    private String term;
    private String definition;
    private int amountOfMistakes;

    public Flashcard(String term, String definition){
        this.term = term;
        this.definition = definition;
        amountOfMistakes = 0;
    }

    public String getTerm() {
        return term;
    }
    public String getDefinition() {
        return definition;
    }

    public int getAmountOfMistakes() {
        return amountOfMistakes;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setAmountOfMistakes(int amountOfMistakes) {
        this.amountOfMistakes = amountOfMistakes;
    }
}
