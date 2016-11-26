package main.java.Graficos;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      "
									+"0123456789.,:;'\"!?$%()-=+/"      ;
	public static void render(String msg, Pantalla screen,int x, int y, int color,int scale){
		msg=msg.toUpperCase();//a mayuscula
		
		for(int i = 0; i< msg.length();i++){
			int charIndex=chars.indexOf(msg.charAt(i));
			// el 8 es porq tiene 8 picheles cada caracter, 30 por fila 30, 32 por los bounds limites de la sheet
			if(charIndex>=0) screen.render(x+(i*8), y, charIndex + 30*32, color,0x00,scale);
		}
	}
	
}
