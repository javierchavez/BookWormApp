package bookworm.model;


public class Asset
{
  private String title;
  private Category category;


  public Asset(String title, Category category)
  {
    this.category = category;
    this.title  = title;
  }

  public Category getCategory ()
  {
    return category;
  }

  public void setCategory (Category category)
  {
    this.category = category;
  }

  public String getTitle ()
  {
    return title;
  }

  public void setTitle (String title)
  {
    this.title = title;
  }
}
