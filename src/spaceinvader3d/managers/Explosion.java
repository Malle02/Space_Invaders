package spaceinvader3d.managers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class Explosion {
    private float x, y;
    private long startTime;
    private long duration = 500;
    private int totalFrames = 64;
    private int columns = 8; 
    private int rows = 8; 
    private Texture texture;

    public Explosion(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }

    public void draw(GL2 gl) {
        long elapsed = System.currentTimeMillis() - startTime;
        int currentFrame = (int) ((elapsed / (float) duration) * totalFrames);
        currentFrame = Math.min(currentFrame, totalFrames - 1); 

        int frameX = currentFrame % columns; 
        int frameY = currentFrame / columns; 

        float frameWidth = 1.0f / columns; 
        float frameHeight = 1.0f / rows; 

        float uStart = frameX * frameWidth;
        float uEnd = uStart + frameWidth;
        float vStart = 1.0f - (frameY + 1) * frameHeight; 
        float vEnd = vStart + frameHeight;

        gl.glPushMatrix();
        gl.glTranslatef(x, y, 0.0f);

        texture.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(uStart, vStart); gl.glVertex2f(-0.1f, 0.1f);
        gl.glTexCoord2f(uEnd, vStart); gl.glVertex2f(0.1f, 0.1f);
        gl.glTexCoord2f(uEnd, vEnd); gl.glVertex2f(0.1f, -0.1f);
        gl.glTexCoord2f(uStart, vEnd); gl.glVertex2f(-0.1f, -0.1f);
        gl.glEnd();

        gl.glPopMatrix();
    }
}