package main.java.juego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener{
	
	public InputHandler(Juego juego){
		juego.addKeyListener(this);
	}
	
	public class Key{//creo la clase key
		private boolean pressed=false;
		private int numeroVecesPulsado=0;
		public boolean isPressed(){
			return pressed;
		}
		public int getNumeroVecesPulsado(){
			return numeroVecesPulsado;
		}
		
		public void toggle(boolean isPressed){
			pressed=isPressed;
			if(isPressed)numeroVecesPulsado++;
		}
	}
	
	public List<Key> keys = new ArrayList<Key>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	
	public void keyPressed(KeyEvent e) {
			toggleKey(e.getKeyCode(),true);
	}

	
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(),false);
	}

	
	public void keyTyped(KeyEvent e) {
		// TODO Auto- method stub
		
	}
	public void toggleKey(int keyCode,boolean isPressed){//toggle es activar
		
		if(keyCode == KeyEvent.VK_W||keyCode==KeyEvent.VK_UP){up.toggle(isPressed);}//para usar la W
		if(keyCode == KeyEvent.VK_S||keyCode==KeyEvent.VK_DOWN){down.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_A||keyCode==KeyEvent.VK_LEFT){left.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_D||keyCode==KeyEvent.VK_RIGHT){right.toggle(isPressed);}
		
		}

}
