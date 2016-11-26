package main.java.entidad;

import main.java.Graficos.Pantalla;
import main.java.nivel.Nivel;

public abstract class Entidad {
	public int x,y;
	protected  Nivel nivel;
	
	public Entidad(Nivel nivel){
		init(nivel);
	}
	public final void init(Nivel nivel){// corra una vez
		this.nivel=nivel;
	}
	public abstract void tick();
	public abstract void render (Pantalla pantalla);
}
