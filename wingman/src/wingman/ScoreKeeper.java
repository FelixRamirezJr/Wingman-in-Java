package wingman;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ScoreKeeper 
{
int x;
int y;
int score;
int level = 0;
Font f = new Font("Dialog", Font.PLAIN, 100);

ScoreKeeper (int ex, int why)
{
x = ex;
y = why;
score = 0;
level = 0;
}

public void update() throws IOException 
{
}

public void draw(ImageObserver obs,Graphics2D g2) 
{
	String t = "Level: ";
	level = score/5;
	t+= Integer.toString(level);
	String temp = Integer.toString(this.score);
	t += " Score: ";
	t +=temp;
	g2.setFont(f);
    g2.drawString(t, x-700, y+50);
}

public void addToScore ()
{
	this.score++;
}
void setLocation (int ex, int why)
{
	x = ex;
	y = why;
}

}
