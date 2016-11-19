package entidad;

import juego.Colours;
import juego.InputHandler;
import nivel.Nivel;

public class Orco extends Jugador {

	public Orco(Nivel nivel, int x, int y, InputHandler input, String username) {
		super(nivel, x, y, input, username);
		color=Colours.get(-1, 111,300, 450);
		this.ataque = 15;
		this.defensa = 10;
		this.velocidad = 2;
	}

	@Override
	protected boolean puedeAtacar() {

		return energia >= this.ataque;
	}

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
		return this.obtenerPuntosDeAtaque()*2+this.obtenerPuntosDeDefensa()+this.obtenerPuntosDeVelocidad();
	}
}
