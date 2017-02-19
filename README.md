# movementAI

This project is to explore the various AI moving algorithms. Everything is written in Java, using the Processing library.

## The File Structure

Each different movement algorithm is implemented in a separate package. The details are listed below:

- **DataStructures** package has two classes, *Agent* which represents a character that can move around, and *GameObject* which represents stationary objects in game (for example Targets).
- **Agent** also contains a method called **update** which as the name indicates updates the position, orientation, velocity and rotation of the character.
- **Kinematic Seek** is implemented in the package labeled as **BasicMotion**. It contains the class Kmotion which has a method called **getKinematic()** that handles getting the velocity and rotation that the character should have.
- The class labeled **Kinematic** is the one you should run if you want to see the kinematic motion.
- **Arrive Steering Behavior** is Implemented in the package **ArriveSteering** which contains three classes.
- The first class is **Smotion** which has a method called **getSteering()** which is similar to the **getKinematic()** method we talked about previously, but it gets and sets the linear and angular accelerations for the character.
- The second class is **Align** which is similar to **Smotion** but handles the orientation instead of the position.
- The third class is **Arrive** which implements the actual **Steering Arrive behavior**, and when you run it, you'll get the desired behavior.
- **Wander Steering Behavior** is implemented in the package **WanderSteering**. This package contains two classes, **Wmotion** which randomly picks the direction the character should move towards and delegates to Arrive and Align to implement the movement.
- The **Wander** class simply implements the behavior, and when run, shows the character wandering about the canvas.
- **Flocking Behavior** package contains the flocking behavior. It has two classes, **Boid** which represents a single character whether it be a leader or a follower. And **Flocking** which basically implements the behavior.

