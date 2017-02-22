package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import wingman.game1942WithObserver.MyPlane;

public class Boss 
{

        Image img;
        int x, y, sizeX, sizeY, speed;
        Random gen;
        boolean show;
        boolean EXPLODING = false;
        int explodeX, explodeY;
        int countE = 0;
        boolean goLeft = true;
        boolean goRight = false;
        ArrayList <EnemyBullet> eBullets = new ArrayList <EnemyBullet>();
        int randomShooting = 0;
        int intervals;
        int type;
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        int w = 1080;
        int h = 768;
        
        Image bulletLeft;
    	Image bulletRight;
    	Image bulletImg;
    	
    	Random generator = new Random(1234567);
    	 
        Boss(int speed, Random gen) throws IOException 
        {
        	//this.bullets = b;
        	Random ran = new Random();
        	type = ran.nextInt(4) + 1;
        	
            bulletImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bullet.png"));
        	bulletLeft =  ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bulletRight.png"));
            bulletRight = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bulletLeft.png"));
        	
        	img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/smallMario.png"));
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -20;
            this.speed = speed;
            this.gen = gen;
            this.show = true;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            //img.s
            System.out.println("w:" + sizeX + " y:" + sizeY);
            this.intervals = 2;
       }

        public void update(MyPlane m,ArrayList<Bullet>be)
        {
        	bullets = be;
            y += speed;
            
           if( x > h + 180)
           {
        	   goLeft = true;
        	   goRight = false;
           }
           if( x  < 0)
           {
        	   goRight = true;
        	   goLeft = false;
           }
           
           if(goRight)
        	   x += speed;
           else
        	   x -= speed;
            
            
           if(randomShooting % intervals == 0)
           {
        	   EnemyBullet tempB;
        	   
        		     tempB = new EnemyBullet(bulletRight,x,y,10,true,false,true);
        		     eBullets.add(tempB);
        		     tempB = new EnemyBullet(bulletLeft,x,y,10,true,true,false); 
        		     eBullets.add(tempB);
        		     tempB = new EnemyBullet(bulletRight,x,y,10,true,false,true);
        		     eBullets.add(tempB);
         		     tempB = new EnemyBullet(bulletImg,x,y,10);
         		     eBullets.add(tempB);
        	   eBullets.add( tempB);
           }
           
           randomShooting++;
           
           if(randomShooting > 500)
           {
        	   randomShooting = 0; 
           }
           
            // This Checks the Collision for the Plane to Plane...
            if(m.collision(x, y, sizeX, sizeY) && countE == 0) 
            {
            	
            	countE = 40; ;// Counting the Frames for Explosion!!!! Should only update when zero...
                show = false;
                explodeX = this.x;
                explodeY = this.y;
                EXPLODING = true;
               
            }
            
            // This should Theoretically Check the collision for the bullets....
            
            for(Bullet b: bullets)
            {
            	if(b.collision(x, y, sizeX, sizeY) && countE == 0) 
            	{
            	
            	countE = 40; ;// Counting the Frames for Explosion!!!! Should only update when zero...
                show = false;
                explodeX = this.x;
                explodeY = this.y;
                EXPLODING = true;
                
                // You need to remove this one and increase score etc
            	}
            }
            
            // In the Process of Exploding!!!!
            if(EXPLODING == true)
            {
            	countE--;
            	//System.out.println("This is the countE value: " + countE);
            	if(countE == 0)
            	{
            		 this.reset();
                     show = true;
                     EXPLODING = false;
                     countE = 0;
            	}
            }
            else 
               // gameEvents.setValue("");
            
            if(y >= h)
            {
            this.reset();
            }
        
       
        }
        
        public void reset() 
        {
            this.x = Math.abs(generator.nextInt() % (600 - 30));
            this.y = -10;
        }

        public void draw(ImageObserver obs, Graphics2D g2) 
        {
            if (show) 
            {
                g2.drawImage(img, x, y, obs);
            }
        }
        public BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
            BufferedImage dbi = null;
            if(sbi != null) 
            {
                dbi = new BufferedImage(dWidth, dHeight, imageType);
                Graphics2D g = dbi.createGraphics();
                AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
                g.drawRenderedImage(sbi, at);
            }
            return dbi;
        }
    }
