package field;

import basicItems.Character;

public class Stage {
	private Character[] stage;
	public Stage(){
		stage = new Character[5];
	}
	public int charactersOnStage(){
		int num = 0;
		for (int i= 0; i < stage.length; i++) {
			if (stage[i] != null) {
				num++;
			}
		}
		return num;
	}
	public void entry(Character chara, int i){
		stage[i-1]=chara;
	}
	public Character getChara(int i){
		return stage[i];
	}
	public Character retire(int i){
		Character store = stage[i-1];
		stage[i-1]=null;
		return store;
	}
	public Character[] frontRow(){
		Character[] store = new Character[3];
		for (int i = 0; i < 3; i++){
			store[i]= stage[i];
		}
		return store;
	}
	public Character[] backRow(){
		Character[] store = new Character[2];
		for (int i = 0; i < 2; i++){
			store[i] = stage[i+3];
		}
		return store;
	}
	public void move(int i, int j){
		Character store = stage[i-1];
		stage[i-1]=stage[j-1];
		stage[j-1]=store;
	}
	public String toString(){
		String res = "";
		for (int i =0; i<3; i++){
			res = res + "["+stage[i]+"]";
		}
		res = res + "\n"+" ";
		for (int i = 3; i<5; i++){
			res = res + "["+stage[i]+"]";
		}
		return res;
	}
	public Character[] getStage(){
		return stage;
	}
}
