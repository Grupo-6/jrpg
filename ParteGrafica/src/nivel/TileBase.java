package nivel;

import Graficos.Pantalla;

public class TileBase extends Tile {

	protected int tileId;
	protected int tileColor;
	public TileBase(int id,int x,int y ,int tileColor) {
		super(id, false, false);
		this.tileId = x+ y;
		this.tileColor=tileColor;
		
	}

	@Override
	public void render(Pantalla screen, Nivel nivel, int x, int y) {
		screen.render(x, y, tileId, tileColor,0x00,1);
		
	}

}
