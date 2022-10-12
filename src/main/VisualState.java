package main;

/**
 * Represents the various visual states that the Processing model can be in.
 * @author stefanieim
 */
public enum VisualState {
  PRE_REGISTRATION,      //Trip details page prior to submitting a form (Contains a REGISTER button)
  POPUP,                 //Registration form pop-up
  POPUP_EDIT_TEXTFIELD,  //Registration form pop-up (Text-field editing mode)
  POPUP_SUBMIT_SUCCESS,  //The success message for the registration form pop-up
  POST_REGISTRATION,     //Trip details page after submitting a form (Contains EDIT FORM and CANCEL buttons)
  CANCEL_TRIP_POPUP      //"Are you sure?" message for CANCEL button (cancelling a trip)
}
