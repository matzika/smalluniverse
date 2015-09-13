# smalluniverse

Simulation of a small universe in OpenGL

Creation of a 3D solar system. The program is constructed to support any type of solar system depending on user’s preferences. One can have multiple solar systems running, consisting of more than one suns with planets and moons moving around them. It can also support the creation of wormholes and asteroids. We chose to simulate our very own solar system to show the power of our implementation. However, the current code can easily be extended to simulate other universes with small modifications inside the main class.

The program simulates a sun and the 9 planets of the solar system. Revolution and rotation are employed as the basic movements in the program. Shaders are utilized to simulate the lighting and shading of the space objects based on the light emitted from the sun. Similarly, textures are added to the objects to simulate the objects’ surfaces. Planets orbits are drawn so that the viewer can have a better perspective of the solar system. In addition, asteroids appear occasionally, entering the system and being destroyed when they reach the sun.

The user can navigate around the solar system using the keyboard or the mouse.

![System's Architecture](/images/architecture.png)

1. Universe
￼The main class of the program is implemented in Universe.java. There the basic structure of the universe is created. Shaders are initialized and basic textures that relate to the sun, the asteroids and the sky are imported. OpenGL methods’ initialization happens in initGL() method. The system supports the creation of several solar systems. Universe’s creation is done within createUniverse() method. A night sky is drawn in the background using a sphere that surrounds the whole universe (drawBackground() method). The drawing of all the space objects in the universe along with shaders application occurs in renderGL(). Finally, pollInput() method is responsible for receiving input from the keyboard or the mouse in order to give the user the freedom to navigate themselves around in space. Camera is set inside the Camera.java class and navigates based on users input.

2. Solar System 
The basic structure of the solar system is implemented in SolarSystem.java class. A solar system can consist of more than one sun. It also contains planets. A solar system instance is created with a number of suns. Then planets are added as soon as they are created based on their specific features using the respective methods provided in the class.
Our implementation utilizes HomeSolarSystem.java, which extends SolarSystem.java and adds the specifications of the planets and their moons based on data acquired from NASA. These include each planet’s and moon’s radius, orbit radius and tilt.
Textures are also applied to each object using the library Slick2D, which is a 2D Java Game Library for LWJGL. All textures were taken from http://planetpixelemporium.com/.

3. Space Objects
The class SpaceObject.java is the base class to implement either a sun, a planet or a moon. All of the latter classes implement SpaceObject. SpaceObject’ basic features are a radius, an orbit radius, tilt, a revolution angle and a rotation angle. Based on those features object’s movement around space is defined using the methods revolve() and rotate(). Other general features are also applied to a space object, such as its texture and its shader.
The Asteroid object is created separately from the other space objects in the program. The reason is that the way it is implemented utilizes different methods than the other objects (has no radius, tilt, rotation, or revolution). An asteroid is basically a particle source in space whose particles are fading as it moves towards the sun. The particle source is instantiated in Asteroid.java class and is implemented in ParticleSource.java class. An Asteroid is created randomly in the program and its position is constantly updated as it moves towards the sun. Its particles move along with it and fade after some time. As soon as the asteroid hits the sun it’s destroyed.

4. Movement 
The program simulates two basic movements for the planets and their moons:

  - Revolution 
  - Rotation

Sun utilizes only rotation.

The angle and coordinates are updated on each redraw, giving the impression of movement in the space.
The rotation is straight forward, and using a loop to 360 degrees which would use the cosine of an axial tilt as applied to the planet object.

5. Shading and Lighting  
- Shaders
For the shading of the solar system we implemented two separate shaders. The first is a simple light shader that uses Blinn-Phong for the lighting model. The second shader is to animate the sun. This shader was a bit more involved than the lighting one. There were several options for doing this animation. We could have used a map for the height displacement or done the displacement of the vertices through the shader. We opted for the second method as we are only animating one object, so the calculations do not diminish the performance. The animation is vertex displacement, handled through Perlin Noise. The perlin noise is used to determine the offset for each vertex. This is animated by passing in a time variable to the shader, allowing for a moving orb. This is done to simulate the hot, changing surface of the Sun.
￼
ShaderProgram.java was used to handle the loading of shaders and for setting shader parameters.

- Lights and Materials
Following the example within the Ray Tracer project, our light sources and planet surfaces were each represented by their own classes. The Material.java class is a representation of an object’s material. It contains the object’s texture as well the specular and diffuse components.
The Light.java class is a simple point light with a location, diffuse, specular, and ambient component. These are Im,s and Im,d components in the Blinn-Phong equation.
These two classes are part of every planet and sun within our system and are in charge of delegating to the shader what should be drawn.

#How to play with it
You as a user can interact with the program! Use the keyboard to move on the axis, zoom in and out, or the mouse to rotate around in space.
To Navigate inside the Universe:
- Use the arrow buttons to move forwards, backwards, left and right.
- You can also use the ‘QWEASD’ buttons to move on Y – X – Z axes.
- For a simple zoom in use the ‘Z’ button or for zoom out use the ‘O’ button.

#Notes
￼Some of the issues that we have encountered for shading is an odd effect being generated on the planets. Every so often, it appears that parts of the model become transparent, creating either a black circle or lit up texture (depending on the view angle front vs back). It seems to be a problem with depth sorting; however, changes to the lighting model only resulted in more erroneous modeling of the light. This issue is largely present when moving the camera and seems to occur to objects that are further away from the eye. When viewing an object up close, this issue is non-existent.

![Screenshot of the simulated universe](/images/universe.png)

Contact: katerina.iliakopoulou@gmail.com, 
