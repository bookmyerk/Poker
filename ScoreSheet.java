package PokerTools;

class ScoreSheet {
	String playerName;
	int cardCount = 0;
	int[] ranks;
	int highPair = -1;
	int lowPair = -1;
	int threeKind = -1;
	int fourKind = -1;
	int rankLevel = -1;
	int uniqueSingles = 0;
	int winningSingle = -1;
	String[] hand;
	String cardsHeld;

	ScoreSheet(int cardCount) {
		this.cardCount = cardCount;
		this.ranks = new int[cardCount];
	}
}
