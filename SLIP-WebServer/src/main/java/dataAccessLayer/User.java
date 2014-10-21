/**
 * User Object refers to a specific table in the DBS
 */

package dataAccessLayer;

import org.springframework.stereotype.Service;

/**
 * @author Calvin.T.Murray
 */
@Service
public class User {

  private int userID;
  private String forename;
  private String surname;
  
  public User(int userID, String forename, String surname) {
    super();
    this.userID = userID;
    this.forename = forename;
    this.surname = surname;
  }
  
  public User(){
	  
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
  
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return new String("User ID: " + getUserID() + " Forename: " + getForename() + " Surname: " + getSurname());
	}
  
}
