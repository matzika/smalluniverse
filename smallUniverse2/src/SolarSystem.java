import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;


public class SolarSystem {
	private Sun sun;

	private List<Sun> suns;

	private List<Planet> planets = new ArrayList<Planet>();

	private float[] location; //Location in the universe

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

	public float[] getLocation(){
		return this.location;
	}

	public void setLocation(float[] l){
		this.location = l;
	}

	public void createPlanet(float r, float distance){
		Planet planet = new Planet(r,distance);
		planets.add(planet);
	}

	public void createPlanet(float r, float distance, Texture texture){
		Planet planet = new Planet(r,distance);
		planet.setTexture(texture);
		planets.add(planet);
	}

	public void createPlanet(float r, float distance, Texture texture, List<Moon> moons){
		Planet planet = new Planet(r,distance);
		planet.setTexture(texture);

		planet.addMoons(moons);

		planets.add(planet);
	}

	public void createPlanet(float r, float distance, Texture texture, List<Moon> moons,List<Float []> ringsSpecs, List<Float [] > ringsColors){
		Planet planet = new Planet(r,distance,ringsSpecs,ringsColors);
		planet.setTexture(texture);

		planet.addMoons(moons);

		planets.add(planet);
	}

	public void drawVenus(Texture venus){
		this.createPlanet(1.21f, 250f, venus);
	}

	public void drawMercury(Texture mercury){
		this.createPlanet(0.48f, 200f, mercury);
	}

	public void drawEarth(Texture earth, Texture moon){
		Moon earth_moon = new Moon(0.6f,5f);
		earth_moon.setTexture(moon);
		List<Moon> earth_moons = new ArrayList<Moon>();
		earth_moons.add(earth_moon);
		this.createPlanet(1.27f, 350f, earth,earth_moons);
	}

	public void drawMars(Texture mars, Texture phobos, Texture deimos){
		Moon mars_moon_a = new Moon(0.22f,3f);
		Moon mars_moon_b = new Moon(0.14f,5f);
		mars_moon_a.setTexture(phobos);
		mars_moon_b.setTexture(deimos);
		List<Moon> mars_moons = new ArrayList<Moon>();
		mars_moons.add(mars_moon_a);
		mars_moons.add(mars_moon_b);
		this.createPlanet(0.67f, 400f, mars,mars_moons);
	}

	public void drawJupiter(Texture jupiter, Texture io, Texture ganymedes, Texture europa, Texture callisto){
		Moon jupiter_moon_a = new Moon(1.5f,25f);
		Moon jupiter_moon_b = new Moon(1.8f,35f);
		Moon jupiter_moon_c = new Moon(2.6f,48f);
		Moon jupiter_moon_d = new Moon(2.4f,60f);
		jupiter_moon_a.setTexture(europa);
		jupiter_moon_b.setTexture(io);
		jupiter_moon_c.setTexture(ganymedes);
		jupiter_moon_d.setTexture(callisto);
		List<Moon> jupiter_moons = new ArrayList<Moon>();
		jupiter_moons.add(jupiter_moon_a);
		jupiter_moons.add(jupiter_moon_b);
		jupiter_moons.add(jupiter_moon_c);
		jupiter_moons.add(jupiter_moon_d);
		this.createPlanet(14.29f, 450f,jupiter,jupiter_moons);
	}

	public void drawSaturn(Texture saturn){
		List<Moon> saturn_moons = new ArrayList<Moon>();

		List<Float[] > ringsSpecs = new ArrayList<Float []>();
		List<Float[] > colorSpecs = new ArrayList<Float []>();

		Float[] firstRing = new Float[2];
		firstRing[0] = 16f;
		firstRing[1] = 17f;
		Float[] firstColor = new Float[3];
		firstColor[0] = 139f;
		firstColor[1] = 131f;
		firstColor[2] = 120f;
		ringsSpecs.add(firstRing);
		colorSpecs.add(firstColor);

		Float[] secondRing = new Float[2];
		secondRing[0] = 17f;
		secondRing[1] = 19f;
		Float[] secondColor = new Float[3];
		secondColor[0] = 255f;
		secondColor[1] = 239f;
		secondColor[2] = 219f;
		ringsSpecs.add(secondRing);
		colorSpecs.add(secondColor);

		Float[] thirdRing = new Float[2];
		thirdRing[0] = 20f;
		thirdRing[1] = 22f;
		Float[] thirdColor = new Float[3];
		thirdColor[0] = 205f;
		thirdColor[1] = 192f;
		thirdColor[2] = 176f;
		ringsSpecs.add(thirdRing);
		colorSpecs.add(thirdColor);

		this.createPlanet(12f, 500f, saturn,saturn_moons,ringsSpecs,colorSpecs);
	}

	public void drawUranus(Texture uranus){
		this.createPlanet(5.1f, 550f, uranus);
	}

	public void drawNeptune(Texture neptune){
		this.createPlanet(4.9f, 600f, neptune);
	}

	public void drawPluto(Texture pluto, Texture charon){
		Moon pluto_moon = new Moon(0.15f,5f);
		pluto_moon.setTexture(charon);
		List<Moon> pluto_moons = new ArrayList<Moon>();
		pluto_moons.add(pluto_moon);
		this.createPlanet(0.25f, 650f, pluto,pluto_moons);
	}

	public Sun getSun(){
		return this.sun;
	}

	public List<Planet> getPlanets(){
		return this.planets;
	}
}