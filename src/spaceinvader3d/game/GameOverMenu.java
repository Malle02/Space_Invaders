package spaceinvader3d.game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Font;

public class GameOverMenu {
    private boolean active = false; 
    private TextRenderer textRenderer;
    private int bestScore;

    public GameOverMenu(int bestScore) {
        this.bestScore = bestScore;
        textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 24));
    }

    public void draw(GL2 gl) {
        if (!active) return;

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        textRenderer.beginRendering(800, 600);

        
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f); 
        textRenderer.draw("Game Over", 300, 400);
        textRenderer.draw("Votre Meilleur Score: " + bestScore, 300, 350);
        textRenderer.draw("1. Rejouer", 300, 300);
        textRenderer.draw("2. Quitter", 300, 250);

        textRenderer.endRendering();

        

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
