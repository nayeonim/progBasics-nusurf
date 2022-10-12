package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.sql.Time;

/**
 * This class represents a trip on the NUsurf website
 * @author stefanieim
 *
 */
public class Trip {
  private int id;
  private String tripName;
  private int dateMM, dateDD, dateYYYY;
  private int startTime, endTime; 
  private String location;
  private String locationMapUrl;
  private DayPlan dayPlan;
  private RentalShop rentalShop;
  private String headerImageUrl;
  //member stuff
  private List<User> allMembers;
  private Map<User, Integer> unassignedCarpoolRiders; //user, bringingBoard
  private Carpool carpool;
  private Map<User, List<Boolean>> rentalMembers;

  /**
   * Constructs a trip with the given parameters.
   * @param id the unique trip id
   * @param name the name of the trip (Typically the name of the beach)
   * @param dateMM the month of the date of the trip (2-digits)
   * @param dateDD the day of the date of the trip (2-digits)
   * @param dateYYYY the year of the date of the trip (4-digits)
   * @param startTime the start time of the trip (24 hour format)
   * @param endTime the end time of the trip (24 hour format)
   * @param location the location of the given trip
   * @param locationMapUrl the url of the location of the trip
   * @param headerImageUrl the url for the header background image of the trip
   */
  public Trip(int id, String name, 
      int dateMM, int dateDD, int dateYYYY,
      int startTime, int endTime, 
      String location, String locationMapUrl, 
      String headerImageUrl) {
    this.id = id;
    this.tripName = name;
    if (dateMM > 12) { throw new IllegalArgumentException("Invalid month"); }
    if (dateMM > 31) { throw new IllegalArgumentException("Invalid date");}
    this.dateMM = dateMM;
    this.dateDD = dateDD;
    this.dateYYYY = dateYYYY;
    this.startTime = startTime; 
    this.endTime = endTime;
    this.location = location; 
    this.locationMapUrl = locationMapUrl;
    this.headerImageUrl = headerImageUrl;
    this.allMembers = new ArrayList<>();
    this.dayPlan = new DayPlan();
    this.carpool = new Carpool();
    this.unassignedCarpoolRiders = new HashMap<>();
    this.rentalMembers = new HashMap<>();
  }

  /**
   * Sets the header background image url as the given string
   * @param url the given string to set the header image url to
   */
  public void setHeaderImageUrl(String url) {
    this.headerImageUrl = url;
  } 

  /**
   * Returns the header image url as a string
   * @return the string header background image url
   */
  public String getHeaderImageUrl() {
    return this.headerImageUrl;
  }

  /**
   * Sets the rental shop of this trip to the given rental shop.
   * @param shop the RentalShop object to set this trip's rental shop to
   */
  public void setRentalShop(RentalShop shop) {
    this.rentalShop = shop;
  }

  /**
   * Returns the rental shop being visited for this trip.
   * @return the RentalShop object that represents the rental shop for this trip
   */
  public RentalShop getRentalShop() {
    return this.rentalShop;
  }

  /**
   * Returns the unique trip id of this trip.
   * @return the unique integer id of this trip
   */
  public int getId() {
    return this.id;
  }

  /**
   * Returns the name of this trip.
   * @return the string name of this trip
   */
  public String getTripName() {
    return this.tripName;
  }

  /**
   * Returns the date of this trip as an integer array, containing the month as the first item, 
   * the day as the second object, and the year as the third object.
   * @return the date of this trip as an int array (MM, DD, YYYY)
   */
  public int[] getDates() {
    return new int[]{this.dateMM, this.dateDD, this.dateYYYY};
  }

  /**
   * Returns the formatted date of this trip in string, to be displayed on the Trip Details page header.
   * The month is spelled out in English, and the date and year as numbers.
   * @return a string-formatted date in the format --> Month DD, YYYY
   */
  public String getDatesInString() {
    String month;
    switch (this.dateMM) {
      case 1: month = "Jan"; break;
      case 2: month = "Feb"; break;
      case 3: month = "Mar"; break;
      case 4: month = "Apr"; break;
      case 5: month = "May"; break;
      case 6: month = "Jun"; break;
      case 7: month = "Jul"; break;
      case 8: month = "Aug"; break;
      case 9: month = "Sep"; break;
      case 10: month = "Oct";break;
      case 11: month = "Nov";break;
      case 12: month = "Dec";break;
      default:
        throw new IllegalArgumentException("invalid month");
    }
    return month + " " + this.dateDD + ", " + this.dateYYYY;
  }

