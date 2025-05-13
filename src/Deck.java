import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private List<Card> deckOfCards;

    public Deck() {
        deckOfCards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deckOfCards.add(new Card(suit, rank, false));
            }
        }
    }
    public void shuffle() {
        Collections.shuffle(deckOfCards);
    }
    public int size() {
        return deckOfCards.size();
    }
    public Card dealOneCard() {
        return deckOfCards.removeFirst();
    }
    public List<Card> dealCards(int num) {
        List<Card> cards = new ArrayList<>(num);
        for (Card card : deckOfCards) {
            cards.add(deckOfCards.removeFirst());
            --num;
            if (num == 0) {
                break;
            }
        }
        return cards;
    }
    public void addOneCard(Card card) {
        deckOfCards.add(card);
    }
    public void addCards(List<Card> cards) {
        deckOfCards.addAll(cards);
    }

    @Override
    public String toString() {
        return String.format("This deck has a size of %d cards\n", size());
    }
}
