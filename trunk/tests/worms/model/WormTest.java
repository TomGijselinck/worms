package worms.model;

import static org.junit.Assert.*;

import org.junit.*;

import worms.exceptions.IllegalJumpException;
import worms.exceptions.IllegalNameException;
import worms.exceptions.IllegalRadiusException;
import worms.exceptions.IllegalStepsException;
import static worms.util.Util.*;

/**
 * A class collecting tests for the class of worms.
 * 
 * @version 1.1
 * @author Tom Gijselinck
 * 
 */

public class WormTest {

	/**
	 * Variable referencing a worm with position (3.5, 1.5), direction 0, radius
	 * 1 and name "Standard".
	 */
	private static Worm standardWorm;
	
	private static Worm moveableWorm;

	/**
	 * Variable referencing a worm with upward direction of Pi/4 radians.
	 */
	private static Worm wormUpwardDirection;

	/**
	 * Variable referencing a worm with downward direction of 7/4*Pi radians.
	 */
	private static Worm wormDownwardDirection;

	/**
	 * Variable referencing a worm with radius of 2.
	 */
	private static Worm wormRadius2;
	
	/**
	 * Variable referencing a world with width and height of 5 and passableMap
	 * as the passable map.
	 */
	private static World world;
	
	// 10x10 pixels
		//     0 1 2 3 4 5 6 7 8 9
		//    --------------------
		//  1| . . . . . . . . . .
		//  2| . . X . . . . . . .
		//  3| X X X . . . . . . .
		//  4| . X X X X X . . . .
		//  5| . . . . . . . . . .
		//  6| . . . . . . . . . .
		//  7| . . . . . . O . . .
		//  8| . . . . . . . . . .
		//  9| . . . . . . . . . .
		// 10| X X X X X X X X X X
		private boolean[][] passableMap = new boolean[][] {
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{true, 	true, 	false, 	true,	true,	true,	true,	true,	true,	true},
				{false, false, 	false, 	true,	true,	true,	true,	true,	true,	true},
				{true, false, 	false, 	false,	false,	false,	true,	true,	true,	true},
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{true, 	true, 	true, 	true,	true,	true,	true,	true,	true,	true},
				{false, false, 	false, 	false,	false,	false,	false,	false,	false,	false}
		};

	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable standardWorm references a new worm with default
	 *       variable values.
	 */
	@Before
	public void setUpMutableFixture() throws Exception {
		standardWorm = new Worm(new Position(3.5, 1.5), 0, 1, "Standard");
		wormUpwardDirection = new Worm(new Position(0, 0), Math.PI / 4, 1,
				"Upward direction");
		wormDownwardDirection = new Worm(new Position(0, 0), Math.PI * 7 / 4,
				1, "Downward direction");
		moveableWorm = new Worm(new Position(0.5, 1), 0, 0.5, "MoveWorm");
		world = new World(5, 5, passableMap);
		world.addAsWorm(standardWorm);
		world.addAsWorm(moveableWorm);
	}

	/**
	 * Set up an immutable test fixture.
	 * 
	 * @post The variable wormRadius2 references a new worm with radius 2.
	 */
	@BeforeClass
	public static void SetUpImmutableFixture() throws Exception {
		wormRadius2 = new Worm(new Position(0, 0), 0, 2, "Radius of two");
	}

	@Test
	public void extendedConstructor_LegalCase() throws Exception {
		Worm theWorm = new Worm(new Position(3, 5), 70, 11, "The worm");
		assertTrue(theWorm.getPosition().equals(new Position(3, 5)));
		assertTrue(fuzzyEquals(70, theWorm.getDirection()));
		assertTrue(fuzzyEquals(11, theWorm.getRadius()));
		assertEquals("The worm", theWorm.getName());
		assertEquals(theWorm.getActionPointsMaximum(),
				theWorm.getCurrentActionPoints());
		assertEquals(theWorm.getHitPointsMaximum(), 
				theWorm.getCurrentHitPoints());
		assertTrue(theWorm.hasAsWeapon(Weapon.RIFLE));
		assertTrue(theWorm.hasAsWeapon(Weapon.BAZOOKA));
	}

	@Test(expected = IllegalRadiusException.class)
	public void extendedConstructor_InvalidRadius() throws Exception {
		new Worm(new Position(0, 0), 0, Double.POSITIVE_INFINITY,
				"Infinite radius");
	}

	@Test(expected = IllegalNameException.class)
	public void extendedConstructor_InvalidName() throws Exception {
		new Worm(new Position(0, 0), 0, 1, "W");
	}

	@Test
	public void hasProperPosition_TrueCase() {
		assertTrue(standardWorm.hasProperPosition());
	}
	
