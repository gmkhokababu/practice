
package com.game.slotgame;

import com.game.slotgame.Slot;
import java.util.Random;

public class Spin {
    Slot slot = new Slot();
    public static Slot spin(Slot slot, int spin){
        double bet=slot.betAmmount;
        double main=slot.mainWallet;
        double win=slot.winAmmount;
        if(spin==1 && bet<main){
            main-=bet;
            Random random = new Random();
            int num = random.nextInt(100)+1;
            
            System.out.println("Selected number is: "+num);
            
            if(num==1){
                System.out.println("Congratulation you got Jackpot 10X");
                win=bet*10;
                main+=win;
            }else if(num<=30){
                System.out.println("Congratulation you got 2X");
                win=bet*2;
                main+=win;
            }else if(num<=50){
                System.out.println("Congratulation you got 1X");
                win=bet*1;
                main+=win;
            }else{
                System.out.println("Better luck next time");
            }
        }
        slot.setBetAmmount(bet);
        slot.setMainWallet(main);
        slot.setWinAmmount(win);
        return slot;
    }
    
}
