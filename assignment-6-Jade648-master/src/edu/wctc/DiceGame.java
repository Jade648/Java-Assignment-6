package edu.wctc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DiceGame {

    private final List <Player> players;
    private final List<Die> dice;
    private final int maxRolls;
    private Player currentPlayer;

    public DiceGame(int players, int countDice, int maxRolls) {
this.players = new ArrayList<>();
dice = new ArrayList<>();
this.maxRolls = maxRolls;

 for(int i = 0; i< players;i++){
this.players.add(new Player());
this.players.add(new Player());
 }
 for (int i=0;i<countDice;i++){
     this.dice.add(new Die());
 }
 if(players<2){
     throw  new IllegalArgumentException();
 }
    }
private boolean allDiceHeld (){
    return dice.stream().map(Die :: isBeingHeld).allMatch(Held -> Held == true );

    }
    public boolean autoHold (int faceValue) {
        if (isHoldingDie(faceValue)) {
            return true;
        }
        if (dice.stream().filter(Die -> Die.getFaceValue()== faceValue && ! Die.isBeingHeld()).findFirst().isPresent()) {
         dice.stream().filter(Die -> Die.getFaceValue()== faceValue && ! Die.isBeingHeld()).findFirst().ifPresent(Die::holdDie);
        return true;
        } else return false;
    }
public boolean currentPlayerCanRoll(){
        if (currentPlayer.getRollsUsed()< maxRolls && !allDiceHeld()){
return true;
        } else return false;
}
    public int	getCurrentPlayerNumber(){
      return currentPlayer.getPlayerNumber();
    }
    public int getCurrentPlayerScore (){
        return currentPlayer.getScore();
    }
    public String getDiceResults (){
       return dice.stream().map(Die :: toString).collect(Collectors.joining(" "));
    }
    public String getFinalWinner () {
        return Collections.max(players,Comparator.comparingInt(Player::getWins)).toString();
    }
        public String getGameResults () {
            List<Player> playerList = players.stream().sorted(Comparator.comparingInt(Player::getScore).reversed()).collect(Collectors.toList());
            int Score = playerList.get(0).getScore();
            playerList.get(0).addWin();
            playerList.stream().filter(l -> l.getScore() < Score).forEach(Player::addLoss);
            return players.stream().map(Player::toString).collect(Collectors.joining(" "));
        }
        private boolean isHoldingDie (int faceValue){
            return dice.stream().filter(Die -> Die.getFaceValue() == faceValue && Die.isBeingHeld()).findFirst().isPresent();
        }
        public boolean nextPlayer (){
        if (players.indexOf(currentPlayer)+1<players.size()){
            currentPlayer = players.get(players.indexOf(currentPlayer)+1);
            return true;
            }else return false; }
            public	void playerHold	(char dieNum){
                dice.stream().filter(Die -> Die.getDieNum()== dieNum && Die.isBeingHeld()).findFirst().isPresent();
            }
            public void resetPlayer(){
                players.stream().forEach(Player::resetPlayer);
                }
                public void resetDice (){
        dice.forEach(Die::resetDie);
                }
                public void rollDice(){
        currentPlayer.roll();
dice.stream().forEach(Die :: rollDie);
                }
                public void scoreCurrentPLayer() {
                    boolean six = false, five = false, four = false;
                    int score = 0;
                    for (Die d : dice) {
                        if (d.getFaceValue() == 6) {
                            if (six) {
                                score += 6;
                            } else six = true;
                        } else if (d.getFaceValue() == 5) {
                            if (five) {
                                score += 5;
                            } else five = true;
                        } else if (d.getFaceValue() == 4) {
                            if (four) {
                                score += 4;
                            } else four = true;
                        } else score += d.getFaceValue();
                        if (six && five && four) {
                            currentPlayer.setScore(score);
                        }
                    }
                }
        public void startNewGame(){
currentPlayer = players.get(0);
                       resetPlayer();
        }
            }




    




