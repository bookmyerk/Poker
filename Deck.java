package PokerTools;

class Deck {
	static private boolean[][] isMissing = new boolean[13][4];

	static private int hackCnt;

	static Card getCard(){
		int rank;
		int suit;
/*
		int [] hackRanks = new int[]{12,8,0,0,1,4,2,7,3,10};
		int [] hackSuits = new int[]{1,3,0,3,3,3,0,3,3,3};

		rank = hackRanks[hackCnt];
		suit = hackSuits[hackCnt];
		isMissing[rank][suit] = true;
		hackCnt++;
*/
		do {
			rank = (int)(Math.random() * 13);
			suit = (int)(Math.random() * 4);
		} while (isMissing[rank][suit]);
		isMissing[rank][suit] = true;

		Card card = new Card();
		card.setRank(rank);//13 possible: 0 thru 12 representing 2 - 14 (11 Jack - 14 Ace)
		card.setSuit(suit);//4 possible: 0 thru 3 representing H (heart) S (spade) C (club) D (diamond)
				
		return card;
		}

		static void listCardsMissing(){
			for (int i=0;i<isMissing.length;i++) {
				for (int j=0;j<isMissing[i].length;j++){
					if(isMissing[i][j]){
						System.out.println(i + "," + j);
					}
				}
			}
		}
}

