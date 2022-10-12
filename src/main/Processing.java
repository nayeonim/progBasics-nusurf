package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Constants.Colors;
import Constants.TextSize;
import main.Processing.ScrollBar;
import model.Driver;
import model.Form;
import model.Model;
import model.RentalShop;
import model.Trip;
import model.User;
import model.RentalShop.Equipment;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;


/**
 * 
 * This class represents the Controller and View for the NUsurf website.
 * @author stefanieim
 *
 */
public class Processing extends PApplet {
  /*
   * TODO: 
   * - location img
   * - form submission printing (console)
   * - NEW VISUALSTATE close popup confirm state "Are you sure? Your form will be cleared."
   */
  //----------------------------------------------------------------------------------------------
  //----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------- 
  public final static User USER = new User(991031, "Stefanie", "Im", new ArrayList<>(Arrays.asList(9,2,9,3,1,9,5,3,9,8)), "im.n@northeastern.edu");
  public final static Trip TRIP_0 = new Trip(123,"Good Harbor", 06,19,2022, 10,18, "Good Harbor Beach, Gloucester", 
      "https://www.google.com/maps/place/Good+Harbor+Beach/@42.6206814,-70.6600699,14.23z/data=!4m5!3m4!1s0x89e32577fd1c8ab5:0xf268e9b3c44f4b9f!8m2!3d42.6207363!4d-70.6320892",
      "src/resources/GoodHarbor.png");
  //----------------------------------------------------------------------------------------------
  //----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------- 
  //  manipulate the DIVISOR constants to change the canvas dimensions
  //  (increase the divisor number to decrease the screen size)
  public final static double DIVISOR = 1.0; 
  //----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------- 
  //canvas dimensions
  public final static float CANVAS_W = (float)(1280/DIVISOR);
  public final static float CANVAS_H = (float)(720/DIVISOR);
  public final static float BROWSER_H = CANVAS_H/9;
  public final static float NONBROWSER_TOPLEFT_X = 0; //top left corner in relation to main canvas
  public final static float NONBROWSER_WIDTH = CANVAS_W;
  public final static float NONBROWSER_HEIGHT = (float)((CANVAS_H/2)*3.2);
  public final static float POPUP_W = (float)(CANVAS_W/2);
  public final static float POPUP_H = (float)(CANVAS_H*0.75);
  public final static float POPUP_TOPLEFTX = (CANVAS_W/2)-(POPUP_W/2);
  public final static float POPUP_TOPLEFTY = (CANVAS_H/2)+(BROWSER_H/2)-(POPUP_H/2);
  public final static float POPUP_BUFFER = (float)(CANVAS_H/30); 
  //----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------- 
  //SCROLLBAR COLORS
  public static final int[] COL_NORM = Colors.SCROLLBAR_GREY_LIGHT;
  public static final int[] COL_HOVER = Colors.SCROLLBAR_GREY_MEDIUM;
  public static final int[] COL_CLICK = Colors.SCROLLBAR_GREY_DARK;
  //----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------- 
  public final static TextSize TXTPT = new TextSize(CANVAS_H);
  public final static float BTN_S_W = (CANVAS_W/10);
  public final static float BTN_S_H = (CANVAS_H/20);
  public final static float BTN_M_W = (float) (CANVAS_W/6.4);
  public final static float BTN_M_H = (CANVAS_H/15);
  //---variables-----------------------------------------------------------------------------------
  private Model m;
  //scroll
  private ScrollBar scrollBar;
  //images
  PImage imgChromeBar, imgGoodHarborHeader , imgRentalShop, imgRoute, imgRouteHover, imgCarIcon;
  //popup
  private float popUpCenterX, popUpCenterY, popUpW, popUpH;
  private float cancelPopUpWH;
  //button dimensions+positions
  private List<Float> btnRegisterTopLeftXYWH;
  private List<Float> btnRentalWebsiteTopLeftXYWH;
  private List<Float> btnEditFormTopLeftXYWH;
  private List<Float> btnCancelTopLeftXYWH;
  private List<Float> btnCancelConfirmTopLeftXYWH;
  private List<Float> btnCancelNvmTopLeftXYWH;
  private List<Float> btnPopUpCloseTopLeftXYWH;
  private List<Float> btnSubmitTopLeftXYWH;
  private List<Float> btnRouteTopLeftXYWH;
  //text field dimensions+positions
  private List<Float> txtFld0TopLeftXYWH;
  private List<Float> txtFld1TopLeftXYWH;
  private List<Float> txtFld2TopLeftXYWH;
  private List<Float> txtFld3TopLeftXYWH;
  //radio button / check box dimensions+positions

  private float radioTransportXs, radioTransport0Y, radioTransport1Y, radioTransport2Y, 
  radioTransport2yesX, radioTransport2noX, radioTransport2yesnoYs ;
  private float radioRentalYesX, radioRentalNoX, radioRentalYs; //center
  private float checkBoxWetsuitY, checkBoxBoardY, checkBoxXs, checkBoxWH; //top left
  //-----------------------------------------------------------------------------------------------


  /**
   * This function will set up the initial canvas by running once when the program starts.
   */
  @Override
  public void settings() {
    size((int)CANVAS_W, (int)CANVAS_H);
  }

  /**
   * This function will set up all variables by running once when the program starts.
   */
  public void setup() {
    rectMode(CENTER);
    imageMode(CENTER);

    setUp_Trip_0();

    this.m = new Model();
    this.m.addTrip(TRIP_0);

    this.consolePrintCurrentState();

    this.loadImages();

    this.scrollBar = new ScrollBar(CANVAS_H, BROWSER_H);

    setTextFieldPosDim();
    setPopUpPosDim();
    setButtonPosDim();

    this.radioTransport0Y = (float)(POPUP_TOPLEFTY+(this.popUpH/2)+(2.0*POPUP_BUFFER)+(TXTPT.l*0.25));
    this.radioTransport1Y = (float)(POPUP_TOPLEFTY+(this.popUpH/2)+(3.4*POPUP_BUFFER)+(TXTPT.l*0.25));
    this.radioTransport2Y = (float)(POPUP_TOPLEFTY+(this.popUpH/2)+(4.8*POPUP_BUFFER)+(TXTPT.l*0.25));
    this.radioTransportXs = (float)(POPUP_TOPLEFTX+(POPUP_BUFFER)+(TXTPT.l*0.5));
    this.radioTransport2yesX = this.radioTransportXs+POPUP_BUFFER+(TXTPT.l/2);
    this.radioTransport2noX = radioTransport2yesX+(3*POPUP_BUFFER);
    this.radioTransport2yesnoYs = (float)(radioTransport2Y+(POPUP_BUFFER*2)-(TXTPT.xs/3));
    this.checkBoxWH = TXTPT.m;
    this.cancelPopUpWH = (float)(CANVAS_H*0.38);

    Processing.USER.setAddressSt("79 Gainsborough St. Apt 999");
    Processing.USER.setAddressZipcode("02115");
  }



  private void setPopUpPosDim() {
    this.popUpCenterX = (float)(CANVAS_W/2);
    this.popUpCenterY = (float)(CANVAS_H/2)+(BROWSER_H/2);
    this.popUpW = POPUP_W;
    this.popUpH = POPUP_H;
  }




