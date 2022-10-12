package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.RentalShop.Equipment;

/**
 * Represents a registration form for a trip. It is created
 * @author stefanieim
 *
 */
public class Form {
  private List<Integer> phoneNum;
  private String email;
  private String addressSt, addressZipcode;
  //Transportation Selection
  private int transportOption;  //-1 unselected, 0=none 1=canDrive, 2=needRide;
  private int driverNumOfExtraRiders;
  private int driverNumOfExtraBoards;
  private int bringingOwnBoard; //-1=unselected, 0=yes, 1=no
  //Rental Selection
  private int rentalNeeded; //-1=unselected, 0=yes, 1=no;
  private List<Boolean> rentalItems; //(item0:wetsuit, item1:board);
  //form textFields
  private List<Boolean> textBoxStates;
  //form
  private boolean submitted;

  /**
   * Constructs a new form with the given user's details.
   * (user needed for prefilling form)
   * @param u the logged-in user to construct this form for
   */
  public Form(User u) {
    this.phoneNum = u.getPhoneNum();
    this.email = u.getEmail();
    this.addressSt = u.getAddressSt();
    this.addressZipcode = u.getAddressZipcode();
    this.transportOption = -1;
    this.driverNumOfExtraRiders = 0;
    this.driverNumOfExtraBoards = 0;
    this.bringingOwnBoard = -1;
    this.rentalNeeded = -1;
    this.rentalItems = new ArrayList<>(Arrays.asList(false, false));
    this.textBoxStates = new ArrayList<>(Arrays.asList(false, false, false, false));
    this.submitted = false;
  }
  
  /**
   * Constructs a new form with the given parameters.
   * @param phone the phone number to prefill the form with
   * @param st the street address to prefill the form with
   * @param zipcode the zipcode to prefill the form with
   * @param email the email address to prefill the form with.
   */
  public Form(List<Integer> phone, String st, String zipcode, String email) {
    this.phoneNum = phone;
    this.email = email;
    this.addressSt = st;
    this.addressZipcode = zipcode;
    this.transportOption = -1;
    this.driverNumOfExtraRiders = 0;
    this.driverNumOfExtraBoards = 0;
    this.bringingOwnBoard = -1;
    this.rentalNeeded = -1;
    this.rentalItems = new ArrayList<>(Arrays.asList(false, false));
    this.textBoxStates = new ArrayList<>(Arrays.asList(false, false, false, false));
    this.submitted = false;
  }

  /**
   * Removes the last digit of the phone number saved in this form.
   */
  public void removeLastDigitOfPhone() {
    if (this.phoneNum.size() > 0) {
      int idx = this.phoneNum.size()-1;
      this.phoneNum.remove(idx);
    }
  }

  /**
   * Adds the given digit to the end of the phone number saved in this form. 
   * Does not add anything if the phone number already has 10 digits
   * @param digit the given digit to add to the end of this phone number
   */
  public void addLastDigitOfPhone(int digit) {
    if (this.phoneNum.size() < 10) {
      this.phoneNum.add(digit);
    }
  }

  /**
   * Returns the phone number saved in this form, formatted in a String
   * @return the phone number
   */
  public String getPhoneString() {
    String acc = "";
    for (int i = 0; i < this.phoneNum.size() ; i++) { //backwards
      if (i == 6) {acc += "-"; }
      if (i == 3) {acc += ") "; }
      if (i == 0) {acc += "("; }
      acc += this.phoneNum.get(i);
    }
    return acc;
  }

  /**
   * Replaces the original email for this form with the given string
   * @param email the new email to set this form's email to.
   */
  public void changeEmail(String email) {
    this.email = email;
  }

  /**
   * Return the email attached to this form.
   * @return the string value of the email filled in for this form
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Replaces the original street address with the given string.
   * @param street the new street address
   */
  public void changeAddressSt(String street) {
    this.addressSt = street;
  }

  /**
   * Return the street address currently saved in this form.
   * @return the street address of this form
   */
  public String getAddressSt() {
    return this.addressSt;
  }

  /**
   * Replaces the original zipcode with the given zipcode, as long as it is a number and less than 5 digits.
   * @param zipcode the new zipcode to set this form's address' zipcode to, in string format
   */
  public void changeAddressZipcode(String zipcode) {
    try {
      //make sure that all characters are numbers
      for (int i = 0; i < zipcode.length(); i++) {
        String character = zipcode.substring(i, i+1);
        Integer.parseInt(character);
      }
      //make sure that it does not go over five digits
      if (zipcode.length() <= 5) {
        this.addressZipcode = zipcode;
      }
    } catch (NumberFormatException nfr) {
      //do nothing
    }
  }

