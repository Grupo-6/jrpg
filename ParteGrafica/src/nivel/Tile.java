package nivel;

import Graficos.Pantalla;
import juego.Colours;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];//max can de tiles posible
	public static final Tile VOID = new BasicSolidTile(0,0,0,Colours.get(000, -1, -1, -1));
	public static final Tile STONE = new BasicSolidTile(1,1,0,Colours.get(-1,333,-1,-1));
	public static final Tile GRASS = new TileBase(2,2,0,Colours.get(-1,132,280,-1));;
	
	protected byte id;
	protected boolean solid;//colision
	protected boolean emitter;//light

	public Tile(int id,boolean esSolido,boolean esEmitter){
		this.id=(byte)id;
		if(tiles[id]!=null)throw new RuntimeException("Duplicate tile id on"+id);
		tiles[id]=this;
		this.solid=esSolido;
		this.emitter=esEmitter;
	}
	public byte getId(){
		return id;
	}
	public boolean isSolid(){
		return solid;
	}
	public boolean isEmitter(){
		return emitter;
	}
	public abstract void render(Pantalla screen, Nivel nivel, int x, int y);

}
