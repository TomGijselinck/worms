 package worms.model;

import java.util.HashSet;
import java.util.Set;

import worms.exceptions.IllegalPositionException;
import worms.exceptions.IllegalCoordinateException;
import worms.exceptions.IllegalJumpException;
import worms.exceptions.IllegalNameException;
import worms.exceptions.IllegalRadiusException;
import worms.exceptions.IllegalStepsException;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worms involving a position, a direction, a radius, a mass
 * a maximum of action points, current action points and a name.
 * 
 * @invar	The direction of each worm must be a valid direction for any worm.
 * 		  |	isValidDirection(getDirection())
 * @invar	Each worm can have its radius as its radius.
 * 		  |	canHaveAsRadius(getRadius())
 * @invar	The name of each worm must be valid name for any worm.
 * 		  |	isValidName(getName())
 * @invar	Each worm has a proper position.
 * 		  |	hasProperPosition()
 * @invar	Each worm has a proper world.
 * 		  |	hasProperWorld()
 * @invar	The weapons attached to each worm must be proper weapons for that
 * 			worm.
 * 		  |	hasProperWeapons()
 * 
 * @version 1.1
 * @author 	Tom Gijselinck
 *
 */

public class Worm {
	
	//CONTSTRUCTORS
	/**
	 * Initialize this new worm with given position, given direction, 
	 * given radius and given name.
	 * 
	 * @param 	position
	 * 			The position (x,y) for this new worm in a two dimensional space.
	 * @param 	direction
	 * 			The direction this new worm faces.
	 * @param 	radius
	 * 			The radius for this new worm.
	 * @param 	name
	 * 			The name for this new worm.
	 * @pre		The given direction for this new worm is a valid direction for
	 * 			any worm.
	 * 		  |	isValidDirection(direction)
	 * @post	The new position for this new worm is equal to the given
	 * 			position.
	 * 		  |	new.getPosition() == position
	 * @post	The new direction for this new worm is equal to the given
	 * 			direction.
	 * 		  |	new.getDirection() == direction
	 * @post	The new radius for this new worm is equal to the given radius.
	 * 		  |	new.getRadius() = radius
	 * @post	The new name for this new worm is equal to the given name.
	 * @post	The new current action points for this new worm is equal to the 
	 * 			action points maximum of this worm.
	 * @throws	IllegalPositionException
	 * 			The given position for this new worm is not a valid position for
	 * 			any worm.
	 * 		  |	! isValidPosition(position)
	 * @throws	IllegalRadiusException(radius, this)
	 * 			This new worm cannot have the given radius as its radius.
	 * 		  |	! canHaveAsRadius(radius)
	 * @throws	IllegalNameException(name, this)
	 * 		  	The given name for this new worm is not a valid name for any 
	 * 			worm.
	 * 		  |	! isValidName(name)
	 */
	@Raw
	public Worm(Position position, double direction, double radius, String name)
			throws IllegalPositionException, IllegalRadiusException,
			IllegalNameException {
		assert isValidDirection(direction);
		setPosition(position);
		setDirection(direction);
		setRadius(radius);
		setCurrentActionPoints(getActionPointsMaximum());
		setName(name);
	}
	
	/**
	 * Initialize this new worm with given name.
	 * 
	 * @param 	name
	 * 			The name for this new worm.
	 * @effect	This new worm  is initialized with the given name as its name,
	 * 			the origin (0,0) as its position, the lower angle bound as its 
	 * 			direction, the lower radius bound as its radius and the action  
	 * 			points maximum as its current amount of action points.
	 * 		  |	this(new position(0,0), lowerAngleBound, lowerRadiusBound, 
	 * 		  |		getActionPointsMaximum(), name)
	 */
	public Worm(String name) throws IllegalNameException {
		this(new Position(0, 0), 0, 1, name);
		setRadius(getLowerRadiusBound());
		setCurrentActionPoints(getActionPointsMaximum());
		setDirection(getLowerAngleBound());
	}
	
	
	
	
	//DESTRUCTOR
	public void terminate() {}
	
	public boolean isTerminated() { return isTerminated;}
	
