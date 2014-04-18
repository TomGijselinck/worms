package worms.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.lang.Math;

import static worms.util.Util.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * ...
 * 	 
 * @invar	Each world has a proper projectile.
 * 		  |	hasProperProjectile()
 * @invar	The worms attached to each world must be proper worms for that 
 * 			world.
 * 		  |	hasProperWorms()
 * 
 * @author 	Tom Gijselinck
 * @version	1.0
 *
 */
public class World {
	
	//CONSTRUCTORS
	
	/**
	 * ...
	 * 
	 * @param 	width
	 * 			...
	 * @param 	height
	 * 			...
	 * @param 	passableMap
	 * 			...
	 * @post	...
	 * 		  |	new.getWidth() == width
	 * @post	...
	 * 		  |	new.getHeight() == height
	 * @post	...
	 * 		  |	new.getPassableMap() == passableMap
	 * @throws	IllegalArgumentException
	 * 		  	...
	 * 		  |	! isValidWidth(width)
	 * @throws	IllegalArgumentException
	 * 		  	...
	 * 		  |	! isValidHeight(height)
	 */
	public World(double width, double height, boolean[][] passableMap) {
		this.width = width;
		this.height = height;
		this.passableMap = passableMap;
		this.isTerminated = false;
	}
	
	
	
	
	//DESTRUCTOR
	/**
	 * Terminate this world.
	 * 
	 * @post	...
	 * 		  |	new.isTerminated()
	 * @post	This world no longer references a projectile as the projectile
	 * 		  	to which it is attached.
	 * 		  |	new.getProjectile() == null
	 * @post	If this world was not already terminated and if this world had
	 * 			an effective projectile, the projectile to which this world was
	 * 			attached no longer has a world attached to it.
	 * 		  |	if ((! this.isTerminated()) && (getProjectile != null))
	 * 		  |		then (! ((new) this.getProjectile()).hasWorld() )
	 * @post	No worms are any longer attached to this world.
	 * 		  |	new.getNbWorms() == 0
	 * @post	...
	 * 		  |	if (this.hasAsWorm(worm))
	 * 		  |		then ((new worm).getWorld() == null)
	 * @effect	Each worm attached to this world is removed from this world.
	 * 		  |	for each worm in getAllWorms():
	 * 		  |		this.removeAsWorm(worm)
	 */
	public void terminate() {
		if ((! this.isTerminated()) && (getProjectile() != null)) {
			getProjectile().setWorld(null);
		}
		setProjectile(null);
		Iterator<Worm> iterator = worms.iterator();
		while (iterator.hasNext()) {
			Worm worm = iterator.next();
			if (hasAsWorm(worm)) {
				worm.setWorld(null);
			}
			iterator.remove();
		}
		isTerminated = true;
	}
	
	/**
	 * Check whether this world is terminated.
	 */
	public boolean isTerminated() {
		return isTerminated;
	}
	
	/**
	 * Variable registering whether this world is terminated.
	 */
	private boolean isTerminated = false;
	
	
	
	
	/**
	 * Return the width of this world.
	 */
	@Basic
	public double getWidth() { 
		return width;
	}
	
	/**
	 * Checks whether the given width is a valid width for any world.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		width <= World.getWidthUpperBound()
	 * 		  |		&& width > 0
	 */
	public boolean isValidWidth() { return true;}
	
	/**
	 * 
	 */
	private double width;
	
	
	
	
	/**
	 * Return the width upper bound for any world.
	 */
	@Basic
	public static double getWidthUpperBound() { 
		return widthUpperBound;
	}
	
	/**
	 * Variable registering the width upper bound of any world.
	 */
	private static double widthUpperBound = Double.MAX_VALUE;
	
	
	
	
	/**
	 * Return the height of this world.
	 */
	@Basic
	public double getHeight() { 
		return height;
	}
	
	/**
	 * Checks whether the given height is a valid height for any world.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		height <= World.getHeightUpperBound()
	 * 		  |		&& height > 0
	 */
	public boolean isValidHeight() { return true;}
	
	/**
	 * 
	 */
	private double height;
	
	
	
	
	/**
	 * Return the height upper bound for any world.
	 */
	@Basic
	public static double getheighthUpperBound() { 
		return heightUpperBound;
	}
	
