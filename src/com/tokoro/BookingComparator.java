package com.tokoro;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Teomo
 * Date: 15/10/13
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class BookingComparator implements Comparator<Booking>{


    @Override
    public int compare(Booking booking1, Booking booking2){

        if(booking1.getMeetingDate().before(booking2.getMeetingDate())){

            return -1;
        }
        else if (  (booking1.getMeetingDate().equals(booking2.getMeetingDate())) ){

             if(booking1.getBookingStartTime().compareTo(booking2.getBookingStartTime()) < 1)  {
                 return -1;
             }
             else if((booking1.getMeetingDate().equals(booking2.getMeetingDate())) && (booking1.getBookingStartTime().equals(booking2.getBookingStartTime())) ){
                 if (booking1.getDateBookedByEmployee().before(booking2.getDateBookedByEmployee())){
                     return -1;
                 }
        }


        }
        else return 1;

        return 1;
    }


}
