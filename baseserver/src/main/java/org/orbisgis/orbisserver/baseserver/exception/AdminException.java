package org.orbisgis.orbisserver.baseserver.exception;

import org.orbisgis.orbisserver.baseserver.model.Group;

/**
 * <b>This exception is thrown when there is an attempt to remove the last
 * administrator of a group.</b>
 * 
 * @author Group A - 2017/2018
 * 
 * @version 1.0
 * 
 * @see Group
 */
public class AdminException extends Exception {
  /**
   * The automatically created serial version ID of the class.
   */
  private static final long serialVersionUID = -323525939623990467L;

  /**
   * The constructor of the exception, to authorize the addition of text.
   * 
   * @param s
   *          the text binded to the exception
   */
  public AdminException(String s) {
    super(s);
  }
}