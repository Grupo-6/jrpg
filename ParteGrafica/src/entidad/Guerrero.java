package entidad;

public class Guerrero extends Casta{
	public  Guerrero() {
		ataque=5;
		defensa = 8;
		velocidad =2;
	}

	@Override
	public int ataqueEspecial(int ataque) {
		
		return ataque*2;
	}
}
