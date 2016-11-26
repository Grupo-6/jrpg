package main.java.entidad;

public class Asesino extends Casta {
	public Asesino (){
		ataque=10;
		defensa = 2;
		rapides =2;
	}

	@Override
	public int ataqueEspecial(int ataque) {
		return (int) (Math.random()*(ataque+2))+ataque;
	}
}
