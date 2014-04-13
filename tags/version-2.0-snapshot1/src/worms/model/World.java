package worms.model;

import java.util.HashSet;
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
	 * @post	If this world was not already terminated, the projectile to
	 * 			which this world was attached no longer has a world attached to
	 * 			it.
	 * 		  |	if (! this.isTerminated())
	 * 		  |		then (! (new.getProjectile()).hasWorld() )
	 */
	public void terminate() {}
	
	/**
	 * Check whether this world is terminated.
	 */
	public boolean isTerminated() { return isTerminated;}
	
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
	public static double getWidthUpperBound() { return 0;}
	
	
	
	
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
	public static double getheighthUpperBound() { return 0;}
	
	
	
	
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
			return (getPassableMap()[X][Y] == true);
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
	 * 
	 * @param position
	 * @param outerRadius
	 * @param innerRadius
	 * @return
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
			//System.out.println("new angle: " + angle);
			radius = innerRadius;
			while (radius <= outerRadius) {
				//System.out.println("new radius: " + radius);
				x = radius * Math.cos(angle) + x0;
				y = radius * Math.sin(angle) + y0;
				//System.out.println("x/y: " + x + "/" + y);
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
	 * Check whether the area between a inner circle with given inner radius and
	 * an outer circle with given outer radius at the given position is 
	 * passable.
	 * 	If the given inner radius is zero, the area to check is the area of a
	 * 	circle with radius equal to the given outer radius and point of center
	 * 	the given position.
	 * 
	 * @param 	position
	 * 			...
	 * @param 	radius
	 * 			...
	 * @return	...
	 * 		  |	for each position in radius of position:
	 * 		  |		if (isImpassable(position))
	 * 		  |			then result == false
	 * 		  |		else result == true
	 * 
	 */
	public boolean isPassableAreaCartesian(Position position, double outerRadius,
			double innerRadius) { 
		double x = 0;
		double y = 0;
		double x0 = position.getX();
		double y0 = position.getY();
		double dx = getPixelWidth();
		double dy = getPixelHeight();
		double xmax;
		double xmin;
		double ymax;
		double ymin;
		double A1;
		double A2;
		
		//RIGHT SHIFTING INNER CIRCLE
		xmax = x0 + innerRadius;
		x = x0;
		while (fuzzyLessThan(x, xmax)) {
			System.out.println("right shift x: " + x);
			A1 = Math.pow(outerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A1, 0)) {
				A1 = 0;
			}
			A2 = Math.pow(innerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A2, 0)) {
				A2 = 0;
			}
			
			//TOP SHIFTING
			ymax = y0 + Math.sqrt(A1);
			y = y0 + Math.sqrt(A2);
			while (fuzzyLessThan(y, ymax)) {
				System.out.println("top shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y + dy;
			}
			System.out.println("final top shift y: " + ymax);
			if (isImpassable(new Position(x, ymax))) {
				System.out.println("false (x, ymax): (" + x + ", " + ymax + ")");
				return false;
			}
			
			//BOTTOM SHIFTING
			ymin = y0 - Math.sqrt(A1);
			y = y0 - Math.sqrt(A2);
			while (fuzzyGreaterThan(y, ymin)) {
				System.out.println("bottom shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y - dy;
			}
			System.out.println("final bottom shift y: " + ymin);
			if (isImpassable(new Position(x, ymin))) {
				System.out.println("false (x, ymin): (" + x + ", " + ymin + ")");
				return false;
			}
			
			x = x + dx;
		}
		
		
		//RIGHT SHIFTING OUTER CIRCLE
		xmax = x0 + outerRadius;
		x = x0 + innerRadius;
		while (fuzzyLessThan(x, xmax)) {
			System.out.println("right shift x: " + x);
			A1 = Math.pow(outerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A1, 0)) {
				A1 = 0;
			}
			A2 = Math.pow(innerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A2, 0)) {
				A2 = 0;
			}
			
			//TOP SHIFTING
			ymax = y0 + Math.sqrt(A1);
			y = y0 + Math.sqrt(A2);
			while (fuzzyLessThan(y, ymax)) {
				System.out.println("top shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y + dy;
			}
			System.out.println("final top shift y: " + ymax);
			if (isImpassable(new Position(x, ymax))) {
				System.out.println("false (x, ymax): (" + x + ", " + ymax + ")");
				return false;
			}
			
			//BOTTOM SHIFTING
			ymin = y0 - Math.sqrt(A1);
			y = y0 - Math.sqrt(A2);
			while (fuzzyGreaterThan(y, ymin)) {
				System.out.println("bottom shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y - dy;
			}
			System.out.println("final bottom shift y: " + ymin);
			if (isImpassable(new Position(x, ymin))) {
				System.out.println("false (x, ymin): (" + x + ", " + ymin + ")");
				return false;
			}
			
			x = x + dx;
		}
		
		
		//FINAL RIGHT SHIFT
		A1 = Math.pow(outerRadius, 2) - Math.pow(xmax-x0, 2);
		if (fuzzyEquals(A1, 0)) {
			A1 = 0;
		}
		ymax = y0 + Math.sqrt(A1);
		System.out.println("final right shift xmax/ymax: " + xmax + "/" + ymax);
		if (isImpassable(new Position(xmax, ymax))) {
			System.out.println("false (xmax, ymax): (" + xmax + ", " + ymax + ")");
			return false;
		}
		
		
		//LEFT SHIFTING INNER CIRCLE
		xmin = x0 - innerRadius;
		x = x0;
		while (fuzzyGreaterThan(x, xmin)) {
			System.out.println("left shift x: " + x);
			A1 = Math.pow(outerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A1, 0)) {
				A1 = 0;
			}
			A2 = Math.pow(innerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A2, 0)) {
				A2 = 0;
			}
			
			//TOP SHIFTING
			ymax = y0 + Math.sqrt(A1);
			y = y0 + Math.sqrt(A2);
			while (fuzzyLessThan(y, ymax)) {
				System.out.println("top shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y + dy;
			}
			System.out.println("final top shift y: " + ymax);
			if (isImpassable(new Position(x, ymax))) {
				System.out.println("false (x, ymax): (" + x + ", " + ymax + ")");
				return false;
			}
			
			//BOTTOM SHIFTING
			ymin = y0 - Math.sqrt(A1);
			y = y0 - Math.sqrt(A2);
			while (fuzzyGreaterThan(y, ymin)) {
				System.out.println("bottom shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y - dy;
			}
			System.out.println("final bottom shift y: " + ymin);
			if (isImpassable(new Position(x, ymin))) {
				System.out.println("false (x, ymin): (" + x + ", " + ymin + ")");
				return false;
			}
			
			x = x - dx;
		}
		
		
		//LEFT SHIFTING OUTER CIRCLE
		xmin = x0 - outerRadius;
		x = x0 - innerRadius;
		while (fuzzyGreaterThan(x, xmin)) {
			System.out.println("left shift x: " + x);
			A1 = Math.pow(outerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A1, 0)) {
				A1 = 0;
			}
			A2 = Math.pow(innerRadius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(A2, 0)) {
				A2 = 0;
			}
			
			//TOP SHIFTING
			ymax = y0 + Math.sqrt(A1);
			y = y0 + Math.sqrt(A2);
			while (fuzzyLessThan(y, ymax)) {
				System.out.println("top shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y + dy;
			}
			System.out.println("final top shift y: " + ymax);
			if (isImpassable(new Position(x, ymax))) {
				System.out.println("false (x, ymax): (" + x + ", " + ymax + ")");
				return false;
			}
			
			//BOTTOM SHIFTING
			ymin = y0 - Math.sqrt(A1);
			y = y0 - Math.sqrt(A2);
			while (fuzzyGreaterThan(y, ymin)) {
				System.out.println("bottom shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y - dy;
			}
			System.out.println("final bottom shift y: " + ymin);
			if (isImpassable(new Position(x, ymin))) {
				System.out.println("false (x, ymin): (" + x + ", " + ymin + ")");
				return false;
			}
			
			x = x - dx;
		}
		
		
		//FINAL LEFT SHIFT
		A1 = Math.pow(outerRadius, 2) - Math.pow(xmin-x0, 2);
		if (fuzzyEquals(A1, 0)) {
			A1 = 0;
		}
		ymax = y0 + Math.sqrt(A1);
		System.out.println("final left shift xmin/ymax: " + xmin + "/" + ymax);
		if (isImpassable(new Position(xmin, ymax))) {
			System.out.println("false (xmin, ymax): (" + xmin + ", " + ymax + ")");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check whether the given position is passable for an object with the given
	 * radius.
	 * 
	 * @param 	position
	 * 			...
	 * @param 	radius
	 * 			...
	 * @return	...
	 * 		  |	for each position in radius of position:
	 * 		  |		if (isImpassable(position))
	 * 		  |			then result == false
	 * 		  |		else result == true
	 * 
	 */
	public boolean isPassableForObject(Position position, double radius) {
		return isPassableArea(position, radius, 0);
	}
	
	/**
	 * Check whether the given position is passable for an object with the given
	 * radius.
	 * 
	 * @param 	position
	 * 			...
	 * @param 	radius
	 * 			...
	 * @return	...
	 * 		  |	for each position in radius of position:
	 * 		  |		if (isImpassable(position))
	 * 		  |			then result == false
	 * 		  |		else result == true
	 * 
	 */
	public boolean isPassableForObject2(Position position, double radius) { 
		double x = 0;
		double y = 0;
		double x0 = position.getX();
		double y0 = position.getY();
		double dx = getPixelWidth();
		double dy = getPixelHeight();
		
		double xmax = x0 + radius;
		x = x0 - radius;
		while (fuzzyLessThan(x, xmax)) {
			//System.out.println("right shift x: " + x);
			double D = Math.pow(radius, 2) - Math.pow(x-x0, 2);
			if (fuzzyEquals(D, 0)) {
				D = 0;
			}
			double ymax = y0 + Math.sqrt(D);
			y = y0 - Math.sqrt(D);
			while (fuzzyLessThan(y, ymax)) {
				//System.out.println("top shift y: " + y);
				if (isImpassable(new Position(x, y))) {
					//System.out.println("false (x, y): (" + x + ", " + y + ")");
					return false;
				}
				y = y + dy;
			}
			//System.out.println("final top shift y: " + ymax);
			if (isImpassable(new Position(x, ymax))) {
				System.out.println("false (x, y): (" + x + ", " + ymax + ")");
				return false;
			}
			x = x + dx;
		}
		double D = Math.pow(radius, 2) - Math.pow(xmax-x0, 2);
		if (fuzzyEquals(D, 0)) {
			D = 0;
		}
		double ymax = y0 + Math.sqrt(D);
		//System.out.println("final right/top shift x/y: " + xmax + "/" + ymax);
		if (isImpassable(new Position(xmax, ymax))) {
			//System.out.println("false (x, y): (" + xmax + ", " + ymax + ")");
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether the given location is passable and adjacent to an
	 * impassable location for an object with the given radius.
	 * 
	 * @return	...
	 * 		  |	result ==
	 * 		  |		( isPassable(location)
	 * 		  |	   && )
	 */
	public boolean isAdjacent(Position location, double radius) { return true;}
	
	
	
	
	//ASSOCIATIONS
	/**
	 * Return the live projectile of this world. 
	 */
	@Basic
	public Projectile getProjectile() { return projectile;}
	
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
	public boolean canHaveAsProjectile(Projectile projectile) { return true;}
	
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
	public boolean hasProperProjectile() { return true;}
	
	/**
	 * Set the projectile which is active in this world to the given projectile.
	 * 
	 * @param 	projectile
	 * 			...
	 * @post	...
	 * 		  |	new.getProjectile() == projectile
	 * @throws	IllegalArgumentException
	 * 			...
	 * 		  |	! canHaveAsProjectile(projectile)
	 */
	public void setProjectile(Projectile projectile) {}
	
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
	public boolean hasAsWorm(Worm worm) { return true;}
	
	/**
	 * Check whether this world can have the given worm as one of its worms.
	 * 
	 * @param 	worm
	 * 			...
	 * @return	Returns false if the given worm is not effective.
	 * 		  |	if (worm == null)
	 * 		  |		then result == false
	 * 			Otherwise, true if and only if this world is not yet terminated,
	 * 			the given worm is not yet terminated and the given worm is not
	 * 			yet registered as a worm attached to this world.
	 * 		  |	else result ==
	 * 		  |		( (! this.isTerminated())
	 * 		  |	   && (! worm.isTerminated()) 
	 * 		  |	   && (! this.hasAsWorm(worm)) )
	 */
	public boolean canHaveAsWorm(Worm worm) { return true;}
	
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
	public boolean hasProperWorms() { return true;}
	
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
	public Set<Worm> getAllWorms() {return worms;}
	
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
	public void addAsWorm(Worm worm) {}
	
	/**
	 * Remove the givenworm from the set of worms attached to this world.
	 * 
	 * @param 	worm
	 * 			...
	 * @post	...
	 * 		  |	! new.hasAsWorm(worm)
	 * @post	...
	 * 		  |	if (this.hasAsWorm(worm))
	 * 		  |		then ((new worm).getWorld() == null)
	 */
	public void removeAsWorm(Worm worm) {}
	
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
	
	
	
	
	public void startGame() {}
	
}