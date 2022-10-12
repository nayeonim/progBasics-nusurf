package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Surf Rental Shop
 * @author stefanieim
 *
 */
public class RentalShop {
  private int id;
  private String name;
  private String imageLink;
  private String websiteLink;
  private String routeLink;
  private String routeImageLink;
  private String routeImageHoverlink;
  
  /**
   * The types of equipment that can be rented
   * @author stefanieim
   */
  public enum Equipment {
    BOARD("BOARD"), WETSUIT("WETSUIT"), DRYSUIT("DRYSUIT"), BOARD_N_WETSUIT("BOARD & WETSUIT");
    String string;
    Equipment(String str) {
      this.string = str;
    }
    @Override
    public String toString() {
      return this.string;
    }
  }
  
  private Map<Equipment, Map<String, Integer>> rentalDeals; //the list of menu options a map of option--price, for each Equipment type

  /**
   * A rental shop with the given unique id and name
   * @param id the unique id of this rental shop
   * @param name the name of this rental shop
   */
  public RentalShop(int id, String name) {
    this.name = name;
    this.imageLink = "";
    this.websiteLink = "";
    this.routeLink = "";
    this.rentalDeals = new HashMap<>();
  }
  
  /**
   * Returns the string name of this rental shop
   * @return the name of this rental shop
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Sets the name of this rental shop to the given string
   * @param str the new name of this rental shop
   */
  public void setName(String str) {
    this.name = str;
  }
  
  /**
   * Adds a menu item to this shop's list of deals
   * @param type the equipment type of this deal
   * @param option the description of the specific deal option
   * @param price the price of this deal option
   */
  public void addRentalMenu(Equipment type, String option, int price) {
    try {
      this.rentalDeals.get(type).put(option, price);
    } 
    catch (NullPointerException npe) {
      this.rentalDeals.put(type, new HashMap<>());
      this.rentalDeals.get(type).put(option, price);
    }
  }

  /**
   * Returns the map of rental deals available at this rental shop
   * @return the rental deals for this rental shop
   */
  public Map<Equipment, Map<String, Integer>> getRentalDealsMap() {
    return this.rentalDeals;
  }
  
  /**
   * Sets the image link for this rental shop to the given link
   * @param link the given link to set the image url of this rental shop to
   */
  public void setImageLink(String link) {
    this.imageLink = link;
  }

  /**
   * Sets the url of this rental shop's website
   * @param link the url to set this rental shop's website link to
   */
  public void setWebsiteLink(String link) {
    this.websiteLink = link;
  }

  /**
   * Sets the url of the route to this rental shop.
   * Typically a google maps link from Northeastern to this Rental Shop's address
   * @param link the url to set this rental shop map to
   */
  public void setRouteLink(String link) {
    this.routeLink = link;
  }

  /**
   * Sets the url to the image thumbnail for the route to the rental shop, to the given links.
   * @param link the link to the normal thumbnail
   * @param hoverLink the link to the thumbnail to be shown when mouse hovers over it
   */
  public void setRouteImageLink(String link, String hoverLink) {
    this.routeImageLink = link;
    this.routeImageHoverlink = hoverLink;
  }
  
  /**
   * Returns the image url of this rental shop
   * @return the image url of this rental shop in string
   * @throws IllegalStateException if the link is empty
   */
  public String getImageLink() throws IllegalStateException {
    if (this.imageLink == "") {
      throw new IllegalStateException("Image link empty");
    }
    return this.imageLink;
  }

  /**
   * Returns the website url of this rental shop
   * @return the website url of this rental shop in string
   * @throws IllegalStateException if the link is empty
   */
  public String getWebsiteLink() throws IllegalStateException {
    if (this.imageLink == "") {
      throw new IllegalStateException("Website link empty");
    }
    return this.websiteLink;
  }

  /**
   * Returns the url of a website showing the route to this rental shop
   * @return the url of the route to this rental shop in string
   * @throws IllegalStateException if the link is empty
   */
  public String getRouteLink() throws IllegalStateException {
    if (this.imageLink == "") {
      throw new IllegalStateException("Route link empty");
    }
    return this.routeLink;
  }
  
  /**
   * Returns the image thumbnail url for the route to this rental shop, 
   * depending on whether to return the normal image or the hover image.
   * @hover whether to return the hover state url or not
   * @return the image thumbnail url for the route to this rental shop in string
   * @throws IllegalStateException if the link is empty
   */
  public String getRouteImageLink(boolean hover) throws IllegalStateException {
    if (this.imageLink == "") {
      throw new IllegalStateException("Route link empty");
    }
    if (hover) {
      return this.routeImageHoverlink;
    }
    else {
      return this.routeImageLink;
    }
  }
  
  
  
}

