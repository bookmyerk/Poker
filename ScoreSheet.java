package PokerTools;

class ScoreSheet {
	String playerName;
	int cardCount = 0;
	int[] ranks;
	int highPair = 0;
	int lowPair = 0;
	int threeKind = 0;
	int fourKind = 0;
	int rankLevel = 0;
	int uniqueSingles = 0;
	int winningSingle = 0;
	String[] hand;
	String cardsHeld;

	ScoreSheet(int cardCount) {
		this.cardCount = cardCount;
		this.ranks = new int[cardCount];
	}
}
