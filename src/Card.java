public class Card {
    private Suit suit;
    private Rank rank;
    private boolean faceUp;

    public Card(Suit suit, Rank rank, boolean faceUp) {
        this.suit = suit;
        this.rank = rank;
        this.faceUp = faceUp;
    }

    public Suit getSuit() {
        return suit;
    }
    public Rank getRank() {
        return rank;
    }
    public boolean isFaceUp() {
        return faceUp;
    }
    public void flip() {
        faceUp = !isFaceUp();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rank);
        builder.append(" of ");
        builder.append(suit);
        return new String(builder);
    }
}
