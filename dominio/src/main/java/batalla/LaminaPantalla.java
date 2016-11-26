package main.java.batalla;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.entidad.Personaje;
import main.java.util.JugadorModelo;


public class LaminaPantalla extends JPanel implements ActionListener{
	
	private JTextArea area;
	private JLabel areaText, j1, j2, vida, energia;
	private Image imagen, im2, im3;
	private JComboBox box;
	private String [] ataques = {"Ataque normal", "Ataque Especial"};
	private JButton atacar, comenzarBatalla;
	private JScrollPane scroll;
	private String vida1, vida2, mensajeInicial, pj;
	private JProgressBar vidaJ1,energiaJ1, vidaJ2, energiaJ2;
	private JugadorModelo personajeModelo;
	private Personaje personaje;
	private JTextField ataqueJ1, defensaJ1, velocidadJ1, ataqueJ2, defensaJ2, velocidadJ2;
	private JLabel atqJ1, defJ1, velJ1, atqJ2, defJ2, velJ2;
	private String miPersonaje;
	private VentanaBatalla ventana;
	private Socket socket;
	
	private JLabel img, propio, enemigo;
	public LaminaPantalla (JugadorModelo personaje, String pj,  VentanaBatalla ventana, Socket socket){
		
		this.ventana = ventana;
		this.socket = socket;
		this.pj= pj;
		vida1 = personaje.getNombre();
		
		this.personaje = JugadorModelo.recrearPersonaje(personaje);
		
		setLayout(null);
		
		 if (personaje.getRaza() == "orco") 
			   miPersonaje = "orc.png";
			
		   else if (personaje.getRaza() == "elfo")
			   miPersonaje = "elfo.png";  
		   else
			   miPersonaje = "human.png";
			    
		 
		mensajeInicial = "/"+String.valueOf(personaje.getNombre())+"/"+String.valueOf(personaje.getAtaque())+"/"+
				String.valueOf(personaje.getDefensa())+"/"+String.valueOf(personaje.getVelocidad())+"/"+miPersonaje;
		
		propio = new JLabel();
		propio.setBounds(50,145,65,65);
		
		enemigo = new JLabel();
		enemigo.setBounds(350,75,65,65);
		enemigo.setVisible(false);
		
		areaText = new JLabel("Registro de combate");
		areaText.setBounds(275, 465, 200,35);
		
		j1 = new JLabel(vida1);
		j1.setBounds(55, 280, 125,25);
		
		j2 = new JLabel("");
		j2.setBounds(300, 280, 125,25);
		add(j2);
		
		vida = new JLabel("VIDA");
		energia = new JLabel("ENERGIA");
		vida.setBounds(225, 300, 125,40);
		energia.setBounds(215, 340, 125,40);
		
		area = new JTextArea();
		area.setEditable(false);
		area.setBorder(BorderFactory.createLineBorder(new Color(255,0,0)));
		
		box = new JComboBox ();
		box.setBounds(25, 475, 125,25);
		box.setModel(new javax.swing.DefaultComboBoxModel(ataques));
		
		atacar = new JButton("Atacar");
		atacar.setBounds(35,525,100,40);
		atacar.setEnabled(false);
		
		comenzarBatalla = new JButton("COMENZAR!");
		comenzarBatalla.setBounds(170, 200, 150,50);
		comenzarBatalla.setForeground(Color.WHITE);
		comenzarBatalla.setBackground(Color.BLACK);
		comenzarBatalla.setEnabled(true);
		
		scroll = new JScrollPane(area);
		scroll.setBounds(new Rectangle(200,500,275,110));
		
		vidaJ1 = new JProgressBar(0,100);
		vidaJ1.setValue(100);
		vidaJ1.setStringPainted(true);
		vidaJ1.setBounds(55, 310, 125,25);
		vidaJ1.setStringPainted(true);
		vidaJ1.setForeground(Color.red);
		
		
		
		vidaJ2 = new JProgressBar(0,100);
		vidaJ2.setValue(100);
		vidaJ2.setStringPainted(true);
		vidaJ2.setBounds(300, 310, 125,25);
		vidaJ2.setStringPainted(true);
		vidaJ2.setForeground(Color.red);
		
		energiaJ1 = new JProgressBar(0,100);
		energiaJ1.setValue(100);
		energiaJ1.setStringPainted(true);
		energiaJ1.setStringPainted(true);
		energiaJ1.setForeground(Color.blue);
		
		energiaJ2 = new JProgressBar(0,100);
		energiaJ2.setValue(100);
		energiaJ2.setStringPainted(true);
		energiaJ2.setStringPainted(true);
		energiaJ2.setForeground(Color.blue);
		
		if (pj.equals("PJ1")){
			energiaJ1.setBounds(55, 350, 125,25);
			energiaJ2.setBounds(300, 350, 125,25);
		}
		else{
			energiaJ1.setBounds(300, 350, 125,25);
			energiaJ2.setBounds(55, 350, 125,25);
		}
					
		atqJ1 = new JLabel("Atq");
		atqJ1.setBounds(50, 385,40,25);
		ataqueJ1 = new JTextField();
		ataqueJ1.setEditable(false);
		ataqueJ1.setBounds(40, 410, 40, 25);
		ataqueJ1.setText(String.valueOf(personaje.getAtaque()));
		ataqueJ1.setHorizontalAlignment(JTextField.CENTER);
		
		defJ1 = new JLabel("Def");
		defJ1.setBounds(105, 385,40,25);
		defensaJ1 = new JTextField();
		defensaJ1.setEditable(false);
		defensaJ1.setBounds(95, 410, 40, 25);
		defensaJ1.setText(String.valueOf(personaje.getDefensa()));
		defensaJ1.setHorizontalAlignment(JTextField.CENTER);
		
		velJ1 = new JLabel("Vel");
		velJ1.setBounds(160, 385,40,25);
		velocidadJ1 = new JTextField();
		velocidadJ1.setEditable(false);
		velocidadJ1.setBounds(150, 410, 40, 25);
		velocidadJ1.setText(String.valueOf(personaje.getVelocidad()));
		velocidadJ1.setHorizontalAlignment(JTextField.CENTER);
		
		atqJ2 = new JLabel("Atq");
		atqJ2.setBounds(295, 385,40,25);
		ataqueJ2 = new JTextField();
		ataqueJ2.setEditable(false);
		ataqueJ2.setBounds(285, 410, 40, 25);
		
		defJ2 = new JLabel("Def");
		defJ2.setBounds(350, 385,40,25);
		defensaJ2 = new JTextField();
		defensaJ2.setEditable(false);
		defensaJ2.setBounds(340, 410, 40, 25);
		
		velJ2 = new JLabel("Vel");
		velJ2.setBounds(405, 385,40,25);
		velocidadJ2 = new JTextField();
		velocidadJ2.setEditable(false);
		velocidadJ2.setBounds(395, 410, 40, 25);
		
		img = new JLabel();
		img.setBounds(70,150,50,50);
		img.setVisible(false);
		
		//solo tengo que agregar el scroll, no el textarea
		
		atacar.addActionListener(this);
		comenzarBatalla.addActionListener(this);
		add(comenzarBatalla);
		add(vida);
		add(energia);
		add(velJ1);
		add(velocidadJ1);
		add(defJ1);
		add(defensaJ1);
		add(atqJ1);
		add(ataqueJ1);
		add(velJ2);
		add(velocidadJ2);
		add(defJ2);
		add(defensaJ2);
		add(atqJ2);
		add(ataqueJ2);
		add(scroll);
		add(areaText);
		add(box);
		add(atacar);
		add(vidaJ1);
		add(energiaJ1);
		add(vidaJ2);
		add(energiaJ2);
		add(j1);
		add(propio);
		add(enemigo);
		
		add(img);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		try {
			imagen = ImageIO.read(new File("escenarioBatalla.jpg"));
			//im2 = ImageIO.read(new File("elfo.png"));
		//	im3 = ImageIO.read(new File("orc.png"));
			Image m4 = ImageIO.read(new File("Transparente3.png"));
			
		    ImageIcon icon = new ImageIcon(m4);  
		    img.setIcon(icon); 
		  
		   
		   Image m5 = ImageIO.read(new File(miPersonaje));
		   ImageIcon icon2 = new ImageIcon(m5);  
		    propio.setIcon(icon2); 
		    
			
		} catch (IOException e) {
			System.err.println("La imagen no se encuentra");
		}
		g.setColor(Color.red);
		g.drawImage(imagen, 20, 5, 450, 250,this);
		//g.drawImage(im2, 50, 145, 65, 65,this);
		//g.drawImage(im3, 350, 75, 65, 65,this);
		
	}

	
	public void actionPerformed(ActionEvent clic) {
		
		if (clic.getSource() == atacar){
			String mensaje = String.valueOf(personaje.getAtaque());
			atacar.setEnabled(false);
			enviarMensaje(mensaje);	
		}
		
		if (clic.getSource() == comenzarBatalla){
			enviarMensaje(mensajeInicial);
			enviarMensaje(String.valueOf("#"+personaje.getVelocidad()));
			
		}
	}
	
