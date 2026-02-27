package com.game.slotgame;

import java.util.Scanner;


public class SlotGame {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Write your total playing Ammount");
        
        double mainwallet = input.nextDouble();
        
        System.out.println("Input your bet ammount");
        double betAmmount = input.nextDouble();
        
        System.out.println("Your Total Ammount: "+mainwallet);
        
        System.out.println("Your Bet Ammount: "+betAmmount);

    }
}