  /**
   * Returns the zipcode currently saved in this form, in String format
   * @return the string format of the zipcode saved
   */
  public String getAddressZipcode() {
    return this.addressZipcode;
  }

  /**
   * The text-field to activate for keyboard inputs
   * @param idx the index of the textbox to activate for typing (0=phone, 1=email, 2=street, 3=city)
   */
  public void activateTextBox(int idx) { 
    for (int i = 0; i < this.textBoxStates.size(); i++) {
      if (i == idx) {
        this.textBoxStates.set(i, true);
      } else {
        this.textBoxStates.set(i, false);
      }
    }
  }

  /**
   * Disable all text-fields in this form to disable keyboard inputs.
   */
  public void disableAllTextBoxes() {
    for (int i = 0; i < this.textBoxStates.size(); i++) {
      this.textBoxStates.set(i, false);
    }
  }

  /**
   * Returns the active text field.
   * @return -1 if none are active, index of the active text field if it exists
   */
  public int getActiveTextField() {
    for (int i = 0; i < this.textBoxStates.size(); i++) {
      if (this.textBoxStates.get(i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the transport option selected in this form.
   * @param idx the index of the selected transport option
   */
  public void setTransportOption(int idx) {
    this.transportOption = idx;
  }
  
  /**
   * Return the index of the selected transportation option
   * @return -1 if unselected,
   *          0 if "I'll meet you there"
   *          1 if "I can drive"
   *          2 if "I need a ride"
   */
  public int getTransportOption() {
    return this.transportOption;
  }

  /**
   * Sets the option to indicate whether the user is bringing their own board or not, to the given index.
   * @param idx 0 if they are brining, 1 if not
   */
  public void setBringingOwnBoard(int idx) {
    this.bringingOwnBoard = idx;
  }

  /**
   * Return whether the user is bringing their own board.
   * @return -1 if option has not been checked, 0 if checked, 1 if not
   */
  public int getBringingOwnBoard() {
    return this.bringingOwnBoard;
  }

  /**
   * Sets the rental needed status of this form to the given index
   * @param idx the given index to set the rental arrangement option to
   */
  public void setRentalNeeded(int idx) {
    this.rentalNeeded = idx;
  }

  /**
   * Return whether the checked rental option is yes or no
   * @return -1 if not checked, 0 if yes, 1 if no
   */
  public int getRentalNeeded() {
    return this.rentalNeeded;
  }

  /**
   * toggkes the rental state of the item at the given index.
   * @param itemIdx the item to toggle the rental state for (true<-->false)
   */
  public void toggleRentalItem(int itemIdx) { //0:wetsuit, //1:board
    boolean orig = this.rentalItems.get(itemIdx);
    this.rentalItems.set(itemIdx, !orig);
  }

  /**
   * Unchecks all rentable items.
   */
  public void resetRentalItems() {
    for (int i = 0; i < this.rentalItems.size(); i++) {
      this.rentalItems.set(i, false);
    }
  }

  /**
   * Returns the list of booleans representing whether certain items are being rented.
   * @return the list of booleans (item 0 = wetsuit, item 1 = board) indicating if checkbox has been checked
   */
  public List<Boolean> getRentalItems() {
    return this.rentalItems;
  }

  /**
   * Returns whether the user has checked off the rental item at the given index
   * @param idx 0 if wetsuit, 1 if board
   * @return true if item is being rented
   */
  public boolean getRentalItem(int idx) {
    return this.rentalItems.get(idx);
  }

  /**
   * Check if this form is currently valid for submission.
   * @return true if this form passes all conditions required for submission
   */
  public boolean checkIfValidSubmitState() {
    if (transportOption == -1) {
      //System.out.println("Unable to submit: Choose transport option");
      return false;
    }
    if (rentalNeeded == -1) {
      //System.out.println("Unable to submit: Choose rental option");
      return false;
    }
    //if they need a ride but they didn't check whether theyre bringing their own board
    if (transportOption == 2 && this.bringingOwnBoard == -1) {
      //System.out.println("Unable to submit: Indicate if you are bringing your own board or not");
      return false;
    }
    //indicated that they need to rent but didn't check wetsuit or board
    if (this.rentalNeeded == 0 && !this.rentalItems.contains(true)) { 
      //System.out.println("Unable to submit: Check at least one rental item");
      return false;
    }
    return true;
  }

  public void setSubmitted(boolean submitted) {
    this.submitted = submitted;
  }

  /**
   * Returns whether this form has been submitted.
   * @return true if this form has been submitted
   */
  public boolean isSubmitted() {
    return this.submitted;
  }

  /**
   * Prints the current state of the form, with saved details formatted in a user-friendly way.
   */
  public void consolePrintForm() {
    String formattedPhone = "";
    for (int i : this.phoneNum) {formattedPhone += i;}
    
    List<String> formattedPersonalInfo = new ArrayList<>(Arrays.asList(formattedPhone, this.email, this.addressSt, this.addressZipcode));
    
    int numOfCharInRow = 40;
    int subtitleChar = 9;
    List<Integer> numOfSpaces = new ArrayList<>();
    int numOfSpacesPhone = (numOfCharInRow - subtitleChar) - this.phoneNum.size();
    int numOfSpacesEmail = (numOfCharInRow - subtitleChar) -  this.email.length();
    int numOfSpacesStreet = (numOfCharInRow - subtitleChar) - this.addressSt.length();
    int numOfSpacesZipcode = (numOfCharInRow - subtitleChar) - this.addressZipcode.length();
    List<Integer> numOfSpacesList = new ArrayList<>(Arrays.asList(numOfSpacesPhone, numOfSpacesEmail, numOfSpacesStreet, numOfSpacesZipcode));
    
    for (int i = 0; i < 4; i++) {
      int spaces = numOfSpacesList.get(i);
      for (int j = 0; j <= spaces; j++) {
        String orig = formattedPersonalInfo.get(i);
        if (j == spaces) {
          orig += "|";
        } else {
          orig += " ";
        }
        formattedPersonalInfo.set(i, orig);
      }
    }
    
    String transportStr = "";
    if (this.transportOption == 0) { //i'll meet you there
      transportStr += "meet at destination         |";
    } else if (this.transportOption == 1) { //can drive
      transportStr += "driver volunteered          |";
    } else if (this.transportOption == 2) { //carpool requested
      transportStr += "carpool requested           |";
    }
    
    String rentalStr = "";
    if (this.rentalNeeded == 0) { //i'll meet you there
      
      for (int i = 0; i < rentalItems.size(); i++) {
        if (rentalItems.get(i)) {
          if (i != 0) {
            rentalStr += " & ";
          }
          switch (i) {
            case 0:
              rentalStr += "WETSUIT";
              break;
            case 1:
              rentalStr += "BOARD";
              break;
          }
        } 
      }
      int numOfSpacesRequests = numOfCharInRow - 12 - rentalStr.length();
      
      for (int j = 0; j <= numOfSpacesRequests; j++) {
        if (j == numOfSpacesRequests) {
          rentalStr += "|";
        } else {
          rentalStr += " ";
        }
      }
      
    } else if (this.rentalNeeded == 1) {
      rentalStr += "n/a                         |";
    }
    System.out.println("__________________________________________");
    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
    System.out.println("|!FORM SUBMITTED!!!!!!!!!!!!!!!!!!!!!!!!!|");
    System.out.println("|----------------------------------------|");
    System.out.println("|                                        |");
    System.out.println("|                                        |");
    System.out.println("|--Personal Information------------------|");
    System.out.println("|                                        |");
    System.out.println("|phone   :" + formattedPersonalInfo.get(0));
    System.out.println("|email   :" + formattedPersonalInfo.get(1));
    System.out.println("|street  :" + formattedPersonalInfo.get(2));
    System.out.println("|zipcode :" + formattedPersonalInfo.get(3));
    System.out.println("|                                        |");
    System.out.println("|                                        |");
    System.out.println("|--Transportation------------------------|");
    System.out.println("|                                        |");
    System.out.println("|transport : " + transportStr);
    System.out.println("|                                        |");
    System.out.println("|                                        |");
    System.out.println("|--Rental--------------------------------|");
    System.out.println("|                                        |");
    System.out.println("|requested : " + rentalStr);
    System.out.println("|                                        |");
    System.out.println("|                                        |");
    System.out.println("|________________________________________|");
  }
}
