package Graficos;

public class Pantalla {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH -1;
	
	public static final byte BIT_MIRROR_X = 0x01;//flipea de acuerdo eje x 
	public static final byte BIT_MIRROR_Y = 0x02;//flipea de acuerdo eje y
	public int[]pixels;
	
	//public int[] tiles =new int[MAP_WIDTH*MAP_WIDTH];
	//public int[] colores =new int[MAP_WIDTH*MAP_WIDTH*4];
	
	public int xOffset = 0;//lo de la camara
	public int yOffset=0;
	
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	public Pantalla(int width,int height, SpriteSheet sheet){
		this.width=width;
		this.height=height;
		this.sheet = sheet;
		
		pixels = new int[width*height];
		
		
		
		
	}
	
	public void render(int xPos,int yPos,int tile,int color,int mirrorDir,int scale){
		xPos-= xOffset;
		yPos-= yOffset;
		
		boolean mirrorX=(mirrorDir & BIT_MIRROR_X)>0;//
		boolean mirrorY=(mirrorDir & BIT_MIRROR_Y)>0;
		//dar un nombre a cada tile
		int xTile=tile %32;
		int yTile=tile/32;
		int tileOffset =(xTile<<3)+(yTile<<3)*sheet.width;
		int scaleMap=scale-1;
		
		for(int y=0;y<8;y++){
			int ySheet=y;
			if(mirrorY) 
				ySheet=7-y;
			
			
			int yPixel=y+yPos+(y*scaleMap)-((scaleMap<<3)/2);
			for(int x=0;x<8;x++){
				int xSheet=x;
				if(mirrorX) 
					xSheet=7-x;
			
				int xPixel = x+ xPos +(x*scaleMap)-((scaleMap<<3)/2);
				int col =(color>> (sheet.pixels[xSheet+ySheet*sheet.width+tileOffset]*8))& 255;//te da color data de donde tile estas
				if(col<255)
					for(int yScale=0;yScale<scale;yScale++){
						if(yPixel+yScale < 0 || yPixel +yScale>=height)
							continue;
						for(int xScale =0;xScale<scale;xScale++){
							if(xPixel+xScale<0||xPixel+xScale>=width)
								continue;
							pixels[(xPixel+xScale)+(yPixel+yScale)*width]=col;
						}
					}
					
					
					
			}
		}
		
	}
	
	public void setOffset(int xOffset2, int yOffset2) {
		// TODO Auto-generated method stub
		xOffset=xOffset2;
		yOffset=yOffset2;
		
	}
}
