package flashcards;

public class Main {
    public static void main(String[] args) {
        FlashcardService service = new FlashcardService();

        for(int i = 0; i < args.length; i += 2){
            if(args[i].equals("-import")){
                String fileToImport = args[i+1];
                service.setFileToImport(fileToImport);
                service.loadFileOnStart(fileToImport);
            }

            if(args[i].equals("-export")) {
                String fileToExport = args[i + 1];
                service.setFileToExport(fileToExport);
            }
        }

        while(true){
            service.inputAction();
        }



    }
}
