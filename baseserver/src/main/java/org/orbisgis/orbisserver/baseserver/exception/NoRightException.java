package org.orbisgis.orbisserver.baseserver.exception;

/**
 * <b>This exception is thrown when an user attempt to do something without the
 * rights to do it.</b>
 * 
 * @author Group A - 2017/2018
 * 
 * @version 1.0
 * 
 * @see Group
 */
public class NoRightException extends Exception {
  /**
   * The automatically created serial version ID of the class.
   */
  private static final long serialVersionUID = 7704945449476150163L;

  /**
   * The constructor of the exception, to authorize the addition of text.
   * 
   * @param s
   *          the text binded to the exception
   */
  public NoRightException(String s) {
    super(s);
  }
}
