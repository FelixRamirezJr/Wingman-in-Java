package wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class PlayerDeath 
{
int x;
int y;
Image img;
int counter = 0;
boolean enemy;

String exSound = "C:/Users/Optimus Prime/workspace/wingman/Resources/snd_explosion1.wav";
AudioStream asExplode = null;
InputStream inExplode = null;

// Constructor that builds Sound And the Location of the 
PlayerDeath (int ex, int why)
{
x = ex+17;
y = why;

	try
	{
		inExplode = new FileInputStream(exSound);
	} catch (FileNotFoundException e2) 
	{
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
    try 
    {
		asExplode = new AudioStream(inExplode);
	} 
    catch (IOException e2) 
    {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
    AudioPlayer.player.start(asExplode); 
}

// This Sets the Constructor and also sends an explosive sound....
PlayerDeath (int ex, int why, boolean en)
{
x = ex;
y = why;
en = true;
try
{
	inExplode = new FileInputStream(exSound);
} catch (FileNotFoundException e2) 
{
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
try 
{
	asExplode = new AudioStream(inExplode);
} 
catch (IOException e2) 
{
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
AudioPlayer.player.start(asExplode); 
}


public void update() throws IOException 
{
	counter++;
	if(enemy == true)
	{
	y += 5;
	System.out.println("Y should be moving..." + y);
	}
	
	if(counter <= 5)
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_1.png"));
	}
	
	if(counter > 5 && counter <= 10 )
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_2.png"));
	}
	
	if(counter > 10 && counter <= 15)
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_3.png"));
	}
	
	if(counter > 15 && counter <= 20)
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_4.png"));
	}
	
	if(counter > 20 && counter <= 25)
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_5.png"));
	}
	
	if(counter > 25 && counter <= 30)
	{
		img = ImageIO.read(new File("C:/Users/Optimus Prime/workspace/wingman/Resources/explosion2_6.png"));
	}
	if(counter >= 30)
	{
		img = null;
	}
	
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
