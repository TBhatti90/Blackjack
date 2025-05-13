import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Blackjack {
	Player player;
	Player dealer;
	Deck deck;
	Deck discard;

	public Blackjack() {
		dealer = new Player(false);
		player = new Player(true);
		deck = new Deck();
		discard = new Deck();
		deck.shuffle();
		discard.dealCards(discard.size());
	}
}
