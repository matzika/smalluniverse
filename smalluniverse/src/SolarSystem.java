import java.util.ArrayList;
import java.util.List;


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
	
	public void createPlanet(float r, float distance){
		Planet planet = new Planet(r,distance);
		
		planets.add(planet);
	}
	
	public Sun getSun(){
		return this.sun;
	}
	
	public List<Planet> getPlanets(){
		return this.planets;
	}
}
