package main.java.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.java.util.JugadorModelo;



public class HiloEscrituraServidor implements Runnable {

	private DataOutputStream out;
	private ArrayList<Socket> usuarios;
	private ArrayList<JugadorModelo> jugadores;
	private Socket socket;

	public HiloEscrituraServidor(Socket so, ArrayList<Socket> usuarios, ArrayList<JugadorModelo> personajes,
			int numCliente) {
		this.usuarios = usuarios;
		this.jugadores = personajes;
		this.socket = so;
		try {
			out = new DataOutputStream(usuarios.get(numCliente).getOutputStream());
		} catch (IOException e1) {
			System.err.println("Problema con el cliente" + numCliente);
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {

		// Inicializamos los canales de comunicacion y mandamos un mensaje
		// de bienvenida
		// Ciclo infinito para escuchar por mensajes del cliente
		String gsonPersonajes = "";
		Gson gson = new Gson();

		while (true) {

			gsonPersonajes = gson.toJson(jugadores);
			// Cuando se recibe un mensaje se envia a todos los usuarios
			// conectados

			System.out.println("Personajes antes gson:" + jugadores);
			System.out.println("Servidor escribe: " + gsonPersonajes);
			esperar();

			try {
				out.writeUTF(gsonPersonajes);

			} catch (IOException e) {

				for (int i = 0; i < usuarios.size(); i++) {
					if (usuarios.get(i) == socket) {
						usuarios.remove(i);
						jugadores.remove(i);

					}
				}
				// si se desconecta el cliente lo elimino de la lista

				break;
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
