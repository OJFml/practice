public class InfoWord {
    private int[] timesInBook;
    private String word;
    private int booksIn;

    public InfoWord(int[] timesInBook, String word, int booksIn){
        this.timesInBook = timesInBook;
        this.word = word;
        this.booksIn = booksIn;
    }
    
    public double calculate(int book, int books, int words){
        float tf = (float)timesInBook[book]/(float)words;
        double itf = Math.log10(books/this.booksIn);
        // System.out.println((float)(timesInBook[book]/words));
        // System.out.println("times: " + timesInBook[book] + " words: " + words + " TF: " + tf + " ITF: " + itf);
        return tf*itf;
    }
    public String showInfo(int book){
        return "La palabra " + word + " aparece: " + timesInBook[book] + " y aparece en " + booksIn + " libros";
    }
}
