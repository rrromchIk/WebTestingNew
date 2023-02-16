package com.epam.testing.controller;

import com.epam.testing.controller.command.Command;
import com.epam.testing.controller.command.CommandFactory;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main Controller servlet. Handles all requests.
 *
 * @author rom4ik
 */

@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, //1MB
        maxFileSize = 1024 * 1024 * 10,       //10MB
        maxRequestSize = 1024 * 1024 * 100)   //100MB
public class Controller extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    processRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    processRequest(req, resp);
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    /**
      Command pattern implemented
    */
    CommandFactory commandFactory = CommandFactory.commandFactory();
    Command command = commandFactory.getCommand(req);
    DispatchInfo dispatchInfo = command.execute(req, resp);
    String page = dispatchInfo.getPage();

    /**
     * PRG pattern implemented
     */
    if (dispatchInfo.isRedirect()) {
      resp.sendRedirect(page);
    } else {
      req.getRequestDispatcher(page).forward(req, resp);
    }
  }
}