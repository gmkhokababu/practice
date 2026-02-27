package com.game.slotgame;

import java.util.Scanner;


public class SlotGame {
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        
        System.out.println("Write your total playing Ammount");
        
        double mainwallet = input.nextDouble();
        
        System.out.println("Input your bet ammount");
        double betAmmount = input.nextDouble();
        
        System.out.println("Your Total Ammount: "+mainwallet);
        
        System.out.println("Your Bet Ammount: "+betAmmount);
        
        System.out.println("Do you want to spin if yes input 1 or input 0");
        int spin=input.nextInt();
        play(spin,mainwallet,betAmmount);
    }
    public static void play(int spin,double mainwallet, double betAmmount){
           Slot slot = new Slot();
        
        double totalWin=0;
        Spin spins= new Spin();
            if(spin<0||spin>1){
            System.out.println("Invalid Input! Input 1 for yes 0 for no");
            spin=input.nextInt();
            play(spin,mainwallet,betAmmount);
        }else if(spin==1){
            while(spin==1){
                slot.setBetAmmount(betAmmount);
                slot.setMainWallet(mainwallet);
                Slot s=spins.spin(slot,spin);
                mainwallet=s.mainWallet;
                betAmmount=s.betAmmount;
                totalWin+=s.winAmmount;
                System.out.println("You own: "+s.winAmmount+"\nYour Balance is now: "+mainwallet);
                System.out.println("Do you want to play again? Input 1 for yes 0 for no");
                spin=input.nextInt();
            }
            if(spin==0){
                System.out.println("You total own: "+totalWin);
                System.out.println("Your Balance is now: "+mainwallet);
            }
        }
    }
}
