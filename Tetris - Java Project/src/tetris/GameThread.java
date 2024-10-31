package tetris;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private Area ar;
    private TetrisForm tf;
    private int score;
    private int level = 1 ;
    private int scorePerLevel = 3;
    //Toc do mac dinh
    private int pause = 1000;
    //Toc do tang len 100 khi len moi level
    private int speedupPerLevel = 100;

    public GameThread(Area ar, TetrisForm tf) {
        this.ar = ar;
        this.tf = tf;
        //Khoi tao lai level va score moi khi an Vao start game
        tf.updateScore(score);
        tf.updateLevel(level);
    }

    @Override
    public void run() {
        while (true) {
            ar.spawnBlock();
            while (ar.moveBlockDown()) {
                try {

                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    return;
                }
            }
            //Khi block cham dinh cua man hinh thi game OVER
            if(ar.isBlockOutofBounds()){
                Tetris.gameOver(score);
                break;
            }
            //Neu khong thi game tiep tuc
            ar.moveBlockToBackground();
            score += ar.clearLines();
            tf.updateScore(score);
            
            int lvl = (score/scorePerLevel) + 1;
            if (lvl > level){
                level = lvl;
                tf.updateLevel(level);
                //Tang toc khi len level
                pause-=speedupPerLevel;
            } 
        }
    }
}
