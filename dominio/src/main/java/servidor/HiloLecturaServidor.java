package main.java.servidor;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.java.baseDeDatos.ConexionBD;
import main.java.util.JugadorModelo;

public class HiloLecturaServidor implements Runnable {
	private DataInputStream in;
	private ArrayList<Socket> usuarios;
	private ArrayList<JugadorModelo> jugadores;
	private int numCliente;
	private Socket socket;
	
	private ArrayList<HiloEscrituraServidor> hilosEscritura;
	private ArrayList<HiloLecturaServidor> hilosLectura;
	private boolean correr=true;
	
	

	public HiloLecturaServidor(Socket so, ArrayList<Socket> usuarios, ArrayList<JugadorModelo> personajes,
			int numCliente,ArrayList<HiloEscrituraServidor> hilosEscritura,ArrayList<HiloLecturaServidor> hilosLectura) {
		this.hilosEscritura=hilosEscritura;
		this.hilosLectura=hilosLectura;
		this.socket = so;
		this.usuarios = usuarios;
		this.jugadores = personajes;
		this.numCliente = numCliente;
	}

	@Override
	public void run() {
		try {
			// Inicializamos los canales el servidor lee al cliente
			in = new DataInputStream(usuarios.get(numCliente).getInputStream());

			// Ciclo infinito para escuchar por mensajes del cliente
			Gson gson = new Gson();
			JugadorModelo per;
			while (true) {
				if(correr){
				String recibido = in.readUTF();
				esperar();
				per = gson.fromJson(recibido, JugadorModelo.class);

				for (int i = 0; i < usuarios.size(); i++) {
					if (usuarios.get(i) == socket) {

						if (jugadores.size() > i)
							jugadores.set(i, per);

						else {
							jugadores.add(per);

						}

					}
				}

			}
			}
		} catch (IOException e) {
			// Si ocurre un excepcion lo mas seguro es que sea por que el
			// cliente se desconecto asi que lo quitamos de la lista de
			// conectados

			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i) == socket) {
					if (usuarios.size() > i) {
						usuarios.remove(i);
						if (jugadores.size() > i) {
							
							try {
								ConexionBD conect= new ConexionBD ();
								conect.cargarDatosDeUsuario(jugadores.get(i).getNombre(), new Gson().toJson(jugadores.get(i)));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							jugadores.remove(i);
							if(hilosEscritura.size()>i)
							{
								hilosEscritura.remove(i);
							}
							if(hilosLectura.size()>i)
							{
								hilosLectura.remove(i);
							}
						}
					}

				}
			}

		}
	}
	public boolean isCorrer() {
		return correr;
	}

	public void setCorrer(boolean correr) {
		this.correr = correr;
	}
	public void esperar() {
		
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			
		}
	}

}
