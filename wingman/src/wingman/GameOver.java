package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameOver 
{

int currentHealth;
int x;
int y;
Image gameOverImg;
Image finalScene;

GameOver (int ex, int why) throws IOException
{
gameOverImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/gameOver.png"));
finalScene = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/youLose.png"));
x = ex;
y = why;
}

public void update() 
{
}
public void draw(ImageObserver obs,Graphics2D g2) throws InterruptedException 
{
    g2.drawImage(finalScene, 200, 100, obs);
}

void setLocation (int ex, int why)
{
	x = ex;
	y = why;
}

}
