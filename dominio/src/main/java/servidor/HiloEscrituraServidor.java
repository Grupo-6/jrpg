package main.java.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.java.baseDeDatos.ConexionBD;
import main.java.util.JugadorModelo;
import sun.awt.util.IdentityArrayList;

public class HiloEscrituraServidor implements Runnable {

	private DataOutputStream out;
	private ArrayList<Socket> usuarios;
	private ArrayList<JugadorModelo> jugadores;
	private Socket socket;
	private ArrayList<HiloEscrituraServidor> hilosEscritura;
	private ArrayList<HiloLecturaServidor> hilosLectura;
	private boolean correr = true;

	public HiloEscrituraServidor(Socket so, ArrayList<Socket> usuarios, ArrayList<JugadorModelo> personajes,
			int numCliente, ArrayList<HiloEscrituraServidor> hilosEscritura,ArrayList<HiloLecturaServidor> hilosLectura) {
		this.hilosEscritura = hilosEscritura;
		this.hilosLectura = hilosLectura;
		this.usuarios = usuarios;
		this.jugadores = personajes;
		this.socket = so;
		try {
			out = new DataOutputStream(usuarios.get(numCliente).getOutputStream());
		} catch (IOException e1) {
			// System.err.println("Problema con el cliente" + numCliente);
			e1.printStackTrace();
		}
	}

	public boolean isCorrer() {
		return correr;
	}

	public void setCorrer(boolean correr) {
		this.correr = correr;
	}

	@Override
	public void run() {

		// Inicializamos los canales de comunicacion y mandamos un mensaje
		// de bienvenida
		// Ciclo infinito para escuchar por mensajes del cliente
		String gsonPersonajes = "";
		Gson gson = new Gson();

		while (true) {

			if (correr) {
				gsonPersonajes = gson.toJson(jugadores);
				// Cuando se recibe un mensaje se envia a todos los usuarios
				// conectados

				esperar();

				try {
					out.writeUTF(gsonPersonajes);

				} catch (IOException e) {

					for (int i = 0; i < usuarios.size(); i++) {
						if (usuarios.get(i) == socket) {
							if (usuarios.size() > i) {
								usuarios.remove(i);
								if (jugadores.size() > i) {

									try {
										ConexionBD conect = new ConexionBD();
										conect.cargarDatosDeUsuario(jugadores.get(i).getNombre(),
												new Gson().toJson(jugadores.get(i)));
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									jugadores.remove(i);				// si se desconecta el cliente lo elimino de la lista

								}
								if (hilosEscritura.size() > i) {
									hilosEscritura.remove(i);
								}
								if (hilosLectura.size() > i) {
									hilosLectura.remove(i);
								}
							}

						}
					}
				}

				
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
