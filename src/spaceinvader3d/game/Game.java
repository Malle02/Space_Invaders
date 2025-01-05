package spaceinvader3d.game;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import spaceinvader3d.entities.Bullet;
import spaceinvader3d.entities.Enemy;
import spaceinvader3d.entities.Player;
import spaceinvader3d.entities.ShootingEnemy;
import spaceinvader3d.managers.Explosion;
import spaceinvader3d.managers.ScoreManager;
import spaceinvader3d.managers.SoundManager;
import spaceinvader3d.managers.WaveManager;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Game implements GLEventListener {
    protected Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private Texture playerTexture;
    public static Texture enemyTexture; 
    private TextRenderer textRenderer;
    protected boolean gameOver = false; 
    private Texture bulletTexture;
    private ScoreManager scoreManager = new ScoreManager();
    private WaveManager waveManager = new WaveManager(); 
    private long lastShotTime = 0; 
    private static final long SHOT_COOLDOWN = 270; 
    protected Menu menu;
    protected PauseMenu pauseMenu = new PauseMenu();
    protected GameOverMenu gameOverMenu;
    public static Texture fastEnemyTexture;
    public static Texture tankEnemyTexture;
    public static Texture shooterEnemyTexture;
    private final float[][] backgroundColors = {
        {0.0f, 0.0f, 0.0f}, // Noir
        {0.0f, 0.5f, 0.7f}, // Bleu doux
        {0.3f, 0.7f, 0.5f}, // Vert d'eau
        {0.5f, 0.7f, 0.9f}, // Bleu ciel
        {0.7f, 0.8f, 0.6f}, // Vert pâle
        {0.6f, 0.7f, 0.9f}, // Bleu grisâtre
        {0.2f, 0.4f, 0.6f}  // Bleu profond
    };
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();
    private Texture explosionTexture;
    public Game() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public static void main(String[] args) {
        GLWindow window = GLWindow.create(new com.jogamp.opengl.GLCapabilities(com.jogamp.opengl.GLProfile.get(com.jogamp.opengl.GLProfile.GL2)));
        Game game = new Game(); 
        window.addGLEventListener(game);
        window.setSize(800, 600);
        window.setVisible(true);
    
        window.addKeyListener(new GameKeyListener(game));
    
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();
    }
    

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
        menu = new Menu();
        gl.glEnable(GL2.GL_TEXTURE_2D);

        // Chargement des textures
        try {
            explosionTexture = TextureIO.newTexture(new File("res/Image/explosion_spritesheet.png"), true);
            fastEnemyTexture = TextureIO.newTexture(new File("res/Image/Invaders1.png"), true);
            tankEnemyTexture = TextureIO.newTexture(new File("res/Image/Invaders2.jpg"), true);
            shooterEnemyTexture = TextureIO.newTexture(new File("res/Image/invaders3.jpg"), true);
            playerTexture = TextureIO.newTexture(new File("res/Image/SpaceShip1.jpg"), true);
            enemyTexture = TextureIO.newTexture(new File("res/Image/Space_Invader-Render03.jpg"), true);
            bulletTexture = TextureIO.newTexture(new File("res/Image/FireBullet.jpg"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new Player(playerTexture);
        textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 18));

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        //  la première vague
        waveManager.initWave(); 
        waveManager.updateEnemies(enemies); 

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

    }


    public void restartGame() {
        gameOver = false; 
        scoreManager.resetScore(); 
        waveManager = new WaveManager(); 
        enemies.clear(); 
        bullets.clear(); 
        enemyBullets.clear();
        waveManager.initWave(); 
        waveManager.updateEnemies(enemies); 
        System.out.println("Le jeu a été redémarré. Première vague !");
    }
    

    private void displayScores(GL2 gl) {
    int index = waveManager.getWaveNumber() % backgroundColors.length;
    float[] currentBackgroundColor = backgroundColors[index];
    boolean isDark = isBackgroundColorDark(currentBackgroundColor);
    float[] textColor = isDark ? new float[]{1.0f, 1.0f, 1.0f, 1.0f} // Blanc
                               : new float[]{0.0f, 0.0f, 0.0f, 1.0f}; // Noir

    textRenderer.beginRendering(800, 600);

    textRenderer.setColor(textColor[0], textColor[1], textColor[2], textColor[3]);
    textRenderer.draw("Pseudo: " + menu.getPlayerName(), 10, 560);
    textRenderer.draw("Niveau: " + waveManager.getWaveNumber(), 10, 540);
    textRenderer.draw("Score: " + scoreManager.getCurrentScore(), 10, 520);
    textRenderer.draw("Meilleur Score: " + scoreManager.getBestScore(), 10, 500);
    textRenderer.draw("Appuyez sur 'P' pour mettre en pause", 10, 470);

    textRenderer.endRendering();
    }

    private void changeBackgroundColor(GL2 gl) {
    //    premiere vague fond noire 
        if (waveManager.getWaveNumber() == 1) {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            return;
        }
        int index = waveManager.getWaveNumber() % backgroundColors.length;

        
        float[] color = backgroundColors[index];
    
        
        gl.glClearColor(color[0], color[1], color[2], 1.0f);
    
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Blanc
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        if (menu.isActive()) {
            menu.draw(gl); 
        } else if (pauseMenu.isActive()) {
            pauseMenu.draw(gl);
        } else if (gameOver) {
            
        if (gameOverMenu == null) {
                gameOverMenu = new GameOverMenu(scoreManager.getBestScore());
                gameOverMenu.setActive(true);
            }
            gameOverMenu.draw(gl);
        } else {
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT); 

            gl.glLoadIdentity(); 
            changeBackgroundColor(gl); 

            player.draw(gl); 

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (enemy.isAlive()) {
                    enemy.move();
                    enemy.draw(gl); 
                } else {
                    enemyIterator.remove();
                }
            }

            
            List<Bullet> bulletCopy = new ArrayList<>(bullets);
            for (Bullet bullet : bulletCopy) {
                bullet.move();
                bullet.draw(gl);

                if (bullet.isOffScreen()) {
                    bullets.remove(bullet);
                    continue;
                }

                for (Enemy enemy : enemies) {
                    if (bullet.checkCollision(enemy) && enemy.isAlive()) {
                        enemy.setAlive(false);
                        bullets.remove(bullet);
                        scoreManager.updateScore(10);
                        explosions.add(new Explosion(enemy.getX(), enemy.getY(), explosionTexture));
                        SoundManager.playSound("res/sounds/explosion.wav"); // le son d'explosion
                        break;
                    }
                }
            }


            Iterator<Explosion> explosionIterator = explosions.iterator();
            while (explosionIterator.hasNext()) {
                Explosion explosion = explosionIterator.next();
                if (explosion.isExpired()) {
                    explosionIterator.remove();
                } else {
                    explosion.draw(gl);
                }
            }


        int maxConcurrentShots = 3; 
        int currentShots = 0;

        for (Enemy enemy : enemies) {
            if (currentShots >= maxConcurrentShots) {
                break; 
            }

            if (enemy instanceof ShootingEnemy) {
                Bullet enemyBullet = ((ShootingEnemy) enemy).shoot(bulletTexture);
                if (enemyBullet != null) {
                    enemyBullets.add(enemyBullet);
                    currentShots++; 
                }
            }
        }

        Iterator<Bullet> bulletIterator = enemyBullets.iterator();
        while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                bullet.move(); 
            
                
                if (bullet.isOffScreen()) {
                    bulletIterator.remove(); 
                    continue;
                }
            
                // Collision avec le joueur
                if (checkCollisionWithPlayer(bullet)) {
                    gameOver = true; 
                    break;
                }
            
                
                bullet.draw(gl);
            }
           
            if (checkGameOver()) {
                gameOver = true;
            }

           
            if (waveManager.checkNextWave() && !gameOver) {
                waveManager.nextWave();
                waveManager.updateEnemies(enemies);
            }
            displayScores(gl);
        }
    
    }
    


    public boolean checkGameOver() {
        for (Enemy enemy : enemies) {
            if (enemy.getY() < player.getY() + 0.1f && enemy.isAlive()) {
                return true; 
            }
        }
        return false;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height); 
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glDisable(GL2.GL_TEXTURE_2D); 
    }

    public void shootBullet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOT_COOLDOWN) { 
            Bullet bullet = new Bullet(player.getX(), player.getY() + 0.1f, bulletTexture,1);
            bullets.add(bullet);
            lastShotTime = currentTime; 
            SoundManager.playSound("res/sounds/player_shoot.wav");
        }
    }

    private boolean isBackgroundColorDark(float[] color) {
        float brightness = 0.299f * color[0] + 0.587f * color[1] + 0.114f * color[2];
        return brightness < 0.5; 
    }

    private boolean checkCollisionWithPlayer(Bullet bullet) {
        return bullet.getX() > player.getX() - 0.05f && bullet.getX() < player.getX() + 0.05f
          && bullet.getY() > player.getY() - 0.05f && bullet.getY() < player.getY() + 0.05f;
    }
    
}
