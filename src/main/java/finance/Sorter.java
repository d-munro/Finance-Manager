/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finance;

import java.time.LocalDate;

/**
 *
 * @author Dylan Munro
 */
public class Sorter {
    
    /**
     * Compares two dates to determine which date is earlier
     * @param date1 The first date being compared
     * @param date2 The second date being compared
     * @return -1 if date1 comes before date2, 0 if date1 and date2 are equal, 
     * 1 if date1 comes after date2
     */
    public int compareDates(LocalDate date1, LocalDate date2) {
        if (date1.isBefore(date2)) {
            return -1;
        } else if (date1.isEqual(date2)) {
            return 0;
        } else {
            return 1;
        }
    }
    
    /**
     * Compares 2 item categories to determine which comes first alphabetically. 
     * Is not case sensitive
     * @param category1 The first item category being compared
     * @param category2 The second item category being compared
     * @return -1 if category1 comes before category2 alphabetically, 0 if 
     * category1 and category2 are the same, 1 if category1 comes after category2 
     * alphabetically
     */
    public int compareCategories(String category1, String category2) {
        return category1.compareToIgnoreCase(category2);
    }
    
    /**
     * Compares 2 item fees to see which is cheaper
     * @param fee1
     * @param fee2
     * @return -1 if fee1 is less than fee2, 0 if fee1 is equal to fee2, 
     * 1 if fee1 is greater than fee2
     */
    public double compareFee(double fee1, double fee2) {
        if (fee1 < fee2) {
            return -1;
        } else if (fee1 == fee2) {
            return 0;
        } else {
            return 1;
        }
    }

}
