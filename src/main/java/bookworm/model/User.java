package bookworm.model;



public class User
{

  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String salt;

  public User(String username, String firstName, String lastName)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
  }

  public String getUsername ()
  {
    return username;
  }

  public void setUsername (String username)
  {
    this.username = username;
  }

  public String getPassword ()
  {
    return password;
  }

  public String getSalt ()
  {
    return salt;
  }

  public void setEncryptedPassword (String password, String salt)
  {
    this.salt = salt;
    this.password = password;
  }

  public String getFirstName ()
  {
    return firstName;
  }

  public void setFirstName (String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName ()
  {
    return lastName;
  }

  public void setLastName (String lastName)
  {
    this.lastName = lastName;
  }
}