	private boolean isTerminated;
	
	
	
	
	//POSITION RELATED METHODS (defensive)
	/**
	 * Return the position of this worm.
	 */
//TODO: check bij implementatie en verwijder
//	@Basic
//	public Position getPosition() {
//		return this.position;
//	}
	
	/**
	 * Move this worm a number of given steps in the direction it is facing.
	 *   Active moving costs action points proportional to the horizontal and
	 *   vertical movement of a worm. Passive movement does not cost any action
	 *   points.
	 * 
	 * @param 	steps
	 * 			The number of steps this worm has to move.
	 * @post	The new position of this worm is equal to the initial position
	 * 			of this worm incremented by the number of steps to be taken in
	 * 			the direction this worm faces.
	 * 		  |	new.getPosition() == 
	 * 		  |		this.setPosition(getPosition().translate(
	 * 		  |			steps*this.getRadius()*Math.cos(this.getDirection()),
	 * 		  |			steps*this.getRadius()*Math.sin(this.getDirection()) ))
	 * @post	If the movement is active the new current action points of this 
	 * 			worm is equal to the initial current action points decremented 
	 * 			with the consumed action points.
	 * 		  | if (isActive == true)
	 * 		  |		new.getCurrentActionPoints() ==
	 * 		  |			this.getCurrentActionPoints() 
	 * 		  |		  		- steps*Math.cos(this.getDirection())
	 * 		  |		  		- steps*4*Math.sin(this.getDirection())
	 * @effect	The new position of this worm is the initial position of this
	 * 			worm translated by the given number of steps.
	 * 		  |	this.setPosition(getPosition().translate(
	 * 		  |		steps*this.getRadius()*Math.cos(this.getDirection()),
	 * 		  |		steps*this.getRadius()*Math.sin(this.getDirection()) ))
	 * @throws	IllegalStepsException(steps, this)
	 * 			This worm cannot actively move the given number of steps.
	 * 		  | ! canActivelyMoveSteps(steps)
	 */
	public void move(int steps, boolean isActive) 
			throws IllegalStepsException {
		if (! canActivelyMoveSteps(steps))
			throw new IllegalStepsException(steps, this);
		setPosition(getPosition().translate(steps*getRadius()*Math.cos(getDirection()),
			steps*getRadius()*Math.sin(getDirection()) ));
		if (isActive) {
			setCurrentActionPoints(getCurrentActionPoints() 
				- (int) Math.round(steps*Math.cos(getDirection())
				+ steps*4*Math.sin(getDirection())) );
		}
	}
	
	/**
	 * Jump this worm from the current position with respect to its direction
	 * and its current number of action points if this worm can jump.
	 *   Jumping of a worm is an active movement and consumes all its remaining
	 *   current action points.
	 *   
	 * @post	The new position of this worm is equal to the initial position
	 *   		of this worm translated by the jump distance if its direction
	 *   		is upwards.
	 *   	  |	new.getPosition ==
	 *   	  |		this.setPosition(getPosition.translate(jumpDistance(),0))
	 * @post	The new current action points of this worm is equal to zero.
	 * 		  |	new.getActionPoints() == 0
	 * @throws	IllegalJumpException(this)
	 * 			This worm cannot jump.
	 * 		  |	! canJump()
	 * @effect	The new position of this worm is the initial position of this
	 * 			worm translated by the jump distance.
	 * 		  |	this.setPosition(getPosition().translate(jumpDistance(), 0))
	 */
	public void jump() throws IllegalCoordinateException, IllegalJumpException {
		if (! canJump())
			throw new IllegalJumpException(this);
		setPosition(getPosition().translate(jumpDistance(),0));
		setCurrentActionPoints(0);
	}
	
	/**
	 * Checks whether this worm can jump.
	 * 
	 * @return	True if and only if this worm is facing upwards and this worm
	 * 			has current action points greater than zero.
	 * 		  |	result ==
	 * 		  |		(this.getDirection >= lowerAngleBound)
	 *   	  |		&& (this.getDirection() <= upperAngleBound/2)) 
	 *   	  |		&& (this.getCurrentActionPoints() > 0)
	 */
	public boolean canJump() {
		return ( (getDirection() >= lowerAngleBound) 
				&& (getDirection() <= upperAngleBound/2)
				&& (getCurrentActionPoints() > 0) );
	}
	
	
	public static double getGravityOfEarth() {
		return gravityOfEarth;
	}
	
