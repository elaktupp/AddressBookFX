/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbookfx;

import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author Kimmo
 */
public class UserInfo implements Serializable, Comparable<UserInfo> {
    private String firstName;
    private String lastName;
    private String homeAddress;
    private String phoneNumber;
    private String emailAddress;

    public UserInfo() {
        this.firstName = "-";
        this.lastName = "-";
        this.homeAddress = "-";
        this.phoneNumber = "-";
        this.emailAddress = "-";
    }
    
    public UserInfo(String firstName, String lastName, String homeAddress,
                    String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // This is actually something that should be done by the class using
    // the UserInfo, it is not this class responsibility to decide how
    // the information is displayed. User should just use accessor methods
    // and do its own styling.
    public String getInfo() {
        return "# "+getLastName()+", "+getFirstName()+"\n"+
               "| "+getHomeAddress()+"\n"+
               "| "+getPhoneNumber()+"\n"+
               "| "+getEmailAddress();
    }
    
    public boolean findText(String text) {
        
        boolean result = false;
        String findThis = text.toUpperCase(Locale.ROOT);
        
        if (firstName.toUpperCase(Locale.ROOT).contains(findThis)) {
            result = true;
        } else if (lastName.toUpperCase(Locale.ROOT).contains(findThis)) {
            result = true;
        } else if (homeAddress.toUpperCase(Locale.ROOT).contains(findThis)) {
            result = true;
        } else if (phoneNumber.toUpperCase(Locale.ROOT).contains(findThis)) {
            result = true;
        } else if (emailAddress.toUpperCase(Locale.ROOT).contains(findThis)) {
            result = true;
        }
        
        return result;
    }
    
    @Override
    public int compareTo(UserInfo o) {
        // Order by Last name if possible
        String thisName = this.getLastName();
        String otherName = o.getLastName();
        
        int result = thisName.compareTo(otherName);
        
        if (result == 0) {
            // Same Last names, proceed to First names
            thisName = this.getFirstName();
            otherName = o.getFirstName();
            result = thisName.compareTo(otherName);
        }

        return result;
    }

}
