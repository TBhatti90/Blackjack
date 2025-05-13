public enum Suit {
    SPADES("Spades", Color.BLACK),
    HEARTS("Hearts", Color.RED),
    CLUBS("Clubs", Color.BLACK),
    DIAMONDS("Diamonds", Color.RED);

    private final String suitName;
    private final Color color;

    Suit(String suitName, Color color) {
        this.suitName = suitName;
        this.color = color;
    }

    @Override
    public String toString() {
        return suitName;
    }
    public Color getColor() {
        return color;
    }
}
