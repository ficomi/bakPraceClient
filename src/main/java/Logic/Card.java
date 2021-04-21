/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 * Tato třída reprezentuje kartu na poli.
 * @author Miloslav Fico
 */
public class Card {
    private final int VALUE;

    private boolean flipped;
    private boolean clickable;
    
    
    public Card(int value,boolean flipped,boolean clickable){
        this.VALUE = value;
       this.clickable= clickable;
        this.flipped=flipped;
    }
    
    public int getValue(){
    return VALUE;
    }
    

    
    public boolean isFlipped(){
    return flipped;
    }
    
    public void setFlip(boolean bool){
        flipped = bool;
    }
    
    public boolean isClickable(){
    return clickable;
    }
    public void setClickable(boolean bool){
    clickable = bool;
    }
}