	@Test
	public void canMove_LegaCase() {
		assertTrue(wormRadius2.canActivelyMoveSteps(1));
	}

	@Test
	public void canMove_LegalCaseUpwardOrientation() {
		assertTrue(wormUpwardDirection.canActivelyMoveSteps(1));
	}

	@Test
	public void canMove_FalseCase() {
		assertFalse(wormRadius2.canActivelyMoveSteps(100000));
	}

	@Test
	public void move_Horizontal() {
		standardWorm.move(4);
		assertTrue(fuzzyEquals(7.5, standardWorm.getPosition().getX()));
		assertTrue(fuzzyEquals(1.5, standardWorm.getPosition().getY()));
		assertEquals(4444, standardWorm.getCurrentActionPoints());
	}

	@Test
	public void move_Vertical() {
		Worm worm = new Worm(new Position(0, 0), Math.PI / 2, 1, "Vertical");
		worm.move(3);
		assertTrue(fuzzyEquals(0, worm.getPosition().getX()));
		assertTrue(fuzzyEquals(3, worm.getPosition().getY()));
	}

	@Test
	public void move_UpwardOrientation() {
		wormUpwardDirection.move(10);
		assertTrue(fuzzyEquals(10 * Math.cos(Math.PI / 4), wormUpwardDirection
				.getPosition().getX()));
		assertTrue(fuzzyEquals(10 * Math.sin(Math.PI / 4), wormUpwardDirection
				.getPosition().getY()));
	}

	@Test
	public void move_MultipleIntervals() {
		wormUpwardDirection.move(5);
		wormUpwardDirection.move(5);
		assertTrue(fuzzyEquals(10 * Math.cos(Math.PI / 4), wormUpwardDirection
				.getPosition().getX()));
		assertTrue(fuzzyEquals(10 * Math.sin(Math.PI / 4), wormUpwardDirection
				.getPosition().getY()));
	}

	@Test(expected = IllegalStepsException.class)
	public void move_IllegalCase() throws Exception {
		standardWorm.move(10000);
	}

	@Test
	public void move_PassiveSingleCase() {
		standardWorm.move(1);
		assertTrue(standardWorm.getPosition().equals(new Position(4.5, 1.5)));
		assertEquals(standardWorm.getActionPointsMaximum(),
				standardWorm.getCurrentActionPoints());
	}

	@Test
	public void jump_SingleCase() {
		Position positionBefore = new Position(wormUpwardDirection
				.getPosition().getX(), wormUpwardDirection.getPosition().getY());
		double jumpdistance = wormUpwardDirection.jumpDistance();
		wormUpwardDirection.jump();
		assertTrue(fuzzyEquals(positionBefore.getX() + jumpdistance,
				wormUpwardDirection.getPosition().getX()));
		assertTrue(fuzzyEquals(positionBefore.getY(), wormUpwardDirection
				.getPosition().getY()));
		assertEquals(0, wormUpwardDirection.getCurrentActionPoints());
	}

	@Test(expected = IllegalJumpException.class)
	public void jump_IllegalJump() throws Exception {
		Worm worm = new Worm(new Position(0, 0),
				Worm.getUpperAngleBound() / 2 + 1, 1, "Illegal Jumper");
		worm.jump();
	}

	@Test(expected = IllegalJumpException.class)
	public void jump_SecondJump() throws Exception {
		wormUpwardDirection.jump();
		wormUpwardDirection.jump();
	}

	@Test
	public void canJump_TrueCase() {
		assertTrue(standardWorm.canJump());
	}

	@Test
	public void canJump_BelowLowerAngleBound() {
		Worm worm = new Worm(new Position(0, 0), Worm.getLowerAngleBound() - 1,
				1, "Worm");
		assertFalse(worm.canJump());
	}

	@Test
	public void canJump_AboveUpperAngleBound() {
		Worm worm = new Worm(new Position(0, 0), Worm.getUpperAngleBound() + 1,
				1, "Worm");
		assertFalse(worm.canJump());
	}

	@Test
	public void canJump_ZeroActionPoints() {
		standardWorm.setCurrentActionPoints(0);
		assertFalse(standardWorm.canJump());
	}

	@Test
	public void jumpTime_SingleCase() {
		assertTrue(fuzzyEquals(
				wormUpwardDirection.jumpDistance()
						/ (wormUpwardDirection.jumpSpeed() * Math.cos(wormUpwardDirection
								.getDirection())),
				wormUpwardDirection.jumpTime()));
	}

