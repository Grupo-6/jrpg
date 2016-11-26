package main.java.logeo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;

import main.java.baseDeDatos.ConexionBD;
import main.java.entidad.Personaje;
import main.java.util.JugadorModelo;

@SuppressWarnings("serial")
public class LaminaLogin extends JPanel implements ActionListener {

	private JLabel usuario, contraseña, ip, puerto;
	private JTextField usuarioText, contraseñaText, ipText, puertoText;
	private JButton ingresar, registrarse;
	private Personaje personaje;
	private VentanaSeleccion ventanaSelect = null;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	String usuarioEscrito = "", contraseñaEscrita = "";
	private VentanaLogin ventana;
	// private ConexionBD conect;

	public LaminaLogin(VentanaLogin ventana) throws Exception {

		setLayout(null);
		this.ventana = ventana;
		ingresar = new JButton("Ingresar");
		ingresar.setBounds(50, 225, 125, 25);

		registrarse = new JButton("Registrarse");
		registrarse.setBounds(225, 225, 125, 25);

		ip = new JLabel("IP:");
		ip.setBounds(128, 30, 125, 25);

		puerto = new JLabel("Puerto:");
		puerto.setBounds(113, 70, 125, 25);

		usuario = new JLabel("Usuario:");
		usuario.setBounds(110, 110, 125, 25);

		contraseña = new JLabel("Contraseña:");
		contraseña.setBounds(100, 150, 125, 25);

		ipText = new JTextField();
		ipText.setBounds(175, 30, 125, 25);

		puertoText = new JTextField();
		puertoText.setBounds(175, 70, 125, 25);

		usuarioText = new JTextField();
		usuarioText.setBounds(175, 110, 125, 25);

		contraseñaText = new JTextField();
		contraseñaText.setBounds(175, 150, 125, 25);

		ingresar.addActionListener(this);
		registrarse.addActionListener(this);

		add(ip);
		add(puerto);
		add(ipText);
		add(puertoText);
		add(ingresar);
		add(registrarse);
		add(usuario);
		add(contraseña);
		add(usuarioText);
		add(contraseñaText);

	}

	public void actionPerformed(ActionEvent clic) {

		usuarioEscrito = usuarioText.getText();
		contraseñaEscrita = contraseñaText.getText();

		if (verificarServidor(ipText.getText(), puertoText.getText())) {

			if (!usuarioEscrito.equals("") && !contraseñaEscrita.equals("")) {	

				if (clic.getSource() == ingresar) {

					
					try {
						in = new DataInputStream(socket.getInputStream());
						out = new DataOutputStream(socket.getOutputStream());
						out.writeUTF("ingresar");
						out.writeUTF(usuarioEscrito);
						out.writeUTF(contraseñaEscrita);
						if(in.readUTF().equals("true")){
							String jugadorGson=in.readUTF();
							Gson gson= new Gson();
							personaje= JugadorModelo.recrearPersonaje(gson.fromJson(jugadorGson, JugadorModelo.class));
							iniciarJuego(personaje);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Usuario y/o constrseña incorrecta", "Advertencia",
									JOptionPane.WARNING_MESSAGE);
							
							socket.close();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}

					ventana.dispose();
					ventana.setVisible(false);
		
				}

				if (clic.getSource() == registrarse) {
					// if (!valorVerdad){
					try {
						in = new DataInputStream(socket.getInputStream());
						out = new DataOutputStream(socket.getOutputStream());
						out.writeUTF("registrar");//1 escritura
						
						out.writeUTF(usuarioEscrito);//2 escritura
						out.writeUTF(contraseñaEscrita);//3 escritura
						
						if(in.readUTF().equals("true")){
							JOptionPane.showMessageDialog(null, "Usuario ya registrado", "Advertencia",
									JOptionPane.WARNING_MESSAGE);
							socket.close();
						}
						else {
							//ventana.dispose();
							ventana.setVisible(false);
							
							VentanaSeleccion ventanaSelect = new VentanaSeleccion(usuarioEscrito, this);
							ventanaSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					

				} 
				

			} else {
				usuarioText.setText("");
				contraseñaText.setText("");
				JOptionPane.showMessageDialog(null, "Debe ingresar usuario y contraseña", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {

		}
	}

	public void guardarCuenta(Personaje personaje) {
		JugadorModelo jugador = new JugadorModelo(personaje);
		Gson gson = new Gson();
		String jugadorGson = gson.toJson(jugador);
		try {
			out.writeUTF(jugadorGson);//4 escritura
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		iniciarJuego(personaje);
	}

	private static boolean esNumerico(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public boolean verificarServidor(String ip, String puerto) {

		if (!ipText.getText().equals("") && esNumerico(puertoText.getText())) {

			try {

				socket = new Socket(ipText.getText(), Integer.parseInt(puertoText.getText()));

				JOptionPane.showMessageDialog(null, "IP y puerto" + " correcto", "Informacion", JOptionPane.OK_OPTION);
				return true;

			} catch (NumberFormatException | IOException e1) {
				JOptionPane.showMessageDialog(null, "Servidor " + "no encontrado", "Error",
						JOptionPane.WARNING_MESSAGE);
				ipText.setText("");
				puertoText.setText("");
				return false;
			}
		} else if (!esNumerico(puertoText.getText()) && !puertoText.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El puerto debe " + "ser un valor numerico", "Error",
					JOptionPane.WARNING_MESSAGE);
			ipText.setText("");
			puertoText.setText("");
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "Debe ingresar " + "IP y Puerto", "Error", JOptionPane.WARNING_MESSAGE);
			ipText.setText("");
			puertoText.setText("");
			return false;
		}
	}

	public void iniciarJuego(Personaje personaje) {
		ventana.setSocket(socket);
		ventana.iniciarJuego(personaje);
		ventana.setVisible(false);
		ventana.dispose();
	}

}