  /**
   * Returns the formatted start and end time of this trip to be displayed on the Trip Details page header.
   * Converts the hour from a 24 hour format to a 12 hour (am/pm) format
   * @return
   */
  public String getStartEndTimeInString() {
    String acc = "";
    String am = " a.m.";
    String pm = " p.m.";
    if (this.startTime > 12) {
      acc += (this.startTime - 12);
      acc += pm;
    }
    else {
      acc += this.startTime;
      acc += am;
    }
    acc += " - ";
    if (this.endTime > 12) {
      acc += (this.endTime - 12);
      acc += pm;
    }
    else {
      acc += this.endTime;
      acc += am;
    }
    return acc;
  }

  /**
   * 
   * @return
   */
  public String getLocation() {
    return this.location;
  }

  public String getLocationMapUrl() {
    return this.locationMapUrl;
  }


  //---------

  public void addMember(User u) {
    this.allMembers.add(u);
  }

  public void addRentalMember(User u, List<Boolean> rentalList) {
    this.rentalMembers.put(u, rentalList);
  }

  public void removeRentalMember(User u) {
    this.rentalMembers.remove(u);
  }

  public void addDriver(User account, int numOfSeatsLimit, int numOfBoardsLimit) {
    Driver driver = new Driver(account, this, numOfSeatsLimit, numOfBoardsLimit);
    this.carpool.addDriver(driver);
  }

  public void removeDriver(User account) {
    Driver driver = new Driver(account, this, 0, 0);
    this.carpool.removeDriver(driver);
  }

  public void addRider(User account) {
    this.carpool.addRider(null, account);
  }


  public void removeRider(User u) {
    for (Driver d : this.carpool.getDrivers()) {
      d.removePassenger(u);
    }
  }

  public void requestCarpool(User u, Integer bringingOwnBoard) {
    this.unassignedCarpoolRiders.put(u, bringingOwnBoard);
  }

  public List<User> getAllCarpoolRiders() {
    List<User> allRiders = new ArrayList<>();
    for (Driver d : this.carpool.getDrivers()) {
      for (User u : this.carpool.getPassengers(d)) {
        allRiders.add(u);
      }
    }
    return allRiders;
  }

  public List<Driver> getAllCarpoolDrivers() {
    return this.carpool.getDrivers();
  }

  /**
   * CANCEL TRIP button
   * @param u
   */
  public void removeMemberFromEverything(User u) {
    //remove from allmembers list
    this.allMembers.remove(u);
    //remove from unassigned carpool members
    this.unassignedCarpoolRiders.remove(u);
    //remove from rentalMembers list
    this.removeRentalMember(u);
    //remove from assigned driver
    this.removeDriver(u);
    //remove from assigned passenger
    this.removeRider(u);
    //remove all boards tied to the user
    this.carpool.removeBoard(u);
  }

  //remove this trip from the user's list of registered trips
  //u.removeFormCreatedTrips(this);


  /**
   * Auto-assigned the given rider according to an assignment logic.
   * Current auto-assignment logic is to fill the first available seat for the first available driver on the list.
   */
  public void autoAssignCarpoolRider() throws IllegalArgumentException {
    if (!this.carpool.seatsExist()) {
      throw new IllegalArgumentException("No more available seats");
    }
    List<User> ridersList = new ArrayList<>();
    for (User u : this.unassignedCarpoolRiders.keySet()) {
      ridersList.add(u);
    }
    for (int i = 0; i < this.unassignedCarpoolRiders.size(); i++) {
      User riderToBeAssigned = ridersList.get(i); //get the rider at this index 
      int bringingOwnBoard = this.unassignedCarpoolRiders.get(riderToBeAssigned);
      //assignment logic
      for (int j = 0; j < this.carpool.getDrivers().size(); i++) {
        try {
          this.carpool.getDrivers().get(j).addPassenger(riderToBeAssigned);
          if (bringingOwnBoard == 0) {
            this.carpool.addBoard(riderToBeAssigned);
          }
          this.unassignedCarpoolRiders.remove(riderToBeAssigned);
          i--;
          return;
        } 
        catch (IllegalArgumentException iae) {
          if (iae.getMessage().equals("User is already on the passenger list")) {
            System.out.println(riderToBeAssigned.getName() + "does not need to be assigned to a carpool--already assigned");
          }
          else { //no more space for riders in this driver
            System.out.println("Driver" + j + "does not have more space for riders");
          }
        }
      }
      System.out.println("All cars are full. No more seats available.");
    }
  }

