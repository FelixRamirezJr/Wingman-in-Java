package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class EnemyBullet 
{
	Image img;
	int x,y;
	int speed;
	boolean outOfBounds;
	Rectangle bbox;
	int width;
	int height;
	boolean diagonal;
	boolean right;
	boolean left;
	
	EnemyBullet(Image i, int ex, int why, int sp)
	{
		img = i;
		x = ex;
		y = why;
		speed = sp;
		width = img.getWidth(null);
		height = img.getHeight(null);
		// TODO Auto-generated constructor stub
	}
	
	EnemyBullet(Image i, int ex, int why, int sp, boolean diagonal, boolean left, boolean right)
	{
		img = i;
		x = ex;
		y = why;
		speed = sp;
		width = img.getWidth(null);
		height = img.getHeight(null);
		this.diagonal = true;
		if(left == true)
		{
		this.left = true;
		}
		if(right == true)
		{
			this.right = true;
		}
	}
	
	public void update() 
	{
		if(diagonal)
		{
			if(right)
			{
			x+= speed;
			
			}
			if(left)
			{
			x-= speed;
			}
				
		}
		
		y += speed;
		if(y > 1030)
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
