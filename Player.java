package pokerTools;

import java.util.Arrays;
import java.util.Comparator;

class Player {
	private String[] hand = new String[5];
	private int cardCount = 0;
	private Card[] cards = new Card[5];
	private String playerName;
	private int[] suits = new int[5];
	private int[] ranks = new int[5];
	private int[] rankScores = new int[13];
	
	ScoreSheet GetScoreSheet(){
		int i = 0;
		for (Card c: cards) {
			int CardRank = c.getRank();
			int CardSuit = c.getSuit();
			ranks[i] = CardRank;
			suits[i] = CardSuit;
			i++;
		}

		final int[] rawRanks = new int[cardCount];
		System.arraycopy(ranks,0,rawRanks,0,ranks.length);

		final int[] rawSuits = new int[cardCount];
		System.arraycopy(suits,0,rawSuits,0,suits.length);

		Integer[] indexSeqs  = new Integer[cardCount];
		for(i=0;i < cardCount;i++){//initialize for as many cards
			indexSeqs[i] = i;
		}
		Arrays.sort(indexSeqs, new Comparator<Integer>() {
			public int compare(Integer idx1, Integer idx2) {
				return Integer.compare(rawRanks[idx1], rawRanks[idx2]);
			}
		});

		i = 0;
		for(int iSeq: indexSeqs){
			ranks[i] = rawRanks[iSeq];
			suits[i] = rawSuits[iSeq];
			i++;
		}

		for (int f=0;f<cardCount;f++){
			rankScores[cards[f].getRank()] += 1;//offset making 2 be 0 card index value
		}

		ScoreSheet ss = new ScoreSheet(cardCount);
		ss.playerName = this.playerName;
		ss.rankLevel = 1;
		i = 0;
		for (int x: rankScores){
			switch (x){
				case 4:
					ss.rankLevel = 8;//Four of a kind
					ss.fourKind = i;
					break;
				case 3:
					ss.threeKind = i;
					if(ss.rankLevel == 2){
						ss.rankLevel = 7;//Full house
					}
					else {
						ss.rankLevel = 4;//Three of a kind
					}
					break;
				case 2:
					if (ss.rankLevel == 2){
						ss.rankLevel = 3;//Two pair
						ss.lowPair = ss.highPair;
						ss.highPair = i;
					}
					else if(ss.rankLevel == 4){
						ss.rankLevel = 7;//Full house
						ss.highPair = i;
					}
					else {
						ss.rankLevel = 2;//Pair
						ss.highPair = i;
					}
					break;
				case 1:
					ss.uniqueSingles++;
				}
			i++;
		}
		if (ss.uniqueSingles == cardCount){
			if (ranks[cardCount - 1] == cardCount - 1 + ranks[0]) {
				ss.rankLevel = 5;//Straight
			}
			else {
				if (ranks[cardCount - 1] == 12){//Ace low straight
					if (ranks[cardCount - 2] == 3){
						int [] aceLowStraightRanks = new int[cardCount];
						int [] aceLowStraightSuits = new int[cardCount];
						for (i=0;i < cardCount; i++){
							if (i < cardCount - 1) {
								aceLowStraightSuits[i + 1] = suits[i];
								aceLowStraightRanks[i + 1] = ranks[i];
							}
							else {
								aceLowStraightSuits[0] = suits[i];
								aceLowStraightRanks[0] = ranks[i];
							}
						}
						suits = aceLowStraightSuits;
						ranks = aceLowStraightRanks;

						ss.rankLevel = 5;//Straight
					}
				}
            }
		}
        int[] tempSuits = new int[cardCount];
        System.arraycopy(suits,0,tempSuits,0,suits.length);
        Arrays.sort(tempSuits);
        if (tempSuits[0] == tempSuits[cardCount - 1]) {
            if (ss.rankLevel == 5) {
                ss.rankLevel = 9;//Straight Flush
            } else {
                ss.rankLevel = 6;//Flush
            }
        }
		i = 0;
        for (Card c: cards) {
            c.setRank(ranks[i]);
            c.setSuit(suits[i]);
            hand[i] = c.getFormattedCard();
            i++;
        }
        ss.ranks = ranks;
        ss.hand = hand;
        ss.cardsHeld = String.join(" ",hand);
		return ss;
	}
	
	Player(String s) {
		playerName = s;
	}

	String getPlayerName() {
		return playerName;
	}
	
	void takeCard(Card c) {
		cards[cardCount] = c;
		cardCount++;
	}
}
