package spaceinvader3d.entities;

import com.jogamp.opengl.util.texture.Texture;

public class FastEnemy extends Enemy {
    public FastEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
        setSpeed(0.02f); // Plus rapide
    }
}
