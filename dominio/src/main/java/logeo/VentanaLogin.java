package main.java.logeo;

import java.net.Socket;

import javax.swing.JFrame;

import main.java.entidad.Personaje;
import main.java.juego.Juego;

public class VentanaLogin extends JFrame {

	private LaminaLogin lamina;
	private Socket socket;
	
	public VentanaLogin()  {

		setBounds(500, 250, 400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Iniciar Sesion");

		try {
			lamina = new LaminaLogin(this);
		} catch (Exception e) {
			
		}
		add(lamina);

		setVisible(true);
	}
	
	public void setSocket(Socket socket){
		this.socket= socket;
	}
	public void iniciarJuego(Personaje personaje){
		Juego juego= new Juego(personaje,socket);
		juego.start();
	}
	
	
	public static void main (String [] args){
		
		VentanaLogin ventana = new VentanaLogin();
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
