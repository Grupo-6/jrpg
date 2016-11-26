package main.java.util;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

import main.java.Graficos.Font;
import main.java.Graficos.Pantalla;
import main.java.entidad.Asesino;
import main.java.entidad.Elfo;
import main.java.entidad.Guerrero;
import main.java.entidad.Humano;
import main.java.entidad.Jugador;
import main.java.entidad.Orco;
import main.java.entidad.Personaje;
import main.java.juego.Colours;

public class JugadorModelo {

	private String nombre = "";

	private String raza;
	private String casta;
	
	private int x;
	private int y;
	private int movingDir;
	private int numPasos;
	private int velocidad;
	private int defensa;
	private int ataque;
	
	

	private int nivel;
	private int experiencia;
	
	public JugadorModelo(Jugador jugador) {
		this.nombre = jugador.getUsername();

		this.raza = jugador.getPersonaje().getRaza();
		this.nivel= jugador.getPersonaje().getLvl();
		this.experiencia= jugador.getPersonaje().getExperiencia();
		this.casta= jugador.getPersonaje().getCasta();
		this.velocidad=jugador.getPersonaje().getVelocidad();
		this.ataque=jugador.getPersonaje().getAtaque();
		this.defensa=jugador.getPersonaje().getDefensa();
		
		this.numPasos=jugador.getNumPasos();
		this.movingDir=jugador.getMovingDir();
		

		this.x = jugador.x;
		this.y = jugador.y;
	}
	public JugadorModelo(Personaje personaje){
		this.nombre = personaje.getUsername();

		this.raza = personaje.getRaza();
		this.nivel= personaje.getLvl();
		this.experiencia= personaje.getExperiencia();
		this.casta= personaje.getCasta();
		this.velocidad=personaje.getVelocidad();
		this.ataque=personaje.getAtaque();
		this.defensa=personaje.getDefensa();
		
		this.numPasos=1;
		this.movingDir=1;
		

		this.x = 0;
		this.y = 0;
	}

	
	public int getDefensa() {
		return defensa;
	}


	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}


	public int getAtaque() {
		return ataque;
	}


	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}


	public JugadorModelo(String nombre, String raza, int x, int y, int movingDir, int numPasos, int velocidad) {
		super();
		this.nombre = nombre;
		this.raza = raza;
		this.x = x;
		this.y = y;
		this.movingDir = movingDir;
		this.numPasos = numPasos;
		this.velocidad = velocidad;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMovingDir() {
		return movingDir;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}
	public String getCasta() {
		return casta;
	}


	public void setCasta(String casta) {
		this.casta = casta;
	}


	public int getNivel() {
		return nivel;
	}


	public void setNivel(int nivel) {
		this.nivel = nivel;
	}


	public int getExperiencia() {
		return experiencia;
	}


	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	public int getNumPasos() {
		return numPasos;
	}

	public void setNumPasos(int numPasos) {
		this.numPasos = numPasos;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	@Override
	public String toString() {
		return "JugadorModelo [nombre=" + nombre + ", raza=" + raza + ", x=" + x + ", y=" + y + ", movingDir="
				+ movingDir + ", numPasos=" + numPasos + ", velocidad=" + velocidad + "]";
	}

	public static void mostrarJugador(Pantalla pantalla, JugadorModelo jugador, int color) {
		int xTile = 0;
		int yTile = 28;
		int scale = 1;
		int modificador = 8 * scale;
		String username = jugador.getNombre();
		int xOffset = jugador.getX() - modificador / 2; // centra al pj
		int yOffset = jugador.getY() - modificador / 2 - 4;
		// variables para animaciones
		int velocidadCaminar = 4;
		int flipTop = ((jugador.getNumPasos() >> velocidadCaminar) & 1);
		int flipBottom = ((jugador.getNumPasos() >> velocidadCaminar) & 1);
		// �ra animar
		if (jugador.getMovingDir() == 1) {
			xTile += 2;
		} else if (jugador.getMovingDir() > 1) {
			xTile += 4 + ((jugador.getNumPasos() >> velocidadCaminar) & 1) * 2;
			flipTop = (jugador.getMovingDir()-1) % 2;
		}

		pantalla.render(xOffset + (modificador * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		pantalla.render(xOffset + modificador - (modificador * flipTop), yOffset, (xTile + 1) + yTile * 32, color,
				flipTop, scale);
		pantalla.render(xOffset + (modificador * flipBottom), yOffset + modificador, xTile + (yTile + 1) * 32, color,
				flipBottom, scale);
		pantalla.render(xOffset + modificador - (modificador * flipBottom), yOffset + modificador,
				(xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);

		if (username != null) {
			Font.render(username, pantalla, xOffset - ((username.length() - 1) / 2 * 8), yOffset - 10,
					Colours.get(-1, -1, -1, 555), 1);
		}
	}
	public static Personaje recrearPersonaje(JugadorModelo modelo) {

		Personaje personaje= new Humano("");
		
		if (modelo.getRaza().equals("Humano"))
			personaje = new Humano(modelo.getNombre());
		
		if (modelo.getRaza().equals("Orco"))
			personaje = new Orco(modelo.getNombre());
		
		if(modelo.getRaza().equals("Elfo"))
			personaje = new Elfo(modelo.getNombre());
		
		if (modelo.getCasta().equals("Guerrero"))
			personaje.setCasta(new Guerrero());
		else
			personaje.setCasta(new Asesino());

		personaje.setExperiencia(modelo.getExperiencia());
		personaje.setLvl(modelo.getNivel());
		
		personaje.setAtaque(modelo.getAtaque());
		personaje.setDefensa(modelo.getDefensa());
		personaje.setVelocidad(modelo.getVelocidad());
		
		return personaje;
	}
}