	/**
	 * Variable registering the height upper bound of any world.
	 */
	private static double heightUpperBound = Double.MAX_VALUE;
	
	
	
	
	/**
	 * Return the passable map of this world.
	 * 	A passable map consists of rows and columns of pixels presented as a
	 * 	matrix.
	 */
	public boolean[][] getPassableMap() { 
		return passableMap;
	}
	
	/**
	 * Return the number of horizontal pixels of the passable map of this world.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		getPassableMap().length
	 */
	public int getNoHorizontalPixels() { 
		return getPassableMap().length;
	}
	
	/**
	 * Return the number of vertical pixels of the passable map of this world.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		getPassableMap()[0].length
	 */
	public int getNoVerticalPixels() { 
		return getPassableMap()[0].length;
	}

	/**
	 * Variable referencing the passable map of this world.
	 */
	private boolean[][] passableMap;
	
	
	
	
	/**
	 * Return the horizontal scale of this world in map pixels per worm-meter.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		getNoHorizontalPixels() / getWidth()
	 */
	public double getHorizontalScale() { 
		return getNoHorizontalPixels() / getWidth();
	}
	
	/**
	 * Return the vertical scale of this world in map pixels per worm-meter.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		getNoVerticalPixels() / getHeight()
	 */
	public double getVerticalScale() { 
		return getNoVerticalPixels() / getHeight();
	}
	
	/**
	 * Return the height of each pixel of the passable map of this world in 
	 * metre.
	 * 
	 * @return	...
	 * 		  |	getWidth() / getNoHorizontalPixels()
	 */
	public double getPixelWidth() { 
		return getWidth() / getNoHorizontalPixels();
	}
	
	/**
	 * Return the height of each pixel of the passable map of this world in 
	 * metre.
	 * 
	 * @return	...
	 * 		  |	getHeight() / getNoVerticalPixels()
	 */
	public double getPixelHeight(){ 
		return getHeight() / getNoVerticalPixels();
	}
	
	
	
	
	/**
	 * Return the row of pixels in the passable map of this world where the 
	 * given position is located.
	 * 	The row is selected by the Y coordinate of the given position.
	 * 
	 * @return	...
	 * 		  |	getNoVerticalPixels() - (int) position.getY() * getVerticalScale()
	 */
	public int getPixelRow(Position position) { 
		return (getNoVerticalPixels() - (int) (position.getY() * getVerticalScale()) );
	}
	
