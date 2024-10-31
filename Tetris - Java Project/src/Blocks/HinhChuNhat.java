
package Blocks;
import tetris.Block;

public class HinhChuNhat extends Block{
    public HinhChuNhat(){
        super(new int[][]{ {1, 1, 1, 1} });
        
    }
    //Viet lai phuong thuc rotate cho phu hop voi hinh chu nhat
    @Override
    public void rotate(){
        super.rotate();
        
        if (this.getWidth() == 1){
            this.setX(this.getX() + 1);
            this.setY(this.getY() - 1);
        }else{
            this.setX(this.getX() - 1);
            this.setY(this.getY() + 1);
        }
    }
    
    
}
