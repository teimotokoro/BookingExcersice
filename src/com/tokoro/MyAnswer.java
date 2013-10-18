package com.tokoro;

/**
 * Created with IntelliJ IDEA.
 * User: Teomo
 * Date: 15/10/13
 * Time: 08:32
 * To change this template use File | Settings | File Templates.
 *
 *
 */

import java.text.DateFormat;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyAnswer {


    public static void main(String[] args){

        List<Booking> myBookingList;

        try{
            //1. Read data in from the file - use java nio
            List<String> lines = getMeetingData();

            // pull out the open and close times.
            String[] openCloseTimes = lines.get(0).split(" ");
            String openTime=openCloseTimes[0];
            String closeTime = openCloseTimes[1];

            // set up date format
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd") ;
            formatter.setLenient(false);

            // At this point we are trusting the format of the file - TODO: add validation later.
            // Initialize vars used in creating th List
            Date tempBookingDate = null;
            String tempBookingTime ="";
            String tempBookingEmp = "";
            Date tempMeetingDate =null;
            String tempMeetingTime ="";
            String tempMeetingDuration ="";
            boolean written;

            // declare a List to hold the list of bookings
             myBookingList = new ArrayList<Booking>();

            for (int i=1; i <= lines.size()-1;i++){
                if(i%2!=0){
                    //System.out.println("odd" + lines.get(i));

                    String[] myOddData = lines.get(i).split(" ");
                    tempBookingDate= formatter.parse(myOddData[0]) ;
                    tempBookingTime= myOddData[1];
                    tempBookingEmp=myOddData[2];
                    written = false;
                }
                else{
                   // System.out.println("even" + lines.get(i));
                    String[] myEvenData = lines.get(i).split(" ");
                    tempMeetingDate = formatter.parse(myEvenData[0]) ;
                    tempMeetingTime =  myEvenData[1];
                    tempMeetingDuration= myEvenData[2];
                    written = true;
                }
                if(written){
                    Booking myBooking = new Booking();

                    myBooking.setMeetingDate(tempMeetingDate);                                  //The date of the meeting
                    myBooking.setMeetingDuration(Integer.parseInt(tempMeetingDuration));        // Duration of the meeting
                    myBooking.setDateBookedByEmployee(tempBookingDate);                         //  The date the employee booked the meeting
                    myBooking.setBookingStartTime(tempMeetingTime);                             // The time that the meeting starts
                    myBooking.setEmployee(tempBookingEmp);                                      //The Employee who booked the meeting
                    myBooking.setBookingEndTime(calculateBookingEndTime(tempMeetingTime, tempMeetingDuration));
                    myBookingList.add(myBooking) ;

                    written = false;
                }
            }
            //2. Now I have my list of bookings  - sort to the correct order
            Collections.sort(myBookingList, new BookingComparator());
            // debug - print the sorted list
            //listMyBookings(myBookingList);

           // pass the list to a method that applies selection rules and discards unwanted items.
            myBookingList =  filterBookingList(myBookingList, openTime, closeTime);
            // debug print the filtered list
            //listMyBookings(myBookingList);
            //Reformat for final output
            List<String> myOutputList = reformatOutput(myBookingList);
            //Print final output to console
            for (String outputLine : myOutputList){
                System.out.println(outputLine);
            }


        }   catch (IOException | ParseException |NullPointerException e){
            e.printStackTrace();
        }
    }

    private static List<String> reformatOutput(List<Booking> myBookingList) {

        // take the list and reformat to a calendar
        String storedDate = "";
        List<String> reformatOutput = new ArrayList<>();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");

         for (Booking booking : myBookingList){

           if (!booking.getMeetingDate().toString().equals(storedDate)){

                 reformatOutput.add(formatter.format(booking.getMeetingDate()));
             }
             String tempEndTime =  booking.getBookingEndTime().substring(0,2) +":" + booking.getBookingEndTime().substring(2);
             reformatOutput.add(booking.getBookingStartTime() + " " + tempEndTime +" " + booking.getEmployee());
             storedDate= booking.getMeetingDate().toString();
        }
      return reformatOutput;
    }

    private static String calculateBookingEndTime(String bookingStartTime, String bookingDuration) {

        String[] mySplitTime = bookingStartTime.split(":");

       int hours = Integer.parseInt(mySplitTime[0]);
       int mins = Integer.parseInt(mySplitTime[1]);
       int duration = Integer.parseInt(bookingDuration);

       int addedTime = hours + duration;

       String myAddedTime = Integer.toString(addedTime) +  Integer.toString(mins) +"0";

        return myAddedTime;


    }

    private static List<String> getMeetingData() throws IOException {
        String myFile = "data\\datafile.txt";
        return Files.readAllLines(Paths.get(myFile), Charset.defaultCharset());
    }

    private static void listMyBookings(List<Booking> myBookingList) {
        System.out.println(myBookingList);
        for (Booking bk : myBookingList){
            StringBuilder myStringBuilder =new StringBuilder();
            myStringBuilder.append(bk.getMeetingDate().toString());
            myStringBuilder.append(" ");
            myStringBuilder.append(bk.getBookingStartTime());
            myStringBuilder.append(" ");
            myStringBuilder.append(bk.getEmployee());
            myStringBuilder.append(" ");
            myStringBuilder.append(bk.getMeetingDuration());
            System.out.println(myStringBuilder);
        }
    }
    public static List<Booking> filterBookingList(List<Booking> myList, String openTime, String closeTime){

        Booking savedBooking = new Booking();
        boolean firstRun = true;

        // parse the list looking for duplicates and for meetings that fall outside business hours
        Iterator iterator = myList.iterator();
        while(iterator.hasNext()){

            Booking myBooking;
            myBooking = (Booking) iterator.next();
            if (firstRun){
                savedBooking = myBooking;
                firstRun = false;
                continue;
            }
            // check to see if the meetings start at the same time on the same day.
            if((myBooking.getMeetingDate().equals(savedBooking.getMeetingDate()) && (myBooking.getBookingStartTime().equals(savedBooking.getBookingStartTime())))){
                iterator.remove();
                continue;
            }
            //Check whether meeting start time is before opening time
            if (myBooking.getBookingStartTime().compareTo(openTime)== -1  ){
                iterator.remove();
                continue;
            }
            // check whether the meeting end time if after close of business
            if (Integer.parseInt(myBooking.getBookingEndTime()) > Integer.parseInt(closeTime)){
                iterator.remove();
                continue;
            }
            savedBooking = myBooking;
        }
        return myList;
    }

}
