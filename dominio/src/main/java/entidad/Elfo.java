package main.java.entidad;

import main.java.juego.Colours;


public class Elfo extends Personaje{

	public Elfo( String username) {
		super(username);
		color=Colours.get(-1, 330,240, 543);
		this.ataque = 12;
		this.defensa = 5;
		this.velocidad = 10;
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
		return this.obtenerPuntosDeAtaque()+this.obtenerPuntosDeDefensa()+this.obtenerPuntosDeVelocidad()*3;
	}
}