  private void setTextFieldPosDim() {
    this.txtFld0TopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.txtFld1TopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.txtFld2TopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.txtFld3TopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));

    float txtFldH = (float)(CANVAS_H/13.85);

    this.txtFld0TopLeftXYWH.add(0, POPUP_TOPLEFTX+POPUP_BUFFER);
    this.txtFld0TopLeftXYWH.add(1, (float)(POPUP_TOPLEFTY+(CANVAS_H*0.14)));
    this.txtFld0TopLeftXYWH.add(2, (float)(CANVAS_H/4.4));
    this.txtFld0TopLeftXYWH.add(3, txtFldH);

    this.txtFld1TopLeftXYWH.add(0, this.txtFld0TopLeftXYWH.get(0)+this.txtFld0TopLeftXYWH.get(2)+POPUP_BUFFER);
    this.txtFld1TopLeftXYWH.add(1, txtFld0TopLeftXYWH.get(1));
    this.txtFld1TopLeftXYWH.add(2, (float)(CANVAS_H/2.4));
    this.txtFld1TopLeftXYWH.add(3, txtFldH);

    this.txtFld2TopLeftXYWH.add(0, this.txtFld0TopLeftXYWH.get(0));
    this.txtFld2TopLeftXYWH.add(1, POPUP_TOPLEFTY+(float)(CANVAS_H*0.27));
    this.txtFld2TopLeftXYWH.add(2, txtFld1TopLeftXYWH.get(2));
    this.txtFld2TopLeftXYWH.add(3, txtFldH);

    this.txtFld3TopLeftXYWH.add(0, this.txtFld2TopLeftXYWH.get(0) + this.txtFld2TopLeftXYWH.get(2) + POPUP_BUFFER);
    this.txtFld3TopLeftXYWH.add(1, txtFld2TopLeftXYWH.get(1));
    this.txtFld3TopLeftXYWH.add(2, txtFld0TopLeftXYWH.get(2));
    this.txtFld3TopLeftXYWH.add(3, txtFldH);
  }


  private static void setUp_Trip_0() {
    //add day plan details
    TRIP_0.addDayPlanEvent(10, 00, "Departure from Northeastern", "last pick-up for each car");
    TRIP_0.addDayPlanEvent(11, 30, "Arrive at Surfari Rental Shop", "rent & change into gear");
    TRIP_0.addDayPlanEvent(12, 00, "Surf", "first session (1h 30min)");
    TRIP_0.addDayPlanEvent(13, 30, "Lunch", "grab a quick sub near the beach");
    TRIP_0.addDayPlanEvent(14, 15, "Surf", "second session (2h)");
    TRIP_0.addDayPlanEvent(16, 30, "Return Equipment", "4 hours from rental start time");
    TRIP_0.addDayPlanEvent(17, 00, "Leave to Northeastern", "e.t.a. 6:00pm");

    //add car pool
    User ben = new User(29301, "Ben", "Frothingham", new ArrayList<>(Arrays.asList(6,1,7,8,1,2,3,1,2,9)), "b.frothy@northeastern.edu");
    User henry = new User(19240, "Henry", "Mayne", new ArrayList<>(Arrays.asList(8,3,2,1,3,7,9,3,3,1)), "h.mayne@northeastern.edu");
    User emil = new User(19240, "Emil", "Furat", new ArrayList<>(Arrays.asList(3,0,6,2,4,5,6,3,6,0)), "efurat@northeastern.edu");
    TRIP_0.addMember(ben);
    TRIP_0.addMember(henry);
    TRIP_0.addMember(emil);
    TRIP_0.addDriver(ben, 2, 0);
    TRIP_0.addDriver(henry, 2, 2);
    TRIP_0.requestCarpool(emil, 1);
    TRIP_0.autoAssignCarpoolRider();

    //add rental info
    RentalShop surfari = new RentalShop(1232, "Surfari");
    surfari.setImageLink("src/resources/Surfari.png");
    surfari.setWebsiteLink("https://www.surfcapeann.com/");
    surfari.setRouteLink("https://goo.gl/maps/5ovdBXQnnYXWoFKVA");
    surfari.setRouteImageLink("src/resources/surfariRoute.png", "src/resources/surfariRouteHover.png");
    surfari.addRentalMenu(Equipment.BOARD, "Half-Day (4 hrs)", 30);
    surfari.addRentalMenu(Equipment.BOARD, "Full-Day (no limit)", 45);
    surfari.addRentalMenu(Equipment.WETSUIT, "Single Option", 20);
    TRIP_0.setRentalShop(surfari);
    TRIP_0.addRentalMember(emil, new ArrayList<Boolean>(Arrays.asList(true, true)));
  }


  private void loadImages() {
    Trip trip =  m.getTrip(TRIP_0.getId());
    RentalShop shop = trip.getRentalShop();

    this.imgChromeBar = loadImage("src/resources/ChromeBar.png");
    this.imgGoodHarborHeader = loadImage(trip.getHeaderImageUrl());
    this.imgRentalShop = loadImage(shop.getImageLink());
    this.imgRoute = loadImage(shop.getRouteImageLink(false));
    this.imgRouteHover = loadImage(shop.getRouteImageLink(true));
    this.imgCarIcon = loadImage("src/resources/iconCar.png");
  }


  private void setButtonPosDim() {
    this.btnRegisterTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnRentalWebsiteTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnEditFormTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnCancelTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnCancelConfirmTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnCancelNvmTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnPopUpCloseTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnSubmitTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));
    this.btnRouteTopLeftXYWH = new ArrayList<>(Arrays.asList((float)0, (float)0, (float)0, (float)0));

    this.btnRegisterTopLeftXYWH.set(0, (float)(NONBROWSER_TOPLEFT_X+(CANVAS_W/1.25)));
    this.btnRegisterTopLeftXYWH.set(2, BTN_M_W);
    this.btnRegisterTopLeftXYWH.set(3, BTN_M_H);

    this.btnRentalWebsiteTopLeftXYWH.set(2, BTN_S_W);
    this.btnRentalWebsiteTopLeftXYWH.set(3, BTN_S_H);

    this.btnEditFormTopLeftXYWH.set(0, (float)(NONBROWSER_TOPLEFT_X+(CANVAS_W/1.45)));
    this.btnEditFormTopLeftXYWH.set(2, (float)(BTN_M_W*0.75));
    this.btnEditFormTopLeftXYWH.set(3, (float)(BTN_M_H));

    this.btnCancelTopLeftXYWH.set(0, (float)(NONBROWSER_TOPLEFT_X+(CANVAS_W/1.2)));
    this.btnCancelTopLeftXYWH.set(2, this.btnEditFormTopLeftXYWH.get(2));
    this.btnCancelTopLeftXYWH.set(3, this.btnEditFormTopLeftXYWH.get(3));

    this.btnCancelConfirmTopLeftXYWH.set(2, this.btnRegisterTopLeftXYWH.get(2));
    this.btnCancelConfirmTopLeftXYWH.set(3, this.btnRegisterTopLeftXYWH.get(3));
    this.btnCancelConfirmTopLeftXYWH.set(0, this.popUpCenterX - this.btnCancelConfirmTopLeftXYWH.get(2)/2);
    this.btnCancelConfirmTopLeftXYWH.set(1, this.popUpCenterY - this.btnCancelConfirmTopLeftXYWH.get(3)/2);
    this.btnCancelNvmTopLeftXYWH.set(0, this.btnCancelConfirmTopLeftXYWH.get(0));
    this.btnCancelNvmTopLeftXYWH.set(2, this.btnCancelConfirmTopLeftXYWH.get(2));
    this.btnCancelNvmTopLeftXYWH.set(3, this.btnCancelConfirmTopLeftXYWH.get(3));
    this.btnCancelNvmTopLeftXYWH.set(1, this.btnCancelConfirmTopLeftXYWH.get(1) 
        + this.btnCancelConfirmTopLeftXYWH.get(3)+POPUP_BUFFER);

    this.btnSubmitTopLeftXYWH.set(2, (float)(this.popUpW - (2*POPUP_BUFFER)));
    this.btnSubmitTopLeftXYWH.set(3, (float)(BTN_M_H));
    this.btnSubmitTopLeftXYWH.set(0, (float)(POPUP_TOPLEFTX+POPUP_BUFFER));
    this.btnSubmitTopLeftXYWH.set(1, (float)(popUpCenterY+(popUpH/2)-(POPUP_BUFFER+this.btnSubmitTopLeftXYWH.get(3))));

    this.btnPopUpCloseTopLeftXYWH.set(0, POPUP_TOPLEFTX+POPUP_W-(2*POPUP_BUFFER));
    this.btnPopUpCloseTopLeftXYWH.set(1, POPUP_TOPLEFTY+POPUP_BUFFER);
    this.btnPopUpCloseTopLeftXYWH.set(2, POPUP_BUFFER);
    this.btnPopUpCloseTopLeftXYWH.set(3, POPUP_BUFFER);

    this.btnRouteTopLeftXYWH.set(2, (float)(this.imgRoute.width));
    this.btnRouteTopLeftXYWH.set(3, (float)(this.imgRoute.height));
  }


  private void consolePrintCurrentState() {
    User user = m.getLoggedInUser();
    Trip trip = m.getTrip(TRIP_0.getId());
    String formStatus;
    try {
      if (user.getAllFormCreatedTrips().get(trip).isSubmitted()) {
        formStatus = "submitted";
      } else {
        formStatus = "created";
      }
    } catch (NullPointerException npe) {
      formStatus = "not created";
    }

    System.out.println("------------------------------------");
    System.out.println("--Current Session-------------------");
    System.out.println("------------------------------------");
    System.out.println("Logged-in user : " + user.getName()[0] + " " + user.getName()[1]);
    System.out.println("          email : " + user.getEmail());
    System.out.println("Trip ID : " + trip.getId());
    System.out.println("Form status : " + formStatus);
    System.out.println();
    System.out.println();
    System.out.println("--Trip Info-------------------------");
    System.out.println("Name : " + trip.getTripName());
    System.out.println("Date : " + trip.getDatesInString());
    System.out.println("Time : " + trip.getStartEndTimeInString());
    System.out.println("Location : " + trip.getLocation());
    System.out.println("LocationMapUrl : " + trip.getLocationMapUrl().substring(0, 20));
    System.out.println();
    System.out.println();
    System.out.println("--Day Plan-------------------------");
    for (int i = 0; i < trip.getDayPlanNumOfEvents(); i++) {
      System.out.println();
      System.out.println("Event#" + i);
      System.out.println("Time: " + trip.getDayPlanEventString(i, 0));
      System.out.println("Title: " + trip.getDayPlanEventString(i, 1)); 
      System.out.println("Subtitle: " + trip.getDayPlanEventString(i, 2)); 
    }
    System.out.println();
    System.out.println();
    System.out.println("--Rental Shop-------------------------");
    System.out.println("Name : " + trip.getRentalShop().getName());
    System.out.println("imageUrl : " + trip.getRentalShop().getImageLink());
    System.out.println("webLink : " + trip.getRentalShop().getWebsiteLink());
    System.out.println();
    System.out.println("Rental Deals----");
    for (Map.Entry<RentalShop.Equipment, Map<String, Integer>> equipmentType : trip.getRentalShop().getRentalDealsMap().entrySet()) {
      System.out.println();
      System.out.println("" + equipmentType.getKey().toString()); 
      for (Map.Entry<String, Integer> menuItem : equipmentType.getValue().entrySet()) {
        System.out.println(menuItem.getKey() + "........$" +  menuItem.getValue()); 
      }
    }
    System.out.println();
    System.out.println();
    System.out.println("--Car Pool-------------------------");
    for (Driver d : trip.getAllCarpoolDrivers()) {
      System.out.println();
      System.out.println("Driver: " + d.getUserAccount().getName()[0] + " " + d.getUserAccount().getName()[1].substring(0,1) + ".");
      for (int i = 0; i < d.getPassengersList().size(); i++) {
        System.out.println("Rider#" + i + " : " + d.getPassengersList().get(i).getName()[0] + " " + d.getPassengersList().get(i).getName()[1].substring(0,1) + ".");
      }
      System.out.println("SeatsLeft: " + d.getNumOfSeatsLeft());
      System.out.println("BoardSpaceLeft: " + d.getNumOfBoardSpaceLeft());
    }
    System.out.println();
    System.out.println();
  }


  private void setColor(int[] col) { stroke(col[0], col[1], col[2]); fill(col[0], col[1], col[2]); }


  //------------------------------------------------------------

  /**
   * This method is executed repeatedly to draw onto the canvas at every frame.
   */
  public void draw() {
    background(255);
    float nonBrowserTopLeftY = BROWSER_H - scrollBar.getPagePosYOffset(); //top left corner

    Trip trip = m.getTrip(TRIP_0.getId());
    User user = m.getLoggedInUser();
    Form form = user.getAllFormCreatedTrips().get(trip);

    switch (m.getVisualState()) {
      case PRE_REGISTRATION:
        this.scrollBar.enable();
        drawTripDetailsPage(trip, false, NONBROWSER_TOPLEFT_X,nonBrowserTopLeftY, NONBROWSER_WIDTH, NONBROWSER_HEIGHT);
        break;

      case POPUP:case POPUP_EDIT_TEXTFIELD:
        this.scrollBar.disable();
        drawTripDetailsPage(trip, false, NONBROWSER_TOPLEFT_X, nonBrowserTopLeftY, NONBROWSER_WIDTH, NONBROWSER_HEIGHT);
        drawPopUpBg(trip, popUpCenterX, popUpCenterY, popUpW, popUpH, Colors.WHITE);
        drawRegistrationFormHeader();
        drawPopUpTextFieldBoxes(user, form);
        drawPopUpTransportationArrangements(form);
        drawPopUpRentalArrangements(form);
        drawSubmitButton(form);
        break;

      case POPUP_SUBMIT_SUCCESS:
        this.scrollBar.disable();
        drawTripDetailsPage(trip, true, NONBROWSER_TOPLEFT_X, nonBrowserTopLeftY, NONBROWSER_WIDTH, NONBROWSER_HEIGHT);
        drawPopUpBg(trip, popUpCenterX, popUpCenterY, popUpW, popUpH, Colors.ALMOSTWHITE);
        drawRegistrationFormHeader();
        drawSuccessMessage(trip);
        break;

      case POST_REGISTRATION:
        this.scrollBar.enable();
        drawTripDetailsPage(trip, true, NONBROWSER_TOPLEFT_X, nonBrowserTopLeftY, NONBROWSER_WIDTH, NONBROWSER_HEIGHT);
        break;

      case CANCEL_TRIP_POPUP:
        this.scrollBar.disable();
        drawTripDetailsPage(trip, true, NONBROWSER_TOPLEFT_X, nonBrowserTopLeftY, NONBROWSER_WIDTH, NONBROWSER_HEIGHT);
        drawPopUpBg(trip, popUpCenterX, popUpCenterY, this.cancelPopUpWH, this.cancelPopUpWH, Colors.WHITE);
        drawCancelMessage();
        break;

      default:
        break;
    }
    drawBrowser();
  }





  private void drawTripDetailsPage(Trip trip, boolean registered, float topLeftX, float topLeftY, float w, float h) {
    float col1X = CANVAS_W/42;
    float col2X = CANVAS_W/3;
    float col3X = (float)(CANVAS_W/1.54);
    float headerBgImgHeight = (13*(CANVAS_H/18));
    float subSectionTitleY = (float)(topLeftY+(h/1.7));
    float yFactor = CANVAS_H/12;

    //bg space
    setColor(Colors.WHITE); rectMode(CORNER);
    rect(topLeftX, topLeftY, w, h);

    //header bg image
    image(imgGoodHarborHeader, (topLeftX+w)/2, (topLeftY+BROWSER_H+(headerBgImgHeight/2)), w,  headerBgImgHeight);

    //draw header
    setColor(Colors.WHITE); textAlign(CORNER); textSize(TXTPT.xl);
    text(trip.getTripName(), col1X, (float)(topLeftY+(headerBgImgHeight-BROWSER_H*1.5)));
    textSize(TXTPT.s);
    text(" " + trip.getDatesInString(), col1X,(float)(topLeftY+(headerBgImgHeight-BROWSER_H)));
    text(trip.getStartEndTimeInString(), col1X, (float)(topLeftY+(headerBgImgHeight-BROWSER_H/1.5)));
    text(trip.getLocation(), col1X+col1X, (float)(topLeftY+(headerBgImgHeight+(BROWSER_H/2))));

    drawDayPlan(col1X, trip, subSectionTitleY, yFactor);
    drawRentalShop(col2X, trip, subSectionTitleY, yFactor) ;
    drawCarpool(col3X, trip, subSectionTitleY, yFactor);

    //calculate button dimensions
    this.btnRegisterTopLeftXYWH.set(0, (float)(topLeftX+(CANVAS_W/1.25)));
    this.btnRegisterTopLeftXYWH.set(1, (float)(topLeftY+(headerBgImgHeight)));
    this.btnEditFormTopLeftXYWH.set(0, (float)(topLeftX+(CANVAS_W/1.45)));
    this.btnEditFormTopLeftXYWH.set(1, (float)(topLeftY+(headerBgImgHeight)));
    this.btnCancelTopLeftXYWH.set(0, (float)(topLeftX+(CANVAS_W/1.2)));
    this.btnCancelTopLeftXYWH.set(1, (float)(topLeftY+(headerBgImgHeight)));

    //draw buttons
    if (!registered) {drawRegisterButton();} 
    else {drawEditFormAndCancelButtons();}

    //draw scrollbar
    scrollBar.drawScrollBarBg();
    scrollBar.drawScrollBar();
  }



  private void drawDayPlan(float col1X, Trip trip, float subSectionTitleY, float yFactor) {
    //draw Day Plan
    setColor(Colors.BLACK);
    textSize(TXTPT.m);
    text("Day Plan", col1X, subSectionTitleY);
    float timeX = col1X;
    float timeYfirst = (float)(subSectionTitleY + (yFactor/1.5));
    float titleX = col1X + CANVAS_H/8;
    float titleYfirst = timeYfirst;
    float subtitleX = titleX;
    float subtitleYfirst = titleYfirst + CANVAS_H/36;
    for (int i = 0; i < trip.getDayPlanNumOfEvents(); i++) {
      //graphics
      if (trip.getDayPlanEventString(i, 1).equals("Surf")) { setColor(Colors.TEAL_MED);}
      else { setColor(Colors.YELLOW); }
      circle(titleX-(CANVAS_W/90), timeYfirst-CANVAS_W/300 + (i*yFactor), TXTPT.xs);
      if (i != trip.getDayPlanNumOfEvents()-1) { 
        line(titleX-(CANVAS_W/90), timeYfirst+(i*yFactor)+CANVAS_W/140, titleX-(CANVAS_W/90), timeYfirst+(i*yFactor)+CANVAS_W/28);
      }
      //time
      setColor(Colors.GREY_MEDIUM);
      textSize(TXTPT.xs);
      text(trip.getDayPlanEventString(i, 0), timeX, timeYfirst + (i*yFactor));
      //title
      setColor(Colors.BLACK);
      textSize(TXTPT.s);
      text(trip.getDayPlanEventString(i, 1), titleX, titleYfirst + (i*yFactor));
      //subtitle
      setColor(Colors.BLACK);
      textSize(TXTPT.xs);
      text(trip.getDayPlanEventString(i, 2), subtitleX, subtitleYfirst + (i*yFactor));
    }
  }



  private void drawRentalShop(float col2X, Trip trip, float subSectionTitleY, float yFactor) {
    //draw Rental Shop
    setColor(Colors.BLACK); textSize(TXTPT.m);
    text("Rental Shop", col2X, subSectionTitleY);
    //draw surf shop image
    float surfShopImgY = (float)(subSectionTitleY + (yFactor/1.5));
    imageMode(CORNER);
    image(this.imgRentalShop, col2X, surfShopImgY); imageMode(CENTER);
    //draw the website link button
    this.btnRentalWebsiteTopLeftXYWH.set(0, (float)(col2X+(CANVAS_W/6.66)));
    this.btnRentalWebsiteTopLeftXYWH.set(1, (float)(surfShopImgY+(CANVAS_H/5.4)));
    drawButton("WEBSITE",
        this.btnRentalWebsiteTopLeftXYWH.get(0), this.btnRentalWebsiteTopLeftXYWH.get(1), 
        this.btnRentalWebsiteTopLeftXYWH.get(2), this.btnRentalWebsiteTopLeftXYWH.get(3),
        Colors.TEAL_DARK, Colors.TEAL_DARKHOVER, 
        Colors.WHITE, TXTPT.btnS, false); //-*
    //draw equipment 
    float lineY = surfShopImgY + (CANVAS_H/4); //bottom of image
    for (Map.Entry<RentalShop.Equipment, Map<String, Integer>> equipmentType : trip.getRentalShop().getRentalDealsMap().entrySet()) {
      lineY += (CANVAS_H/16);
      setColor(Colors.TEAL_DARK);
      textSize(TXTPT.s);
      text(equipmentType.getKey().toString(), col2X, lineY);
      for (Map.Entry<String, Integer> menuItem : equipmentType.getValue().entrySet()) {
        lineY += (CANVAS_H/24);
        setColor(Colors.BLACK);
        textSize(TXTPT.xs);
        String menuItemWithConnector = menuItem.getKey() + ".............................................................................."
            + "..............................................................................";
        text(menuItemWithConnector.substring(0, 64), col2X, lineY);
        text("$" + menuItem.getValue(), (float)(col2X+(CANVAS_H/2.6)), lineY);
      }
    }
  }

  private void drawCarpool(float col3X, Trip trip, float subSectionTitleY, float yFactor) {
    //draw Carpool
    setColor(Colors.BLACK); textSize(TXTPT.m); text("Carpool", col3X, subSectionTitleY);
    textSize(TXTPT.xs);
    float xFactor = (float)(CANVAS_W/6);
    float lineY = (float)(subSectionTitleY + (yFactor/1.5)); //bottom of image
    setColor(Colors.TEAL_DARK);
    text("Request a carpool when filling out the Trip Registration Form!", col3X, lineY);
    lineY += (float)(yFactor/1.5);
    List<Driver> drivers = m.getTrip(TRIP_0.getId()).getAllCarpoolDrivers();
    for (int i = 0; i < drivers.size(); i++) { //for all the drivers
      float carIconX = col3X + (i * xFactor);
      if (i%2 == 0) { carIconX = col3X; } //even
      String driverFirstName = drivers.get(i).getUserAccount().getName()[0];
      String driverLastName = drivers.get(i).getUserAccount().getName()[1];
      String driverShortName = driverFirstName + driverLastName.substring(0,1) + ".";
      String numOfSeatsLeft = Integer.toString(drivers.get(i).getNumOfSeatsLeft()) + " seat(s) left!";
      imageMode(CORNER); image(imgCarIcon, carIconX, lineY);
      textSize(TXTPT.xs); setColor(Colors.TEAL_DARKHOVER); 
      text("Driver: ", carIconX+(CANVAS_H/10), lineY);
      setColor(Colors.TEAL_MED); 
      text(numOfSeatsLeft, carIconX+(CANVAS_H/10), lineY+CANVAS_H/16);
      textSize(TXTPT.s); setColor(Colors.TEAL_DARK); 
      text(driverShortName, carIconX+(CANVAS_H/10), lineY+CANVAS_H/30);
      setColor(Colors.WHITE); 
      text("Car#" + i, (float)(carIconX + CANVAS_H/120), lineY+CANVAS_H/19);
      if (i%2 == 1) { lineY += (CANVAS_H/6); }  //odd 
    }
    this.btnRouteTopLeftXYWH.set(0, col3X);
    this.btnRouteTopLeftXYWH.set(1, lineY);
    if (mouseOnPosDim(this.btnRouteTopLeftXYWH)) {image(imgRouteHover, col3X, lineY, imgRoute.width, imgRoute.height);}
    else {image(imgRoute, col3X, lineY);}
    imageMode(CENTER);
  }

  private void drawSuccessMessage(Trip trip) {
    String line0 = "SUCCESS";
    String line1 = "Get ready to surf... " + trip.getTripName()+"!";
    String line2 = "You can edit your submission in your account profile";
    String line3 = "up until 24 hours before your trip.";
    textAlign(CENTER); textSize(TXTPT.xl); setColor(Colors.BLACK); 
    text(line0, CANVAS_W/2, (float)(CANVAS_H/2));
    textSize(TXTPT.l); setColor(Colors.TEAL_MED); 
    text(line1, CANVAS_W/2, (float)(CANVAS_H/2)+(TXTPT.l*2));
    textSize(TXTPT.xs); setColor(Colors.BLACK); 
    text(line2, CANVAS_W/2, (float)(CANVAS_H/2)+(CANVAS_H/6));
    text(line3, CANVAS_W/2, (float)(CANVAS_H/2)+(CANVAS_H/6)+(POPUP_BUFFER));
  }



  private void drawCancelMessage() {
    //draw text
    textAlign(CENTER); textSize(TXTPT.s); setColor(Colors.BLACK);
    text("Cancel this Trip?", popUpCenterX, this.btnCancelConfirmTopLeftXYWH.get(1)-(POPUP_BUFFER*2));
    //draw confirm button
    drawButton("CANCEL TRIP", 
        this.btnCancelConfirmTopLeftXYWH.get(0), this.btnCancelConfirmTopLeftXYWH.get(1), 
        this.btnCancelConfirmTopLeftXYWH.get(2), this.btnCancelConfirmTopLeftXYWH.get(3), 
        Colors.BTN_CANCELCONFIRM_RED, Colors.BTN_CANCELCONFIRM_REDHOVER, Colors.WHITE, TXTPT.btnM, 
        false);
    //draw nvm button
    drawButton("NEVERMIND", 
        this.btnCancelNvmTopLeftXYWH.get(0), this.btnCancelNvmTopLeftXYWH.get(1), 
        this.btnCancelNvmTopLeftXYWH.get(2), this.btnCancelNvmTopLeftXYWH.get(3),
        Colors.BTN_CANCELNVM_WHITE, Colors.BTN_CANCELNVM_WHITEHOVER, Colors.TEAL_DARK, TXTPT.btnM, 
        false);
  }




  private void drawPopUpTransportationArrangements(Form form) {
    //title
    setColor(Colors.TEAL_DARK); textSize(TXTPT.sm); textAlign(CORNER);
    text("Transportation Arrangements", POPUP_TOPLEFTX+POPUP_BUFFER, POPUP_TOPLEFTY+(this.popUpH/2)+POPUP_BUFFER);

    String o0 = "I'll be meeting everyone there";
    String o1 = "I can drive/give people a ride";
    String o2 = "I need a ride!";
    String o2sub = "Are you bringing your own board?";
    String yes = "Yes"; String no = "No";

    float optionTextX = (float)(POPUP_TOPLEFTX+(POPUP_BUFFER*2.5));
    //draw option text
    setColor(Colors.BLACK); textSize(TXTPT.s); textAlign(CORNER); 
    text(o0, optionTextX, radioTransport0Y);
    text(o1, optionTextX, radioTransport1Y);
    text(o2, optionTextX, radioTransport2Y);

    //draw radio button unfilled
    drawRadioButton(form.getTransportOption()==0, radioTransportXs, (float)(radioTransport0Y-(TXTPT.l/4)));
    drawRadioButton(form.getTransportOption()==1, radioTransportXs, (float)(radioTransport1Y-(TXTPT.l/4)));
    drawRadioButton(form.getTransportOption()==2, radioTransportXs, (float)(radioTransport2Y-(TXTPT.l/4)));

    if (form.getTransportOption()==1) { //TODO if i have time
      //paint over (in white) radio button 2
      //reposition radio button 2 (shift down)
      //setColor(Colors.BLACK); textSize(TEXTSIZE_XS); textAlign(CORNER); 
      //write subtext
      //draw radio button n1
    } 
    else if (form.getTransportOption()==2) {
      setColor(Colors.BLACK); textSize(TXTPT.xs); textAlign(CORNER); 
      text(o2sub, optionTextX, (float)(radioTransport2Y+(POPUP_BUFFER)));

      drawRadioButton(form.getBringingOwnBoard()==0, radioTransport2yesX, radioTransport2yesnoYs);
      drawRadioButton(form.getBringingOwnBoard()==1, radioTransport2noX, radioTransport2yesnoYs);

      setColor(Colors.BLACK); textSize(TXTPT.xs); textAlign(CORNER); 
      text(yes, (float)(radioTransport2yesX+POPUP_BUFFER-(TXTPT.xs/4)), radioTransport2yesnoYs+(TXTPT.l/4));
      text(no,  (float)(radioTransport2noX+POPUP_BUFFER-(TXTPT.xs/4)), radioTransport2yesnoYs+(TXTPT.l/4));
    }
  }


  private void drawPopUpRentalArrangements(Form form) {
    setColor(Colors.TEAL_DARK); textSize(TXTPT.sm); textAlign(CORNER);
    text("Rental Arrangements", this.txtFld3TopLeftXYWH.get(0), POPUP_TOPLEFTY+(this.popUpH/2)+POPUP_BUFFER);
    setColor(Colors.BLACK); textSize(TXTPT.s); textAlign(CORNER);
    text("Do you need to rent equipment?", this.txtFld3TopLeftXYWH.get(0), (float)(POPUP_TOPLEFTY+(this.popUpH/2)+(2.0*POPUP_BUFFER)+(TXTPT.l*0.25)));

    this.radioRentalYesX = this.txtFld3TopLeftXYWH.get(0)+(TXTPT.l/2);
    this.radioRentalNoX = this.radioRentalYesX+(3*POPUP_BUFFER);
    this.radioRentalYs = this.radioTransport1Y-(TXTPT.l/2);

    drawRadioButton(form.getRentalNeeded()==0, radioRentalYesX, radioRentalYs);
    drawRadioButton(form.getRentalNeeded()==1, radioRentalNoX, radioRentalYs);

    setColor(Colors.BLACK); textSize(TXTPT.s); textAlign(CORNER); 
    text("Yes", (float)(radioRentalYesX+POPUP_BUFFER-(TXTPT.xs/4)), radioRentalYs+(TXTPT.l/4));
    text("No",  (float)(radioRentalNoX+POPUP_BUFFER-(TXTPT.xs/4)), radioRentalYs+(TXTPT.l/4));

    if (form.getRentalNeeded()==0) { //"Yes"
      setColor(Colors.BLACK); textSize(TXTPT.xs); textAlign(CORNER); 
      this.checkBoxXs = (float)(radioRentalYesX+POPUP_BUFFER-(TXTPT.xs/4));
      float subQuestionY = (float)(radioRentalYs+(TXTPT.l/4)+(POPUP_BUFFER*1.2));
      text("What do you need?", checkBoxXs, subQuestionY);
      this.checkBoxWetsuitY = (float)(subQuestionY+(POPUP_BUFFER*0.5));
      this.checkBoxBoardY = (float)(subQuestionY+(POPUP_BUFFER*1.8));
      text("Wetsuit", checkBoxXs+POPUP_BUFFER+(CANVAS_W/320), checkBoxWetsuitY+(checkBoxWH/2)+(CANVAS_W/320));
      text("Board", checkBoxXs+POPUP_BUFFER+(CANVAS_W/320), checkBoxBoardY+(checkBoxWH/2)+(CANVAS_W/320));

      drawCheckBox(form.getRentalItem(0), checkBoxXs, checkBoxWetsuitY); //wetsuit
      drawCheckBox(form.getRentalItem(1), checkBoxXs, checkBoxBoardY); //board
    }
  }

  private void drawCheckBox(boolean selected, float topLeftX, float topLeftY) {
    stroke(color(Colors.TEAL_MED[0], Colors.TEAL_MED[1], Colors.TEAL_MED[2])); fill(255);
    //outer square
    rectMode(CORNER);
    rect(topLeftX, topLeftY, checkBoxWH, checkBoxWH, checkBoxWH/5);
    if (selected) {
      //draw inner square
      setColor(Colors.TEAL_MED);
      rect(topLeftX, topLeftY, checkBoxWH, checkBoxWH, checkBoxWH/5);
      //draw checkmark
      setColor(Colors.WHITE);
      strokeWeight(2);
      line(topLeftX+(CANVAS_W/320), topLeftY+(CANVAS_W/128), topLeftX+(CANVAS_W/160), (float)(topLeftY+(CANVAS_H/47)));
      line(topLeftX+(CANVAS_W/160), (float)(topLeftY+(CANVAS_H/47)), topLeftX+(CANVAS_W/80), topLeftY+(CANVAS_H/120));
    }
  }

  private void drawRadioButton(boolean selected, float centerX, float centerY) {
    //outer circle
    stroke(color(Colors.TEAL_MED[0], Colors.TEAL_MED[1], Colors.TEAL_MED[2]));
    fill(255);
    circle(centerX, centerY, TXTPT.l);
    if (selected) {
      //inner circle
      setColor(Colors.TEAL_MED);
      circle(centerX, centerY, (float)(TXTPT.l*0.5));
    }
  }

  private void drawPopUpTextFieldBoxes(User u, Form form) {
    String caption0 = "Phone*";
    String caption1 = "Email*";
    String caption23a = "Pick-up Address";
    String caption23b = "(this is only visible to the driver upon requesting a carpool)";
    rectMode(CORNER);

    int activeTxtFld = form.getActiveTextField();

    //captions
    setColor(Colors.BLACK);
    textAlign(CORNER);
    textSize(TXTPT.xs);
    text(caption0, this.txtFld0TopLeftXYWH.get(0)+(POPUP_BUFFER/4), this.txtFld0TopLeftXYWH.get(1)-(POPUP_BUFFER/3));
    text(caption1, this.txtFld1TopLeftXYWH.get(0)+(POPUP_BUFFER/4), this.txtFld1TopLeftXYWH.get(1)-(POPUP_BUFFER/3));
    text(caption23a, this.txtFld2TopLeftXYWH.get(0)+(POPUP_BUFFER/4), this.txtFld2TopLeftXYWH.get(1)-(POPUP_BUFFER/3));
    setColor(Colors.GREY_LIGHT);
    text(caption23b, (float)(this.txtFld2TopLeftXYWH.get(0)+(POPUP_BUFFER/4)+(CANVAS_H/7.2)), this.txtFld2TopLeftXYWH.get(1)-(POPUP_BUFFER/3));

    //disabled boxes
    setColor(Colors.TEXTFIELD_BG_DISABLED);
    rect(this.txtFld0TopLeftXYWH.get(0), this.txtFld0TopLeftXYWH.get(1), this.txtFld0TopLeftXYWH.get(2), this.txtFld0TopLeftXYWH.get(3), this.txtFld0TopLeftXYWH.get(3)/4);
    rect(this.txtFld1TopLeftXYWH.get(0), this.txtFld1TopLeftXYWH.get(1), this.txtFld1TopLeftXYWH.get(2), this.txtFld1TopLeftXYWH.get(3), this.txtFld1TopLeftXYWH.get(3)/4);
    rect(this.txtFld2TopLeftXYWH.get(0), this.txtFld2TopLeftXYWH.get(1), this.txtFld2TopLeftXYWH.get(2), this.txtFld2TopLeftXYWH.get(3), this.txtFld2TopLeftXYWH.get(3)/4);
    rect(this.txtFld3TopLeftXYWH.get(0), this.txtFld3TopLeftXYWH.get(1), this.txtFld3TopLeftXYWH.get(2), this.txtFld3TopLeftXYWH.get(3), this.txtFld3TopLeftXYWH.get(3)/4);

    //draw the enabled box
    strokeWeight(2);
    stroke(color(Colors.TEXTFIELD_STROKE_ENABLED[0],Colors.TEXTFIELD_STROKE_ENABLED[0],Colors.TEXTFIELD_STROKE_ENABLED[0]));
    fill(color(Colors.TEXTFIELD_BG_ENABLED[0],Colors.TEXTFIELD_BG_ENABLED[0],Colors.TEXTFIELD_BG_ENABLED[0]));
    if (activeTxtFld != -1) {
      switch (activeTxtFld) {
        case 0:
          rect(this.txtFld0TopLeftXYWH.get(0), this.txtFld0TopLeftXYWH.get(1), this.txtFld0TopLeftXYWH.get(2), this.txtFld0TopLeftXYWH.get(3), this.txtFld0TopLeftXYWH.get(3)/4);
          break;
        case 1:
          rect(this.txtFld1TopLeftXYWH.get(0), this.txtFld1TopLeftXYWH.get(1), this.txtFld1TopLeftXYWH.get(2), this.txtFld1TopLeftXYWH.get(3), this.txtFld1TopLeftXYWH.get(3)/4);
          break;
        case 2:
          rect(this.txtFld2TopLeftXYWH.get(0), this.txtFld2TopLeftXYWH.get(1), this.txtFld2TopLeftXYWH.get(2), this.txtFld2TopLeftXYWH.get(3), this.txtFld2TopLeftXYWH.get(3)/4);
          break;
        case 3:
          rect(this.txtFld3TopLeftXYWH.get(0), this.txtFld3TopLeftXYWH.get(1), this.txtFld3TopLeftXYWH.get(2), this.txtFld3TopLeftXYWH.get(3), this.txtFld3TopLeftXYWH.get(3)/4);
          break;
        default:
          System.out.println("invalid active text-field index.");
          break;
      }
    }
    drawTextInBoxes(form);
  }

  private void drawTextInBoxes(Form form) {
    textAlign(CORNER);
    textSize(TXTPT.sm);

    List<String> texts = new ArrayList<>();
    texts.add(form.getPhoneString());
    texts.add(form.getEmail());
    texts.add(form.getAddressSt());
    texts.add(form.getAddressZipcode());

    float textBufferX = POPUP_BUFFER/2;
    float textBufferY = (float)(POPUP_BUFFER*1.4);

    float[] xArray = new float[]{
        txtFld0TopLeftXYWH.get(0)+textBufferX,
        txtFld1TopLeftXYWH.get(0)+textBufferX,
        txtFld2TopLeftXYWH.get(0)+textBufferX,
        txtFld3TopLeftXYWH.get(0)+textBufferX};
    float[] yArray = new float[] {
        (float)(txtFld0TopLeftXYWH.get(1)+textBufferY),
        (float)(txtFld1TopLeftXYWH.get(1)+textBufferY),
        (float)(txtFld2TopLeftXYWH.get(1)+textBufferY),
        (float)(txtFld3TopLeftXYWH.get(1)+textBufferY)};

    for (int i = 0; i < 4; i++) {
      if (form.getActiveTextField() == i) {setColor(Colors.GREY_DARK);}
      else {setColor(Colors.TEXTFIELD_STROKE_ENABLED);}
      text(texts.get(i), xArray[i], yArray[i]);
    }
  }

  private void drawPopUpBg(Trip trip, float centerX, float centerY, float w, float h, int[] color) {
    //overlay
    rectMode(CENTER);
    stroke(0, 200); fill(0, 200);
    rect(CANVAS_W/2, CANVAS_H/2, CANVAS_W, CANVAS_H);
    //popup white bg
    setColor(color);
    rect(centerX, centerY, w, h);
  } 


  private void drawRegistrationFormHeader() {
    textAlign(CORNER);textSize(TXTPT.l);setColor(Colors.TEAL_MED);
    text(m.getLoggedInUser().getName()[0]+"’s Trip Registration Form", POPUP_TOPLEFTX+POPUP_BUFFER, (float)(POPUP_TOPLEFTY+POPUP_BUFFER*2.3));
    drawBtnPopUpClose();
  }


  private void drawBtnPopUpClose() {
    if (mouseOnPosDim(this.btnPopUpCloseTopLeftXYWH)) {
      setColor(Colors.WHITE);
    } 
    else { setColor(Colors.ALMOSTWHITE); }
    strokeWeight(CANVAS_H/240);
    rectMode(CORNER);
    rect(this.btnPopUpCloseTopLeftXYWH.get(0), this.btnPopUpCloseTopLeftXYWH.get(1), 
        this.btnPopUpCloseTopLeftXYWH.get(2), this.btnPopUpCloseTopLeftXYWH.get(3));
    setColor(Colors.BLACK);
    line(this.btnPopUpCloseTopLeftXYWH.get(0), this.btnPopUpCloseTopLeftXYWH.get(1), 
        this.btnPopUpCloseTopLeftXYWH.get(0)+ this.btnPopUpCloseTopLeftXYWH.get(2), this.btnPopUpCloseTopLeftXYWH.get(1)+this.btnPopUpCloseTopLeftXYWH.get(3));
    line(this.btnPopUpCloseTopLeftXYWH.get(0), this.btnPopUpCloseTopLeftXYWH.get(1)+this.btnPopUpCloseTopLeftXYWH.get(3), 
        this.btnPopUpCloseTopLeftXYWH.get(0)+this.btnPopUpCloseTopLeftXYWH.get(2), this.btnPopUpCloseTopLeftXYWH.get(1));
    rectMode(CENTER);
  }

  private void drawBrowser() {
    float w = CANVAS_W;
    float h = BROWSER_H;
    float x = w/2; //middle
    float y = h/2; //middle
    image(imgChromeBar, x,y,w,h);
  }


  private void drawButton(String buttonTEXT, float topLeftX, float topLeftY, float width, float height,
      int[]rgb, int[] hoverRgb, int[] textRgb, float buttonTextSize,
      boolean disableCondition) {
    rectMode(CORNER);
    if (topLeftX <= mouseX && mouseX <= (topLeftX + width) && topLeftY <= mouseY && mouseY <= (topLeftY + height)) { 
      this.setColor(hoverRgb);
    }
    else {
      this.setColor(rgb);
    }
    if (disableCondition) {
      this.setColor(Colors.GREY_LIGHT);
    }
    rect(topLeftX, topLeftY, width, height, height/4);
    //text
    this.setColor(textRgb);
    textAlign(CENTER);
    textSize(buttonTextSize);
    text(buttonTEXT, topLeftX+(width/2), topLeftY+(height/2)+(height/10));
    textAlign(CORNER);
  }


  private void drawRegisterButton() {
    rectMode(CORNER);
    drawButton("REGISTER", 
        this.btnRegisterTopLeftXYWH.get(0),  this.btnRegisterTopLeftXYWH.get(1), 
        this.btnRegisterTopLeftXYWH.get(2),  this.btnRegisterTopLeftXYWH.get(3),
        Colors.TEAL_MED, Colors.TEAL_MEDHOVER, Colors.WHITE, TXTPT.btnM, 
        m.getVisualState()==VisualState.POPUP || m.getVisualState()==VisualState.POPUP_EDIT_TEXTFIELD || m.getVisualState()==VisualState.POPUP_SUBMIT_SUCCESS);
    rectMode(CENTER);
  }


  private void drawEditFormAndCancelButtons() {
    rectMode(CORNER);
    drawButton("EDIT FORM", 
        this.btnEditFormTopLeftXYWH.get(0),  this.btnEditFormTopLeftXYWH.get(1), 
        this.btnEditFormTopLeftXYWH.get(2),  this.btnEditFormTopLeftXYWH.get(3),
        Colors.ALMOSTWHITE, Colors.WHITE, Colors.TEAL_DARK, TXTPT.btnM,
        m.getVisualState()==VisualState.POPUP || m.getVisualState()==VisualState.POPUP_EDIT_TEXTFIELD || m.getVisualState()==VisualState.POPUP_SUBMIT_SUCCESS);
    drawButton("CANCEL", 
        this.btnCancelTopLeftXYWH.get(0),  this.btnCancelTopLeftXYWH.get(1), 
        this.btnCancelTopLeftXYWH.get(2),  this.btnCancelTopLeftXYWH.get(3),
        Colors.ALMOSTWHITE, Colors.WHITE, Colors.RED, TXTPT.btnM,
        m.getVisualState()==VisualState.POPUP || m.getVisualState()==VisualState.POPUP_EDIT_TEXTFIELD || m.getVisualState()==VisualState.POPUP_SUBMIT_SUCCESS);
    rectMode(CORNER);
  }

  private void drawSubmitButton(Form form) {
    drawButton("SUBMIT", 
        this.btnSubmitTopLeftXYWH.get(0), this.btnSubmitTopLeftXYWH.get(1),
        this.btnSubmitTopLeftXYWH.get(2), this.btnSubmitTopLeftXYWH.get(3),
        Colors.TEAL_DARK, Colors.TEAL_DARKHOVER, Colors.WHITE, TXTPT.btnM, !form.checkIfValidSubmitState());
  }




  //----------------------------------------------------------------------------
  //typing----------------------------------------------------------------------
  //----------------------------------------------------------------------------

  public void keyPressed() {
    //scrollBar.keyPressed(); <-- uncomment to print scrollbar position information (debugging)
    Form form = m.getLoggedInUser().getAllFormCreatedTrips().get(m.getTrip(TRIP_0.getId()));
    List<String> originals = new ArrayList<>();
    originals.add(form.getPhoneString());
    originals.add(form.getEmail());
    originals.add(form.getAddressSt());
    originals.add(form.getAddressZipcode());

    Map<Integer, String> newlyTyped = new HashMap<>();

    if (m.getVisualState() == VisualState.POPUP_EDIT_TEXTFIELD) {
      for (int i = 0; i < 4; i++) {

        if (form.getActiveTextField() == i) { 
          //for the box that is enabled...

          String orig = originals.get(i);

          if (orig != "" && key == BACKSPACE) { //backspace functionality (except when a name box has nothing in it)
            if (i == 0) { form.removeLastDigitOfPhone(); }
            else {
              String newString = orig.substring(0, orig.length()-1);
              newlyTyped.put(i, newString);
            }
          }
          else if (key == '\n') {
            //disabling enter key (do nothing
          }
          else { //adding typed key to the original string in the name box
            if (i == 0) { //if phone
              try {
                int digit = Integer.parseInt(Character.toString(key));
                form.addLastDigitOfPhone(digit);
              } 
              catch (NumberFormatException nfe) { System.out.println("only numbers allowed."); }
            }
            else {
              newlyTyped.put(i, orig + key);
            }
          }
          switch(i) {
            case 1: //typing in email
              form.changeEmail(newlyTyped.get(i));
              break;
            case 2:
              form.changeAddressSt(newlyTyped.get(i));
              break;
            case 3:
              form.changeAddressZipcode(newlyTyped.get(i));
              break;
            default:
              break;
          }
        }
      }
    }
  }


  //----------------------------------------------------------------------------
  //mouseClicked Button GUI-----------------------------------------------------
  //----------------------------------------------------------------------------

  public void mouseClicked() {
    Trip trip = m.getTrip(TRIP_0.getId());
    Form form = m.getLoggedInUser().getAllFormCreatedTrips().get(trip);

    switch (m.getVisualState()) {
      case PRE_REGISTRATION:
        registerButtonListener();
        listenRentalShopWebsite();
        routeImageListener(trip);
        break;

      case POPUP:
        editTextFieldButtonListener(form);
        closePopupButtonListener(form);
        transportRadioButtonListener(form);
        rentalRadioButtonListener(form);
        submitButtonListener(form);
        break;

      case POPUP_EDIT_TEXTFIELD:
        editTextFieldButtonListener(form);
        disableEditTextFieldListener(form);
        submitButtonListener(form);
        closePopupButtonListener(form);
        break;


      case POPUP_SUBMIT_SUCCESS:
        closePopupButtonListener(form);
        break;

      case POST_REGISTRATION:
        editFormButtonListener();
        cancelTripButtonListener();
        listenRentalShopWebsite();
        routeImageListener(trip);
        break;

      case CANCEL_TRIP_POPUP:
        confirmCancelTripButtonListener();
        nvmCancelTripButtonListener();
        break;

      default:
        System.out.println(m.getVisualState().toString());
    }
  }


  private void routeImageListener(Trip trip) {
    if (mouseOnPosDim(this.btnRouteTopLeftXYWH)) {
      String link = trip.getRentalShop().getRouteLink();
      link(link);
    }
  }

  private void listenRentalShopWebsite() {
    if (mouseOnPosDim(this.btnRentalWebsiteTopLeftXYWH)) {
      String link = m.getTrip(TRIP_0.getId()).getRentalShop().getWebsiteLink();
      link(link);
    }
  }

  private boolean mouseOnPosDim(List<Float> xywh) {
    return (xywh.get(0) <= mouseX && mouseX <= xywh.get(0)+xywh.get(2)
    && xywh.get(1) <= mouseY && mouseY <= xywh.get(1)+xywh.get(3));
  }

  private void submitButtonListener(Form form) {
    if (mouseOnPosDim(this.btnSubmitTopLeftXYWH)) {
      if (form.checkIfValidSubmitState()) {
        m.getLoggedInUser().submitForm(m.getTrip(TRIP_0.getId()));
        m.setVisualState(VisualState.POPUP_SUBMIT_SUCCESS);
      }
    }
  }

  private void closePopupButtonListener(Form form) {
    if (mouseOnPosDim(this.btnPopUpCloseTopLeftXYWH)) {
      //if it has not been submitted but closed, then reset the form
      if (!form.isSubmitted()) {
        TRIP_0.removeMemberFromEverything(USER);
        m.setVisualState(VisualState.PRE_REGISTRATION);
      } 
      else { //if it has been submitted, and popup is closed
        m.setVisualState(VisualState.POST_REGISTRATION);
      }
    }
  }


  private void rentalRadioButtonListener(Form form) {
    float btnRadius = TXTPT.l/2;
    if (this.radioRentalYs-(btnRadius) <= mouseY && mouseY <= this.radioRentalYs+(btnRadius)) {
      if (this.radioRentalYesX-(btnRadius) <= mouseX && mouseX <= this.radioRentalYesX+(btnRadius)) {
        form.setRentalNeeded(0);
      }
      else if (this.radioRentalNoX-(btnRadius) <= mouseX && mouseX <= this.radioRentalNoX+(btnRadius)) {
        form.setRentalNeeded(1);
      }
    }
    if (form.getRentalNeeded()==0) {rentalEquipmentCheckListener(form);}
    else if (form.getRentalNeeded()==1) {form.resetRentalItems();}
  }


  private void rentalEquipmentCheckListener(Form form) {
    if (form.getRentalNeeded() == 0) {
      if (checkBoxXs <= mouseX && mouseX <= checkBoxXs+checkBoxWH) {
        if (checkBoxWetsuitY <= mouseY && mouseY <= checkBoxWetsuitY+checkBoxWH) {//IF they click on wetsuit
          form.toggleRentalItem(0);
        }
        else if (checkBoxBoardY <= mouseY && mouseY <= checkBoxBoardY+checkBoxWH) {//IF they click on board
          form.toggleRentalItem(1);
        }
      }
    }
  }

  private void transportRadioButtonListener(Form form) {
    List<Float> buttonYs = new ArrayList<>(Arrays.asList(this.radioTransport0Y, this.radioTransport1Y, this.radioTransport2Y));
    float btnRadius = TXTPT.l/2;
    //if mouse clicks on radioButton
    for (int i = 0; i < 3; i++) {
      if ((radioTransportXs-(btnRadius) <= mouseX && mouseX <= radioTransportXs+(btnRadius))
          && buttonYs.get(i)-(btnRadius) <= mouseY && mouseY <= buttonYs.get(i)+(btnRadius)){
        form.setBringingOwnBoard(-1);
        form.setTransportOption(i);
      }
    }
    if (form.getTransportOption()==2) { //"I need a ride"
      //Are you bringing your own board board?
      if (this.radioTransport2yesnoYs-(btnRadius) <= mouseY && mouseY <= this.radioTransport2yesnoYs+(btnRadius)) {
        if (this.radioTransport2yesX-(btnRadius) <= mouseX && mouseX <= this.radioTransport2yesX+(btnRadius)) {
          form.setBringingOwnBoard(0);
        }
        else if (this.radioTransport2noX-(btnRadius) <= mouseX && mouseX <= this.radioTransport2noX+(btnRadius)) {
          form.setBringingOwnBoard(1);
        }
      }
    }
  }

  private void registerButtonListener() {
    if (mouseOnPosDim(this.btnRegisterTopLeftXYWH)) {
      USER.newFormCreatedTrip(TRIP_0);
      m.setVisualState(VisualState.POPUP);
    }
  }


  private void editTextFieldButtonListener(Form form) {
    if (mouseOnWhichTextField() != -1) {m.setVisualState(VisualState.POPUP_EDIT_TEXTFIELD);}
    form.activateTextBox(this.mouseOnWhichTextField());
  }


  private void disableEditTextFieldListener(Form form) {
    if (mouseOnWhichTextField() == -1) {
      form.disableAllTextBoxes();
      m.setVisualState(VisualState.POPUP);
    }
  }

  private int mouseOnWhichTextField() {
    Map<Integer, List<Float>> textFieldPosSize = new HashMap<>();
    textFieldPosSize.put(0, this.txtFld0TopLeftXYWH);
    textFieldPosSize.put(1, this.txtFld1TopLeftXYWH);
    textFieldPosSize.put(2, this.txtFld2TopLeftXYWH);
    textFieldPosSize.put(3, this.txtFld3TopLeftXYWH);

    for (int i = 0; i < 4; i++) { //for each text field
      float iX = textFieldPosSize.get(i).get(0);
      float iY = textFieldPosSize.get(i).get(1);
      float iW = textFieldPosSize.get(i).get(2);
      float iH = textFieldPosSize.get(i).get(3);
      if (iX <= mouseX && mouseX <= iX+iW && iY <= mouseY && mouseY <= iY+iH) { //mouse click lands
        return i;
      }
    }
    return -1;
  }


  private void editFormButtonListener() {
    if (mouseOnPosDim(this.btnEditFormTopLeftXYWH)) {
      m.setVisualState(VisualState.POPUP);
    }
  }

  private void cancelTripButtonListener() {
    if (mouseOnPosDim(this.btnCancelTopLeftXYWH)) {
      m.setVisualState(VisualState.CANCEL_TRIP_POPUP);
    }
  }

  private void confirmCancelTripButtonListener() {
    if (mouseOnPosDim(this.btnCancelConfirmTopLeftXYWH)) {
      TRIP_0.removeMemberFromEverything(USER);
      USER.removeFormCreatedTrips(TRIP_0);
      m.setVisualState(VisualState.PRE_REGISTRATION);
    }
  }

  private void nvmCancelTripButtonListener() {
    if (mouseOnPosDim(this.btnCancelNvmTopLeftXYWH)) {
      m.setVisualState(VisualState.POST_REGISTRATION);
    }
  }





  //----------------------------------------------------------------------------
  //ScrollBar-------------------------------------------------------------------
  //----------------------------------------------------------------------------

  public void mousePressed() { scrollBar.mousePressed(); }

  public void mouseDragged() { scrollBar.mouseDragged(); }

  public void mouseReleased() { scrollBar.mouseReleased(); }

  /*
   * public static final int[] COL_NORM = Colors.SCROLLBAR_GREY_LIGHT;
   * public static final int[] COL_HOVER = Colors.SCROLLBAR_GREY_MEDIUM;
   * public static final int[] COL_CLICK = Colors.SCROLLBAR_GREY_DARK;
   */

  class ScrollBar { //with reference to Processing scrollbar example
    private float bx, by; //middle x and y of bar
    private float bw, bh;
    private boolean overBox;
    private boolean locked;
    private float yOffset;
    private float upperLimit, lowerLimit;
    private boolean active;

    public ScrollBar(float canvasH, float browserH) {
      this.bw = (CANVAS_W/160);
      this.bh = (CANVAS_H/2);
      this.overBox = false;
      this.locked = false;
      this.yOffset = (float) 0.0; 
      this.upperLimit = ((canvasH/2)/2) + browserH;
      this.lowerLimit = canvasH-(canvasH/4);
      this.setupScroll();
      this.active = true;
    }

    public void setupScroll() {
      bx = (float) (CANVAS_W - ((bw/2.0)*2));
      by = (float) this.upperLimit; 
      rectMode(CENTER);
      setColor(COL_NORM);
    }

    public void enable() {this.active = true;}
    public void disable() {this.active = false;}    


    public void drawScrollBar() {
      rectMode(CENTER);
      setColor(COL_NORM);
      // Test if the cursor is over the box 
      if (mouseX > bx-bw && mouseX < bx+bw && mouseY > by-bh && mouseY < by+bh) { //if cursor is over the bar
        overBox = true;
        if(!locked) { 
          setColor(COL_HOVER);
        } //if mouse isnt pressing
        else {
          setColor(COL_CLICK);
        }
      } 
      else { //cursor not over the box
        overBox = false;
        if (locked) {setColor(COL_CLICK);}
      }
      // Draw the bar
      rect(bx, by, bw, bh);
      rectMode(CORNER);
    }


    public void drawScrollBarBg() {
      rectMode(CENTER);
      setColor(Colors.SCROLLBAR_BG_GREY);
      rect(bx, BROWSER_H+((CANVAS_H-BROWSER_H)/2), 
          (float)(bw*2), (CANVAS_H-BROWSER_H));
      rectMode(CORNER);
    }


    public void mousePressed() {
      if (active) {
        setColor(COL_CLICK);
        if(overBox) { 
          locked = true; 
        } else {
          locked = false;
        }
        yOffset = mouseY-by;
      }
    }

    public void mouseDragged() {
      if (active) {
        if(locked) {
          by = (mouseY-yOffset);
          if (by < this.upperLimit) { 
            by = this.upperLimit;
          }
          else if (by > this.lowerLimit) {
            by = this.lowerLimit;
          }
        }
      }
    }

    public void mouseReleased() {
      if (active) {
        setColor(COL_NORM);
        locked = false;
      }
    }

    public void keyPressed() {
      if (key == 's') {
        System.out.println("x: " + bx);
        System.out.println("y: " + by);
        System.out.println("w: " + bw);
        System.out.println("h: " + bh);
        System.out.println("yOffset: " + yOffset);
      }
    }

    public float getPagePosYOffset() {
      //this.by --> from 180 to 540 
      //        --> from   0 to 360
      float currentBarYpos = this.by - (bh/2); //top-middle point
      float perScrollPixel = CANVAS_H/2;
      float heightOfPageBox = CANVAS_H-BROWSER_H;
      float heightOfContent = NONBROWSER_HEIGHT;
      float heightOfNotshowing = heightOfContent-heightOfPageBox;
      float incrementPerScrollPixel = heightOfNotshowing/perScrollPixel;
      //every time the currentBarYpos moves downwards by one pixel,
      //the actual page's y position should go upwards by 1.22
      return currentBarYpos*incrementPerScrollPixel; //would start at 0,
    }
  }


  //----------------------------------------------------------------------------
  //MAIN------------------------------------------------------------------------
  //----------------------------------------------------------------------------
  // Driver code
  public static void main(String[] args) {
    PApplet.main(new String[] {"--present", "main.Processing"});
  }
}
