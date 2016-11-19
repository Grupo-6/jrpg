package entidad;

public abstract class Casta {
	protected int velocidad;
	protected int defensa;
	protected int ataque;

	public abstract int ataqueEspecial(int ataque);
	public int getVelocidad() {
		return velocidad;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getAtaque() {
		return ataque;
	}
}
