package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;


public class Bullet 
{
int x;
int y;
int speed;
Image img;
Boolean outOfBounds = false;
Rectangle bbox;
int width;
int height;

Bullet (Image i, int ex, int why, int sp)
{
img = i;
x = ex+17;
y = why;
speed = sp;
width = img.getWidth(null);
height = img.getHeight(null);
}

public void update() 
{
	y -= speed;
	if(y < 0)
	{
		outOfBounds = true;
	}
}

public boolean collision(int x, int y, int w, int h) 
{
    bbox = new Rectangle(this.x, this.y, this.width, this.height);
    Rectangle otherBBox = new Rectangle (x,y,w,h);
    if(this.bbox.intersects(otherBBox)) 
    { 
        return true;
    }
    return false;
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
