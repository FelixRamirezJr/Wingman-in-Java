package wingman;
//package wingman;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;




/**
 *
 * @author Felix Ramirez
 */
import sun.audio.*;
import sun.*;
public class game1942WithObserver extends JApplet implements Runnable 
{
	boolean bulletFired = false;
    private Thread thread;
    Image sea;
    Image myPlane;
    Image myPlane2;
    private BufferedImage bimg;
    Graphics2D g2;
    int speed = 1, move = 0;
    Random generator = new Random(1234567);
    Random genSpeed = new Random();
    Island I1, I2, I3;
    Image bulletImg;
    MyPlane m;
    PlayerDeath pDeath;
   // MyPlane m2;
    int w = 1024, h = 768; // fixed size window game 
   // Enemy e1;
    GameEvents gameEvents; 
    
    // My New Variables
    boolean planeExplode = false;
    Death death;
     HealthBar hb;
    Image healthImg;
    int numOfHits = 0;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    boolean goDeath;
    Death d = null;
    PlayerDeath biggerDeath = null;
    int playerScore = 0;
    ScoreKeeper sk = new ScoreKeeper(900,700);
    Bullet b = null;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<EnemyBullet> eBull = new ArrayList<EnemyBullet>();
    
    // Audio Stuff...
    String bMusic = "C:/Users/Optimus Prime/workspace/wingman/Resources/Z.wav";
    String exSound = "C:/Users/Optimus Prime/workspace/wingman/Resources/snd_explosion1.wav";
    AudioStream as = null;
    InputStream in = null;
    
    AudioStream asExplode = null;
    InputStream inExplode = null;
    Image enemyImg;
    
    boolean addExtra = false;
    
    int bulletLimiter = -1;
    boolean shoot = true;
    boolean removedEnemyBullet = false;
	int numOfCollision = 0;
	
	ArrayList<Death> bulletExplosion = new ArrayList<Death>();
	
	Image bulletLeft;
	Image bulletRight;
	
	Boss boss;
    