	@Test
	public void jumpStep_LegalCase() {
		double g = Worm.getGravityOfEarth();
		Position resultPosition = wormUpwardDirection.jumpStep(1);
		Position expectedPosition = wormUpwardDirection.getPosition()
				.translate(
						wormUpwardDirection.jumpSpeed()
								* Math.cos(wormUpwardDirection.getDirection()),
						wormUpwardDirection.jumpSpeed()
								* Math.sin(wormUpwardDirection.getDirection())
								- 0.5 * g * Math.pow(1, 2));
		assertTrue(fuzzyEquals(expectedPosition.getX(), resultPosition.getX()));
		assertTrue(fuzzyEquals(expectedPosition.getY(), resultPosition.getY()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void jumpStep_IllegalTimeInterval() throws Exception {
		wormUpwardDirection.jumpStep(2);
	}

	@Test
	public void jumpSpeed_SingleCase() {
		double g = Worm.getGravityOfEarth();
		double F = 5 * wormUpwardDirection.getCurrentActionPoints()
				+ wormUpwardDirection.getMass() * g;
		assertTrue(fuzzyEquals(F / wormUpwardDirection.getMass() * 0.5,
				wormUpwardDirection.jumpSpeed()));
	}

	@Test
	public void jumpDistance_SingleCase() {
		double g = Worm.getGravityOfEarth();
		assertTrue(fuzzyEquals(Math.pow(wormUpwardDirection.jumpSpeed(), 2)
				* Math.sin(2 * wormUpwardDirection.getDirection()) / g,
				wormUpwardDirection.jumpDistance()));
	}

	@Test
	public void canTurn_TrueCase() {
		standardWorm.canTurn(standardWorm.getAngleRange() / 2);
	}

	@Test
	public void canTurn_FalseCaseActionPoints() {
		standardWorm.setCurrentActionPoints(0);
		standardWorm.canTurn(standardWorm.getAngleRange() / 2);
	}

	@Test
	public void canTurn_FalseCaseDirection() {
		standardWorm.canTurn(2 * standardWorm.getAngleRange());
	}

	@Test
	public void turn_InAngleRange() {
		int initialAP = standardWorm.getCurrentActionPoints();
		standardWorm.turn(standardWorm.getAngleRange() / 2);
		assertTrue(fuzzyEquals(standardWorm.getAngleRange() / 2,
				standardWorm.getDirection()));
		assertTrue(fuzzyEquals((initialAP - 30),
				standardWorm.getCurrentActionPoints()));

	}

	@Test
	public void turn_OutsideAngleRange() {
		int initialAP = standardWorm.getCurrentActionPoints();
		standardWorm.turn(standardWorm.getAngleRange() * 3 / 4);
		standardWorm.turn(standardWorm.getAngleRange() * 1 / 2);
		assertTrue(fuzzyEquals(standardWorm.getAngleRange() * 1 / 4,
				standardWorm.getDirection()));
		assertTrue(fuzzyEquals((initialAP - 75),
				standardWorm.getCurrentActionPoints()));
	}

	@Test
	public void move_AfterTurningUpward() {
		wormDownwardDirection.turn(Math.PI / 2);
		wormDownwardDirection.move(10);
		assertTrue(fuzzyEquals(Math.PI / 4,
				wormDownwardDirection.getDirection()));
		assertTrue(fuzzyEquals(10 * Math.cos(Math.PI / 4),
				wormDownwardDirection.getPosition().getX()));
		assertTrue(fuzzyEquals(10 * Math.sin(Math.PI / 4),
				wormDownwardDirection.getPosition().getY()));
	}

	@Test
	public void jump_AfterTurningUpward() {
		Position positionBefore = new Position(wormDownwardDirection
				.getPosition().getX(), wormDownwardDirection.getPosition()
				.getY());
		wormDownwardDirection.turn(Math.PI / 2);
		assertTrue(fuzzyEquals(Math.PI / 4,
				wormDownwardDirection.getDirection()));
		double jumpdistance = wormDownwardDirection.jumpDistance();
		wormDownwardDirection.jump();
		assertTrue(fuzzyEquals(positionBefore.getX() + jumpdistance,
				wormDownwardDirection.getPosition().getX()));
		assertTrue(fuzzyEquals(positionBefore.getY(), wormDownwardDirection
				.getPosition().getY()));
		assertEquals(0, wormDownwardDirection.getCurrentActionPoints());
	}

	@Test
	public void canHaveAsRadius_LegalCase() {
		assertTrue(standardWorm.canHaveAsRadius(standardWorm
				.getLowerRadiusBound() + 1));
	}

	@Test
	public void canHaveAsRadius_NaN() {
		assertFalse(standardWorm.canHaveAsRadius(Double.NaN));
	}

	@Test
	public void canHaveAsRadius_UnderLowerRadiusBound() {
		assertFalse(standardWorm.canHaveAsRadius(standardWorm
				.getLowerRadiusBound() - 1));
	}

	@Test
	public void canHaveAsRadius_PostiveInfinity() {
		assertFalse(standardWorm.canHaveAsRadius(Double.POSITIVE_INFINITY));
	}

	@Test
	public void setRadius_LegalCase() {
		standardWorm.setRadius(standardWorm.getLowerRadiusBound() + 1);
		assertTrue(fuzzyEquals(standardWorm.getLowerRadiusBound() + 1,
				standardWorm.getRadius()));
	}

	@Test(expected = IllegalRadiusException.class)
	public void setRadius_InvalidRadius() throws Exception {
		standardWorm.setRadius(Double.POSITIVE_INFINITY);
	}

	@Test
	public void setCurrentActionPoints_LegalCase() {
		standardWorm.setCurrentActionPoints(standardWorm
				.getActionPointsMaximum() - 1);
		assertEquals(standardWorm.getActionPointsMaximum() - 1,
				standardWorm.getCurrentActionPoints());
	}

	@Test
	public void setCurrentActionPoints_NegativeAmount() {
		standardWorm.setCurrentActionPoints(-10);
		assertEquals(0, standardWorm.getCurrentActionPoints());
	}

	@Test
	public void setCurrentActionPoints_AboveMaximum() {
		standardWorm.setCurrentActionPoints(standardWorm
				.getActionPointsMaximum() + 1);
		assertEquals(standardWorm.getActionPointsMaximum(),
				standardWorm.getCurrentActionPoints());
	}

	@Test
	public void setName_LegalCase() {
		standardWorm.setName("New name");
		assertEquals("New name", standardWorm.getName());
	}

	@Test(expected = IllegalNameException.class)
	public void setName_InvalidName() throws Exception {
		standardWorm.setName("Inval1d nam$");
	}

	@Test
	public void isValidName_TrueCase() {
		assertTrue(Worm.isValidName("James o'Hara 007"));
	}
	
	@Test
	public void isValidName_SingleCharacterName() {
		assertFalse(Worm.isValidName("M"));
	}

	@Test
	public void isValidName_LowerCaseCharacterName() {
		assertFalse(Worm.isValidName("lower case"));
	}

	@Test
	public void isValidName_IllegalCharacterName() {
		assertFalse(Worm.isValidName("Illegal$ character"));
	}
	
	@Test
	public void canHaveAsWorld_NonEffectiveWorld() {
		assertTrue(standardWorm.canHaveAsWorld(null));
	}
	
	@Test
	public void canHaveAsWorld_EffectiveWorld() {
		assertTrue(standardWorm.canHaveAsWorld(world));
	}
	
	@Test
	public void hasProperWorld_SingleCase() {
		assertTrue(standardWorm.hasProperWorld());
	}
	
	@Test
	public void hasAsWeapon_SingleCase() {
		assertTrue(standardWorm.hasAsWeapon(Weapon.RIFLE));
	}
	
	@Test
	public void canHaveAsWeapon_TrueCase() {
		standardWorm.removeAsWeapon(Weapon.RIFLE);
		assertTrue(standardWorm.canHaveAsWeapon(Weapon.RIFLE));
	}
	
	@Test
	public void canHaveAsWeapon_NonEffectiveWeapon() {
		assertFalse(standardWorm.canHaveAsWeapon(null));
	}
	
	@Test
	public void canHaveAsWeapon_TerminatedWorm() {
		standardWorm.terminate();
		assertFalse(standardWorm.canHaveAsWeapon(Weapon.RIFLE));
	}
	
	@Test
	public void canHaveAsWeapon_HasWeaponAlready() {
		assertFalse(standardWorm.canHaveAsWeapon(Weapon.RIFLE));
	}
	
	@Test
	public void move_SingleCase() {
		moveableWorm.setWorld(world);
		moveableWorm.setPosition(new Position(2.25, 4));
		System.out.println(moveableWorm.getPosition().toString());
		System.out.println(moveableWorm.getCurrentHitPoints());
		moveableWorm.move(5);
		System.out.println(moveableWorm.getPosition().toString());
		System.out.println(moveableWorm.getCurrentHitPoints());
	}
	
	@Test
	public void fall_SingleCase() {
		standardWorm.setWorld(world);
		standardWorm.setPosition(new Position(3.5, 2));
		System.out.println(standardWorm.getPosition().toString());
		standardWorm.fall();
		System.out.println(standardWorm.getPosition().toString());
	}

}
