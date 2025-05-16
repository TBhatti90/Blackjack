import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public static void main(String[] args) {
		Blackjack game = new Blackjack();
		boolean busted = false;
		Scanner input = new Scanner(System.in);
		boolean keepPlaying = true;

		do {
			System.out.println("Hi, welcome to Blackjack! Please place your bet in $5, $10, $50, $100, or $500 increments:");
			game.getPlayer().setBet(input.nextInt());
			input.nextLine();
			game.getPlayer().setMoney(game.getPlayer().currentMoney() - game.getPlayer().currentBet());
			if (game.getPlayer().currentMoney() < 0) {
				System.out.println("Player, you have no money to bet, you're disqualified.");
				break;
			}
			System.out.println("Dealing initial cards to player and dealer, respectively.");
			game.initialDeal();
			System.out.println(game.getPlayer());
			System.out.println(game.getDealer());

			System.out.println("Checking for blackjack");
			HandAssessment dealerAssess = game.assessHand(game.getDealer().getHand());
			HandAssessment playerAssess = game.assessHand(game.getPlayer().getHand());
			if (dealerAssess == HandAssessment.NATURAL_BLACKJACK) {
				game.getDealer().getHand().getLast().flip();
				if (playerAssess == HandAssessment.NATURAL_BLACKJACK) {
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + game.getPlayer().currentBet());
					System.out.println(GameResult.PUSH);
				} else {
					System.out.println(GameResult.PLAYER_LOST);
				}
				game.getDiscard().addCards(game.getPlayer().getHand());
				game.getDiscard().addCards(game.getDealer().getHand());
				game.getPlayer().removeCards(game.getPlayer().getHand());
				game.getDealer().removeCards(game.getDealer().getHand());
				continue;
			}
			System.out.println("Player, it is your turn.");
			System.out.println("Would you like to hit or stay?");
			String hit = input.nextLine();
			while (hit.equalsIgnoreCase("hit")) {
				game.takeCard(game.getPlayer());
				System.out.println(game.getPlayer());
				System.out.println(game.getDealer());
				if (game.assessHand(game.getPlayer().getHand()) == HandAssessment.BUST) {
					System.out.println(GameResult.PLAYER_LOST);
					busted = true;
					break;
				}
				System.out.println("Hit or Stay?");
				hit = input.nextLine();
			}
			if (busted) {
				continue;
			}
			game.getDealer().getHand().getLast().flip();
			while (game.dealerShouldTakeCard()) {
				game.takeCard(game.getDealer());
				System.out.println(game.getPlayer());
				System.out.println(game.getDealer());
			}
			switch (game.gameAssessment()) {
				case NATURAL_BLACKJACK:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + (1.5 * (2 + game.getPlayer().currentBet())));
					break;
				case PLAYER_WON:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + (2 * game.getPlayer().currentBet()));
					break;
				case PUSH:
					game.getPlayer().setMoney(game.getPlayer().currentMoney() + game.getPlayer().currentBet());
					break;
				case PLAYER_LOST:
					break;
				case HOUSE_BUST:
					break;
				default:
					break;
			}
			game.getDiscard().addCards(game.getPlayer().getHand());
			game.getDiscard().addCards(game.getDealer().getHand());
			game.getPlayer().removeCards(game.getPlayer().getHand());
			game.getDealer().removeCards(game.getDealer().getHand());

			System.out.println("Would you like to keep playing?");
			String response = input.nextLine();
			if (response.equalsIgnoreCase("no")) {
				keepPlaying = false;
			}
		} while(keepPlaying);
		System.out.println("Thank you for playing Blackjack!");
		input.close();
	}
}
