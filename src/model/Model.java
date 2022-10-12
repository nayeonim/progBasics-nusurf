package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Processing;
import main.VisualState;

/**
 * Represents the model for the programmed flow of the NUsurf website
 * @author stefanieim
 */
public class Model {
  private VisualState state;
  private User loggedInUser;
  private Map<Integer, Trip> trips; //id, trip
  
  /**
   * Constructs a new model for the trip registration flow for the NUsurf website.
   * Sets the state for the initial visual state (pre-registration),
   * the logged-in user as the constant defined in the Processing class,
   * and an empty list of trips that exist in the database
   */
  public Model() {
    this.state = VisualState.PRE_REGISTRATION;
    this.loggedInUser = Processing.USER;
    this.trips = new HashMap<>();
  }

  /**
   * Returns the current visual state of this model, to be displayed by the Processing class.
   * @return the current visual state
   */
  public VisualState getVisualState() {
    return this.state;
  }
  
  /**
   * Sets the current visual state of this model to the given VisualState enum type.
   * @param state the visual state to be drawn for this model, by the Processing class
   */
  public void setVisualState(VisualState state) {
    this.state = state;
  }
  
  /**
   * Returns the logged-in user for this flow session
   * @return the logged-in user
   */
  public User getLoggedInUser() {
    return this.loggedInUser;
  }
  
  /**
   * Adds a trip to the database of this model.
   * @param t the trip to add to this model
   */
  public void addTrip(Trip t) {
    if (this.trips.containsValue(t)) {
      throw new IllegalArgumentException("Trip already exists");
    }
    this.trips.put(t.getId(), t);
  }
  
  /**
   * Returns the trip with the given id.
   * @param id the unique id of the trip to return
   * @return the trip with the given unique id
   */
  public Trip getTrip(int id) {
    return this.trips.get(id);
  }
  
}
