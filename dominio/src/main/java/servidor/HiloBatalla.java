package main.java.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;
import main.java.entidad.Jugador;
import main.java.util.JugadorModelo;

public class HiloBatalla implements Runnable {

	private DataInputStream in1;
	private DataInputStream in2;
	private DataOutputStream out1;
	private DataOutputStream out2;

	private ArrayList<Socket> usuarios;
	private ArrayList<JugadorModelo> jugadores;
	private ArrayList<HiloEscrituraServidor> hilosEscritura;
	private ArrayList<HiloLecturaServidor> hilosLectura;

	public HiloBatalla(ArrayList<Socket> usuarios, ArrayList<JugadorModelo> jugadores,ArrayList<HiloEscrituraServidor> hilosEscritura,ArrayList<HiloLecturaServidor> hilosLectura) {
		this.hilosEscritura = hilosEscritura;
		this.hilosLectura = hilosLectura;
		this.usuarios = usuarios;
		this.jugadores = jugadores;
	}

	@Override
	public void run() {
		JugadorModelo jugador1;
		JugadorModelo jugador2;
		DataInputStream in1;
		DataInputStream in2;
		DataOutputStream out1;
		DataOutputStream out2;
		while (true) {
			System.out.println(jugadores.size());
			if (jugadores.size() > 1) {
				for (int i = 0; i < jugadores.size(); i++) {
					for (int j = 0; j < jugadores.size(); j++) {
						jugador1 = jugadores.get(i);
						jugador2 = jugadores.get(j);
						System.out.println(jugador1.getX()+" "+ jugador2.getX()+" "+ jugador1.getY()+" "+ jugador2.getX());

						if (j != i) {
							//System.out.println(jugador1.getX()+" "+ jugador2.getX()+" "+ jugador1.getY()+" "+ jugador2.getX());
							if (seTocan(jugador1.getX(), jugador2.getX(), jugador1.getY(), jugador2.getX())) {
								try {
									System.out.println(jugador1.getNombre() + " " + jugador2.getNombre());
									
									out1 = new DataOutputStream(usuarios.get(i).getOutputStream());
									out2 = new DataOutputStream(usuarios.get(j).getOutputStream());
									hilosEscritura.get(i).setCorrer(false);
									hilosEscritura.get(j).setCorrer(false);
									hilosLectura.get(i).setCorrer(false);
									hilosLectura.get(j).setCorrer(false);
									
									out1.writeUTF("batalla");
									out2.writeUTF("batalla");
									
									
									
									out1.writeUTF("PJ1");
									out2.writeUTF("PJ2");
									
									
									
									hilosEscritura.get(i).setCorrer(true);
									hilosEscritura.get(j).setCorrer(true);
									hilosLectura.get(i).setCorrer(true);
									hilosLectura.get(j).setCorrer(true);
									
								} catch (IOException e) {
									System.out.println("NO anda nadaaaa");
									e.printStackTrace();
								}

							}
						}
					}
				}
			}
		}
	}

	public boolean seTocan(int x1, int x2, int y1, int y2) {

		int x = x1 - x2;
		if (x < 0)
			x *= -1;
		int y = y1 - y2;
		if (y < 0)
			y *= -1;

		if (x < 7 && y < 7)
			return true;
		
		return false;
	}

}
