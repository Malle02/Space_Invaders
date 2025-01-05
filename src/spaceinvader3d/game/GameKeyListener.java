package spaceinvader3d.game;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

public class GameKeyListener extends KeyAdapter {
    private Game game;

    public GameKeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char key = e.getKeyChar();

        if (keyCode == KeyEvent.VK_P && !game.menu.isActive() && !game.gameOver) {
            game.pauseMenu.setActive(!game.pauseMenu.isActive()); 
            System.out.println("Pause activée : " + game.pauseMenu.isActive());
            return; 
        }
        
        if (game.pauseMenu.isActive()) {
            if (key == '1') {
                game.pauseMenu.setActive(false); 
            } else if (key == '2') {
                game.restartGame(); 
                game.pauseMenu.setActive(false);
            } else if (key == '3') {
                System.exit(0); 
            }
            return;
        }

        if (game.gameOverMenu != null && game.gameOverMenu.isActive()) {
            if (key == '1') {
                game.restartGame(); 
                game.gameOverMenu.setActive(false);
            } else if (key == '2') {
                System.exit(0);
            }
            return;
        }

        if (game.menu.isActive()) {
            if (key == KeyEvent.VK_ENTER) {
                if (!game.menu.getPlayerName().isEmpty()) {
                    if (game.menu.isNameValid()) {
                        game.menu.setActive(false); 
                    } else {
                        game.menu.handleInput('\n'); 
                    }
                } else {
                    System.out.println("Veuillez entrer un pseudo avant de continuer.");
                }
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0); 
            } else {
                game.menu.handleInput(key); 
            }
        }
        if (keyCode == KeyEvent.VK_LEFT && !game.gameOver) {
            game.player.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT && !game.gameOver) {
            game.player.moveRight();
        } else if (keyCode == KeyEvent.VK_SPACE && !game.gameOver) {
            game.shootBullet();
        }
    }
}
