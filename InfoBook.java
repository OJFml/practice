public class InfoBook {
    private String book;
    private int words;
    private double pf;

    public InfoBook(String book, int words, double pf) {
        this.book = book;
        this.words = words;
        this.pf = pf;
    }
    public String getBook() {
        return book;
    }
    public double getPf() {
        return pf;
    }
    public int getWords() {
        return words;
    }
    public void setPf(double pf) {
        this.pf = pf;
    }
}
