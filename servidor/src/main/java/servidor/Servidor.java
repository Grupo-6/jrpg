package main.java.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.java.util.JugadorModelo;





public class Servidor {
	private final int puerto = 2027;
	private final int noConexiones = 10;
	// Creamos una lista de sockets, donde guardaremos los sockets que se vayan
	// conectando
	private ArrayList<JugadorModelo> personajes = new ArrayList<>();
	private ArrayList<Socket> usuarios = new ArrayList<Socket>();


	// Funcion para que el servidor empieze a recibir conexiones de clientes
	public void escuchar() {
		try {
			// Creamos el socket servidor
			@SuppressWarnings("resource")
			ServerSocket servidor = new ServerSocket(puerto, noConexiones);
			// Ciclo infinito para estar escuchando por nuevos clientes
			while (true) {
				System.out.println("Escuchando....");
				// Cuando un cliente se conecte guardamos el socket en nuestra
				// lista
				Socket cliente = servidor.accept();
				usuarios.add(cliente);
				// Instanciamos un hilo que estara atendiendo al cliente y lo
				// ponemos a escuchar

				Thread hilo = new Thread(new HiloLecturaServidor( cliente,usuarios, personajes, usuarios.indexOf(cliente)));
				hilo.start();
				Thread hiloEscritura = new Thread(new HiloEscrituraServidor(cliente,usuarios, personajes,usuarios.indexOf(cliente)));
				System.out.println("Servidor empeso a escribir");
				hiloEscritura.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	// Funcion main para correr el servidor
	public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.escuchar();
		
	}
}
