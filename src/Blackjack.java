import java.util.*;

public class Blackjack {
	private Player player;
	private Player dealer;
	private Deck deck;
	private Deck discard;

	public Blackjack() {
		dealer = new Player(false);
		player = new Player(true);
		deck = new Deck();
		discard = new Deck();
		deck.shuffle();
		discard.dealCards(discard.size());
	}

	public Player getPlayer() {
		return player;
	}
	public Player getDealer() {
		return dealer;
	}
	public Deck getDeck() {
		return deck;
	}
	public Deck getDiscard() {
		return discard;
	}

	public void initialDeal() {
		player.addCards(deck.dealCards(2));
		for (Card card : player.getHand()) {
			card.flip();
		}
		dealer.addCards(deck.dealCards(2));
		for (Card card : dealer.getHand()) {
			card.flip();
		}
		dealer.getHand().getLast().flip();
	}
	public void takeCard(Player dummy) {
		if (dummy.isPlayer()) {
			player.addOneCard(deck.dealOneCard());
			player.getHand().getLast().flip();
		} else {
			dealer.addOneCard(deck.dealOneCard());
			dealer.getHand().getLast().flip();
		}
	}

	public String simpleHand(List<Card> hand) {
		StringBuilder builder = new StringBuilder();
		for (Card card : hand) {
			if (card.isFaceUp()) {
				switch (card.getRank()) {
					case TEN:
					case JACK:
					case QUEEN:
					case KING:
						builder.append(10);
						break;
					case TWO:
						builder.append(2);
						break;
					case THREE:
						builder.append(3);
						break;
					case FOUR:
						builder.append(4);
						break;
					case FIVE:
						builder.append(5);
						break;
					case SIX:
						builder.append(6);
						break;
					case SEVEN:
						builder.append(7);
						break;
					case EIGHT:
						builder.append(8);
						break;
					case NINE:
						builder.append(9);
						break;
					case ACE:
						builder.append('A');
						break;
					default:
						break;
				}
			} else {
				builder.append("?");
			}
			builder.append(" ");
		}
		builder.delete(builder.length() - 1, builder.length());
		return new String(builder);
	}

