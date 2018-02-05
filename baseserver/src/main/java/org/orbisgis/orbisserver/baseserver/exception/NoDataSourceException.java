package org.orbisgis.orbisserver.baseserver.exception;

import org.orbisgis.orbisserver.baseserver.utils.DatabaseRequest;

/**
 * <b>This exception is thrown when there is an attempt to use the methods to
 * alter the database without defining the data source beforehand.</b>
 * 
 * @author LP S2IMa - 2017/2018
 * 
 * @version 1.0
 * 
 * @see DatabaseRequest
 */
public class NoDataSourceException extends Exception {
  /**
   * The automatically created serial version ID of the class.
   */
  private static final long serialVersionUID = -8184553191829917560L;

  /**
   * The constructor of the exception, to authorize the addition of text.
   * 
   * @param s
   *          the text binded to the exception
   */
  public NoDataSourceException(String s) {
    super(s);
  }
}
