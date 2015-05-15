import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

/**
 * @class SolarSystem
 * Implements a solar system object that can have a  number of suns, planets and moons  
 * 
 * @author Aikaterini (Katerina) Iliakopoulou
 * @email ai2315@columbia.edu
 *
 */
public class SolarSystem {
	private Sun sun;
	
	private List<Sun> suns;
	
	private List<Planet> planets = new ArrayList<Planet>();
	
	private float[] location; //Location in the universe
	
	private ShaderProgram planetShader;
	
	public SolarSystem(){
		this.location = new float[]{0.0f, 0.0f, 0.0f};
	}

	public SolarSystem(float[] l){
		this.location = l;
	}

	public SolarSystem(Sun sun,Texture sunTexture){
		this.sun = sun;
		this.sun.setTexture(sunTexture);
	}
	
	public SolarSystem(List<Sun> suns){
		this.suns = suns;
	}
	
	public void setShader(ShaderProgram planetShader){
		this.planetShader = planetShader;
		for(Planet p : planets){
	      p.setShader(planetShader);
	      for(Moon m : p.getMoons()){
	        m.setShader(planetShader);
	      }
	    }
	}
	
	/**
	 * Creates a planet with a radius, orbitRadius and tilt
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 */
	public void createPlanet(float radius, float orbitRadius, float axisTilt){
		Planet planet = new Planet(radius,orbitRadius, axisTilt);
		planets.add(planet);
	}
	
	/**
	 * Creates a planet with a radius, orbitRadius, tilt and a texture
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 */
	public void createPlanet(float radius, float orbitRadius, float axisTilt, Texture texture){
		Planet planet = new Planet(radius,orbitRadius, axisTilt);
		planet.setTexture(texture);
		planets.add(planet);
	}
	
	/**
	 * Creates a planet with a radius, orbitRadius, tilt, texture and a number of moons
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 */
	public void createPlanet(float radius, float orbitRadius, float axisTilt, Texture texture, List<Moon> moons){
		Planet planet = new Planet(radius,orbitRadius, axisTilt);
		planet.setTexture(texture);
		
		for(Moon moon : moons)
			moon.setCenter(planet);
		planet.addMoons(moons);
			
		planets.add(planet);
	}
	
	/**
	 * Creates a planet with a radius, orbitRadius, tilt, texture, a number of moons and rings
	 * @param radius
	 * @param orbitRadius
	 * @param axisTilt
	 */
	public void createPlanet(float radius, float orbitRadius, float axisTilt, Texture texture, List<Moon> moons,List<Float []> ringsSpecs, List<Float [] > ringsColors){
		Planet planet = new Planet(radius,orbitRadius, axisTilt, ringsSpecs,ringsColors);
		planet.setTexture(texture);
	
		for(Moon moon : moons)
			moon.setCenter(planet);
		planet.addMoons(moons);
		
		planets.add(planet);
	}
	
	public Sun getSun(){
		return this.sun;
	}
	
	public List<Planet> getPlanets(){
		return this.planets;
	}
}
