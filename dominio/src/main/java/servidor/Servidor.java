package main.java.servidor;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.org.apache.bcel.internal.generic.NEW;

import main.java.baseDeDatos.ConexionBD;
import main.java.entidad.Personaje;
import main.java.util.JugadorModelo;

public class Servidor extends Thread {
	private final int puerto = 9999;
	private final int noConexiones = 10;
	
	
	// Creamos una lista de sockets, donde guardaremos los sockets que se vayan
	// conectando
	private ArrayList<JugadorModelo> personajes = new ArrayList<>();
	private ArrayList<Socket> usuarios = new ArrayList<Socket>();
	
	private ArrayList<HiloEscrituraServidor> hilosEscritura= new ArrayList<HiloEscrituraServidor>();
	private ArrayList<HiloLecturaServidor> hilosLectura=new ArrayList<HiloLecturaServidor>();
	
	// Funcion para que el servidor empieze a recibir conexiones de clientes
	public void run() {
		try {
			// Creamos el socket servidor
			@SuppressWarnings("resource")
			ServerSocket servidor = new ServerSocket(puerto, noConexiones);
	
			// Ciclo infinito para estar escuchando por nuevos clientes
			while (true) {
				// Cuando un cliente se conecte guardamos el socket en nuestra
				// lista
				Socket cliente = servidor.accept();
				consultarBaseDeDatos(cliente);

				usuarios.add(cliente);
				// Instanciamos un hilo que estara atendiendo al cliente y lo
				// ponemos a escuchar
				HiloLecturaServidor runLectura= new HiloLecturaServidor(cliente, usuarios, personajes, usuarios.indexOf(cliente),hilosEscritura,hilosLectura);
				Thread hilo = new Thread(runLectura);
				hilosLectura.add(runLectura);
				hilo.start();
				
				HiloEscrituraServidor runEscritura= new HiloEscrituraServidor(cliente, usuarios, personajes, usuarios.indexOf(cliente),hilosEscritura,hilosLectura);
				Thread hiloEscritura = new Thread(runEscritura);
				hilosEscritura.add(runEscritura);
				hiloEscritura.start();
				Thread hilobatalla= new Thread(new HiloBatalla(usuarios, personajes ,hilosEscritura,hilosLectura));
				hilobatalla.start();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, " Problema con el servidor"
					,"Fin",JOptionPane.WARNING_MESSAGE);
		}

	}
	
	
	public void consultarBaseDeDatos(Socket cliente) throws Exception {
		DataInputStream in;
		DataOutputStream out;
		ConexionBD conect= new ConexionBD ();
		try {
			in = new DataInputStream(cliente.getInputStream());

			out = new DataOutputStream(cliente.getOutputStream());
			
			if(in.readUTF().equals("ingresar")){//1 leida
				String usuario=in.readUTF();///2 leida
				String contrasenia=in.readUTF();//3 leida
				if(conect.consultarUsuario(usuario, contrasenia))
					out.writeUTF("true");
				else
					out.writeUTF("false");
				String dato=conect.obtenerDatosDeUsuario(usuario);
				out.writeUTF(dato);
			}
			else
			{
				String usuario=in.readUTF();//2 leida
				String contrasenia=in.readUTF();//3 leida
				
				
				if(conect.consultarUsuario(usuario, contrasenia))
					out.writeUTF("true");
				else
					out.writeUTF("false");
				
				String jugadorGson = in.readUTF();//4 leida
				conect.cargarUsuario(usuario, contrasenia, jugadorGson);

			}
		} catch (Exception e) {
			return; //solo hiso una pericion
		}
		
	}

	// Funcion main para correr el servidor
	public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.start();

	}
}
