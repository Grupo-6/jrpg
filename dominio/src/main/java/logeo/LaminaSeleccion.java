package main.java.logeo;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;

import main.java.entidad.Asesino;
import main.java.entidad.Elfo;
import main.java.entidad.Guerrero;
import main.java.entidad.Humano;
import main.java.entidad.Orco;
import main.java.entidad.Personaje;

public class LaminaSeleccion extends JPanel implements ActionListener {
		
		private JTextField  textAtaque, textDefensa, textVelocidad, ipText, puertoText;
		private JLabel nombrePersonaje,titulo,adicionales, nombre, ataque, defensa, velocidad, atributos, ip, puerto;
		private JComboBox seleccionPersonaje, seleccionCasta;
		private String personajes[] = {"--------", "Humano","Orco", "Elfo"};
		private String castas [] = {"--------", "Guerrero", "Asesino"};
		private JButton aceptar,cancelar, generarPersonaje, verificar;
		
		private Personaje personaje2=null;
		private Personaje personaje;
		private int personajeElegido = 0, castaElegida = 0;
		private VentanaSeleccion ventanaPrincipal;
		private Image imagen;
		private String nom;
		public LaminaSeleccion(VentanaSeleccion ventanaPrincipal,String nom){
			this.nom=nom;
			this.ventanaPrincipal = ventanaPrincipal;
			setLayout(null);
			nombre = new JLabel();
			nombre.setText("Nombre");
			nombre.setBounds(50, 260, 120, 25);
			
			//ip = new JLabel("<html><body>Direccion IP <br> del Servidor: </body></html>");
			ip = new JLabel("IP del Servidor: ");
			ip.setBounds(40,167,100,50);
			
			verificar = new JButton("Verificar Conexiñn");
			verificar.setBounds(125, 225, 150, 25);
			
			puerto = new JLabel("Puerto:");
			puerto.setBounds(225, 167, 55, 50);
			
			ipText = new JTextField();
			ipText.setBounds(130, 180, 80, 25);
			
			puertoText = new JTextField();
			puertoText.setBounds(280, 180, 50, 25);
			
			ataque = new JLabel();
			ataque.setText("Ataque:");
			ataque.setBounds(50, 460, 120, 25);
			
			textAtaque = new JTextField();
			textAtaque.setEditable(false);
			textAtaque.setBounds(48,485, 50,25);
			textAtaque.setBackground(Color.white);
			
			defensa = new JLabel();
			defensa.setText("Defensa:");
			defensa.setBounds(155, 460, 120, 25);
			
			textDefensa = new JTextField();
			textDefensa.setEditable(false);
			textDefensa.setBounds(154,485, 50,25);
			textDefensa.setBackground(Color.white);
			
			velocidad = new JLabel();
			velocidad.setText("Velocidad:");
			velocidad.setBounds(260, 460, 120, 25);
			
			textVelocidad = new JTextField();
			textVelocidad.setEditable(false);
			textVelocidad.setBounds(263,485, 50,25);
			textVelocidad.setBackground(Color.white);
			
		  nombrePersonaje = new JLabel();
		  nombrePersonaje.setText(nom);
		  nombrePersonaje.setBounds(125, 260, 150, 25);
		 
		  aceptar =new JButton();
		  aceptar.setText("Entrar al Mundo!");
		  aceptar.setBounds(70, 525, 150, 25);
		  aceptar.setEnabled(false);
		  
		  cancelar=new JButton();
		  cancelar.setText("Salir");
		  cancelar.setBounds(230, 525, 90, 25);
		  
		  
		  generarPersonaje =new JButton();
		  generarPersonaje.setText("Generar Personaje");
		  generarPersonaje.setBounds(110, 375, 150, 25);
		  generarPersonaje.setEnabled(false);
			
		  titulo= new JLabel();
		  titulo.setText("Personaje");
		  titulo.setBounds(50, 295, 150, 25);

		  atributos= new JLabel();
		  atributos.setText("ATRIBUTOS");
		  atributos.setBounds(148, 420, 150, 25);
		  
		  adicionales= new JLabel();
		  adicionales.setText("Casta");
		  adicionales.setBounds(50, 340, 180, 25);
		  
		  seleccionPersonaje= new JComboBox();
		  seleccionPersonaje.setBounds(125, 295, 150, 25);
		  seleccionPersonaje.setModel(new javax.swing.DefaultComboBoxModel(personajes));
		
		 
		  seleccionCasta= new JComboBox();
		  seleccionCasta.setBounds(125, 330, 150, 25);
		  seleccionCasta.setModel(new javax.swing.DefaultComboBoxModel(castas));
		  seleccionCasta.setEnabled(false);
		
		  
		  	
		  seleccionPersonaje.addActionListener(this);
		  verificar.addActionListener(this);
		  seleccionCasta.addActionListener(this);
		  generarPersonaje.addActionListener(this);
		  aceptar.addActionListener(this);
		  cancelar.addActionListener(this);
		 	
		  add(verificar);
		  add(ip);
		  add(puerto);
		  add(ipText);
		  add(puertoText);
		  add(nombre);
		  add(nombrePersonaje);
		  add(cancelar);
		  add(aceptar);
		  add(generarPersonaje);
		  add(atributos);
		  add(ataque);
		  add(textAtaque);
		  add(defensa);
		  add(textDefensa);
		  add(velocidad);
		  add(textVelocidad);
		  add(adicionales);
		  add(titulo);
		  add(seleccionPersonaje);
		  add(seleccionCasta);

			
		}

		
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==cancelar){
				System.exit(0);
			}
			
			if (e.getSource()==verificar){
				verificarServidor(ipText.getText(), puertoText.getText());
			}
			
			if (e.getSource() == seleccionPersonaje){
				
				if (seleccionPersonaje.getSelectedIndex() == 0){
					generarPersonaje.setEnabled(false);
					seleccionCasta.setEnabled(false);
					aceptar.setEnabled(false);
					vaciarCampos();
					
					
				}
				else{
					seleccionCasta.setEnabled(true);
					
					if (seleccionPersonaje.getSelectedIndex() == 1){
						personajeElegido = 1;
					}
					else if (seleccionPersonaje.getSelectedIndex() == 2){
						personajeElegido = 2;
					}
					else{
						personajeElegido = 3;
					}
				}
			}
			
			if (e.getSource() == seleccionCasta){
				
				if (seleccionCasta.getSelectedIndex() == 0){
					aceptar.setEnabled(false);
					generarPersonaje.setEnabled(false);
					vaciarCampos();
					
				}
				else{
					generarPersonaje.setEnabled(true);
					if (seleccionCasta.getSelectedIndex() == 1){
						castaElegida = 1;
					}
					else if (seleccionCasta.getSelectedIndex() == 2){
						castaElegida = 2;
					}
				}
			}
			
			if (e.getSource() == generarPersonaje){
				aceptar.setEnabled(true);
				
				
				if (personajeElegido == 1){
					personaje = new Humano(nom);	
				}
				if (personajeElegido == 2){
					personaje = new Orco(nom);
					
					
				}
				if (personajeElegido == 3){
					personaje = new Elfo(nom);
					
					
				}
				if(castaElegida==1){
					personaje.setCasta(new Guerrero());
				}
				else
					personaje.setCasta(new Asesino());
				
				textAtaque.setText(String.valueOf(personaje.obtenerPuntosDeAtaque()));
				textDefensa.setText(String.valueOf(personaje.obtenerPuntosDeDefensa()));
				textVelocidad.setText(String.valueOf(personaje.obtenerPuntosDeVelocidad()));
				
			}
			
			if (e.getSource() == aceptar){
				
				if (nombrePersonaje.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Debe ingresar un" +
							" nombre para el personaje","Advertencia!!!",JOptionPane.WARNING_MESSAGE);
				}
				
				else {//if(verificarServidor(ipText.getText(), puertoText.getText()))
					
					if (personajeElegido == 1){
						personaje2 = new Humano (nom);
						
					}
					if (personajeElegido == 2){
						personaje2 = new Orco (nom);				
					}
					if (personajeElegido == 3){
						personaje2 = new Elfo (nom);
					}
					
					if(castaElegida==1){
						personaje2.setCasta(new Guerrero());
					}
					else
						personaje2.setCasta(new Asesino());
					
					ventanaPrincipal.guardarCuenta(personaje2);
					ventanaPrincipal.dispose();
					ventanaPrincipal.setVisible(false);
					
				}
				
			}
		 }
	
		
		public void vaciarCampos(){
			textAtaque.setText("");
			textDefensa.setText("");
			textVelocidad.setText("");
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			try {
				imagen = ImageIO.read(new File("recursos/Portada.jpg"));
				
			} catch (IOException e) {
				System.err.println("La imagen no se encuentra");
			}
			
			g.drawImage(imagen, 50, 10, 300, 150,this);
		}
		
	    private static boolean esNumerico(String cadena){
	    	try {
	    		Integer.parseInt(cadena);
	    		return true;
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }
	    
	    public boolean verificarServidor(String ip, String puerto){
	    	
	    	if (!ipText.getText().equals("") && esNumerico(puertoText.getText())){
				
				try {
					new Socket(ipText.getText(), Integer.parseInt(puertoText.getText()));
					JOptionPane.showMessageDialog(null, "IP y puerto" +
							"Correcto","Informacion",JOptionPane.OK_OPTION);
					return true;
					
				} catch (NumberFormatException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Servidor " +
							"no encontrado","Error",JOptionPane.WARNING_MESSAGE);
					ipText.setText("");
					puertoText.setText("");
					return false;
				}
				
			}
			else if (!esNumerico(puertoText.getText()) && !puertoText.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El puerto debe " +
						"ser un valor numñrico","Error",JOptionPane.WARNING_MESSAGE);
				ipText.setText("");
				puertoText.setText("");
				return false;
			}
			else{
				JOptionPane.showMessageDialog(null, "Debe ingresar " +
						"IP y Puerto","Error",JOptionPane.WARNING_MESSAGE);
				ipText.setText("");
				puertoText.setText("");
				return false;
			}
	    }

}


