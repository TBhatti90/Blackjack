public enum GameResult {
    PLAYER_WON("You won!"),
    PLAYER_LOST("Aww bad luck. It happens."),
    PUSH("A push, try your luck again?"),
    NATURAL_BLACKJACK("Whoa a natural blackjack! You win one a half times your bet! Congratulations!"),
    HOUSE_BUST("This is surprising! You've run us clean! I'm sorry but you're going to have to leave the casino.");

    private final String flavorText;

    GameResult(String flavorText) {
        this.flavorText = flavorText;
    }

    @Override
    public String toString() {
        return flavorText;
    }
}
