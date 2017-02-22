package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class HealthBar 
{

int currentHealth;
int x;
int y;
Image img;

HealthBar (Image i, int ex, int why)
{
img = i;
x = ex;
y = why;
}

public void update() 
{
}
public void draw(ImageObserver obs,Graphics2D g2) 
{
    g2.drawImage(img, x, y, obs);
}

void setLocation (int ex, int why)
{
	x = ex;
	y = why;
}

}
