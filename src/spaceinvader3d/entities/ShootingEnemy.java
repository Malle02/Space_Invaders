package spaceinvader3d.entities;

import com.jogamp.opengl.util.texture.Texture;


public class ShootingEnemy extends Enemy {
    private long lastShotTime = 0;
    private long shootCooldown;

    public ShootingEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
        this.shootCooldown = 1000 + (long) (Math.random() * 1000); 
    }

    public Bullet shoot(Texture bulletTexture) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shootCooldown) {
            lastShotTime = currentTime;
  
            if (Math.random() < 0.2) { 
                return new Bullet(getX(), getY() - 0.1f, bulletTexture, -1); 
            }
        }
        return null;
    }
    
    
}
