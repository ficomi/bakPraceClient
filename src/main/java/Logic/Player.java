/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 * Tato třída reprezentuje hráče.
 * @author Miloslav Fico
 */


public class Player {
    
    private String name;
    private int elo;
    private int score=0;
    private final String PASSWORD;

    public Player(String name) {
        this.name = name;
        PASSWORD = null;
    }
    
    public Player(String name,String password) {
        this.name = name;
        this.PASSWORD = password;
    }

    public synchronized void setScore(int score){
    this.score=score;
    }
    
    public synchronized void addScore() {
        score++;
    }

   
    public synchronized String getName() {
       return name;
    }

    
    public synchronized int getScore() {
        return score;
    }

    
    public synchronized void setName(String name) {
        this.name=name;
    }
    
    public synchronized void setElo(int elo){
    this.elo=elo;
    }
    
    public synchronized int getElo(){
    return elo;
    }
    
    public synchronized String getPassword()
    {
    return PASSWORD;         
    }        
}
