package spaceinvader3d.entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Enemy {
    private float xPos, yPos;
    private boolean alive;
    private float speed = 0.01f; 
    private float direction = 1.0f; 
    private Texture texture;

    public Enemy(float x, float y, Texture texture) {
        xPos = x;
        yPos = y;
        alive = true; 
        this.texture = texture;
    }

    public void draw(GL2 gl) {
        if (alive) {
            gl.glPushMatrix();
            gl.glTranslatef(xPos, yPos, 0.0f); 
            texture.bind(gl); 

            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-0.05f, 0.05f, 0.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0.05f, 0.05f, 0.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(0.05f, -0.05f, 0.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-0.05f, -0.05f, 0.0f);
            gl.glEnd();

            gl.glPopMatrix();
        }
    }

    public void move() {
        xPos += speed * direction; 
        if (xPos > 0.9f || xPos < -0.9f) {
            direction = -direction; 
            yPos -= 0.1f; 
        }
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
   
}

