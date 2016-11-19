package entidad;

import Graficos.Pantalla;
import juego.Colours;
import juego.InputHandler;
import nivel.Nivel;


public class Jugador extends Mob{
	private int scale=1;
	private InputHandler input;
	private int color = Colours.get(-1, 111,300, 543);//fondo ,outline colores
	
	public Jugador(Nivel nivel, int x, int y,InputHandler input) {
		super(nivel, "Jugador", x, y, 1);
		this.input=input;
	}

	
	
	
	public void tick() {
		int xa=0;
		int ya=0;
		
		if(input.up.isPressed()){ya--;}
		if(input.down.isPressed()){ya++;}
		if(input.left.isPressed()){xa--;}
		if(input.right.isPressed()){xa++;}
		
		if(xa!=0|| ya!=0){
			mover(xa,ya);
			isMoving= true;
		}
		else{
			isMoving=false;
		}
	}

	
	public void render(Pantalla pantalla) {
		
		int xTile=0;
		int yTile=28;
		
		int modificador = 8*scale;
		int xOffset = x -modificador/2;	//centra al pj
		int yOffset = y -modificador/2 - 4;
		//variables para animaciones
		int velocidadCaminar=4;
		int flipTop=((numPasos>> velocidadCaminar)&1);
		int flipBottom=((numPasos>> velocidadCaminar)&1);
	//àra animar
		if(movingDir==1){
			xTile+=2;
		}else if(movingDir>1){
			xTile+=4 + ((numPasos>>velocidadCaminar)&1)*2;
			flipTop=(movingDir-1)%2;
		}
		
		pantalla.render(xOffset+(modificador*flipTop), yOffset, xTile+yTile*32, color,flipTop,scale);
		pantalla.render(xOffset+modificador-(modificador*flipTop), yOffset, (xTile+1)+yTile*32, color,flipTop,scale);
		pantalla.render(xOffset+(modificador*flipBottom), yOffset+modificador, xTile+(yTile+1)*32, color,flipBottom,scale);
		pantalla.render(xOffset+modificador-(modificador*flipBottom), yOffset+modificador, (xTile+1)+(yTile+1)*32, color,flipBottom,scale);
		
	}
	public boolean colisiona(int xa, int ya) {
		
		return false;
	}


}
