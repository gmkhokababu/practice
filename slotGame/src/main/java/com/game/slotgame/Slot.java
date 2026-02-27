
package com.game.slotgame;


public class Slot {
    
    double mainWallet;
    double betAmmount;
    double winAmmount;
    
    
    public Slot (){
        
    }

    public Slot(double mainWallet, double betAmmount, double winAmmount) {
        this.mainWallet = mainWallet;
        this.betAmmount = betAmmount;
        this.winAmmount = winAmmount;
    }

    public double getMainWallet() {
        return mainWallet;
    }

    public void setMainWallet(double mainWallet) {
        this.mainWallet = mainWallet;
    }

    public double getBetAmmount() {
        return betAmmount;
    }

    public void setBetAmmount(double betAmmount) {
        this.betAmmount = betAmmount;
    }

    public double getWinAmmount() {
        return winAmmount;
    }

    public void setWinAmmount(double winAmmount) {
        this.winAmmount = winAmmount;
    }

    @Override
    public String toString() {
        return "Slot{" + "mainWallet=" + mainWallet + ", betAmmount=" + betAmmount + ", winAmmount=" + winAmmount + '}';
    }
    
    
}
