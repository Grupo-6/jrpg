package main.java.cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import main.java.entidad.Jugador;
import main.java.util.JugadorModelo;




public class HiloEscrituraJugador implements Runnable{
	private DataOutputStream out;
	private Jugador jugador;
	
	public HiloEscrituraJugador(final Socket so,Jugador personaje) {
		Socket socket = so;
		this.jugador=personaje;
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("ERROR "+personaje.getNombre());
		}
	}
	
	@Override
	public void run() {
		Gson gson = new Gson();
		String pjGson="";

		while(true){
			System.out.println("Cliente esclibiendo");
			
			try {
				pjGson= gson.toJson(new JugadorModelo(this.jugador));
				esperar();
				out.writeUTF(pjGson);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void esperar(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