	public void estadisticasEnemigas(String mensaje) throws IOException{
		String [] mensajeAux = new String [5];
		
			for(int i=0; i<mensajeAux.length; i++)
				mensajeAux[i] ="";
		
			int j=0;
			for(int i=1; i<mensaje.length(); i++){
				
				if (mensaje.charAt(i) != '/')
				mensajeAux[j] += mensaje.charAt(i);
				else{
					j++;
				}
			}
		j2.setText(mensajeAux[0]);
		vida2 = mensajeAux[0];
		ataqueJ2.setText(mensajeAux[1]);
		ataqueJ2.setHorizontalAlignment(JTextField.CENTER);
		defensaJ2.setText(mensajeAux[2]);
		defensaJ2.setHorizontalAlignment(JTextField.CENTER);
		velocidadJ2.setText(mensajeAux[3]);
		velocidadJ2.setHorizontalAlignment(JTextField.CENTER);
		Image m4 = ImageIO.read(new File(mensajeAux[4]));
		ImageIcon icon = new ImageIcon(m4);  
	    enemigo.setIcon(icon);
	    enemigo.setVisible(true);
		
		
	}
	
	public void actualizarPersonajes(String mensaje){
		
		String mensajeDefensa = "";
		String mensajeVida = "";
		String mensajeEnergia = "";
		String mensajeRegistro ="";
		String mensajeTotal ="*";
		
		int danio = Integer.parseInt(mensaje);
		int salud = personaje.getSalud();
		
		personaje.serAtacado(danio);
		
		if (salud == personaje.getSalud()){
			//significa que se bajo la defensa, actualizo defensa en mi pantalla y en la pantalla enemiga.
			mensajeDefensa = String.valueOf(personaje.getDefensa());
			defensaJ1.setText(mensajeDefensa);
			mensajeVida = String.valueOf(personaje.getSalud());
			//actualizo la defensa en la pantalla enemiga
			
		}
		else {
			//baja la vida
			mensajeVida = String.valueOf(personaje.getSalud());
			mensajeDefensa = String.valueOf(personaje.getDefensa());
			vidaJ1.setValue(personaje.getSalud());
			//actualizo la vida en la pantalla enemiga
		}
		
		if (personaje.getSalud() <= 0){
			enviarMensaje("Salir");
			JOptionPane.showMessageDialog(null, vida2+" gano la batalla"
					,"Fin",JOptionPane.INFORMATION_MESSAGE);
			ventana.dispose();
			ventana.setVisible(false);
		}
			mensajeEnergia = mensaje;
			
			if (pj.equals("PJ1"))
				energiaJ2.setValue(energiaJ2.getValue()-Integer.parseInt(mensaje));
			else
				energiaJ1.setValue(energiaJ1.getValue()-Integer.parseInt(mensaje));
			
			mensajeRegistro = "["+vida1+"]: Recibe "+mensaje+" de danio\n["+vida2+"]: Pierde "+mensaje+" de energia\n";
			area.append(mensajeRegistro);
			//se lo mando a la pantalla enemiga
			
			mensajeTotal += mensajeDefensa+"*"+mensajeVida+"*"+mensajeEnergia+"*"+mensajeRegistro;
			atacar.setEnabled(true);	
			enviarMensaje(mensajeTotal);
			
			Thread hilo = new Thread();
			
			try {
				
				img.setVisible(true);
				hilo.sleep(1000);
				img.setVisible(false);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void recibirMensaje (String mensaje){
		
		String [] mensajeAux = new String [4];
		
		for(int i=0; i<mensajeAux.length; i++)
			mensajeAux[i] ="";
		
		int j=0;
		for(int i=1; i<mensaje.length(); i++){
				
				if (mensaje.charAt(i) != '*')
				mensajeAux[j] += mensaje.charAt(i);
				else{
					j++;
				}
			}
		
		defensaJ2.setText(mensajeAux[0]);
		vidaJ2.setValue(Integer.parseInt(mensajeAux[1]));
		if (pj.equals("PJ1"))
			energiaJ1.setValue(energiaJ1.getValue()-Integer.parseInt(mensajeAux[2]));
		else
			energiaJ2.setValue(energiaJ2.getValue()-Integer.parseInt(mensajeAux[2]));
		area.append(mensajeAux[3]);
		
	}
	
	public void primerAtacante(String mensaje){
		
		String mensajeAux="";
		for(int i=1; i<mensaje.length(); i++)
			mensajeAux+=mensaje.charAt(i);
		int vel = Integer.parseInt(mensajeAux);
		
		if (personaje.getVelocidad() > vel){
			JOptionPane.showMessageDialog(null, "Posees mayor velocidad,comienzas primero"
					,"Inicio",JOptionPane.INFORMATION_MESSAGE);
			enviarMensaje("&Desactivar");
			enviarMensaje(mensajeInicial);
			atacar.setEnabled(true);
			comenzarBatalla.setVisible(false);
		}
		else{
			JOptionPane.showMessageDialog(null, vida2+" tiene mayor velocidad, comienza primero"
					,"Inicio",JOptionPane.INFORMATION_MESSAGE);
			
			enviarMensaje("&Desactivar");
			enviarMensaje(mensajeInicial);
			enviarMensaje("-Activar");
			comenzarBatalla.setVisible(false);
		}
	}
	
	public void desactivar(){
		comenzarBatalla.setVisible(false);
	}
	
	public void activarAtaque(){
		atacar.setEnabled(true);
	}
	
	public void ganadorBatalla (){
		JOptionPane.showMessageDialog(null, " Ganaste la batalla"
				,"Fin",JOptionPane.INFORMATION_MESSAGE);
		ventana.dispose();
		ventana.setVisible(false);
	}
	
public void enviarMensaje (String mensaje){
		try {
			new DataOutputStream(socket.getOutputStream()).writeUTF(mensaje);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
}
