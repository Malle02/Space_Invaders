package spaceinvader3d.managers;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

public class ScoreManager {
    private int score = 0;  
    private Integer bestScore = null;  

   
    public void updateScore(int points) {
        score += points;
    }

   
    public void updateBestScore() {
        if (bestScore == null || score > bestScore) {
            bestScore = score;
        }
    }

    
    public int getCurrentScore() {
        return score;
    }

    
    public int getBestScore() {
        if (bestScore == null) {
            return score; 
        }
        return bestScore;
    }

   
    public void resetScore() {
        score = 0;
    }

    
    public void displayScore(GL2 gl, TextRenderer textRenderer) {
        gl.glLoadIdentity();
        textRenderer.beginRendering(800, 600);
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);  
        textRenderer.draw("Score: " + score, 10, 580);
        textRenderer.endRendering();
    }

   
    public void displayBestScore(GL2 gl, TextRenderer textRenderer) {
        gl.glLoadIdentity();
        textRenderer.beginRendering(800, 600);
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);  
        textRenderer.draw("Meilleur Score: " + getBestScore(), 10, 550);
        textRenderer.endRendering();
    }
}