	/**
	 * Variable registering the gravity of the Earth in m/s� referring to the
	 * acceleration the earth gives to worms on or near its surface.
	 */
	private static final double gravityOfEarth = 9.80665;
	
	/**
	 * Check whether this worm can actively move the given number of steps.
	 * 
	 * @param	steps
	 * 			The number of steps to check.
	 * @return	True if and only if this worm has enough action points to 
	 * 			actively move the given number of steps in the direction it is
	 * 			facing.
	 * 		  |	result ==
	 * 		  |	  (this.getCurrentActionPoints() >= 
	 * 		  |		  steps*Math.cos(this.getDirection())
	 * 		  |		+ steps*4*Math.sin(this.getDirection()) )
	 */
	public boolean canActivelyMoveSteps(double steps) {
		return (getCurrentActionPoints() >= 
				   steps*Math.cos(getDirection())
				 + steps*4*Math.sin(getDirection()) );
	}
	
	/**
	 * Returns the time in seconds this worm needs for jumping a certain 
	 * distance.
	 * 
	 * @return	The resulting time is equal to the number of seconds it takes to
	 * 			translate a horizontal distance at the horizontal component of
	 * 			the initial velocity of this worm.
	 * 		  |	result ==
	 * 		  |		jumpDistance()/(jumpSpeed()*Math.cos(getDirection()))
	 */
	public double jumpTime() {
		return jumpDistance()/(jumpSpeed()*Math.cos(getDirection()));
	}
	
	/**
	 * Returns the in-flight position of a jumping worm after a given time 
	 * interval after launch.
	 * 
	 * @throws	IllegalJumpException
	 * 			This worm cannot jump.
	 * 		  |	! canJump()
	 * @throws	IllegalArgumentException
	 * 			The given time interval is greater than the jump time.
	 * 		  |	timeInterval > jumpTime()
	 * @return	The resulting position is equal to the initial position of this
	 * 			worm translated by the distance covered in the given time
	 * 			interval at the jump speed of this worm.
	 * 		  |	result ==
	 * 		  |		getPosition.translate(
	 * 		  |			+ jumpSpeed()*Math.cos(getDirection())*timeInterval,
	 * 		  |			jumpSpeed()*Math.sin(getDirection())
	 * 		  |				- 0.5*g*Math.pow(timeInterval, 2) )
	 */
	public Position jumpStep(double timeInterval)
			throws IllegalArgumentException, IllegalJumpException {
		if (! canJump())
			throw new IllegalJumpException(this);
		if (timeInterval > jumpTime())
			throw new IllegalArgumentException();
		double g = getGravityOfEarth();
		double deltaX = jumpSpeed() * Math.cos(getDirection()) * timeInterval;
		double deltaY = jumpSpeed() * Math.sin(getDirection()) * timeInterval
				- 0.5 * g * Math.pow(timeInterval, 2);
		Position flightPosition = getPosition().translate(deltaX, deltaY);				
		return flightPosition;
	}
	
	/**
	 * Returns the initial velocity this worm has in m/s when jumping.
	 * 
	 * @return	The resulting speed of this worm is equal to the speed as a
	 * 			result of the jump force exerted by the worm in an interval of 
	 * 			0.5 seconds.
	 * 		  | double F = 5*this.getCurrentActionPoints() + this.getMass()*g
	 * 		  |	result ==
	 *   	  |		F/this.getMass()*0.5
	 */
	public double jumpSpeed() {
		double g = getGravityOfEarth();
		double F = 5 * getCurrentActionPoints() + getMass() * g;
		return F / getMass() * 0.5;
	}
	
	/**
	 * Returns the horizontal distance covered when this worm jumps, expressed
	 * in metres.
	 * 
	 * @return	The resulting jump distance of this worm is equal to the 
	 * 			distance covered by	this worm at its jump speed in the direction 
	 * 			it is facing.
	 * 		  |	result ==
	 *   	  |		jumpSpeed()^2*Math.sin(2*this.getDirection())/g
	 */
	public double jumpDistance() {
		double g = getGravityOfEarth();
		return Math.pow(jumpSpeed(), 2)*Math.sin(2*getDirection())/g;
	}
	
