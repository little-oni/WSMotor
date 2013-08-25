package dataHandlers;
import auxClasses.*;
public class Effect {
	private Chain<Skill> skills;
	public Effect(){
		skills = new Chain<Skill>();
	}
	public int getIndex(){
		return skills.getIndex();
	}
	public Chain<Skill> getSkills(){
		return this.skills;
	}
}
