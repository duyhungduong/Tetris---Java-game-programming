package tetris;

import Blocks.HinhChuNhat;
import Blocks.HinhJ;
import Blocks.HinhL;
import Blocks.HinhS;
import Blocks.HinhT;
import Blocks.HinhVuong;
import Blocks.HinhZ;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class Area extends JPanel {

    private int gridRows, gridColumns, gridCellSize;
//    private int[][] block = {{1, 0}, {1, 0}, {1, 1}};
    private Block block;
    private Color[][] background;
    
    private Block[] blocks;

    public Area(JPanel placeholder, int columns) {
        //placeholder.setVisible(false);
        this.setBounds(placeholder.getBounds());
        this.setBackground(placeholder.getBackground());
        this.setBorder(placeholder.getBorder());

        gridColumns = columns;
        gridCellSize = this.getBounds().width / gridColumns;
        gridRows = this.getBounds().height / gridCellSize;

        //    spawnBlock();
        //background = new Color[gridRows][gridColumns];
        
        blocks = new Block[]{ new HinhChuNhat(), new HinhJ(), 
                              new HinhL(), new HinhS(), new HinhT(), 
                              new HinhVuong(), new HinhZ()};
    }
    
    public void initBackgroundArray(){
        background = new Color[gridRows][gridColumns];
    }

    public void spawnBlock() {
        Random r = new Random();
        this.block = this.blocks[ r.nextInt( blocks.length )];
        this.block.spawn(gridColumns);
    }
    
//Check cham day hay khong?
    private boolean checkBottom() {
        if (block.getBottomEdge() == gridRows) {
            return false;
        }
        
        int[][] shape1 = block.getShape();
        int w1 = block.getWidth();
        int h1 = block.getHight();
        
        for (int col1 = 0 ; col1 < w1 ; col1++){
            for (int row1 = h1 - 1; row1 >= 0 ; row1-- ){
                if (shape1[row1][col1] != 0 ){
                    int x1 = col1 + block.getX();
                    int y1 = row1 + block.getY() + 1;
                    if (y1 < 0) break;
                    if(background[y1][x1] != null ) return false;
                    break;
                }
            }
        }
        return true;
    }
//Check cham 2 canh hay khong
    private boolean checkLeft(){
        if (block.getLeftEdge() == 0){
            return false;
        }
        
        int[][] shape1 = block.getShape();
        int w1 = block.getWidth();
        int h1 = block.getHight();
        
        for (int row1 = 0 ; row1 < h1 ; row1++){
            for (int col1 = 0; col1 < w1 ; col1++ ){
                if (shape1[row1][col1] != 0 ){
                    int x1 = col1 + block.getX() - 1;
                    int y1 = row1 + block.getY();
                    if (y1 < 0) break;
                    if(background[y1][x1] != null ) return false;
                    break;
                }
            }
        }
        
        return true;
    }
    private boolean checkRight(){
        if ( block.getRightEdge() == gridColumns ){
            return false;
        }
        
        int[][] shape1 = block.getShape();
        int w1 = block.getWidth();
        int h1 = block.getHight();
        
        for (int row1 = 0 ; row1 < h1 ; row1++){
            for (int col1 = w1 - 1 ; col1 >= 0 ; col1-- ){
                if (shape1[row1][col1] != 0 ){
                    int x1 = col1 + block.getX() + 1;
                    int y1 = row1 + block.getY();
                    if (y1 < 0) break;
                    if(background[y1][x1] != null ) return false;
                    break;
                }
            }
        }
        return true;
    }
    
    public boolean isBlockOutofBounds(){
        if(block.getY() < 0 ){
            block = null;
            return true;
        }
        return false;
    }
    
    public boolean moveBlockDown() {
        if (checkBottom() == false) {
        //    moveBlockToBackground();
        //    clearLines();
            return false;
        }
        block.moveDown();
        repaint();

        return true;
    }
//Di chuyen block sang trai, phai
    public void moveBlockRight(){
        if (block == null) return;
        if ( !checkRight() ) return;
        block.moveRight();
        repaint();
    }
    
    public void moveBlockLeft(){
        if (block == null) return;
        if ( !checkLeft() ) return;
        block.moveLeft();
        repaint();
    }
    
    public void rotateBlock(){
        if (block == null) return;
        block.rotate();
        
        if(block.getLeftEdge() < 0 ) block.setX(0);
        if(block.getRightEdge() >= gridColumns ) block.setX( gridColumns - block.getWidth());
        if(block.getBottomEdge() >= gridRows ) block.setY( gridRows - block.getHight());
        
        repaint();
    }
    
    public void dropBlock(){
        if (block == null) return;
        while(checkBottom()){
            block.moveDown();
        }
        
        repaint();
    }
    
    public int clearLines(){
        boolean LineFilled;
        int linesCleared = 0;
        
        for (int r1 = gridRows - 1 ; r1>= 0 ; r1--){
            LineFilled = true;
            for(int c3 = 0 ; c3 < gridColumns; c3++){
                if (background[r1][c3] == null){
                    LineFilled = false;
                    break;
                }
            }
            if(LineFilled){
                linesCleared++;
                clearLine(r1);
                shiftDown(r1);
                clearLine(0);
                
                r1++;
                repaint();
            }
        }
        return linesCleared;
    }
    
    private void clearLine(int r){
        for (int i =0  ; i < gridColumns ; i++ ){
            background[r][i] = null;
        }
    }
    private void shiftDown(int r){
        for (int row = r ; row > 0 ; row-- ){
            for (int col = 0 ; col < gridColumns ; col++){
                background[row][col] = background[row - 1][col];
            }
        }
    }
    
    public void moveBlockToBackground(){
        int[][] shape = block.getShape();
        int h = block.getHight();
        int w = block.getWidth();
        
        int xPos = block.getX();
        int yPos = block.getY();
        
        Color color = block.getColor();
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (shape[r][c] == 1){
                    background[r+yPos][c+xPos] = color;
                }
            }
        }
        
    }
    
    private void drawBlock(Graphics g) {
        int h = block.getHight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape = block.getShape();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    int x = (block.getX() + col) * gridCellSize;
                    int y = (block.getY() + row) * gridCellSize;

                    drawGridSquare(g, c, x, y);
                }
            }
        }
    }

    private void drawBackground(Graphics g) {
        Color color;
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridColumns; c++) {
                color = background[r][c];

                if (color != null) {
                    int x = (c * gridCellSize);
                    int y = (r * gridCellSize);

                    drawGridSquare(g, color, x, y);
                }
            }
        }
    }

    private void drawGridSquare(Graphics g, Color color, int x , int y) {
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Tao luoi Grid
        for (int y = 0; y < gridRows; y++) {
            for (int x = 0; x < gridColumns; x++) {
                g.drawRect(x * gridCellSize, y * gridCellSize, gridCellSize, gridCellSize);
            }
        }
        drawBackground(g);
        drawBlock(g);

    }
}