	/**
	 * Set the position of this worm to the given position.
	 * 
	 * @param 	position
	 * 			The new position for this worm.
	 * @post	The new position of this worm is equal to the given position.
	 * 		  |	new.getPosition() == position
	 * @throws	IllegalPositionException
	 * 			The given position for this worm is not a valid position for any
	 * 			worm.
	 * 		  |	! isValidPosition(position)
	 */
//TODO: check bij implementatie en vewijder
//	private void setPosition(Position position) {
//		if (! isValidPosition(position))
//			throw new IllegalPositionException(position, this);
//		this.position = position;
//	}
	
	/**
	 * Variable referencing the position of this worm.
	 */
//TODO: check bij implementatie en verwijder
//	private Position position = Position.ORIGIN;
	
	/**
	 * 
	 * @param	position
	 * 			The position to check.
	 * @return	True if and only if the given position is an effective position.
	 * 		  |	result ==
	 * 		  |		position != null
	 */
	//TODO: overhevelen naar canHaveAsPosition()
//	public boolean isValidPosition(Position position) {
//		return position != null;
//	}
	
	
	
	//DIRECTION RELATED METHODS (nominal)
	/**
	 * Return the direction of this worm expressed in radians.
	 *   The direction is the angle expressed in radians at which a worm is 
	 *   facing.
	 */
	@Basic @Raw
	public double getDirection() {
		return this.direction;
	}
	
	/**
	 * Turn this worm by the given angle in radians by changing its direction.
	 * @param 	angle
	 * 			The angle in radians by which this worm has to turn.
	 * @pre		This worm can turn the given angle.
	 * 		  |	canTurn(angle)
	 * @post	The new direction of this worm is equal to modulo angle range of  
	 * 			the sum of the old direction from this worm and the given angle.
	 * 			The resulting angle is a valid angle.
	 * 		  | let 
	 * 		  |		double newAngle = (this.getDirection() + angle)%(angleRange) 
	 * 		  |	in
	 * 		  |		if (newAngle > 0)
	 * 		  |			new.getDirection() == newAngle;
	 * 		  |		if (newAngle < 0)
	 * 		  |			new.getDirection() == newAngle + angleRange;
	 * @post	The new current action points is equal to the initial current 
	 * 			action points decremented by the fraction of the angle range 
	 * 			times sixty.
	 * 		  |	new.getCurrentActionPoints() ==
	 * 		  |		this.getCurrentActionPoints() 
	 * 		  |		- Math.abs(angle)/(angleRange)*60)
	 */
	public void turn(double angle) {
		assert canTurn(angle);
		double newAngle = (this.getDirection() + angle) % (getAngleRange());
		if (newAngle > 0) {
			setDirection(newAngle);
			setCurrentActionPoints((int) (getCurrentActionPoints() - 60
					* Math.abs(angle) / (getAngleRange())));
		}
		if (newAngle < 0) {
			setDirection(newAngle + getAngleRange());
			setCurrentActionPoints((int) (getCurrentActionPoints() - 60
					* Math.abs(angle) / (getAngleRange())));
		}
	}
	
	/**
	 * Checks whether the given direction is a valid direction for any worm.
	 * 
	 * @param 	direction
	 * 			The direction to check.
	 * @return	True if and only if the given direction is a valid number, not 
	 * 			less than the lower angle bound and smaller than the upper angle
	 * 			bound.
	 * 		  |	result == ( (direction.isNaN() == false)
	 * 		  |				&& (direction >= lowerAngleBound) 
	 * 		  |				&& (direction < upperAngleBound) )
	 */
	public static boolean isValidDirection(double direction) {
		return 	( (Double.isNaN(direction) == false)
				&& (direction >= getLowerAngleBound())
				&& (direction < getUpperAngleBound()) );
	}
	
	/**
	 * Return the upper angle bound for any worm.
	 */
	@Basic
	public static double getUpperAngleBound() {
		return upperAngleBound;
	}

	/**
	 * Return the lower angle bound for any worm.
	 */
	@Basic
	public static double getLowerAngleBound() {
		return lowerAngleBound;
	}

