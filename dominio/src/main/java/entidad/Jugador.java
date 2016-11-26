package main.java.entidad;

import java.util.ArrayList;

import main.java.Graficos.Font;
import main.java.Graficos.Pantalla;
import main.java.juego.Colours;
import main.java.juego.InputHandler;
import main.java.nivel.Nivel;
import main.java.util.JugadorModelo;


public   class Jugador extends Mob {
	
	//atributos


	private int scale=1;
	private InputHandler input;
	private int color;//fondo ,outline colores
	private Personaje personaje;
	public Jugador(Nivel nivel, int x, int y,InputHandler input,Personaje personaje) {
		super(nivel, x, y, 1);
		color=personaje.color;
		this.input=input;
		this.personaje = personaje;
		this.nivel.setMiNombre(personaje.username);
		
	}
	public void setJugadores(ArrayList<JugadorModelo> jugadores){
		nivel.setJugadores(jugadores);
	}
	
	
	public String getUsername() {
		return personaje.username;
	}


	public void setUsername(String username) {
		this.personaje.username = username;
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
	//�ra animar
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
		
		if(personaje.username!=null){
			Font.render(personaje.username, pantalla, xOffset-((personaje.username.length()-1)/2*8), yOffset-10, Colours.get(-1, -1, -1, 555), 1);
		}
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	public boolean colisiona(int xa, int ya) {
		int xMin= 0;
		int xMax=7;
		int yMin=3;
		int yMax=7;
		//colisiones del bottom del sprite
		for(int x = xMin;x<xMax;x++){
			if(isSolidTile(xa,ya,x,yMin)){
				return true;
			}
		}
		for(int x = xMin;x<xMax;x++){
			if(isSolidTile(xa,ya,x,yMin)){
				return true;
			}
		}
		for(int y = yMin;y<yMax;y++){
			if(isSolidTile(xa,ya,xMin,yMin)){
				return true;
			}
		}
		for(int y = yMin;y<yMax;y++){
			if(isSolidTile(xa,ya,xMax,yMin)){
				return true;
			}
		}
		return false;
	}
	


	

}
