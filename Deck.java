package pokerTools;

class Deck {
	static private boolean[][] isMissing = new boolean[13][4];

//	static private int hackCnt;

	static Card getCard(){
		int rank;
		int suit;
/*
		int [] hackRanks = new int[]{4,1,2,5,0,10,1,9,0,10};
		int [] hackSuits = new int[]{2,2,3,1,1,0,0,3,0,3};

		rank = hackRanks[hackCnt];
		suit = hackSuits[hackCnt];
		isMissing[rank][suit] = true;
		hackCnt++;
*/
///*
		do {
			rank = (int)(Math.random() * 13);
			suit = (int)(Math.random() * 4);
		} while (isMissing[rank][suit]);
		isMissing[rank][suit] = true;
//*/
		Card card = new Card();
		card.setRank(rank);//13 possible: 0 thru 12 representing 2 - 14 (11 Jack - 14 Ace)
		card.setSuit(suit);//4 possible: 0 thru 3 representing H (heart) S (spade) C (club) D (diamond)

		return card;
		}

}