	/**
	 * Return a boolean reflecting whether this worm can turn by the given
	 * angle.
	 * 
	 * @param 	angle
	 * 			The angle to be checked.
	 * @return	True if and only if the absolute value of the given angle is a 
	 * 			valid direction and if this worm has enough action points to
	 * 			turn by the given angle.
	 * 		  |	result == ( (isValidDirection(Math.abs(angle)))
	 * 		  |			 	&& (this.getCurrentActionPoints()
	 * 		  |				  >= (Math.abs(angle)/(angleRange)*60)) );
	 */
	public boolean canTurn(double angle) {
		return ( (isValidDirection(Math.abs(angle)))
				&& (this.getCurrentActionPoints()
					>= (Math.abs(angle)/(getAngleRange())*60)) );
	}
	
	/**
	 * Return the angle range any worm has.
	 * @return	The resulting angle range is the range between the lower angle
	 * 			bound and the upper angle bound.
	 * 			| result == 
	 * 		    |		getUpperAngleRange() - getLowerAngleRange()
	 */
	public double getAngleRange() {
		return getUpperAngleBound() - getLowerAngleBound();
	}

	/**
	 * Variable registering the lower angle bound any worm can have as 
	 * a direction.
	 */
	private final static double lowerAngleBound = 0;
	
	/**
	 * Variable registering the upper angle bound any worm can have as 
	 * a direction.
	 */
	private final static double upperAngleBound = 2*Math.PI;
	
	/**
	 * Set the direction of this worm to the given direction.
	 * 
	 * @param	direction
	 * 			The new direction this worm faces.
	 * @pre		The given direction is a valid direction for any worm.
	 * 		  |	isValidDirection(direction)
	 * @post	The new direction of this worm is equal to the given direction.
	 * 		  |	new.getDirection() == direction
	 */
	@Raw
	private void setDirection(double direction) {
		assert isValidDirection(direction);
		this.direction = direction;
	}
	
	/**
	 * Variable referencing the direction of this worm.
	 */
	private double direction;
	
	
	
	
	
	//RADIUS RELATED METHODS (defensive)
	/**
	 * Return the radius of this worm expressed in metres.
	 *   The radius of a worm defines the circular shape of that worm.
	 */
	@Basic
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Return the lower radius bound of this worm expressed in metres.
	 */
	@Basic @Immutable
	public double getLowerRadiusBound() {
		return lowerRadiusBound;
	}
	
	/**
	 * Return a boolean reflecting whether this worm can have the given radius
	 * as its radius.
	 * 
	 * @param 	radius
	 * 			The radius to check.
	 * @return	True if and only if the given radius is a valid number, not less 
	 * 			than the lower radius bound of this worm and less than positive 
	 * 			infinity (i.e. a finite radius).
	 * 		  |	result ==
	 * 		  |	  ( (Double.isNaN(radius) == false)
	 * 		  |	 && (radius >= getLowerRadiusBound())
	 * 		  |  && (radius < Double.POSITIVE_INFINITY) )
	 */
	public boolean canHaveAsRadius(double radius) {
		return
		 (  (Double.isNaN(radius) == false)
		 &&	(radius >= getLowerRadiusBound())
		 && (radius < Double.POSITIVE_INFINITY) );
	}
	
	/**
	 * Variable registering the lower radius bound of this worm expresssed in 
	 * metres.
	 */
	private final double lowerRadiusBound = 0.25;
	
	/**
	 * Return the mass of this worm in kilograms.
	 *   The mass of a worm is derived from its radius which specifies a
	 *   spherical body from which the mass is calculated.
	 *   
	 * @return	The resulting mass is equal to the mass of a spherical body with
	 * 			a predefined worm density.
	 * 		  |	result ==
	 * 		  |		wormDensity*4/3*Math.PI*Math.pow(getRadius(), 3)
	 */
	public double getMass() {
		return getWormDensity()*4/3*Math.PI*Math.pow(getRadius(), 3);
	}
	
	/**
	 * Return the worm density of all worms expressed in kg/m�.
	 */
	private static double getWormDensity() {
		return wormDensity;
	}
	
	/**
	 * Variable registering the density of any worm in kg/m�.
	 */
	private static final double wormDensity = 1062;
	
