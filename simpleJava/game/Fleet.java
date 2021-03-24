/**
 * 
 */
package golledge.empire.game;

/**
 * @author Chris Golledge
 *
 */
public class Fleet {
	private int ships;
	private Player owner;
	private World destination;

	public Fleet(int ships, Player owner, World destination) {
		this.ships = ships;
		this.owner = owner;
		this.destination = destination;
	}

	public int decrement() {
		return --ships;
	}

	public World getDestination() {
		return destination;
	}

	public Player getOwner() {
		return owner;
	}

	public int getShips() {
		return ships;
	}

	public String toString() {
		return owner.toString() + " " + destination.getName() + " " + String.valueOf(ships);
	}
}
