import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Blackjack {
	public class Player {
		private double money;
		private double bet;
		private ArrayList<String> hand;

		public Player() {
			money = 10000;
			bet = 0;
			hand = new ArrayList<>();
		}

		public Player(String dealer) {
			this();
			this.setMoney(1000000);
		}

		public double getMoney() {
			return money;
		}
		public void setMoney(double money) {
			this.money = money;
		}
		public double getBet() {
			return bet;
		}
		public void setBet(double bet) {
			this.bet = bet;
		}
		public ArrayList<String> getHand() {
			return new ArrayList<>(hand);
		}
	}

	ArrayList<Player> players;
	Player dealer;
	ArrayList<String> deck;
	ArrayList<String> discard;

	public Blackjack(int size) {
		dealer = new Player("dealer");
		players = new ArrayList<>(size);
		deck = new ArrayList<>(312);
		discard = new ArrayList<>();
		for (int i = 1; i < 14; ++i) {
			for (int j = 0; j < 24; ++j) {
				switch (i) {
					case 1:
						deck.add("Ace");
						break;
					case 11:
						deck.add("Jack");
						break;
					case 12:
						deck.add("Queen");
						break;
					case 13:
						deck.add("King");
						break;
					default:
						deck.add(Integer.toString(i));
						break;
				}
			}
		}
		Collections.shuffle(deck);
	}

	public ArrayList<String> getDeck() {
		return new ArrayList<>(deck);
	}

	public void setDeck(ArrayList<String> deck) {
		this.deck = deck;
	}

	public static void main(String[] args) {
		int size = 0;
		System.out.println("Hi, welcome to Blackjack, how many players are playing? (Max of 7)");
		Scanner input = new Scanner(System.in);
		do {
			try {
				size = input.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Error, input must be a natural number, between 1 and 7.");
				input.nextLine();
			}
		} while (size < 1 || size > 7);
		System.out.println();
		Blackjack game = new Blackjack(size);
		ArrayList<String> currentDeck = game.getDeck();
		currentDeck.forEach(System.out::println);




		input.close();
	}
}