	/**
	 * Return the maximum action points this worm can have.
	 *   The maximum of action points of this worm is derived from its mass.
	 * 
	 * @return	The resulting maximum action points is equal to the mass of this
	 * 			worm rounded to the nearest integer.
	 * 		  |	result ==
	 * 		  |		Math.round(getMass())
	 */
	public int getActionPointsMaximum() {
		return (int) Math.round(getMass());
	}
	
	/**
	 * Return the maximum hit points this worm can have.
	 *   The maximum of hit points of this worm is derived from its mass.
	 * 
	 * @return	The resulting maximum hit points is equal to the mass of this
	 * 			worm rounded to the nearest integer.
	 * 		  |	result ==
	 * 		  |		Math.round(getMass())
	 */
	public int getHitPointsMaximum() {
		return (int) Math.round(getMass());
	}
	
	/**
	 * Set the radius of this worm to the given radius.
	 * 
	 * @param 	radius
	 * 			The new radius for this worm.
	 * @post	The new radius of this worm is equal to the given radius.
	 * 		  |	new.getRadius() == radius
	 * @throws	IllegalRadiusException(radius, this)
	 * 			The given radius is not a valid radius for this worm.
	 * 		  |	! canHaveAsRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalRadiusException {
		if (! canHaveAsRadius(radius)) 
			throw new IllegalRadiusException(radius, this);
		this.radius = radius;
	}
	
	/**
	 * Variable registering the radius of this worm.
	 */
	private double radius;
	
	
	
	
	
	//ACTION POINTS RELATED METHODS (total)
	/**
	 * Return the current action points of this worm.
	 *   The current action points of a worm determines how much a worm can 
	 *   move, turn and how far a worm can jump.
	 */
	@Basic
	public int getCurrentActionPoints() {
		return this.currentActionPoints;
	}
	
	/**
	 * Set the current action points of this worm to the given action points.
	 * 
	 * @param	actionPoints
	 *			The new current action points for this worm.
	 * @post	If the given current action points is less than zero, the 
	 * 			new current action points for this new worm is equal to zero.
	 * 			If the given current action points is greater than or equal to
	 * 			the maximum amount of action points, the new current action
	 * 			points for this new worm is equal to the maximum action points. 
	 * 			Otherwise the new current action points for this new worm is
	 * 			equal to the given current action points.
	 * 		  |	if (actionPoints <= 0) this.currentActionPoints = 0;
	 * 		  | if ( (actionPoints > 0) && (actionPoints < getActionPointsMaximum()))
	 *		  |		currentActionPoints = actionPoints;
	 *		  | if (actionPoints >= getActionPointsMaximum())
	 *		  | 	this.currentActionPoints = getActionPointsMaximum();
	 */
	public void setCurrentActionPoints(int actionPoints) {
		if (actionPoints <= 0) {
			currentActionPoints = 0;
		} else if ((actionPoints > 0)
				&& (actionPoints < getActionPointsMaximum())) {
			currentActionPoints = actionPoints;
		} else if (actionPoints >= getActionPointsMaximum()) {
			this.currentActionPoints = getActionPointsMaximum();
		}
	}
	
	/**
	 * Variable registering the the current action points of a worm.
	 */
	private int currentActionPoints;
	
	
	
	
	
	//HIT POINTS
	/**
	 * Return the current amount of hit points this worm has left.
	 */
	@Basic
	public int getCurrentHitPoints() { return currentHitPoints;}
	
	/**
	 * Set the current hit points of this worm to the given hit points.
	 * 
	 * @param	hitPoints
	 *			The new current hit points for this worm.
	 * @post	If the given current hit points is less than zero, the 
	 * 			new current hit points for this new worm is equal to zero.
	 * 			If the given current hit points is greater than or equal to
	 * 			the maximum amount of hit points, the new current hit
	 * 			points for this new worm is equal to the maximum hit points. 
	 * 			Otherwise the new current hit points for this new worm is
	 * 			equal to the given current hit points.
	 * 		  |	if (hitPoints <= 0) this.currentHitPoints = 0;
	 * 		  | if ( (hitPoints > 0) && (hitPoints < getHitPointsMaximum()))
	 *		  |		currentHitPoints = hitPoints;
	 *		  | if (hitPoints >= getHitPointsMaximum())
	 *		  | 	this.currentHitPoints = getHitPointsMaximum();
	 */
	public void setCurrentHitPoints(int hitPoints) {
		if (hitPoints <= 0) {
			currentHitPoints = 0;
		} else if ((hitPoints > 0)
				&& (hitPoints < getActionPointsMaximum())) {
			currentHitPoints = hitPoints;
		} else if (hitPoints >= getActionPointsMaximum()) {
			this.currentHitPoints = getActionPointsMaximum();
		}
	}
	
