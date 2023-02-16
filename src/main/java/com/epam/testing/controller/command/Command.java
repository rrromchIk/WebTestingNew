package com.epam.testing.controller.command;

import com.epam.testing.controller.DispatchInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author rom4ik
 */

public interface Command {
  /**
   * Execution method for command.
   *
   * @return Address to go once the command is executed and way to go(froward/redirect)
   */
  DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp);
}
