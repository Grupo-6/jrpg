package main.java.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.java.batalla.VentanaBatalla;
import main.java.entidad.Jugador;
import main.java.entidad.Personaje;
import main.java.juego.Juego;
import main.java.util.JugadorModelo;

public class HiloLecturaJugador implements Runnable {

	private DataInputStream in;
	private Socket socket;
	private ArrayList<JugadorModelo> jugadores = new ArrayList<JugadorModelo>();
	private Jugador jugador;
	private Juego juego;

	public HiloLecturaJugador(final Socket so, Juego juego, Jugador jugador) {
		this.jugador = jugador;
		this.socket = so;
		this.juego = juego;
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String pjsGson = "";

		Type tipoObjeto = new TypeToken<ArrayList<JugadorModelo>>() {
		}.getType();

		Gson gson = new Gson();
		while (true) {
			try {
				pjsGson = in.readUTF();
				if (pjsGson.equals("batalla")) {
					modoBatalla();
				}
				if (pjsGson.charAt(0) == '[') {
					esperar();
					jugadores = gson.fromJson(pjsGson, tipoObjeto);
					juego.setJugadores(jugadores);
				}

			} catch (IOException e) {
				System.err.println("Error al leer");
			}
		}

	}

	public void modoBatalla() {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String pj = in.readUTF();
			System.out.println(pj);

			/*
			 * VentanaBatalla batalla= new VentanaBatalla(new
			 * JugadorModelo(jugador),pj,socket);
			 * batalla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void esperar() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