	/**
	 * Variable registering the current hit points of this worm.
	 */
	private int currentHitPoints;
	
	
	
	
	
	//NAME RELATED METHODS (defensive)
	/**
	 * Return the name of this worm.
	 */
	@Basic
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of this worm to the given name.
	 * 
	 * @param	name
	 * 			The new name for this worm.
	 * @post	The new name of this worm is equal to the given name.
	 * 		  |	this.getName() == name
	 * @throws	IllegalNameException(name, this)
	 * 		  	The given name is not a valid name for any worm.
	 * 		  |	! isValidName(name)
	 */
	public void setName(String name) throws IllegalNameException {
		if (! isValidName(name))
			throw new IllegalNameException(name, this);
		this.name = name;
	}
	
	/**
	 * Variable registering the name of a worm.
	 */
	private String name;
	
	/**
	 * Checks whether the given name is a valid name for any worm.
	 * 
	 * @param 	name
	 * 			The name to check.
	 * @return	True if and only if the given name is at least two characters
	 * 			long, start with an uppercase letter and all characters are
	 * 			upper or lower case letters, spaces or single or double quotes.
	 * 		  |	result ==  
	 * 		  |		(name.length() >= 2)
	 * 		  |		&& (Character.isUpperCase(name.charAt(0)))
	 *   	  |		&& ! for (int i = 0; i < name.length(); i++) {
	 *		  |				char ch = name.charAt(i);
	 *		  |				(! Character.isLowerCase(ch))
	 *		  |				&&	(! Character.isUpperCase(ch))
	 *		  |				&&	(! Character.isWhitespace(ch))
	 *		  |				&&	(ch != '\'')
	 *		  |				&&	(ch != '\"') )
	 *		  |			}		
	 */
	public boolean isValidName(String name) {
		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if (	(! Character.isLowerCase(ch))
				&&	(! Character.isUpperCase(ch))
				&&	(! Character.isWhitespace(ch))
				&&	(ch != '\'')
				&&	(ch != '\"') ) {
						return false;
			}
		}
		return ((name.length() >= 2)
				&& (Character.isUpperCase(name.charAt(0))));
	}
	
	
	
	
	//ASSOCIATIONS
	/**
	 * Return the position of this worm.
	 */
	public Position getPosition() { return position;}
	
	/**
	 * Checks whether this worm can have the given position as its position.
	 * 
	 * @param	position
	 * 			The position to check.
	 * @return	True if the given position is not effective if this worm is 
	 * 			terminated.
	 * 		  |	if (isTerminated())
	 * 		  |		then result == (position == null)
	 * 			Otherwise true if and only if the given position is effective.
	 * 		  |	else result == (position != null) 
	 */
	public boolean canHaveAsPosition(Position position) { return true;}
	
	/**
	 * Check whether this worm has a proper position.
	 * 
	 * @return	True if and only if this worm can have its position as its
	 * 			position.
	 * 		  |	result == canHaveAsPosition(getPosition()
	 */
	public boolean hasProperPosition() { return true;}
	
	/**
	 * Set the position of this worm to the given position.
	 * 
	 * @param 	position
	 * 			The position to attach this worm to.
	 * @post	This worm references the given position as its position.
	 * 		  |	new.getPosition() == position
	 */
	private void setPosition(Position position) {}
	
	/**
	 * Variable referencing the position to which this worm is attached.
	 */
	private Position position;
	
	/**
	 * Return the world where this worm is active in.
	 */
	@Basic
	public World getWorld() { return world;}
	
	/**
	 * Check whether this worm can have the given world as its world.
	 * 
	 * @param 	world
	 * 			The world to check.
	 * @return	True if and only if the given world is not effective or if it
	 * 			can have this worm as one of its worms.
	 * 		  |	result ==
	 * 		  |		( (world == null)
	 * 		  |	   || world.canHaveAsWorm(this) )
	 */
	public boolean canHaveAsWorld(World world) {return true;}
	
	/**
	 * Check whether this worm has a proper world attached to it.
	 * 	Check for consistency of mutual reference implied by bidirectional
	 * 	association and additional restrictions on associated objects.
	 * 
	 * @return	True if and only if this worm can have its world as the world to
	 * 			which it is attached, and if that world is either not effective
	 * 			or has this worm as one of its worms.
	 * 		  |	result == 
	 * 		  |		( canHaveAsWorld(getWorld())
	 * 		  |	   && ( (getWorld() == null)
	 * 		  |	     ||	getWorld().hasAsWorm(this)) )
	 */
	public boolean hasProperWorld() { return true;}
	
	/**
	 * Set the world where this worm is attached to, to the given world.
	 * 
	 * @param 	world
	 * 			The world to which this worm must be attached.
	 * @post	This worm is attached to the given world.
	 * 		  |	new.getWorld() == world
	 */
	public void setWorld(World world) {}
	
	/**
	 * Variable referencing the world where this worm is attached to.
	 */
	private World world;
	
	/**
	 * Check whether this worm has the given weapon as on of its weapons.
	 * 
	 * @param 	weapon
	 * 			The weapon to check.
	 */
	@Basic
	public boolean hasAsWeapon(Weapon weapon) { return true;}
	
	/**
	 * Check whether this worm can have the given weapon as one of its weapons.
	 * 
	 * @param 	weapon
	 * 			The weapon to check.
	 * @return	False if the given weapon is not effective.
	 * 		  |	if (weapon == null)
	 * 		  |		then result == false
	 * 			Otherwise true if and only if this worm is not yet terminated
	 * 			and the given weapon is not yet registered as a weapon attached
	 * 			to this worm.
	 * 		  |	else result == 
	 * 		  |		( (! this.isTerminated())
	 * 		  |	   && (! this.hasAsWeapon(weapon)) )
	 */
	public boolean canHaveAsWeapon(Weapon weapon) { return true;}
	
	/**
	 * Check whether this worm has proper weapons attached to it.
	 * 
	 * @return	True if and only if this worm can have each of its weapons as
	 * 			a weapon attached to it.
	 * 		  |	for each weapon in Weapon:
	 * 		  |		( if (this.hasAsWeapon(weapon))
	 * 		  |			then canHaveAsWeapon(weapon) )
	 */
	public boolean hasProperWeapons() { return true;}
	
	/**
	 * Add the given weapon to the set of weapons attached to this worm.
	 * 
	 * @param	weapon
	 * 			The weapon to be added.
	 * @post	This worm has the given weapon as one of its weapons.
	 * 		  |	new.hasAsWeapon(weapon)
	 * @throws	IllegalArgumentException
	 * 			This worm cannot have the given weapon as one if its weapons.
	 * 		  |	! canHaveAsWeapon(weapon)
	 */
	public void adAsWeapon(Weapon weapon) {}
	
	/**
	 * Remove the given weapon from the set of weapons attached to this worm.
	 * 
	 * @param	weapon
	 * 		  	The weapon to be removed.
	 * @post	This worm does not have the given weapon as on of its weapons.
	 * 		  |	! new.hasAsWeapon(weapon)
	 */
	public void removeAsWeapon(Weapon weapon) {}
	
	/**
	 * Set collecting references to weapons attached to this worm.
	 * 
	 * @invar	The set of weapons is effective.
	 * 		  |	weapons != null
	 * @invar	Each weapon in the set of weapons references a weapon that is an
	 * 			acceptable weapon for this worm.
	 * 		  |	for each weapon in Weapon:
	 * 		  |		canHaveAsWeapon(weapon)
	 */
	private final Set<Weapon> weapons = new HashSet<Weapon>();
	
	
	
	
	
	//SHOOTING AND PROJECTILES
	public void fireActiveWeapon(double propulsionYield) {}
	
	public void selectActiveWeapon(String name) {}
	
	

}
