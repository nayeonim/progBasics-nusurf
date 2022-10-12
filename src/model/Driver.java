package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a driver that can driver for a carpool on a trip.
 * @author stefanieim
 *
 */
public class Driver {
  private final User account;
  private Trip trip;
  private int numOfSeatsLimit;
  private int numOfBoardsLimit;
  private List<User> passengers;
  private List<User> boards; // user, areTheyBringingBoard
  
  /**
   * Constructs a driver with the given user account, and other parameters
   * @param u the user account associated
   * @param trip the trip that the driver can drive for
   * @param numOfSeatsLimit the number of total passengers they can take
   * @param numOfBoardsLimit the number of total boards they can take
   */
  public Driver(User u, Trip trip, int numOfSeatsLimit, int numOfBoardsLimit) {
    this.account = u;
    this.trip = trip;
    this.numOfSeatsLimit = numOfSeatsLimit;
    this.numOfBoardsLimit = numOfBoardsLimit;
    this.passengers = new ArrayList<User>();
    this.boards = new ArrayList<User>();
  }

  /**
   * Return the associated user account of the driver.
   * @return
   */
  public User getUserAccount() {
    return this.account;
  }
  
  /**
   * Return whether this driver has space for more riders
   * @return true if they have space
   */
  public boolean hasSpaceForRiders() {
    return this.getNumOfSeatsLeft() > 0;
  }

  /**
   * Return whether this driver has space for more boards
   * @return true if the driver has more space for more boards
   */
  public boolean hasSpaceForBoards() {
    return this.getNumOfBoardSpaceLeft() > 0;
  }

  public void setNumOfSeatsLimit(int n) {
    this.numOfSeatsLimit = n;
  }

  public void setNumOfBoardsLimit(int n) {
    this.numOfBoardsLimit = n;
  }

  public int getNumOfSeatsLimit() {
    return this.numOfSeatsLimit;
  }

  public int getNumOfBoardsLimit() {
    return this.numOfBoardsLimit;
  }

  public List<User> getPassengersList() { 
    return this.passengers;
  }

  public List<User> getBoardsList() {
    return this.boards;
  }

  public int getCurrentNumOfBoards() {
    return this.boards.size();
  }

  public void addBoard(User u) throws IllegalArgumentException {
    if (!this.hasSpaceForBoards()) {
      throw new IllegalArgumentException("No more space for boards");
    }
    this.boards.add(u);
  }
  
  public void removeBoard(User u) {
    this.boards.remove(u);
  }

  public void addPassenger(User u) throws IllegalArgumentException {
    if (!this.hasSpaceForRiders()) {
      throw new IllegalArgumentException("No more space for riders");
    }
    if (this.passengers.contains(u)) {
      throw new IllegalArgumentException("User is already on the passenger list");
    }
    this.passengers.add(u);
  }

  public void removePassenger(User u) {
    this.passengers.remove(u);
  }

  public int getNumOfPassengers() {
    return this.passengers.size();
  }

  public int getNumOfSeatsLeft() {
    return this.numOfSeatsLimit - this.getNumOfPassengers();
  }


  public int getNumOfBoardSpaceLeft() {
    return this.numOfBoardsLimit - this.getCurrentNumOfBoards();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Driver) {
      Driver that = (Driver) o;
      if ((this.account.equals(that.getUserAccount()))
          && (this.trip.equals(that.trip))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getUserAccount(), this.trip);
  }

}
