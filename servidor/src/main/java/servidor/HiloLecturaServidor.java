package main.java.servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.java.util.JugadorModelo;




public class HiloLecturaServidor implements Runnable{
	private DataInputStream in;
	private ArrayList<Socket> usuarios;
	private ArrayList<JugadorModelo> jugadores;
	private int numCliente;
	private Socket socket;
	public HiloLecturaServidor( Socket so,ArrayList<Socket> usuarios, ArrayList<JugadorModelo> personajes, int numCliente) {
		this.socket=so;
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
			Gson gson= new Gson();
			JugadorModelo per;
			while (true) {
				
				System.out.println("Servidor leyendo");
				String recibido =  in.readUTF();
				esperar();
				per=gson.fromJson(recibido, JugadorModelo.class);
				System.out.println(jugadores.size()+" "+numCliente);
				
				for (int i = 0; i < usuarios.size(); i++) {
					if (usuarios.get(i) == socket) {
						
						if(jugadores.size()>i)
							jugadores.set(i, per);

						else {	
								jugadores.add(per);

						}

					}
				}
				
				
				System.out.println("Personajes activos: "+jugadores);
			}
		} catch (IOException e) {
			// Si ocurre un excepcion lo mas seguro es que sea por que el
			// cliente se desconecto asi que lo quitamos de la lista de
			// conectados
			
			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i) == socket) {
					usuarios.remove(i);
					jugadores.remove(i);

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
