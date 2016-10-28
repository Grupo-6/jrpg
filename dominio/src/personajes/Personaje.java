package personajes;

import java.util.Scanner;

public abstract class Personaje implements Atacable {

	protected int energia = 100;
	protected int salud = 100;
	protected int lvl=1;

	protected int energiaMax = 100;
	protected int saludMax = 100;

	protected int ataque;
	protected int defensa;
	protected int velocidad;
	
	protected int experiencia=0;
	protected int expMax=100;

	protected Casta casta=null;
	protected final int guerrero = 0;
	protected final int asesino = 1;

	public final void atacar(Atacable atacado) {
		if (puedeAtacar()) {
			atacado.serAtacado(calcularPuntosDeAtaque());
			energia -= calcularPuntosDeAtaque();
			despuesDeAtacar();
		}
	}
	public final void lvlUp(){
		
		if(this.lvl<10){
			this.lvl+=1;
			this.expMax+=100;
			this.experiencia=0;
			Scanner entrada = new Scanner (System.in);
			int option=0;
			for(int i =0;i<=5;i++){
				while(option<1 || option >3)
					option=entrada.nextInt();
				switch(option){
					case 1:
						this.ataque+=1;
						break;
					case 2:
						this.defensa+=1;
						break;
					case 3: 
						this.velocidad+=1;
						break;
			}
				option=0;
		}
			entrada.close();
		}
	}
	public final void aumentarXP(int lvlEnemy){
		if(this.lvl <10){
			this.experiencia+=(lvlEnemy)*20;
			int xp=this.experiencia-this.expMax;
			while(xp>=0){
				this.lvlUp();
				this.experiencia+=xp;
				xp=this.experiencia-this.expMax;
			}
		}
	}
	@Override
	public void serAtacado(int danio) {
		danio = this.obtenerPuntosDeDefensa() - danio;
		if (danio > 0)// si el danio es mayor a 0 es que el danio no pudo
			return;// contra la defenza entonces no baja vida
		else
			this.salud -= danio;
	}

	protected void despuesDeAtacar() {
	}

	protected abstract boolean puedeAtacar();

	public abstract int obtenerPuntosDeDefensa();

	public abstract int obtenerPuntosDeVelocidad();

	protected abstract int calcularPuntosDeAtaque();

	public void serCurado() {
		this.salud = saludMax;
	}

	public void serEnergizado() {
		this.energia = energiaMax;
	}

	public int getSalud() {
		return this.salud;
	}

	public int obtenerPuntosDeAtaque() {
		return calcularPuntosDeAtaque();
	}
	public void setCasta(Casta casta){
		this.casta = casta;
	}

}
