import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int bet;
    private double money;
    private final boolean player;

    public Player(boolean player) {
        hand = new ArrayList<>();
        this.player = player;
        if (player) {
            money = 100000;
        } else if (!player) {
            money = 1000000;
        }
        bet = 0;
    }
    public Player(Player player) {
        this.hand = new ArrayList<>(player.hand);
        this.bet = player.bet;
        this.money = player.money;
        this.player = player.player;
    }


    public double currentMoney() {
        return money;
    }
    public int currentBet() {
        return bet;
    }
    public void setBet(int bet) {
        switch (bet) {
            case 10:
            case 50:
            case 100:
            case 500:
                this.bet = bet;
                break;
            default:
                break;
        }
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public boolean isPlayer() {
        return player;
    }
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    public String showHand() {
        StringBuilder builder = new StringBuilder();
        for (Card card : hand) {
            if (card.isFaceUp()) {
                builder.append(card);
            } else {
                builder.append("?");
            }
            builder.append(", ");
        }
        if (!hand.isEmpty()) {
            builder.delete(builder.length() - 2, builder.length());
        } else {
            builder.append("nothing");
        }
        return new String(builder);
    }

    public void addOneCard(Card card) {
        hand.add(card);
    }
    public void addCards(List<Card> cards) {
        hand.addAll(cards);
    }
    public Card removeOneCard(Card card) {
        return hand.remove(hand.indexOf(card));
    }
    public List<Card> removeCards(List<Card> cards) {
        List<Card> removedCards = new ArrayList<>();
        for (Card card : cards) {
            removedCards.add(hand.remove(hand.indexOf(card)));
        }
        return removedCards;
    }

    @Override
    public String toString() {
        if (player) {
            return String.format("Player's current bet is %d, current money is %.2f, and current hand is %s.", bet, money, showHand());
        } else {
            return String.format("The house's current money is %.2f, and the dealer's current hand is %s.", money, showHand());
        }
    }
}