package main.java.nivel;

import java.util.ArrayList;
import java.util.List;

import main.java.Graficos.Pantalla;
import main.java.entidad.Entidad;
import main.java.entidad.Jugador;
import main.java.juego.Colours;
import main.java.util.JugadorModelo;




public class Nivel {

	private byte[] tiles; // array de ids para que tile esta en esa coordenada
	public int width;
	public int height;
	public List<Jugador> entidades = new ArrayList<Jugador>();
	private ArrayList<JugadorModelo> jugadores = new ArrayList<>();
	private static int colorElfo = Colours.get(-1, 330, 240, 543);
	private static int colorOrco = Colours.get(-1, 111, 300, 450);
	private static int colorHumano = Colours.get(-1, 111, 184, 543);
	private String miNombre;

	public void setMiNombre(String miNombre) {
		this.miNombre = miNombre;
	}

	public Nivel(int width, int height) {
		tiles = new byte[width * height];
		this.width = width;
		this.height = height;
		this.generarLvl();
	}

	public void generarLvl() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 10 < 9)// sacar el math random *100 y poner 9
					tiles[x + y * width] = Tile.GRASS.getId();
				else
					tiles[x + y * width] = Tile.STONE.getId();

			}
		}
	}

	public void setJugadores(ArrayList<JugadorModelo> jugadores) {
		this.jugadores = jugadores;
	}

	public void tick() {
		for (Entidad e : entidades) {
			e.tick();
		}
	}

	public void renderTiles(Pantalla screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);
		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {// anterior
																					// version
																					// rendereaba
																					// todo
																					// y
																					// no
																					// solo
																					// de
																					// a
																					// partes
			// ahora te da el id con el yOffset>>3 y renderea solo esta parte
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	public void renderEntidades(Pantalla pantalla) {
		for (Jugador e : entidades) {
			e.render(pantalla);

		}
		for (JugadorModelo jugador : jugadores) {
			if (!miNombre.equals(jugador.getNombre())) {
				String raza = jugador.getRaza();
				System.out.println(jugador.getRaza().equals("Humano"));

				if (raza.equals("Humano")) {
					JugadorModelo.mostrarJugador(pantalla, jugador, colorHumano);
					System.out.println("Se cre un HUMANO");

				}
				if (raza.equals("Orco")) {
					JugadorModelo.mostrarJugador(pantalla, jugador, colorOrco);
					System.out.println("Se cre un ORCO");
				}
				if (raza.equals("Elfo")) {
					JugadorModelo.mostrarJugador(pantalla, jugador, colorElfo);
					System.out.println("Se cre un ELFO");

				}
			}

		}

	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];

	}

	public void addEntidad(Jugador entidad) {
		this.entidades.add(entidad);
	}

}
