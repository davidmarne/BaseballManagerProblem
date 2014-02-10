package baseballmanagerproblem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * @author davemarne
 */

public class BaseballManagerProblem {

    public static void main(String[] args) throws IOException {
        
        ArrayList<Player> positions[] = new ArrayList[9];
        File files[] = {new File("catcher.txt"), new File("first.txt"), new File("second.txt"),
            new File("shortstop.txt"), new File("third.txt"), new File("left.txt"), 
            new File("center.txt"), new File("right.txt"), new File("pitcher.txt")};
        String[] posNames = {"catcher","first","second","shortstop","third","left","center","right","pitcher"};
        
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.csv"));
       
        
        for (int j = 0; j < positions.length; j++) {
            positions[j] = new ArrayList();
            try {
                Scanner s = new Scanner(files[j]);
                while (s.hasNextLine()) {
                    String playerInfo[] = s.nextLine().split(" ");
                    Player pl = new Player(Integer.parseInt(playerInfo[1]), Integer.parseInt(playerInfo[2]), playerInfo[0]);
                    positions[j].add(pl);

                }
            } catch (Exception e) {
                System.out.print("FAILED");
                System.exit(1);
            }
        }
        
        int cap = 1000000;
        int opt[][] = new int[positions.length][(cap / 100000) + 1];
        int lookup[][] = new int[positions.length][(cap / 100000) + 1];
        
        
        for(int x = 0; x <= cap; x += 100000){
            opt[positions.length-1][x / 100000] = 0;
            lookup[positions.length-1][x / 100000] = 0;
            ArrayList<Player> l = positions[positions.length-1];
            for(int k = 0; k < l.size(); k++){
                Player p = positions[positions.length-1].get(k);
                if(p.getPrice() <= x && p.getVorp() > opt[positions.length-1][x / 100000]){
                    opt[positions.length-1][x / 100000] = p.getVorp();
                    lookup[positions.length-1][x / 100000] = k+1;
                }
            }
            bw.write(String.format("X = %d, Position = %s\n", x, posNames[8]));
            printTable(bw, opt, lookup, posNames);
        }
        
        for(int i = (positions.length - 2); i >= 0; i--){
            for(int x = 0; x <= cap; x += 100000){
                opt[i][x / 100000] = opt[i+1][x / 100000];
                lookup[i][x / 100000] = 0;
                ArrayList<Player> l = positions[i];
                for(int k = 0; k < l.size(); k++){
                    Player p = l.get(k);
                    if(p.getPrice() <= x && (opt[i+1][(x / 100000)- (p.getPrice() / 100000)] + p.getVorp()) > opt[i][x / 100000]){
                        opt[i][x / 100000] = opt[i+1][(x / 100000)- (p.getPrice() / 100000)] + p.getVorp();
                        lookup[i][x / 100000] = k+1;
                    }
                }
                bw.write(String.format("X = %d, Position = %s\n", x, posNames[i]));
                printTable(bw, opt, lookup, posNames);
            }
        }
        
        int amt = cap;
        for(int i = 0; i < positions.length; i++){
            int k = lookup[i][amt / 100000];
            if(k != 0){
                bw.write(String.format("\nSign %s for a vorp of %d and a price of $%d\n", positions[i].get(k-1).getName(), positions[i].get(k-1).getVorp(), positions[i].get(k-1).getPrice()));
                amt = amt - positions[i].get(k-1).getPrice();
            }
        }
         bw.close();
    }
    
    private static void printTable(BufferedWriter bw, int[][] opt, int[][] lookup, String[] posNames) throws IOException{
        bw.write(" ,");
        for (int i = 0; i < opt[0].length; i++){
            bw.write(String.format("$%d,", i*100000));
            
        }
        bw.write("\n");
        for (int i = 0; i < opt.length; i++) {
            bw.write(posNames[i]+",");
            for (int j = 0; j < opt[i].length; j++) {
                String s = String.format("%d,", opt[i][j]);
                bw.write(s);
            }
            bw.write("\n");
        }

    }
}
