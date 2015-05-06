import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;


public class SolarSystem {
	private Sun sun;
	private List<Sun> suns;
	
	private List<Planet> planets = new ArrayList<Planet>();

	public SolarSystem(Sun sun){
		this.sun = sun;
	}
	
	public SolarSystem(List<Sun> suns){
		this.suns = suns;
	}
	
	public void createPlanet(float r, float distance, Texture t){
		Planet planet = new Planet(r,distance, t );
		
		planets.add(planet);
	}
	
	public Sun getSun(){
		return this.sun;
	}
	
	public List<Planet> getPlanets(){
		return this.planets;
	}
}
