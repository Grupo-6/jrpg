package main.java.juego;

public class Colours {
	public static int get(int color1,int color2,int color3,int color4){
		
		return (get(color4)<<24)+ (get(color3)<<16)+ (get(color2)<<8)+ (get(color1));
	}

	private static int get(int color) {
		if(color < 0) return 255;//si le mandas negativo transparente
		int r = color/100%10;//lo esta como parseando , quiere solo la primer parte
		int g = color/10%10;
		int b = color%10;
		return r*36 + g*6 +b;
	}
	static{
		Colours.get(555,543,542,123);
	}
}
