package spaceinvader3d.entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Bullet {
    private float xPos, yPos;
    private float speed = 0.05f;
    private Texture texture;
    private int direction;
   
    public Bullet(float x, float y, Texture texture,int direction) {
        this.xPos = x;
        this.yPos = y;
        this.texture = texture; 
        this.direction = direction;

        this.speed = (direction == -1) ? 0.02f : 0.05f; 
    }

    
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(xPos, yPos, 0.0f);  
        texture.bind(gl);  
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.01f, 0.1f, 0.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.01f, 0.1f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.01f, -0.1f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.01f, -0.1f, 0.0f);
        gl.glEnd();

        gl.glPopMatrix();
    }

    public void move() {
        yPos += speed * direction;
    }

    public boolean isOffScreen() {
        return yPos > 1.0f; 
    }

    public boolean checkCollision(Enemy enemy) {
        return (xPos > enemy.getX() - 0.05f && xPos < enemy.getX() + 0.05f) &&
                (yPos > enemy.getY() - 0.05f && yPos < enemy.getY() + 0.05f);
    }
    
    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }
}
