package spaceinvader3d.game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Font;

public class Menu {
    private TextRenderer textRendererLarge;
    private TextRenderer textRendererSmall;
    private boolean active = true;
    private String playerName = ""; 
    private boolean isNameValid = false; 
    private float titleAnimation = 0; 

    public Menu() {
        textRendererLarge = new TextRenderer(new Font("SansSerif", Font.BOLD, 48));
        textRendererSmall = new TextRenderer(new Font("SansSerif", Font.PLAIN, 20));
    }

    public void draw(GL2 gl) {
        if (!active) return; 

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); 
        gl.glLoadIdentity(); 

        
        titleAnimation += 0.05f;
        float colorIntensity = (float) (0.5 + 0.5 * Math.sin(titleAnimation));

        
        textRendererLarge.beginRendering(800, 600);
        textRendererLarge.setColor(1.0f, colorIntensity, 0.0f, 1.0f); // Couleur animée (orange)
        textRendererLarge.draw("SPACE INVADERS 3D", 200, 500);
        textRendererLarge.endRendering();

       
        textRendererSmall.beginRendering(800, 600);

        if (!isNameValid) {
            textRendererSmall.setColor(1.0f, 1.0f, 1.0f, 1.0f); // Blanc pour le texte normal
            textRendererSmall.draw("Entrez votre pseudo : " + playerName, 200, 400);

            
            textRendererSmall.setColor(1.0f, 0.0f, 0.0f, 1.0f); // Rouge
            textRendererSmall.draw("Appuyez sur ENTREE pour valider votre pseudo.", 200, 370);
        } else {
            textRendererSmall.setColor(0.0f, 1.0f, 0.0f, 1.0f); // Vert pour validation
            textRendererSmall.draw("Bonjour " + playerName + " !", 300, 400);
            textRendererSmall.setColor(1.0f, 1.0f, 1.0f, 1.0f); // Blanc pour les instructions
            textRendererSmall.draw("Appuyez sur ENTREE pour commencer le jeu.", 250, 370);
        }

        
        textRendererSmall.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textRendererSmall.draw("Consignes :", 250, 300);
        textRendererSmall.draw("1. Déplacez votre vaisseau avec les flèches gauche/droite.", 150, 280);
        textRendererSmall.draw("2. Tirez sur les ennemis avec la touche ESPACE.", 200, 260);
        textRendererSmall.draw("3. Évitez les projectiles ennemis et les envahisseurs.", 200, 240);

       
        textRendererSmall.draw("Appuyez sur Q pour quitter.", 300, 200);

        textRendererSmall.endRendering();
    }

    
    public void handleInput(char key) {
        if (Character.isLetterOrDigit(key)) {
            playerName += key; 
        } else if (key == '\b' && playerName.length() > 0) {
            playerName = playerName.substring(0, playerName.length() - 1); 
        } else if (key == '\n' && !playerName.isEmpty()) {
            isNameValid = true; 
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isNameValid() {
        return isNameValid;
    }
}