  /**
   * Adds an event to this trip's Day Plan with the given parameters.
   * @param timeHH the hour of the time that this event starts (24 hour format)
   * @param timeMM the minute of the time that this event starts (24 hour format)
   * @param title the title of this day plan event
   * @param subtitle the subtitle of this day plan event
   */
  public void addDayPlanEvent(int timeHH, int timeMM, String title, String subtitle) {
    this.dayPlan.addTime(timeHH, timeMM);
    this.dayPlan.addTitle(title);
    this.dayPlan.addSubtitle(subtitle);
  }

  /**
   * Returns the string-formatted time, title, or subtitle of this day plan event,
   * depending on the given timeTitleSubtitle key.
   * @param idx the index of the event to return the string-formatted information for.
   * @param timeTitleSubtitle timeTitleSubtitle 0 --> the string-formatted time of this event
   *                          timeTitleSubtitle 1 --> the string-formatted title of this event
   *                          timeTitleSubtitle 2 --> the string-formatted subtitle of this event
   * @return the string information to be displayed for this event.
   */
  public String getDayPlanEventString(int idx, int timeTitleSubtitle) {
    if (timeTitleSubtitle == 0) {
      return this.dayPlan.getTimesInStringForEventNum(idx);
    }
    else if (timeTitleSubtitle == 1) {
      return this.dayPlan.getTitleForEventNum(idx);
    }
    else if (timeTitleSubtitle == 2) {
      return this.dayPlan.getSubtitleForEventNum(idx);
    }
    else {
      throw new IllegalArgumentException("invalid time/title/subtitle indicator");
    }
  }


