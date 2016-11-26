package main.java.entidad;

import main.java.juego.Colours;
;

public class Humano extends Personaje {

	public Humano( String username) {
		super(username);
		color=Colours.get(-1, 111,184, 543);
		this.ataque = 10;
		this.defensa = 15;
		this.velocidad = 5;
		
	}

	@Override
	protected boolean puedeAtacar() {

		return energia >= this.ataque;
	}

	@Override
	public int obtenerPuntosDeDefensa() {
		if (this.casta != null)
			return this.defensa + casta.getDefensa();
		else
			return this.defensa;
	}

	@Override
	protected int calcularPuntosDeAtaque() {
			
		if (this.casta != null)
			return this.ataque + casta.getAtaque();
		else
			return this.ataque;
	}

	@Override
	public int obtenerPuntosDeVelocidad() {
		if (this.casta != null)
			return this.velocidad + casta.getVelocidad();
		else
			return this.velocidad;
	}

	@Override
	public int ataqueEspecial() {
		
		return this.calcularPuntosDeAtaque()+this.obtenerPuntosDeDefensa()*2+this.obtenerPuntosDeVelocidad();
	}
}
