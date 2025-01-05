
package spaceinvader3d.managers;

import java.util.ArrayList;

import spaceinvader3d.entities.Enemy;
import spaceinvader3d.entities.FastEnemy;
import spaceinvader3d.entities.ShootingEnemy;
import spaceinvader3d.entities.TankEnemy;
import spaceinvader3d.game.Game;

public class WaveManager {
    private int waveNumber = 1; 
    private ArrayList<Enemy> enemies; 

    public WaveManager() {
        enemies = new ArrayList<>();
    }

   
    public int getWaveNumber() {
        return waveNumber;
    }

   
    public void initWave() {
        resetWave(); 

        int numEnemiesPerLine = 6; 
        int numLines = 1; 

        for (int line = 0; line < numLines; line++) {
            for (int i = 0; i < numEnemiesPerLine; i++) {
                float xPos = (i - numEnemiesPerLine / 2) * 0.2f; 
                float yPos = 0.8f - line * 0.1f; 
                enemies.add(new Enemy(xPos, yPos, Game.enemyTexture));
            }
        }
    }

    public void nextWave() {
        waveNumber++;  
        resetWave(); 

        int numEnemiesPerLine = 6; 
        int numLines = waveNumber;

        for (int line = 0; line < numLines; line++) {
    for (int i = 0; i < numEnemiesPerLine; i++) {
        float xPos = (i - numEnemiesPerLine / 2) * 0.2f;
        float yPos = 0.8f - line * 0.1f;

        if (waveNumber % 3 == 0) {
            enemies.add(new FastEnemy(xPos, yPos, Game.fastEnemyTexture));
        } else if (waveNumber % 5 == 0) {
            enemies.add(new TankEnemy(xPos, yPos, Game.tankEnemyTexture));
        } else {
            enemies.add(new ShootingEnemy(xPos, yPos, Game.shooterEnemyTexture));
        }
    }
}

        for (Enemy enemy : enemies) {
            enemy.setSpeed(0.01f + waveNumber * 0.002f);
        }
    }

    public void resetWave() {
        enemies.clear();
    }

    public boolean checkNextWave() {
        if (enemies.isEmpty()) {
            return false; 
        }
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return false; 
            }
        }
        return true; 
    }

    
    public void updateEnemies(ArrayList<Enemy> gameEnemies) {
        gameEnemies.clear();
        gameEnemies.addAll(enemies); 
    }
}
