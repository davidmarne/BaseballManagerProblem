/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baseballmanagerproblem;

/**
 *
 * @author davemarne
 */
public class Player {
   private int price;
   private int vorp;
   private String name;
   
   public Player(int price, int vorp, String name){
       this.name = name;
       this.price = price;
       this.vorp = vorp;
   } 
   
   public int getPrice(){
       return this.price;
   }
   public int getVorp(){
       return this.vorp;
   }
   public String getName(){
       return this.name;
   }
}
