package spaceinvader3d.entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Player {
    private float xPos = 0.0f; 
    private float yPos = -0.9f; 
    private float speed = 0.05f; 
    private Texture texture;

    public Player(Texture texture) {
        this.texture = texture;
    }

    public void moveLeft() {
        if (xPos > -0.9f) {  
            xPos -= speed;
        }
    }

    public void moveRight() {
        if (xPos < 0.9f) {  
            xPos += speed;
        }
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix(); 
        gl.glTranslatef(xPos, yPos, 0.0f);  
        texture.bind(gl); 
    
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.05f, 0.05f, 0.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.05f, 0.05f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.05f, -0.05f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.05f, -0.05f, 0.0f);
        gl.glEnd();
    
        gl.glPopMatrix(); 
    }
    

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }
}
