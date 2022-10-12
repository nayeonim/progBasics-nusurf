import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import model.Driver;
import model.Trip;
import model.Trip.Carpool;
import model.Trip.DayPlan;
import model.User;

public class tripTest {
  Trip trip;
  User me;
  User r1;
  User r2;

  @Before
  public void setUp() throws Exception {
    /*
    trip = new Trip(1012, "Good Harbor", 06,19,2022, 
        10,20, "Gloucester, MA", "gmaps.com", "imgUrl");

    me = new User(9999, "Stefanie", "Im", new int[]{9,2,9,3,1,9,5,3,9,8}, "im.n@northeastern.edu");
    r1 = new User(1111, "Gerard", "Merhi", new int[]{8,1,0,2,3,2,0,0,8,9}, "merhi.g@northeastern.edu");
    r2 = new User(2342, "Sam", "Johnson", new int[]{2,3,4,5,6,7,4,3,4,2}, "sammi@gmail.com");
    */
  }

  @Test
  public void test() {
    /*
    
    assertEquals("10 a.m. -- 8 p.m.", trip.getStartEndTimeInString());
    assertEquals("Jun 19, 2022", trip.getDatesInString());

    trip.addMember(me);
    trip.addDriver(me, 3, 2);
    trip.addRentalMember(me, new ArrayList<Boolean>(Arrays.asList(true, true)));

    trip.addMember(r1);
    trip.requestCarpool(r1);
    trip.autoAssignCarpoolRider();

    //check that adding auto-assign works (r1 has been added)
    Driver driver0 = trip.getAllCarpoolDrivers().get(0);
    User driver0passenger0 = driver0.getPassengersList().get(0);
    assertEquals(r1, driver0passenger0);

    trip.addMember(r2);
    trip.requestCarpool(r2);
    trip.autoAssignCarpoolRider();

    //check that adding auto-assign works (r1 has been added)=
    User driver0passenger1 = driver0.getPassengersList().get(1);
    assertEquals(r2, driver0passenger1);



    assertEquals(2, trip.getAllCarpoolRiders().size());
    assertEquals(1, me.getRegisteredTripsList().size());
    assertEquals(trip, me.getRegisteredTripsList().get(0));
    //doesn't remove nonexistent driver
    assertEquals(1, trip.getAllCarpoolDrivers().size());
    trip.removeDriver(r2);
    assertEquals(1, trip.getAllCarpoolDrivers().size());

    //i cant drive anymore
    trip.removeDriver(me);
    assertEquals(0, trip.getAllCarpoolDrivers().size());

    //removing driver also removes all riders
    assertEquals(0, trip.getAllCarpoolRiders().size());
    assertEquals(1, r1.getRegisteredTripsList().size());
    assertEquals(trip, r1.getRegisteredTripsList().get(0));
    trip.removeMemberFromEverything(r1);
    assertEquals(0, r1.getRegisteredTripsList().size());

  }*/

}
}
