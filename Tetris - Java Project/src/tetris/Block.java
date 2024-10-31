package tetris;

import java.awt.Color;
import java.util.Random;


public class Block {
    private int [][] shape;
    private Color color;
    private int x,y;
    private int[][][] shapes;
    private int currentRotation;
    
    private Color[] avaiableColor = {Color.ORANGE, Color.BLUE, Color.red, Color.YELLOW, Color.CYAN};
    
    public Block (int[][] s){
        shape = s;
        initShapes();
    }
    
    private void initShapes(){
        shapes = new int[4][][];
        for(int i = 0 ; i < 4 ; i++){
            int r=shape[0].length;
            int c=shape.length;
            
            shapes[i] = new int[r][c];
            
            for (int b = 0 ; b < r ; b++){
                for (int a =0 ; a < c ; a++){
                    shapes[i][b][a] = shape[c-a-1][b];
                }
            }
            shape = shapes[i];
        }
    }
    
    public void spawn(int gridWidth){
        //Ham ramdom giup cho block xuat hien tai 1 vi tri bat ky
        Random r = new Random();
        
        //Random ngau nhien hinh dang co trong chuoi shapes cua khoi 
        this.currentRotation = r.nextInt( shapes.length) ;
        shape = shapes[currentRotation];
        
        //Random vi tri
        y = -getHight();
        this.x = r.nextInt(gridWidth - getWidth());
        //Random mau cua block
        this.color = avaiableColor[ r.nextInt(avaiableColor.length)];
        
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
    
    public int getHight () {
        return shape.length;
    }
    
    public int getWidth(){
        return shape[0].length;
    }
    
    public void setX(int newX){ x = newX; }
    public void setY(int newY){ y = newY; } 

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void moveDown (){
        y++;
    }
    
    public void moveLeft (){
        x--;
    }
    
    public void moveRight(){
        x++;
    }
    
    public void rotate(){
        currentRotation++;
        if (currentRotation > 3 ) currentRotation = 0 ;
        shape = shapes[currentRotation];
    }
    
    public int getBottomEdge (){return y + getHight();}
    
    public int getLeftEdge (){
        return x;
    } 
    public int getRightEdge (){
        return x + getWidth();
    }
}
