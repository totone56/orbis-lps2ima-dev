package org.orbisgis.orbisserver.baseserver.model;

/**
 * <b>This exception is thrown when an error occur during an access to the
 * database.</b>
 * 
 * @author Group A - 2017/2018
 * 
 * @version 1.0
 * 
 * @see Group
 */
public class DatabaseException extends Exception {
  /**
   * The automatically created serial version ID of the class.
   */
  private static final long serialVersionUID = -5067496538005269665L;

  /**
   * The constructor of the exception, to authorize the addition of text.
   * 
   * @param s
   *          the text binded to the exception
   */
  public DatabaseException(String s) {
    super(s);
  }
}
