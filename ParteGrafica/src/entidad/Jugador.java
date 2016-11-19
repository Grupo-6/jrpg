package entidad;

import java.util.Scanner;

import Graficos.Font;
import Graficos.Pantalla;
import juego.Colours;
import juego.InputHandler;
import nivel.Nivel;


public  abstract class Jugador extends Mob implements Atacable{
	
	//atributos
	protected int energia = 100;
	protected int salud = 100;
	protected int lvl = 1;
	protected int experiencia = 0;
	protected Casta casta = null;
	

	protected int energiaMax = 100;
	protected int saludMax = 100;
	protected int expMax = 100;
	
	protected int ataque;
	protected int defensa;
	protected int velocidad;
	
	
	protected int scale=1;
	protected InputHandler input;
	protected int color;//fondo ,outline colores
	protected String username;
	
	public Jugador(Nivel nivel, int x, int y,InputHandler input,String username) {
		super(nivel, "Jugador", x, y, 1);
		this.input=input;
		this.username=username;
		
		
	}

	
	
	
	public void tick() {
		int xa=0;
		int ya=0;
		
		if(input.up.isPressed()){ya--;}
		if(input.down.isPressed()){ya++;}
		if(input.left.isPressed()){xa--;}
		if(input.right.isPressed()){xa++;}
		
		if(xa!=0|| ya!=0){
			mover(xa,ya);
			isMoving= true;
		}
		else{
			isMoving=false;
		}
	}

	
	public void render(Pantalla pantalla) {
		
		int xTile=0;
		int yTile=28;
		
		int modificador = 8*scale;
		int xOffset = x -modificador/2;	//centra al pj
		int yOffset = y -modificador/2 - 4;
		//variables para animaciones
		int velocidadCaminar=4;
		int flipTop=((numPasos>> velocidadCaminar)&1);
		int flipBottom=((numPasos>> velocidadCaminar)&1);
	//àra animar
		if(movingDir==1){
			xTile+=2;
		}else if(movingDir>1){
			xTile+=4 + ((numPasos>>velocidadCaminar)&1)*2;
			flipTop=(movingDir-1)%2;
		}
		
		pantalla.render(xOffset+(modificador*flipTop), yOffset, xTile+yTile*32, color,flipTop,scale);
		pantalla.render(xOffset+modificador-(modificador*flipTop), yOffset, (xTile+1)+yTile*32, color,flipTop,scale);
		pantalla.render(xOffset+(modificador*flipBottom), yOffset+modificador, xTile+(yTile+1)*32, color,flipBottom,scale);
		pantalla.render(xOffset+modificador-(modificador*flipBottom), yOffset+modificador, (xTile+1)+(yTile+1)*32, color,flipBottom,scale);
		
		if(username!=null){
			Font.render(username, pantalla, xOffset-((username.length()-1)/2*8), yOffset-10, Colours.get(-1, -1, -1, 555), 1);
		}
	}
	public boolean colisiona(int xa, int ya) {
		int xMin= 0;
		int xMax=7;
		int yMin=3;
		int yMax=7;
		//colisiones del bottom del sprite
		for(int x = xMin;x<xMax;x++){
			if(isSolidTile(xa,ya,x,yMin)){
				return true;
			}
		}
		for(int x = xMin;x<xMax;x++){
			if(isSolidTile(xa,ya,x,yMin)){
				return true;
			}
		}
		for(int y = yMin;y<yMax;y++){
			if(isSolidTile(xa,ya,xMin,yMin)){
				return true;
			}
		}
		for(int y = yMin;y<yMax;y++){
			if(isSolidTile(xa,ya,xMax,yMin)){
				return true;
			}
		}
		return false;
	}
////////////////metodos de gameplay
	public final void atacar(Atacable atacado) {
		if (puedeAtacar()) {
			int ataque=obtenerPuntosDeAtaque();
			
			atacado.serAtacado(ataque);
			restarEnergia(ataque);
			despuesDeAtacar();
		}
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public int getEnergiaMax() {
		return energiaMax;
	}

	public void setEnergiaMax(int energiaMax) {
		this.energiaMax = energiaMax;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	@Override
	public void serAtacado(int danio) {
		danio = this.obtenerPuntosDeDefensa() - danio;
		if (danio > 0)// si el danio es mayor a 0 es que el danio no pudo
			return;// contra la defenza entonces no baja vida
		else
			this.salud += danio;
		
	}

	protected void despuesDeAtacar() {
	}

	protected abstract boolean puedeAtacar();

	public abstract int obtenerPuntosDeDefensa();
	public abstract int ataqueEspecial();
	public abstract int obtenerPuntosDeVelocidad();

	protected abstract int calcularPuntosDeAtaque();

	public final void lvlUp() {

		if (this.lvl < 10) {
			this.lvl += 1;
			this.expMax += 100;
			this.experiencia = 0;
			Scanner entrada = new Scanner(System.in);
			int option = 0;
			for (int i = 0; i <= 5; i++) {
				while (option < 1 || option > 3)
					option = entrada.nextInt();
				switch (option) {
				case 1:
					this.ataque += 1;
					break;
				case 2:
					this.defensa += 1;
					break;
				case 3:
					this.velocidad += 1;
					break;
				}
				option = 0;
			}
			entrada.close();
		}
	}

	public final void aumentarXP(int lvlEnemy) {
		if (this.lvl < 10) {
			this.experiencia += (lvlEnemy) * 20;
			int xp = this.experiencia - this.expMax;
			while (xp >= 0) {
				this.lvlUp();
				this.experiencia += xp;
				xp = this.experiencia - this.expMax;
			}
		}
	}

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

	public void setCasta(Casta casta) {
		this.casta = casta;
	}
	public String getCasta(){
		return casta.getClass().getSimpleName();
	}
	

	public String getNombre() {
		return nombre;
	}


	public void restarEnergia(int res) {
		if ((energia - res) < 0)
			energia = 0;
		else {
			energia -= res;
		}
	}

	public int ataqueEspecialRaza() {
		int ataque = this.ataqueEspecial();
		this.restarEnergia(ataque);
		return ataque;
	}
	
	public int ataqueEspecialCasta() {
		int ataque = casta.ataqueEspecial(this.obtenerPuntosDeAtaque());
		this.restarEnergia(ataque);
		return ataque;
	}

	

}
