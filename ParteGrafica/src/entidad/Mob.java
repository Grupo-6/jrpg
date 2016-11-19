package entidad;

import nivel.Nivel;

public abstract class Mob extends Entidad {

	protected String nombre;
	protected int velocidad;
	protected int numPasos =0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	public Mob(Nivel nivel, String nombre, int x, int y, int velocidad) {
		super(nivel);//llama constructor de entidad
		this.nombre=nombre;
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
	
	public String getNombre(){
		return nombre;
	}
	
}


