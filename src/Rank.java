public enum Rank {
    ACE("Ace"),
    TWO("Two"),
    THREE("Three"),
    FOUR("Four"),
    FIVE("Five"),
    SIX("Six"),
    SEVEN("Seven"),
    EIGHT("Eight"),
    NINE("Nine"),
    TEN("Ten"),
    JACK("Jack"),
    QUEEN("Queen"),
    KING("King");

    private final String rankName;

    Rank(String rankName) {
        this.rankName = rankName;
    }

    @Override
    public String toString() {
        return rankName;
    }
}
