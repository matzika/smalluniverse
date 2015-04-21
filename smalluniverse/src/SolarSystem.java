import java.util.List;


public class SolarSystem {
	private Sun sun;
	private List<Sun> suns;
	
	private List<Planet> planets;

	public SolarSystem(Sun sun){
		this.sun = sun;
	}
	
	public SolarSystem(List<Sun> suns){
		this.suns = suns;
	}
	
	public void createPlanet(float x,float y, float z, float r){
		Planet planet = new Planet(r);
		
		planets.add(planet);
	}
	
	public Sun getSun(){
		return this.sun;
	}
	
	public List<Planet> getPlanets(){
		return this.planets;
	}
}
