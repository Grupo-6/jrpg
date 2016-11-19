package entidad;

public class Asesino extends Casta {
	public Asesino (){
		ataque=10;
		defensa = 2;
		velocidad =2;
	}

	@Override
	public int ataqueEspecial(int ataque) {
		return (int) (Math.random()*(ataque+2))+ataque;
	}
}
