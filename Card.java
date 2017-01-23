package PokerTools;

class Card {
	private int rank;
	private int suit;

	void setSuit(int suit) {
		this.suit = suit;
	}

	void setRank(int rank) {
		this.rank = rank;
	}

	int getSuit() {
		return suit;
	}

	int getRank() {
		return rank;
	}

	static String getFormattedRank(int rank) {
		String r;
		switch (rank) {
			case 9: {
				r = "J";
				break;
			}
			case 10: {
				r = "Q";
				break;
			}
			case 11: {
				r = "K";
				break;
			}
			case 12: {
				r = "A";
				break;
			}
			default:
				r = Integer.toString(rank + 2);
		}
		return r;
	}

	static String getFormattedSuit(int suit) {
		String s = null;
		switch (suit) {
			case 0: {
				s = "H";
				break;
			}
			case 1: {
				s = "S";
				break;
			}
			case 2: {
				s = "C";
				break;
			}
			case 3: {
				s = "D";
				break;
			}
		}
		return s;
	}

	String getFormattedCard() {
		String r = getFormattedRank(rank);
		String s = getFormattedSuit(suit);
		return r + s;
	}
}
