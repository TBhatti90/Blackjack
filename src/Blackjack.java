import java.util.ArrayList;

public class Blackjack {
	public class Player {
		private double money;
		private double bet;
		private ArrayList<Character> hand;

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
		public ArrayList<Character> getHand() {
			return null;
		}
	}

	ArrayList<Player> players;
	Player dealer;
	ArrayList<Character> deck;

	public Blackjack() {
		dealer = new Player("dealer");
		players = new ArrayList<Player>(1);
		deck = new ArrayList<Character>(312);
	}



	public static void main(String[] args) {
		Blackjack game = new Blackjack();
	}
}
