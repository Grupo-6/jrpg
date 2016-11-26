package main.java.logeo;

import java.awt.RenderingHints;

import javax.swing.JFrame;

import main.java.entidad.Personaje;


public class VentanaSeleccion extends JFrame {
		
	LaminaSeleccion lamina;
	LaminaLogin laminaLogin;
		public VentanaSeleccion (String nombre,LaminaLogin laminaLogin){
			this.laminaLogin=laminaLogin;
			  setSize(400,600);
			  setTitle("Another generic RPG");
			  setLocationRelativeTo(null);
			  setResizable(false);
			 
			  lamina = new LaminaSeleccion(this,nombre);
			  add(lamina); 
			  setVisible(true);
			 
		}
		
		public void iniciarJuego(Personaje personaje){
			laminaLogin.iniciarJuego(personaje);
		}
		public void guardarCuenta(Personaje personaje){
			laminaLogin.guardarCuenta(personaje);
		}
		
}
