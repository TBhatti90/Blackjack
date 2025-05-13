import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int bet;
    private int money;
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

    public int currentMoney() {
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
    public void setMoney(int money) {
        this.money = money;
    }
    public boolean isPlayer() {
        return player;
    }
    public List<Card> getHand() {
        return hand;
    }
    public String showHand() {
        StringBuilder builder = new StringBuilder();
        for (Card card : hand) {
            if (card.isFaceUp()) {
                builder.append(card);
            } else {
                builder.append("?");
            }
        }
        return new String(builder);
    }

    public void addOneCard(Card card) {
        hand.add(card);
    }
    public void addCards(ArrayList<Card> cards) {
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
            return String.format("Player's current bet is %d, current money is %d, and current hand is %s\n", bet, money, showHand());
        } else {
            return String.format("The house's current money is %d, and the dealer's current hand is %s\n", money, showHand());
        }
    }
}