	/**
	 * Return the column of pixels in the passable map of this world where the 
	 * given position is located.
	 * 	The column is selected by the X coordinate of the given position.
	 * 
	 * @return	...
	 * 		  |	(int) position.getX() * getHorizontalScale() + 1
	 */
	public int getPixelColumn(Position position) { 
		return (int) (position.getX() * getHorizontalScale() + 1);
	}
	
	
	
	
	//PASSABILITY (total)
	/**
	 * Check whether the given position is passable.
	 * 
	 * @return	...
	 * 		  |	let
	 * 		  |		passableMap = getPassableMap()
	 * 		  |		&& x = getPixelRow(position) - 1
	 * 		  |		&& y = getPixelColumn(position) - 1
	 * 		  |	in
	 * 		  |		passableMap[x][y] == true
	 */
	public boolean isPassable(Position position) {
		double x = position.getX();
		double y = position.getY();
		double rx = x%getPixelWidth();
		double ry = y%getPixelHeight();
		if (fuzzyEquals(rx, 0) && fuzzyEquals(ry, 0)) {
			//check boven, onder, links en rechts
			double dx = getPixelWidth()/2;
			double dy = getPixelHeight()/2;
			Position left = new Position(x-dx, y);
			Position right = new Position(x+dx, y);
			Position top = new Position(x, y+dy);
			Position bottom = new Position(x, y-dy);
			if (isImpassable(left)) {
				return false;
			} else if (isImpassable(right)) {
				return false;
			} else if (isImpassable(top)) {
				return false;
			} else if (isImpassable(bottom)) {
				return false;
			} else {
				return true;
			}
		} else if (fuzzyEquals(rx, 0) && (! fuzzyEquals(ry, 0))) {
			//check links en rechts
			double dx = getPixelWidth()/2;
			Position left = new Position(x-dx, y);
			Position right = new Position(x+dx, y);
			if (isImpassable(left)) {
				return false;
			} else if (isImpassable(right)) {
				return false;
			} else {
				return true;
			}
		} else if (fuzzyEquals(ry, 0) && (!fuzzyEquals(rx, 0))) {
			//check boven en onder
			double dy = getPixelHeight()/2;
			Position top = new Position(x, y+dy);
			Position bottom = new Position(x, y-dy);
			if (isImpassable(top)) {
				return false;
			} else if (isImpassable(bottom)) {
				return false;
			} else {
				return true;
			}
		} else {
			//normaal
			int X = getPixelRow(position) - 1;
			int Y = getPixelColumn(position) - 1;
			if (! isInsideWorldBorders(position)) {
				// outside world borders
				return true;
			} else {
				return (getPassableMap()[X][Y] == true);
			}
		}
		
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public boolean isInsideWorldBorders(Position position) {
		int X = getPixelRow(position) - 1;
		int Y = getPixelColumn(position) - 1;
		if ((X < 0) || (X >= getNoHorizontalPixels())
				|| (Y < 0) || (Y >= getNoVerticalPixels())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 *
	 * @return
	 */
	//TODO afwerken
	public boolean objectIsInsideWorldBorders(Position position, double radius) {
		Position top = position.translate(0, radius);
		Position bottom = position.translate(0, -radius);
		Position left = position.translate(-radius, 0);
		Position right = position.translate(radius, 0);
		
		if (isInsideWorldBorders(top) && isInsideWorldBorders(bottom)
				&& isInsideWorldBorders(left) && isInsideWorldBorders(right)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check whether the given position is impassable.
	 * 
	 * @param 	location
	 * 			...
	 * @effect	True if the given position is not passable.
	 * 		  |	result == (! isPassable(position))
	 */
	public boolean isImpassable(Position position) { 
		return (! isPassable(position));
	}
	
	/**
	 * ...
	 * 
	 * @param 	position
	 * 			...
	 * @param 	outerRadius
	 * 			...
	 * @param 	innerRadius
	 * 			...
	 * @return	...
	 * 		  | if (for each radius in innerRadius..outerRadius:
	 * 		  |		  for each angle in 0..2*Pi:
	 * 		  |			(getPassableMap()[radius*cos(angle)][radius*sin(angle)]
	 * 		  |				== true) )
	 * 		  |	then result == true
	 */
	public boolean isPassableArea(Position position, double outerRadius,
			double innerRadius) {
		double dr;
		double dtheta;
		double angle = 0;
		double radius;
		double x;
		double y;
		double x0 = position.getX();
		double y0 = position.getY();
		
		if (getHorizontalScale() > getVerticalScale()) {
			dr = 1/getHorizontalScale();
		} else {
			dr = 1/getVerticalScale();
		}
		dtheta = dr/outerRadius;
		
		while (angle < 2*Math.PI) {
			radius = innerRadius;
			while (radius <= outerRadius) {
				x = radius * Math.cos(angle) + x0;
				y = radius * Math.sin(angle) + y0;
				if (isImpassable(new Position(x, y))) {
					return false;
				}
				radius = radius + dr;
			}
			x = outerRadius * Math.cos(angle) + x0;
			y = outerRadius * Math.sin(angle) + y0;
			if (isImpassable(new Position(x, y))) {
				return false;
			}
			angle = angle + dtheta;
			
		}
		
		return true;
	}
	
	/**
	 * ...
	 * 
	 * @param 	position
	 * 			...
	 * @param 	outerRadius
	 * 			...
	 * @param 	innerRadius
	 * 			...
	 * @effect	...
	 * 		  |	result == (! isPassableArea(position, outerRadius, innerRadius))
	 */
	public boolean isImpassableArea(Position position, double outerRadius,
			double innerRadius) {
		return (! isPassableArea(position, outerRadius, innerRadius));
	}
	
	/**
	 * Check whether the given position is passable for an object with the given
	 * radius.
	 * 
	 * @param 	position
	 * 			...
	 * @param 	radius
	 * 			...
	 * @effect	...
	 * 		  |	result == isPassableArea(position, radius, 0)
	 * 
	 */
	public boolean isPassableForObject(Position position, double radius) {
		return isPassableArea(position, radius, 0);
	}
	
	/**
	 * ...
	 * 
	 * @param 	position
	 * 			...
	 * @param 	radius
	 * 			...
	 * @return	...
	 * 		  |	result == (! isPassableArea(position, radius, 0))
	 */
	public boolean isImpassableForObject(Position position, double radius) {
		return (! isPassableForObject(position, radius));
	}
	
	/**
	 * Checks whether the given position is passable and adjacent to an
	 * impassable position for an object with the given radius.
	 * 
	 * @param	position
	 * 			...
	 * @param	radius
	 * 			... 
	 * @effect	...
	 * 		  |	result ==
	 * 		  |		(isPassableForObject(position, radius)
	 * 		  |	   && isImpassableArea(position, outerRadius, radius) )
	 */
	public boolean isAdjacent(Position position, double radius) {
		double outerRadius = 1.1 * radius;
		return (isPassableForObject(position, radius)
				&& isImpassableArea(position, outerRadius, radius));
	}
	
	
	
	
	//ASSOCIATIONS (defensive)
	/**
	 * Return the live projectile of this world. 
	 */
	@Basic
	public Projectile getProjectile() { 
		return projectile;
	}
	
	/**
	 * Checks whether this world can have the given projectile as its 
	 * projectile.
	 * 
	 * @param	projectile
	 * 			...
	 * @return	...
	 * 		  |	if (isTerminated())
	 * 		  |		then result == (projectile == null)
	 * 		  |	else result ==
	 * 		  |		( ( (projectile != null)
	 * 		  |	     && (! projectile.isTerminated()) )
	 * 		  |    || (projectile == null) )
	 */
	public boolean canHaveAsProjectile(Projectile projectile) {
		if (isTerminated()) {
			return (projectile == null);
		} else {
			return ( (projectile == null)
				  || ( (projectile != null)
					&& (! projectile.isTerminated())) );
		}
	}
	
	/**
	 * Check whether this world has a proper projectile attached to it.
	 * 	Check for consistency of mutual reference implied by bidirectional
	 * 	association.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		( canHaveAsProjectile(getProjectile())
	 * 		  |	   && ( (getProjectile() == null)
	 * 		  |		 ||	(getProjectile().getWorld() == this) ) )
	 */
	public boolean hasProperProjectile() {
		return ( canHaveAsProjectile(getProjectile())
			  &&  ( (getProjectile() == null)
				 ||	(getProjectile().getWorld() == this)) ); 
	}
	
	/**
	 * Set the projectile which is active in this world to the given projectile.
	 * 
	 * @param 	projectile
	 * 			...
	 * @post	...
	 * 		  |	new.getProjectile() == projectile
	 * @post	...
	 * 		  |	if (projectile != null)
	 * 		  |		then new.getProjectile().getWorld() == this
	 * @throws	IllegalArgumentException
	 * 			...
	 * 		  |	! canHaveAsProjectile(projectile)
	 */
	public void setProjectile(Projectile projectile) 
			throws IllegalArgumentException {
		if (! canHaveAsProjectile(projectile)) {
			throw new IllegalArgumentException();
		}
		this.projectile = projectile;
		if (projectile != null) {
			getProjectile().setWorld(this);
		}
	}
	
	/**
	 * Checks whether this world has a live effective projectile attached to it.
	 * 
	 * @return	...
	 * 		  |	result == (getProjectile() != null)
	 */
	public boolean hasProjectile() {
		return (getProjectile() != null);
	}
	
	/**
	 * Variable referencing the live projectile in this world. 
	 */
	private Projectile projectile;
	
	/**
	 * Check whether this world has the given worm as one of the worms attached
	 * to it.
	 * 
	 * @param 	worm
	 * 			...
	 */
	@Basic
	public boolean hasAsWorm(Worm worm) {
		return worms.contains(worm);
	}
	
	/**
	 * Check whether this world can have the given worm as one of its worms.
	 * 
	 * @param 	worm
	 * 			...
	 * @return	Returns false if the given worm is not effective.
	 * 		  |	if (worm == null)
	 * 		  |		then result == false
	 * 			Otherwise, true if and only if this world is not yet terminated,
	 * 			the given worm is not yet terminated and the position of the
	 * 			given worm is a valid position for that worm in this world.
	 * 		  |	else result ==
	 * 		  |		( (! this.isTerminated())
	 * 		  |	   && (! worm.isTerminated())
	 * 		  |	   && this.isAdjacent(worm.getPosition(), worm.getRadius()) )
	 */
	public boolean canHaveAsWorm(Worm worm) {
		if (worm == null) return false;
		else return ( (! this.isTerminated())
				&& (! worm.isTerminated())
				&& this.isAdjacent(worm.getPosition(), worm.getRadius()) );
	}
	
	/**
	 * Check whether this world has proper worms attached to it.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		for each worm in Worm:
	 * 		  |			( if (this.hasAsWorm(worm))
	 * 		  |				then canHaveAsWorm(worm)
	 * 		  |				  && (worm.getWorld() == this) )
	 */
	public boolean hasProperWorms() {
		Iterator<Worm> iterator = worms.iterator();
		while (iterator.hasNext()) {
			Worm worm = iterator.next();
			if (hasAsWorm(worm)) {
				if ( (! canHaveAsWorm(worm))
				  || (worm.getWorld() != this)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return a set collecting all worms associated with this world.
	 * 
	 * @return	...
	 * 		  |	result != null
	 * @return	...
	 * 		  |	for each worm in Worm:
	 * 		  |		result.contains(worm) ==
	 * 		  |		this.hasAsWorm(worm)
	 */
	public Set<Worm> getAllWorms() {
		return worms;
	}
	
	/**
	 * Add the given worm to the set of worms attached to this world.
	 * 
	 * @param 	worm
	 * 			...
	 * @post	...
	 * 		  |	new.hasAsWorm(worm)
	 * @post	...
	 * 		  |	(new worm).getWorld() == this
	 * @throws	IllegalArgumentException
	 * 			...
	 * 		  |	! canHaveAsWorm(worm)
	 * @throws	IllegalArgumentException
	 * 			The given worm is already attached to some world.
	 * 		  |	 ( (worm != null)
	 * 		  | && (worm.getWorld() != null) )
	 */
	public void addAsWorm(Worm worm) {
		if (! canHaveAsWorm(worm)) throw new IllegalArgumentException();
		if ((worm != null) && (worm.getWorld() != null))
			throw new IllegalArgumentException();
		worm.setWorld(this);
		worms.add(worm);
	}
	
	/**
	 * Remove the given worm from the set of worms attached to this world.
	 * 
	 * @param 	worm
	 * 			...
	 * @post	...
	 * 		  |	! new.hasAsWorm(worm)
	 * @post	...
	 * 		  |	if (this.hasAsWorm(worm))
	 * 		  |		then ((new worm).getWorld() == null)
	 * @throws	IllegalArgumentException
	 * 			The given worm is not effective.
	 * 		  |	worm == null
	 */
	public void removeAsWorm(Worm worm) {
		if (worm == null) throw new IllegalArgumentException();
		if (hasAsWorm(worm)) {
			worm.setWorld(null);
		}
		worms.remove(worm);
	}
	
	/**
	 * Set collecting references to worms attached to this world.
	 * 
	 * @invar	...
	 * 		  |	worms != null
	 * @invar	...
	 * 		  |	for each worm in Worm:
	 * 		  |		canHaveAsWorm(worm)
	 * @invar	...
	 * 		  |	for each worm in Worm:
	 * 		  |		(worm.getWorld() == this)
	 */
	private final Set<Worm> worms = new HashSet<Worm>();
	
	/**
	 * Return the projectile and all the worms attached to this world
	 * 
	 * @return	The resulting collection contains the projectile attached to 
	 * 			this world if any, and all worms attached to this world.
	 * 		  | (if (hasProjectile()) 
	 * 		  |		then result.contains(getProjectile()) )
	 * 		  | && ( for each worm in worms:
	 * 		  |		 result.contains(worm) )
	 */
	public Set<Object> getAllWorldObjects() {
		final Set<Object> allObjects = new HashSet<Object>();
		allObjects.addAll(worms);
		if (hasProjectile()) allObjects.add(projectile);
		return allObjects;
	}
	
	
	
	
	//GAME
	public void startGame() {}
	
	public Worm getWinningWorm() { return null;}
	
}
