package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A user account on the NUsurf website.
 * Each user has a unique id and email.
 * @author stefanieim
 *
 */
public class User {
  private int id;
  private String nameFirst;
  private String nameLast;
  private List<Integer> phoneNum;
  private String email;
  private String addressSt, addressZipcode;
  private Map<Trip, Form> formCreatedTrips;

  /**
   * Constructs a new user account with the given parameters.
   * @param id the unique user id
   * @param nameFirst first name
   * @param nameLast last name
   * @param phoneNum phone number as a list of integers
   * @param email the unique email address
   */
  public User(int id, String nameFirst, String nameLast, List<Integer> phoneNum, String email) {
    this.id = id;
    this.nameFirst = nameFirst;
    this.nameLast = nameLast;
    this.phoneNum = phoneNum;
    this.email = email;
    this.addressSt = "";
    this.addressZipcode = "";
    this.formCreatedTrips = new HashMap<>();
  }

  /**
   * Sets the name of the user as the given first and last names
   * @param newFirst
   * @param newLast
   */
  public void setName(String newFirst, String newLast) {
    this.nameFirst = newFirst;
    this.nameLast = newLast;
  }

  /**
   * Returns the name of the user as an array containing 
   * the first name as item 0, and last name as item 1.
   * @return
   */
  public String[] getName() {
    return new String[]{this.nameFirst, this.nameLast};
  }

  /**
   * Sets the email of this user account with the given String.
   * @param newEmail the new email address to set the account to.
   */
  public void setEmail(String newEmail) {
    this.email = newEmail;
  }

  /**
   * Returns the email of this user account as a String
   * @return the user's email address
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets this user's phone number as the given number
   * @param newNum the new list of integers representing the user's phone number
   */
  public void setPhoneNum(List<Integer> newNum) {
    this.phoneNum = newNum;
  }

  /**
   * Returns this user's phone number as a list of integers
   * @return
   */
  public List<Integer> getPhoneNum() {
    return this.phoneNum;
  }

  /**
   * Sets this user's street address with the given string
   * @param street the new street address for this user account
   */
  public void setAddressSt(String street) {
    this.addressSt = street;
  }

  /**
   * Returns this user's street address
   * @return the user's stress address as a String
   */
  public String getAddressSt() {
    return this.addressSt;
  }

  /**
   * Sets this user's zipcode with the given string
   * @param zipcode the new zipcode for the user as a String
   */
  public void setAddressZipcode(String zipcode) {
    this.addressZipcode = zipcode;
  }

  /**
   * Return this user's zipcode as a String
   * @return this user's zipcode
   */
  public String getAddressZipcode() {
    return this.addressZipcode;
  }
  
  /**
   * Returns the unique id of this user account.
   * @return
   */
  public int getId() {
    return this.id;
  }
  
  /**
   * Returns this a map of all trips and corresponding forms, 
   * for all the trips that the user has created a form for.
   * @return
   */
  public Map<Trip, Form> getAllFormCreatedTrips() {
    return this.formCreatedTrips;
  }
  
  /**
   * Returns a list of trips for which the user has created a form.
   * @return
   */
  public List<Trip> getFormCreatedTripsList() {
    Set<Trip> setTrip = this.formCreatedTrips.keySet();
    List<Trip> listTrip = new ArrayList<>(setTrip);
    return listTrip;
  }
  
  /**
   * Adds a new form attached to a specific trip, to this user's map of all form-created trips.
   * (called when the user clicks the REGISTER button and starts a form)
   * @param trip
   */
  public void newFormCreatedTrip(Trip trip) {
    this.formCreatedTrips.put(trip, new Form(this));
  }

  /**
   * Removes the given trip from this user's map of form-created trips.
   * Clears the trip and the form attached to it.
   * @param trip
   */
  public void removeFormCreatedTrips(Trip trip) {
    this.formCreatedTrips.remove(trip);
  }


  /**
   * Submits the form for the given trip.
   * @param t the trip to submit the form for
   */
  public void submitForm(Trip t) {
    Form form = this.formCreatedTrips.get(t);

    //clear all previous options
    t.removeMemberFromEverything(this);
    
    //add member to all trip-goers list
    t.addMember(this);
    
    //check form is filled properly
    form.checkIfValidSubmitState();

    //Transportation arrangements
    if (form.getTransportOption() == 2) { //i need a ride
      //request carpool
      t.requestCarpool(this, form.getBringingOwnBoard());
    }
    
    //Rental arrangements
    if (form.getRentalNeeded() == 0) {//yes
      t.addRentalMember(this, form.getRentalItems());
    }
    form.setSubmitted(true);
    form.consolePrintForm();
  }
  

  @Override
  public boolean equals(Object o) {
    if (o instanceof User) {
      User that = (User) o;
      if ((this.id == that.getId())
          && (this.getEmail() == that.getEmail())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.email);
  }

  

}
