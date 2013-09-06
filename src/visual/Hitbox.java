package visual;

public class Hitbox {
	private int x;
	private int y;
	private int xSize;
	private int ySize;
	public Hitbox(int x, int y, int xSize, int ySize) {
		super();
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	public boolean inside(int xin, int yin){
		return (this.x<xin&&xin<this.x+xSize)&&(this.y<yin&&yin<this.y+ySize);
	}
}