    public void init() 
    {
        setBackground(Color.white);
        Image island1, island2, island3;
        
        // Going to Read some of the Audio Files....
        //=========================================
        in = null;
		try {
			in = new FileInputStream(bMusic);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try 
        {
			as = new AudioStream(in);
		} 
        catch (IOException e2) 
        {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        AudioPlayer.player.start(as); 
        
        
        // End of Trying to Read The Sound....

        try 
        {
        sea = getSprite("C:/Users/Optimus Prime/workspace/wingman/Resources/water.png");
        // Change to get Sprite...
        //sea = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/water.png"));
        island1 = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/island1.png"));
        island2 = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/island2.png"));
        island3 = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/island3.png"));
        
        myPlane = ImageIO.read(new File("C:/Users/Optimus Prime/Desktop/wingman/Resources/myplane_1.png"));
        myPlane2 = ImageIO.read(new File("C:/Users/Optimus Prime/Desktop/wingman/Resources/myplane_1.png"));
        enemyImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/enemy1_1.png"));
        
        bulletImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bullet.png"));
        bulletLeft =  ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bulletRight.png"));
        bulletRight = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/bulletLeft.png"));
        healthImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/health.png"));
        
        I1 = new Island(island1, 100, 100, speed, generator);
        I2 = new Island(island2, 200, 400, speed, generator);
        I3 = new Island(island3, 300, 200, speed, generator);
        
        // Creating Health...
        hb = new HealthBar(healthImg,5,730);
        //
     //   e1 = new Enemy(1, generator);
        
        for(int i = 0; i < 1; i++)
        {
        	Enemy e = new Enemy(1, generator);
        	enemies.add(e);
        }
        
        m = new MyPlane(myPlane, 300, 360, 10);
       // m2 = new MyPlane(myPlane2,200,360,10);
        boss = new Boss(1,generator);
        this.setFocusable(true);
        gameEvents = new GameEvents();
        gameEvents.addObserver(m);
      //  gameEvents.addObserver(m2);
        KeyControl key = new KeyControl();
        addKeyListener(key);
        }
        catch (Exception e) 
        {
            System.out.print("No resources are found");
        }
    }

public class GameEvents extends Observable 
{
       int type;
       Object event;
       
   public void setValue(KeyEvent e) 
   {
          type = 1; // let's assume this means key input. 
          //Should use CONSTANT value for this when you program
          event = e;
          setChanged();
         // trigger notification
         notifyObservers(this);  
   }

   public void setValue(String msg) 
   {
          type = 2; 
          event = msg;
          setChanged();
         // trigger notification
         notifyObservers(this);  
        }
    }
    
public class Island 
{

        Image img;
        int x, y, speed;
        Random gen;

        Island(Image img, int x, int y, int speed, Random gen) 
        {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.gen = gen;
        }
        
        public void update() 
        {
            y += speed;
            if (y >= h) 
            {
                y = -100;
                x = Math.abs(gen.nextInt() % (w - 30));
            }
        }

        public void draw(ImageObserver obs) 
        {
            g2.drawImage(img, x, y, obs);
        }
    }


public class Enemy 
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
   
        Enemy(int speed, Random gen) throws IOException 
        {
        	Random ran = new Random();
        	type = ran.nextInt(4) + 1;
        	
        	if(type == 1)
        	{
        	img =ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/enemy1_1.png"));
        	speed *=1;
        	intervals = 300;
        	}
        	
        	if(type == 2)
        	{
        	img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/enemy2_1.png"));
        	speed *=2;
        	intervals = 250;
        	}
        	
        	if(type == 3)
        	{
        		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/enemy3_1.png"));	
        		speed *=3;
        		intervals = 200;
        	}
        	
        	if(type == 4)
        	{
        		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/enemy4_1.png"));	
        		speed *=4;
        		intervals = 150;
        	}
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -20;
            this.speed = speed;
            this.gen = gen;
            this.show = true;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            System.out.println("w:" + sizeX + " y:" + sizeY);
       }

        public void update() 
        {
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
        	   if(type == 3 || type == 4)
        	   {
        		   	if(type == 3 && x < w/2)
        	   		{
        		     tempB = new EnemyBullet(bulletRight,x,y,10,true,false,true);
        	   		}
        	   		else
        	   		{
        		     tempB = new EnemyBullet(bulletLeft,x,y,10,true,true,false); 
        	   		}
        	   
        	   		if(type == 4 && x < w/2)
        	   		{
        		     tempB = new EnemyBullet(bulletRight,x,y,10,true,false,true);
        	   		}
        	   		else
        	   		{
        		     tempB = new EnemyBullet(bulletLeft,x,y,10,true,true,false);
        	   		}
        	   }
        	   else
        	   {
        	   
         		     tempB = new EnemyBullet(bulletImg,x,y,10);
        	   }
        	   
        	   
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
                
                // You need to remove this one and increase score etc
                if(countE == 40)
                { // This only happens when the 
                gameEvents.setValue("Explosion");
                }
               // System.out.println("This is an explosion from the enemy...");
                gameEvents.setValue("");
                sk.addToScore();
                if(sk.score % 5 == 0)
                {
                addExtra = true;
                }
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
                	if(countE == 40)
                	{ 
                		// This only happens when the 
                		gameEvents.setValue("Bullet Hit");
                		//playerScore++;
                		sk.addToScore();
                		if(sk.score % 5 == 0)
                		{
                		addExtra = true;	
                		}
                		
                	//	System.out.println("This is the Player Score..." + sk.score);
                	}
               // System.out.println("This is an explosion from the enemy...");
                gameEvents.setValue("");
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
                gameEvents.setValue("");
            
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

        public void draw(ImageObserver obs) 
        {
            if (show) 
            {
                g2.drawImage(img, x, y, obs);
            }
        }
    }

 public class KeyControl extends KeyAdapter 
 {

        public void keyPressed(KeyEvent e) {
            gameEvents.setValue(e);
        }
    }
 
   
 public class MyPlane implements Observer 
 {
        Image img;
        int x, y, speed, width, height;
        Rectangle bbox;
        boolean boom;
        boolean player2;

        MyPlane(Image img, int x, int y, int speed) 
        {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            width = img.getWidth(null);
            height = img.getHeight(null);
            boom = false;
        }
        
        MyPlane(Image img, int x, int y, int speed, boolean player2) 
        {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            width = img.getWidth(null);
            height = img.getHeight(null);
            boom = false;
            this.player2 = true;
        }

        public void draw(ImageObserver obs) 
        {
            g2.drawImage(img, x, y, obs);
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
      
        public void update(Observable obj, Object arg) 
        {	
        	  bulletLimiter++;	
        	  	Enemy[] ee = new Enemy[enemies.size()];
          		for(int i = 0; i < enemies.size(); i++)
          		{
          		ee[i] = enemies.get(i);
          		}
        	  numOfCollision = 0;
        	  for(int i = 0; i <ee.length;i++)
              {
        		for(int x = 0; x< ee[i].eBullets.size(); x++)
        		{
        			if(ee[i].eBullets.get(x).collision(this.x, this.y, this.width, this.height) && removedEnemyBullet == false) 
        			{	
              		numOfCollision++;
              		ee[i].eBullets.remove(x);
              		System.out.println("Bullet Collided With me... This is num of Collision with Bullets: " + numOfCollision);
              		Death d = new Death(this.x,this.y);
              		bulletExplosion.add(d);
        			}
        			
        		}
              }
        	  
        	  for(int x = 0; x< boss.eBullets.size(); x++)
      			{
      			if(boss.eBullets.get(x).collision(this.x, this.y, this.width, this.height) && removedEnemyBullet == false) 
      			{	
            		numOfCollision++;
            		boss.eBullets.remove(x);
            		System.out.println("Bullet Collided With me... This is num of Collision with Bullets: " + numOfCollision);
            		Death d = new Death(this.x,this.y);
            		bulletExplosion.add(d);
      			}
      			
      			}
            GameEvents ge = (GameEvents) arg;
            if(ge.type == 1) 
            {
            	//System.out.println("Value for player two is: " + player2);
                KeyEvent e = (KeyEvent) ge.event;
                switch (e.getKeyCode()) 
                {    
                    case KeyEvent.VK_LEFT:
                        x -= speed;
	        	break; 
                    case KeyEvent.VK_RIGHT:
                        x += speed;
	        	break;
                    case KeyEvent.VK_UP:
                        y -= speed;
	        	break; 
                    case KeyEvent.VK_DOWN:
                        y += speed;
	        	break;
                    case KeyEvent.VK_SHIFT:
                    	//speed *=3;
                    	break;
                    default:
                  if(e.getKeyChar() == ' ') 
                  {
                	  
                	  if(shoot)
                	  {
                	  b = new Bullet(bulletImg,x,y,5);
                	  bullets.add(b);
                	 // System.out.println("This is the current Plane Location" + x  + " and " + y);
                	  bulletFired = true;
                	  shoot = false;
                	  }
                       // System.out.println("Fire");  
                  }
                  
                }
            }
            else if(ge.type == 2) 
            {
                String msg = (String)ge.event;
                if(msg.equals("Explosion") || numOfCollision != 0) 
                {
                	//System.out.println("This is the Number of Hits: " + numOfHits);
                	if(numOfCollision == 0)
                	{
                	numOfHits++;
                	}
                	else
                	{
                		numOfHits += numOfCollision;
                	}
                	
                	if(numOfHits == 1)
                	{
                		 try 
                		{
                	    healthImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/health1.png"));
						} catch (IOException e) 
                		 {
					
							e.printStackTrace();
						}
                	}
                	
                	if(numOfHits == 2)
                	{
                		 try 
                 		{
                 	    healthImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/health2.png"));
 						} catch (IOException e) 
                		 {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
                	}
                	
                	if(numOfHits == 3)
                	{
                		 try 
                 		{
                 	    healthImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/health3.png"));
 						} catch (IOException e) 
                		 {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
                	}
                	
                	if(numOfHits == 4)
                	{
                		 try 
                 	{
                 	   healthImg = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/gameOver.png"));
                 	   planeExplode = true;
                 	   pDeath = new PlayerDeath(x,y);
                 	   //this.x = -100;
 					} 
                		catch (IOException e) 
                	{
 							e.getCause();
 					}
                	}
                		
                	hb = new HealthBar(healthImg,5,730);
                	if(numOfHits >= 4)
                	{
                		hb = new HealthBar(healthImg,400,10);
                		this.x = -100;
                	}
                    System.out.println("Explosion! Reduce Health");
        
                }
            }
         
            if(bulletLimiter == 100)
            {
            	bulletLimiter = 0;
            	shoot = true;
            }
        }
    }

 public void drawBackGroundWithTileImage() 
 {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) 
        {
            for (int j = 0; j <= NumberX; j++) 
            {
                g2.drawImage(sea, j * TileWidth, 
                        i * TileHeight + (move % TileHeight), TileWidth, 
                        TileHeight, this);
            }
        }
        
        if(planeExplode == false)
        {
        move += speed;
        }
 }
 
 

 // Some Logic, Draw and Updates go in here....
    public void drawDemo() throws IOException, InterruptedException 
    {
    	
    	// Creating The Level Of Difficulty...
    	if(addExtra)
    	{
    	Enemy e = new Enemy(genSpeed.nextInt(2) + 1, generator);
    	enemies.add(e);
    	addExtra = false;
    	}
    	
    	// ========= Removing Bullets that are off screen For the Player.... =========
    	for(int i = 0; i < bullets.size(); i++)
    	{
    		if(bullets.get(i).outOfBounds == true)
    		{
    			bullets.remove(i);
    		}
    	} //======== Removing the bullets that are off screen For the Enemy Bullets ======
    	Enemy[] e = new Enemy[enemies.size()];
    	for(int i = 0; i < enemies.size(); i++)
		{
    		e[i] = enemies.get(i);
		}
    	
    	for(int i = 0; i < e.length; i++)
    	{
    		for(int j = 0; j < e[i].eBullets.size(); j++)
    		{
    			if(e[i].eBullets.get(j).outOfBounds == true)
    			{
    				e[i].eBullets.remove(j);
    			}
    		}
    	}
    		drawBackGroundWithTileImage();
    		
    		// This will Spawn the Amount of enemies that will need to be spawned....
    		// ================ This is the End of the Spawning of Enemies ============
    		
    		if(planeExplode == false)
    		{
    			I1.draw(this);
                I2.draw(this);
                I3.draw(this);
    		}
        	
    		if(planeExplode ==  false)
    		{
    			I1.update();
            	I2.update();
            	I3.update();
            	if(sk.score > 20 && sk.score < 30)
            	{
            	boss.update(m, bullets);
            	if(boss.EXPLODING == true)
            	{
            		
            		//d.update();
            		if(boss.countE == 39)
            		{
            			biggerDeath = new PlayerDeath(boss.explodeX,boss.explodeY);
            		}
            		biggerDeath.update();
            		biggerDeath.draw(this, g2);
            	}
            	else
            	{
            		boss.draw(this, g2);
            		EnemyBullet [] bs = new EnemyBullet[ boss.eBullets.size() ];
	            	for(int a = 0; a < bs.length; a++)
	            	{
	            	bs[a] = boss.eBullets.get(a);
	            	}
	            
	            	for(int b = 0; b <bs.length; b++)
	            	{
	            	bs[b].update();
	            	bs[b].draw(this,g2);
	            	}
            	}
            	boss.draw(this, g2);
            	}
            	
            	
            	Enemy [] es = new Enemy[enemies.size()];
        		for(int i = 0; i < enemies.size(); i++)
        		{
        		es[i] = enemies.get(i);
        		}
        
        		for(int i = 0; i<es.length; i++)
        		{
        		if(es[i].EXPLODING == true)
    			{
        			es[i].eBullets.clear();
    				if(es[i].countE == 39)
    				{
    				System.out.println("Death should have been instantiated...");
    				d = new Death(es[i].explodeX, es[i].explodeY,true);
    				//es[i].eBullets.clear();
    				}
    				d.update();
    				es[i].update();
    				d.draw(this, g2);
    			}
    			else
    			{
    			es[i].update();	
    					EnemyBullet [] bs = new EnemyBullet[ es[i].eBullets.size() ];
    	            	for(int a = 0; a < bs.length; a++)
    	            	{
    	            	bs[a] = es[i].eBullets.get(a);
    	            	}
    	            
    	            	for(int b = 0; b <bs.length; b++)
    	            	{
    	            	bs[b].update();
    	            	bs[b].draw(this,g2);
    	            	}
        			es[i].draw(this);
    			}
        	}
    		
    		}
            // When the Bullets are Fired....
           
          //  boss.draw(this, g2);
          //  e1.draw(this);
            boolean boom;
            int z = 0;
            for(Death d: bulletExplosion)
            {
            d.update();
            d.draw(this, g2);
            }
            if(planeExplode == false)
            {
            m.draw(this);
           // m2.draw(this);
            }
            
            if(planeExplode == true)
            {
            pDeath.update();
            pDeath.draw(this, g2);
            if(pDeath.counter > 30 || m.x == -100)
            {
            	 GameOver gg = new GameOver(300,300);
            	 gg.draw(this, g2);
            }
            }
            

            
            hb.draw(this, g2);
            if(bulletFired == true)
            {
            	
            	Bullet [] bs = new Bullet[bullets.size()];
            	for(int i = 0; i < bullets.size(); i++)
            	{
            	bs[i] = bullets.get(i);
            	}
            
            	for(int i = 0; i<bs.length; i++)
            	{
            	bs[i].update();
            	bs[i].draw(this,g2);
            	
            	}
            }// End if the Bullet Fired Drawing and Updating...
            sk.draw(this, g2);
    }

    public void paint(Graphics g) 
    {
        if(bimg == null) 
        {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width, 
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        try 
        {
			drawDemo();
		} catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        g.drawImage(bimg, 0, 0, this);
    }

    public void start() 
    {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void run() 
    {
    	
        Thread me = Thread.currentThread();
        while (thread == me)
        {
            repaint();  
          try 
            {
                thread.sleep(25);
            } catch (InterruptedException e) 
          {
                break;
          }
            
        }
    }

    public static void main(String argv[]) 
    {
        final game1942WithObserver demo = new game1942WithObserver();
        demo.init();
        JFrame f = new JFrame("Wing Man Sam!!!! HAHHA");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(1024, 768));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

    public Image getSprite(String location)
    {
    Image i = null;
    try 
    {
		i = ImageIO.read(new File(location));
		
	} catch (IOException e) 
    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return i;
    }
}

