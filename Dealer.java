package PokerTools;

import java.util.Arrays;

public class Dealer {

	private static ScoreSheet highCardEval(int cCnt, ScoreSheet[] scoreSheets){
		ScoreSheet rtnSheet = null;
		int rankCount;
		int finalSheetUpperBound;
		int max;
		for(rankCount = scoreSheets[0].cardCount - 1;rankCount > -1;rankCount--) {
			max = -1;//set below threshold of any card value since 0 is 2 due to array index tracking the value
			finalSheetUpperBound = 0;
			ScoreSheet[] finalSheets = new ScoreSheet[cCnt];
			for(ScoreSheet ss: scoreSheets) {
			    if (ss.ranks[rankCount] == max){//a tie exist, so add in the tying sheet
                    finalSheetUpperBound++;
                }
				if (ss.ranks[rankCount] >= max) {
					max = ss.ranks[rankCount];
					finalSheets[finalSheetUpperBound] = ss;
				}
			}
			if(finalSheetUpperBound == 0){
				rtnSheet = finalSheets[0];
				rtnSheet.winningSingle = max;
				break;
			}
		}
		return rtnSheet;
	}

public static void main(String[] args) {
	String outputLine;
	int playerCount;
	int i = 0;
	playerCount = args.length;
	Player[] players = new Player[playerCount];
	for (String s: args) {
		players[i] = new Player(s);
		i++;
	}
	for(i=1;i<6;i++){
		for (Player p: players) {
			Card c = Deck.getCard();
            outputLine = p.getPlayerName() + " is dealt " + c.getFormattedCard();
            int dealRank = c.getRank();
            int dealSuit = c.getSuit();
//          outputLine += " (internally known as " + dealRank + "," + dealSuit + ")";
            System.out.println(outputLine);
			p.takeCard(c);
		}
	}
    outputLine = "\n";
	ScoreSheet[] sSheets = new ScoreSheet[playerCount];
	for (i=0;i<playerCount;i++) {
		sSheets[i] = players[i].GetScoreSheet();
		outputLine += players[i].getPlayerName() + ": ";
		outputLine += sSheets[i].cardsHeld + " ";
	}
	System.out.println(outputLine);
	outputLine = "";
	int[] rankLevels = new int[playerCount];
	i = 0;
	for (ScoreSheet ss: sSheets) {
		rankLevels[i] = ss.rankLevel;
		i++;
	}
	int contenderCount = 0;
	Arrays.sort(rankLevels);
	int maxRankLevel = rankLevels[playerCount - 1];//highest rank is last
	for(ScoreSheet ss: sSheets){//count contenders to initialize maxLevelSheets(below)
		if(maxRankLevel == ss.rankLevel) {
			contenderCount++;
		}
	}
	ScoreSheet[] maxLevelSheets = new ScoreSheet[contenderCount];
	i = 0;
	for(ScoreSheet ss: sSheets){//assign the maxLevel Sheets initialized (above)
		if(maxRankLevel == ss.rankLevel) {
			maxLevelSheets[i++] = ss;
		}
	}

	String fmtRank;
	if (contenderCount == 1){
		String playerName = maxLevelSheets[0].playerName;
		int maxIndex = maxLevelSheets[0].cardCount - 1;
		outputLine += playerName + " wins. - with ";
		switch (maxLevelSheets[0].rankLevel){
			case 1:
				outputLine += "high card: " + maxLevelSheets[0].hand[maxIndex];
				break;
			case 2:
				fmtRank = Card.getFormattedRank(maxLevelSheets[0].highPair);
				outputLine += "pair: " + fmtRank;
				break;
			case 3:
				fmtRank = Card.getFormattedRank(maxLevelSheets[0].highPair);
				outputLine += "two pair: " + fmtRank;
				fmtRank = Card.getFormattedRank(maxLevelSheets[0].lowPair);
				outputLine += " " + fmtRank;
				break;
			case 4:
				fmtRank = Card.getFormattedRank(maxLevelSheets[0].threeKind);
				outputLine += "three of a kind: " + fmtRank;
				break;
			case 5:
				outputLine += "straight. ";
				break;
			case 6:
				outputLine += "flush. ";
				break;
			case 7:
				outputLine += "full house.";
				break;
			case 8:
				outputLine += "four of a kind: " + maxLevelSheets[0].fourKind;
				break;
			case 9:
				outputLine += "straight flush ";
				break;
		}
	}
	else {
        //resolve ties based on hand ranking
        outputLine = "Tie.";
        int max;
        int finalSheetCount;
        int finalSheetUpperBound = 0;
        ScoreSheet[] finalSheets;
        ScoreSheet finalSheet;
        String playerName;
        switch (maxRankLevel){
            case 1://high card evaluation
                finalSheet = highCardEval(contenderCount, maxLevelSheets);
                if(finalSheet != null) {//we a have winner
                    fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                    playerName = finalSheet.playerName;
                    outputLine = playerName + " wins. - high card: " + fmtRank;
                }
                break;
            case 2://resolve single pair.
                max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                finalSheetCount = 1;
                finalSheetUpperBound = 0;
                finalSheets = new ScoreSheet[contenderCount];
                for(ScoreSheet ss: maxLevelSheets) {
                    if (ss.highPair == max){//tie exists, so add to array the tying sheet.
                        finalSheetUpperBound++;
                        finalSheetCount++;
                    }
                    if (ss.highPair >= max) {
                        max = ss.highPair;
                        finalSheets[finalSheetUpperBound] = ss;
                    }
                }
                if(finalSheetCount == 1){
                    fmtRank = Card.getFormattedRank(finalSheets[0].highPair);
                    playerName = finalSheets[0].playerName;
                    outputLine = playerName + " wins. - with pair of " + fmtRank;
                }
                else {
                    finalSheet = highCardEval(contenderCount, maxLevelSheets);
                    if(finalSheet != null) {//we a have winner
                        fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                        playerName = finalSheet.playerName;
                        outputLine = playerName + " wins. - with high card: " +	fmtRank;
                    }
                }
                break;
            case 3://Two Pairs
                max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                finalSheetCount = 1;
                finalSheetUpperBound = 0;
                finalSheets = new ScoreSheet[contenderCount];
                for(ScoreSheet ss: maxLevelSheets) {
                    if (ss.highPair == max){//tie exists, so add to array the tying sheet.
                        finalSheetUpperBound++;
                        finalSheetCount++;
                    }
                    if (ss.highPair >= max) {
                        max = ss.highPair;
                        finalSheets[finalSheetUpperBound] = ss;
                    }
                }

                if(finalSheetCount == 1){
                    fmtRank = Card.getFormattedRank(finalSheets[0].highPair);
                    playerName = finalSheets[0].playerName;
                    outputLine = playerName + " wins. - with pair of " + fmtRank;
                }
                else {
                    max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                    finalSheetCount = 1;
                    finalSheetUpperBound = 0;
                    finalSheets = new ScoreSheet[contenderCount];
                    for (ScoreSheet ss : maxLevelSheets) {
                        if (ss.lowPair == max){//tie exists, so add to array the tying sheet.
                            finalSheetUpperBound++;
                            finalSheetCount++;
                        }
                        if (ss.lowPair >= max) {
                            max = ss.lowPair;
                            finalSheets[finalSheetUpperBound] = ss;
                        }
                    }
                    if (finalSheetCount == 1) {
                        fmtRank = Card.getFormattedRank(finalSheets[0].lowPair);
                        playerName = finalSheets[0].playerName;
                        outputLine = playerName + " wins. - with pair of " + fmtRank;
                    } else {
                        finalSheet = highCardEval(contenderCount, maxLevelSheets);
                        if (finalSheet != null) {//we a have winner
                            fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                            playerName = finalSheet.playerName;
                            outputLine = playerName + " wins. - with high card: " + fmtRank;
                        }
                    }
                }
                break;
            case 4:
                max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                finalSheetCount = 1;
                finalSheetUpperBound = 0;
                finalSheets = new ScoreSheet[contenderCount];
                for(ScoreSheet ss: maxLevelSheets) {
                    if (ss.threeKind == max){//tie exists, so add to array the tying sheet.
                        finalSheetUpperBound++;
                        finalSheetCount++;
                    }
                    if (ss.threeKind >= max) {
                        max = ss.threeKind;
                        finalSheets[finalSheetUpperBound] = ss;
                    }
                }

                if(finalSheetCount == 1){
                    fmtRank = Card.getFormattedRank(finalSheets[0].threeKind);
                    playerName = finalSheets[0].playerName;
                    outputLine = playerName + " wins. - with three of a kind " + fmtRank;
                }
                else {
                    finalSheet = highCardEval(contenderCount, maxLevelSheets);
                    if(finalSheet != null) {//we a have winner
                        fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                        playerName = finalSheet.playerName;
                        outputLine = playerName + " wins. - with high card: " +	fmtRank;
                    }
                }
                break;
            case 5:
            case 6:
            case 9:
                finalSheet = highCardEval(contenderCount, maxLevelSheets);
                if(finalSheet != null) {//we a have winner
                    fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                    playerName = finalSheet.playerName;
                    outputLine = playerName + " wins. - with high card: " + fmtRank;
                }
                break;
            case 7:
                max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                finalSheetCount = 1;
                finalSheetUpperBound = 0;
                finalSheets = new ScoreSheet[contenderCount];
                for(ScoreSheet ss: maxLevelSheets) {
                    if (ss.threeKind == max){//tie exists, so add to array the tying sheet.
                        finalSheetUpperBound++;
                        finalSheetCount++;
                    }
                    if (ss.threeKind >= max) {
                        max = ss.threeKind;
                        finalSheets[finalSheetUpperBound] = ss;
                    }
                }

                if(finalSheetCount == 1){
                    fmtRank = Card.getFormattedRank(finalSheets[0].threeKind);
                    playerName = finalSheets[0].playerName;
                    outputLine = playerName + " wins. - with three (from full house): " + fmtRank;
                }
                else {
                    max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                    finalSheetCount = 1;
                    finalSheetUpperBound = 0;
                    finalSheets = new ScoreSheet[contenderCount];
                    for (ScoreSheet ss : maxLevelSheets) {
                        if (ss.highPair == max){//tie exists, so add to array the tying sheet.
                            finalSheetUpperBound++;
                            finalSheetCount++;
                        }
                        if (ss.highPair >= max) {
                            max = ss.highPair;
                            finalSheets[finalSheetUpperBound] = ss;
                        }
                    }
                    if (finalSheetCount == 1) {
                        fmtRank = Card.getFormattedRank(finalSheets[0].highPair);
                        playerName = finalSheets[0].playerName;
                        outputLine = playerName + " wins. - with pair of (from full house): " + fmtRank;
                    }
                    else {
                        finalSheet = highCardEval(contenderCount, maxLevelSheets);
                        if (finalSheet != null) {//we a have winner
                            fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                            playerName = finalSheet.playerName;
                            outputLine = playerName + " wins. - with high card: " + fmtRank;
                        }
                    }
                }
                break;
            case 8:
                max = -1; //set below threshold of any card value since 0 is 2 due to array index tracking the value
                finalSheetUpperBound = 0;
                finalSheetCount = 1;
                finalSheets = new ScoreSheet[contenderCount];
                for(ScoreSheet ss: maxLevelSheets) {
                    if (ss.fourKind == max){//tie exists, so add to array the tying sheet.
                        finalSheetUpperBound++;
                        finalSheetCount++;
                    }
                    if (ss.fourKind >= max) {
                        max = ss.fourKind;
                        finalSheets[finalSheetUpperBound] = ss;
                    }
                }

                if(finalSheetCount == 1){
                    fmtRank = Card.getFormattedRank(finalSheets[0].fourKind);
                    playerName = finalSheets[0].playerName;
                    outputLine = playerName + " wins. - with four of a kind: " + fmtRank;
                }
                else {
                    finalSheet = highCardEval(contenderCount, maxLevelSheets);
                    if(finalSheet != null) {//we a have winner
                        fmtRank = Card.getFormattedRank(finalSheet.winningSingle);
                        playerName = finalSheet.playerName;
                        outputLine = playerName + " wins. - with high card: " +	fmtRank;
                    }
                }
                break;
        }
    }
    System.out.println(outputLine);
}
}