	public List<Integer> possibleHandValues(List<Card> hand) {
		List<Integer> vals = new ArrayList<>();
		int sumOne = 0;
		int sumTwo = 0;
		for (Card card : hand) {
			switch (card.getRank()) {
				case TEN:
				case JACK:
				case QUEEN:
				case KING:
					sumOne += 10;
					sumTwo += 10;
					break;
				case TWO:
					sumOne += 2;
					sumTwo += 2;
					break;
				case THREE:
					sumOne += 3;
					sumTwo += 3;
					break;
				case FOUR:
					sumOne += 4;
					sumTwo += 4;
					break;
				case FIVE:
					sumOne += 5;
					sumTwo += 5;
					break;
				case SIX:
					sumOne += 6;
					sumTwo += 6;
					break;
				case SEVEN:
					sumOne += 7;
					sumTwo += 7;
					break;
				case EIGHT:
					sumOne += 8;
					sumTwo += 8;
					break;
				case NINE:
					sumOne += 9;
					sumTwo += 9;
					break;
				case ACE:
					sumOne += 1;
					sumTwo += 11;
					break;
				default:
					sumOne = -1;
					break;
			}
		}
		vals.add(sumOne);
		vals.add(sumTwo);
		if (sumTwo > 21 || sumOne == sumTwo) {
			vals.removeLast();
		}
		return vals;
	}
	public HandAssessment assessHand(List<Card> hand) {
		if (hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		} else if (hand.size() == 2 && possibleHandValues(hand).contains(21)) {
			return HandAssessment.NATURAL_BLACKJACK;
		} else if (possibleHandValues(hand).getFirst() > 21) {
			return HandAssessment.BUST;
		} else {
			return HandAssessment.NORMAL;
		}
	}
	public GameResult gameAssessment() {
		int val = possibleHandValues(player.getHand()).getFirst();
		int valAce = possibleHandValues(player.getHand()).getLast();
		int dealerVal = possibleHandValues(dealer.getHand()).getFirst();
		int dealerValAce = possibleHandValues(dealer.getHand()).getLast();

		switch (assessHand(player.getHand())) {
			case NATURAL_BLACKJACK:
				if (assessHand(dealer.getHand()).equals(HandAssessment.NATURAL_BLACKJACK)) {
					return GameResult.PUSH;
				} else {
					dealer.setMoney(dealer.currentMoney() - (1.5 * player.currentBet()));
					if (dealer.currentMoney() < 0) {
						return GameResult.HOUSE_BUST;
					}
					return GameResult.NATURAL_BLACKJACK;
				}
			case BUST:
				dealer.setMoney(dealer.currentMoney() + player.currentBet());
				return GameResult.PLAYER_LOST;
			case NORMAL:
				if (assessHand(dealer.getHand()).equals(HandAssessment.BUST)) {
					dealer.setMoney(dealer.currentMoney() - player.currentBet());
					if (dealer.currentMoney() < 0) {
						return GameResult.HOUSE_BUST;
					}
					return GameResult.PLAYER_WON;
				} else {
					if (val > dealerVal || valAce > dealerValAce) {
						dealer.setMoney(dealer.currentMoney() - player.currentBet());
						if (dealer.currentMoney() < 0) {
							return GameResult.HOUSE_BUST;
						}
						return GameResult.PLAYER_WON;
					} else if (val < dealerVal || valAce < dealerValAce) {
						dealer.setMoney(dealer.currentMoney() + player.currentBet());
						return GameResult.PLAYER_LOST;
					} else {
						return GameResult.PUSH;
					}
				}
			case INSUFFICIENT_CARDS:
			default:
				return null;
		}
	}
	public boolean dealerShouldTakeCard() {
		Integer val = possibleHandValues(dealer.getHand()).getFirst();
		Integer valAce = possibleHandValues(dealer.getHand()).getLast();
		if (valAce < 17) {
			return true;
		} else if (valAce > 17) {
			return false;
		} else {
			return val.equals(7);
		}
	}
	public void discardHand() {
		discard.addCards(player.getHand());
		discard.addCards(dealer.getHand());
		player.removeCards(player.getHand());
		dealer.removeCards(dealer.getHand());
	}
	public boolean keepPlaying() {
		Scanner query = new Scanner(System.in);
		String str = "";

		System.out.println("Would you like to keep playing? Please say yes or no.");
		do {
			try {
				str = query.nextLine();
				if (!str.equalsIgnoreCase("yes") && !str.equalsIgnoreCase("no")) {
					System.err.println("Error, input must be yes or no.");
				}
			} catch (InputMismatchException e) {
				System.err.println("Error, input must be a word.");
			}
		} while (!str.equalsIgnoreCase("yes") && !str.equalsIgnoreCase("no"));

        return str.equalsIgnoreCase("yes");
	}
	public boolean getBet() {
		Scanner inputBet = new Scanner(System.in);

		do {
			try {
				player.setBet(inputBet.nextInt());
				switch (player.currentBet()) {
					case 5:
					case 10:
					case 50:
					case 100:
					case 500:
						break;
					default:
						System.err.println("Error, number must be one of the aforementioned values.");
						break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Error, must be a natural number.");
			} finally {
				inputBet.nextLine();
			}
		} while (player.currentBet() != 500 && player.currentBet() != 100 && player.currentBet() != 50 && player.currentBet() != 10 && player.currentBet() != 5);
		player.setMoney(player.currentMoney() - player.currentBet());

		if (player.currentMoney() < 0) {
			System.out.println("Player, you have no money to bet, you're disqualified.");
			return false;
		} else {
			return true;
		}
	}
	public boolean dealerBlackjackCheck() {
		HandAssessment dealerAssess = assessHand(dealer.getHand());
		HandAssessment playerAssess = assessHand(player.getHand());

		if (dealerAssess == HandAssessment.NATURAL_BLACKJACK) {
			dealer.getHand().getLast().flip();
			if (playerAssess == HandAssessment.NATURAL_BLACKJACK) {
				player.setMoney(player.currentMoney() + player.currentBet());
				System.out.println(GameResult.PUSH);
			} else {
				System.out.println(GameResult.PLAYER_LOST);
			}
			return true;
		}

		return false;
	}
	public boolean playerTurn() {
		Scanner inputHit = new Scanner(System.in);
		String hit = inputHit.nextLine();
		boolean busted = false;

		while (hit.equalsIgnoreCase("hit")) {
			takeCard(player);
			showCards();
			if (assessHand(player.getHand()) == HandAssessment.BUST) {
				System.out.println(GameResult.PLAYER_LOST);
				busted = true;
				break;
			} else {
				if (possibleHandValues(player.getHand()).contains(21)) {
					break;
				}
			}
			System.out.println("Hit or Stay?");
			hit = inputHit.nextLine();
		}

		return busted;
	}
	public void showCards() {
		System.out.printf("Player: %s\n", simpleHand(player.getHand()));
		System.out.printf("Dealer: %s\n", simpleHand(dealer.getHand()));
	}

	public static void main(String[] args) {
		// Initialization of game
		Blackjack game = new Blackjack();

		do {
			// Introduction and obtaining bet
			System.out.println("Hi, welcome to Blackjack! Please place your bet in $5, $10, $50, $100, or $500 increments:");
			if (!game.getBet()) {
				break;
			}
			System.out.printf("Current balance: Player - %.2f, House - %.2f\n", game.getPlayer().currentMoney(), game.getDealer().currentMoney());

			// Initial dealing of cards
			System.out.println("Dealing initial cards to player and dealer, respectively.");
			game.initialDeal();
			// Initial showing of cards
			game.showCards();

			// Some rules stipulate that there is an automatic checking for blackjack, of the dealer's hand, after the
			// initial dealing.
			System.out.println("Checking for blackjack");
			if (game.dealerBlackjackCheck()) {
				game.discardHand();
				if (game.keepPlaying()) {
					continue;
				} else {
					break;
				}
			}
			System.out.println("No blackjack found");

			// Player's turn. Will continue hitting, until the player busts, or the player chooses to stay.
			System.out.println("Player, it is your turn.");
			System.out.println("Would you like to hit or stay?");
			if (game.playerTurn()) {
				game.discardHand();
				if (game.keepPlaying()) {
					continue;
				} else {
					break;
				}
			}

			// Dealer's turn. Will continue hitting, until dealer reaches 17.
			System.out.println("Dealer's turn");
			game.getDealer().getHand().getLast().flip();
			game.showCards();
			while (game.dealerShouldTakeCard()) {
				game.takeCard(game.getDealer());
				game.showCards();
			}

			// Evaluate the game at the end of the player's and dealer's turn.
			switch (game.gameAssessment()) {
				case NATURAL_BLACKJACK:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + (1.5 * (2 + game.getPlayer().currentBet())));
					System.out.println(GameResult.NATURAL_BLACKJACK);
					break;
				case PLAYER_WON:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + (2 * game.getPlayer().currentBet()));
					System.out.println(GameResult.PLAYER_WON);
					break;
				case PUSH:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + game.getPlayer().currentBet());
					System.out.println(GameResult.PUSH);
					break;
				case PLAYER_LOST:
					System.out.println(GameResult.PLAYER_LOST);
					break;
				case HOUSE_BUST:
					System.out.println(GameResult.HOUSE_BUST);
					break;
				default:
					System.err.println("Error, this should never be reached. Please restart the game.");
					break;
			}
			game.discardHand();
			if (!game.keepPlaying()) {
				break;
			}
		// Infinite loop, as a game runs until the user terminates or a fault is reached.
		} while(true);

		System.out.println("Thank you for playing Blackjack!");
	}
}
