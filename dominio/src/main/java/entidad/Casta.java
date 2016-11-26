package main.java.entidad;

public abstract class Casta {
	protected int rapides;
	protected int defensa;
	protected int ataque;

	public abstract int ataqueEspecial(int ataque);
	public int getVelocidad() {
		return rapides;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getAtaque() {
		return ataque;
	}
}