  /**
   * Returns the number of events in this trip's day plan
   * @return
   */
  public int getDayPlanNumOfEvents() {
    return this.dayPlan.getNumOfEvents();
  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof Trip) {
      Trip that = (Trip) o;
      return this.id == that.id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id * 999);
  }



  /**
   * Represents the DayPlan tied to a trip
   * @author stefanieim
   *
   */
  public static class DayPlan {
    private List<int[]> times;
    private List<String> titles;
    private List<String> subtitles;

    /**
     *  Constructs a day plan object with no events.
     */
    public DayPlan() {
      this.times = new ArrayList<>();
      this.titles = new ArrayList<>();
      this.subtitles = new ArrayList<>();
    }

    /**
     * Adds a time to the end of this day plan's list of times
     * @param timeHH the hour of the time to add (24 hour format)
     * @param timeMM the minute of the time to add
     */
    public void addTime(Integer timeHH, Integer timeMM) {
      this.times.add(new int[] {timeHH, timeMM});
    }

    /**
     * 
     * Return the string-formatted time for the event at the given index
     * @param idx the index representing the order of an event
     * @return the string-formatted time of this event 
     *         (converts hour from 24 format, minute ina 60 minute format, and the am/pm depending on the calculation)
     */
    public String getTimesInStringForEventNum(int idx) {
      String acc = "";
      String am = " a.m.";
      String pm = " p.m.";
      String ampm = "";
      if (this.times.get(idx)[0] > 12) { //hour
        acc += (this.times.get(idx)[0] - 12);
        acc += ":";
        acc += this.times.get(idx)[1]; //min
        ampm = pm;
      }
      else if (this.times.get(idx)[0] == 12) { //hour
        acc += "12:";
        acc += this.times.get(idx)[1]; //min
        ampm = pm;
      }
      else if (this.times.get(idx)[0] == 24) { //hour
        acc += "00:";
        acc += this.times.get(idx)[1]; //min
        ampm = am;
      }
      else {
        acc += this.times.get(idx)[0]; //hour
        acc += ":";
        acc += this.times.get(idx)[1]; //min
        ampm = am;
      }
      if (this.times.get(idx)[1] == 0) {
        acc+="0";
      }
      return acc + ampm;
    }

    /**
     * Adds a title to the end of this day plan's list of titles.
     * @param title the title to add
     */
    public void addTitle(String title) {
      this.titles.add(title);
    }

    /**
     * Returns the title at the given index.
     * @param idx the index of the event to return the title for
     * @return the title of the event at the given index
     */
    public String getTitleForEventNum(int idx) {
      return this.titles.get(idx);
    }
    
    /**
     * Adds a subtitle to the end of this day plan's list of subtitles.
     * @param subtitle the subtitle to add
     */
    public void addSubtitle(String subtitle) {
      this.subtitles.add(subtitle);
    }
    
    /**
     * Returns the subtitle at the given index.
     * @param idx the index of the event to return the subtitle for
     * @return the subtitle of the event at the given index
     */
    public String getSubtitleForEventNum(int idx) {
      return this.subtitles.get(idx);
    }

    /**
     * Returns the number of events in this day plan.
     * @return the number of events in integer format
     */
    public int getNumOfEvents() {
      return this.times.size();
    }
  }




  /**
   * Represents the Carpool information attached to a trip.
   * @author stefanieim
   */
  public static class Carpool {
    private List<Driver> drivers;
    private List<User> boards;

    /**
     * Constructs a Carpool object without any drivers and boards that need to be taken in the cars
     */
    public Carpool() {
      this.drivers = new ArrayList<>();
      this.boards = new ArrayList<>();
    }

    /**
     * Returns whether there are any seats left among all the drivers' cars
     * @return
     */
    public boolean seatsExist() {
      for (Driver d : this.drivers) {
        if (d.hasSpaceForRiders()) {
          return true;
        }
      }
      return false;
    }

    /**
     * Returns the list of the users that have boards that need to be taken on the cars
     * @return the list of passengers that have boards
     */
    public List<User> getBoardsList() {
      return this.boards;
    }

    /**
     * Returns the number of boards that need to be taken on the cars
     * @return the number of passengers that have boards
     */
    public int getCurrentNumOfBoards() {
      return this.boards.size();
    }

    /**
     * Adds a board to be taken on a car for the given user
     * @param u the user with the board to be added
     */
    public void addBoard(User u) {
      this.boards.add(u);
    }

    /**
     * Removes the board to be taken on a car for th egiven user
     * @param u the user with the board to be removed
     */
    public void removeBoard(User u) {
      this.boards.remove(u);
    }

    /**
     * Adds a driver to the list of drivers that can pick up other passengers for the associated trip.
     * @param driver the driver to be added
     * @throws IllegalArgumentException if the given driver already exists in the list
     */
    public void addDriver(Driver driver) throws IllegalArgumentException {
      if (this.drivers.contains(driver)) {
        throw new IllegalArgumentException("Driver already exists");
      }
      this.drivers.add(driver);
    }

    /**
     * Adds the given passenger to the given driver's car
     * @param driver the driver to add the passenger to
     * @param rider the passenger to be added
     */
    public void addRider(Driver driver, User rider) {
      for (Driver d : drivers) {
        if (d.equals(driver)) {
          if (d.getPassengersList().contains(rider)) {
            throw new IllegalArgumentException("Passenger already exists");
          }
          d.addPassenger(rider);
          return;
        }
      }
      throw new IllegalArgumentException("Driver does not exist");
    }

    /**
     * Removes rider from the given driver's list of passengers
     * @param rider the rider to be removed from this driver's car
     */
    public void removeRiderFromDriver(Driver driver, User rider) {
      for (int i = 0; i < this.drivers.size(); i++) {
        if (this.drivers.get(i).equals(driver)) {
          this.drivers.get(i).removePassenger(rider);
          i--;
        }
      }
    }

    /**
     * Removes the given driver from this Carpool object's list of drivers
     * @param driver the driver to be removed from available members to drive
     */
    public void removeDriver(Driver driver) {
      for (int i = 0; i < this.drivers.size(); i++) {
        if (this.drivers.get(i).equals(driver)) {
          this.drivers.remove(i);
          i--;
        }
      }
    }

    /**
     * Returns the list of all drivers that can driver others to the associated trip.
     * @return the list of all drivers
     */
    public List<Driver> getDrivers() {
      return this.drivers;
    }

    /**
     * Returns the list of all passengers going on the given driver's car.
     * @param driver the driver to return the list of passengers for
     * @return the list of passengers for the given driver
     */
    public List<User> getPassengers(Driver driver) {
      return driver.getPassengersList();
    }
  }

}

