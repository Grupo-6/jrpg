package main.java.Graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public String path;
	public int width;
	public int heigth;
	
	public int[]pixels;
	
	public SpriteSheet(String path){
		BufferedImage image=null;
		
		try {
			image= ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(image ==null){
			return;
		}
		this.path=path;
		this.width=image.getWidth();
		this.heigth=image.getHeight();
		
		pixels=image.getRGB(0, 0, width, heigth, null, 0, width);
		
		for(int i=0;i<pixels.length;i++){
			pixels[i]=(pixels[i] & 0xff)/64;//remueve el alfa
		}
		for (int i=0;i<8;i++){//muestra los primeros 0 pixeles en la sprite sheet
			System.out.println(pixels[i]);
		}
		
	}
	
}
