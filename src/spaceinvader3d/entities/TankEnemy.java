package spaceinvader3d.entities;

import com.jogamp.opengl.util.texture.Texture;

public class TankEnemy extends Enemy {
    private int health;

    public TankEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
        this.health = 3;
    }

    @Override
    public void setAlive(boolean alive) {
        if (alive && health > 0) {
            health--;
        } else {
            super.setAlive(false);
        }
    }
}
