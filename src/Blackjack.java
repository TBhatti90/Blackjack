import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Blackjack {
	public class Player {
		private double money;
		private double bet;
		private int player;
		private ArrayList<String> hand;

		public Player() {
			money = 10000;
			bet = 0;
			player = 0;
			hand = new ArrayList<>();
		}

		public Player(String dealer) {
			this();
			this.setMoney(1000000);
		}

		private int getPlayer() {
			return player;
		}
		private void setPlayer(int player) {
			this.player = player;
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
		public void addCard(String card) {
			hand.add(card);
		}
	}

	ArrayList<Player> players;
	Player dealer;
	ArrayList<String> deck;
	ArrayList<String> discard;

	public Blackjack(int size) {
		dealer = new Player("dealer");
		players = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			players.add(new Player());
		}
		for (int i = 0; i < players.size(); ++i) {
			players.get(i).setPlayer(i + 1);
		}
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
	public ArrayList<Player> getPlayers() {
		return new ArrayList<>(players);
	}
	public Player getDealer() {
		return dealer;
	}
	public ArrayList<String> getDiscard() {
		return new ArrayList<>(discard);
	}
	public void setDiscard(ArrayList<String> discard) {
		this.discard = discard;
	}

	public static void main(String[] args) {
		int size = 0;
		System.out.println("Hi, welcome to Blackjack, how many players are playing? (Max of 7) ");
		Scanner input = new Scanner(System.in);
		do {
			try {
				size = input.nextInt();
				if (size < 1 || size > 7) {
					System.err.println("Error, input must be a natural number, between 1 and 7.");
					input.nextLine();
				}
			} catch (InputMismatchException e) {
				System.err.println("Error, input must be a natural number, between 1 and 7.");
				input.nextLine();
			}
		} while (size < 1 || size > 7);
		System.out.println();
		Blackjack game = new Blackjack(size);
		ArrayList<String> deck = game.getDeck();
		ArrayList<String> discard = game.getDiscard();
		ArrayList<Player> players = game.getPlayers();
		Player dealer = game.getDealer();

		System.out.println("Beginning game");
		if (deck.isEmpty()) {
            deck.addAll(discard);
			Collections.shuffle(deck);
		}

		for (int i = 0; i < players.size(); ++i) {
			do {
				System.out.printf("Player %d, please place your bet\n", players.get(i).getPlayer());
				try {
					players.get(i).setBet(input.nextDouble());
					if (players.get(i).getBet() < 1) {
						System.err.println("Error, input must be a positive number.");
					}
				} catch (InputMismatchException e) {
					System.err.println("Error, input must be a real, non-negative number.");
					input.nextLine();
				}
			} while (players.get(i).getBet() < 1);
		}

		for (int i = 0; i < 2; ++i) {
			dealer.addCard(deck.removeFirst());
		}
		for (int i = 0; i < players.size(); ++i) {
			for (int j = 0; j < 2; ++j) {
				players.get(i).addCard(deck.removeFirst());
			}
		}

		for (int i = 0; i < players.size(); ++i) {
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < players.get(i).getHand().size(); ++j) {
				builder.append(players.get(i).getHand().get(j));
				if (j == players.get(i).getHand().size() - 1) {
					break;
				}
				builder.append(" ");
			}
			String hand = new String(builder);
			System.out.printf("Player %d's current hand is %s\n", players.get(i).getPlayer(), hand);
		}

		input.close();
	}
}
