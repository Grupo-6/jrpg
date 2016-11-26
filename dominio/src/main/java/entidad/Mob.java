package main.java.entidad;

import main.java.nivel.Nivel;
import main.java.nivel.Tile;

public abstract class Mob extends Entidad {

	protected int velocidad;
	protected int numPasos =0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	
	
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getNumPasos() {
		return numPasos;
	}

	public void setNumPasos(int numPasos) {
		this.numPasos = numPasos;
	}

	public int getMovingDir() {
		return movingDir;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}

	public Mob(Nivel nivel, int x, int y, int velocidad) {
		super(nivel);//llama constructor de entidad
		this.velocidad=velocidad;
		this.x=x;
		this.y=y;
	}
	
	public void mover(int xa,int ya){
		if(xa != 0 && ya !=0){
			mover(xa,0);//mueve en un direccion
			mover(0,ya);
			numPasos--;//lo cuenta 2 veces restamos 1
			return;
		}
		numPasos++;
		if(!colisiona(xa,ya)){
			if(ya<0) 
				movingDir=0;//mueve hacia arriba
			if(ya>0)//mueve hacia abajo
				movingDir=1;
			if(xa<0)
				movingDir=2;
			if(xa>0)
				movingDir=3;
			x+=xa*velocidad;
			y+=ya*velocidad;
			
		}
	}
	public abstract boolean colisiona(int xa, int ya);
	
	
	protected boolean isSolidTile(int xa , int ya,int x,int y){
		
		if(nivel==null){
			return false;
		}
		Tile lastTile=nivel.getTile((this.x+x)>>3,(this.y +y)>>3);
		Tile newTile = nivel.getTile((this.x+x+xa)>>3,(this.y+y+ya)>>3);
		if(!lastTile.equals(newTile)&& newTile.isSolid()){
			return true;
		}
		return false;
		
		
	}
	
}


