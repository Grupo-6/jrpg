package main.java.batalla;

import java.net.Socket;

import javax.swing.JFrame;

import main.java.util.JugadorModelo;



public class VentanaBatalla extends JFrame {
	
	private LaminaPantalla lamina;
	
	public VentanaBatalla(JugadorModelo personaje, String pj, Socket socket){
		
		setResizable(false);
		setTitle(pj);
		setBounds(400,125,500,650);
		setLocationRelativeTo(null);
		lamina = new LaminaPantalla(personaje, pj, this, socket);
		add(lamina);
		
		setVisible(true);
	}

}
