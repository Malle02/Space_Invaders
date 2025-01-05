package spaceinvader3d.game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Font;

public class PauseMenu {
    private boolean active = false; 
    private TextRenderer textRenderer;

    public PauseMenu() {
        this.textRenderer = null;
    }

    public void draw(GL2 gl) {
        if (!active) return;

        if (textRenderer == null) {
            textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 24));
        }

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        textRenderer.beginRendering(800, 600);

       
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f); 
        textRenderer.draw("Menu Pause", 300, 400);
        textRenderer.draw("1. Reprendre", 300, 350);
        textRenderer.draw("2. Rejouer", 300, 300);
        textRenderer.draw("3. Quitter", 300, 250);

        textRenderer.endRendering();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
