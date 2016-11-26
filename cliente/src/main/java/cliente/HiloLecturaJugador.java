package main.java.cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.java.juego.Juego;
import main.java.util.JugadorModelo;


public class HiloLecturaJugador implements Runnable {

	
	private DataInputStream in;
	
	private ArrayList<JugadorModelo> jugadores= new ArrayList<JugadorModelo>();

	private Juego juego;
	public HiloLecturaJugador(final Socket so, Juego juego) {
		Socket socket = so;
		this.juego=juego;
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
				esperar();
				
				jugadores =gson.fromJson(pjsGson, tipoObjeto);
				System.out.println(jugadores);
				juego.setJugadores(jugadores);
			} catch (IOException e) {
				System.err.println("Error al leer");
			}
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
