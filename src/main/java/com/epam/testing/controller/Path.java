package com.epam.testing.controller;

/**
 * Class represents all jsp-pages and commands in webapp
 *
 * @author rom4ik
 */
public final class Path {
  public static final String PAGE_INDEX = "/index.jsp";
  public static final String PAGE_LOGIN = "/logIn.jsp";
  public static final String PAGE_SIGNUP = "/signUp.jsp";
  public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error/error-page.jsp";
  public static final String PAGE_USER_MAIN = "/WEB-INF/jsp/client/user-main.jsp";
  public static final String PAGE_USER_PROFILE = "/WEB-INF/jsp/client/profile.jsp";
  public static final String PAGE_ADMIN_MAIN = "/WEB-INF/jsp/admin/admin-main.jsp";
  public static final String PAGE_USER_TEST_PASSING = "/WEB-INF/jsp/client/test-passing.jsp";
  public static final String PAGE_USER_INFO = "/WEB-INF/jsp/admin/user-info.jsp";
  public static final String PAGE_ADD_TEST = "/WEB-INF/jsp/admin/add-test.jsp";
  public static final String PAGE_ADD_QUESTIONS = "/WEB-INF/jsp/admin/add-questions.jsp";
  public static final String PAGE_TEST_INFO = "/WEB-INF/jsp/admin/test-info.jsp";
  public static final String PAGE_FORGOT_PASSWORD = "forgotPassword.jsp";
  public static final String PAGE_CHANGE_PASSWORD = "resetPassword.jsp";

  public static final String COMMAND_ADMIN_MAIN = "/controller?action=adminMain";
  public static final String COMMAND_USER_INFO = "/controller?action=userInfo";
  public static final String COMMAND_ADD_QUESTIONS = "/controller?action=addQuestions";
  public static final String COMMAND_TEST_INFO = "/controller?action=testInfo";
  public static final String COMMAND_ADD_TEST = "/controller?action=addTest";

  public static final String COMMAND_USER_MAIN = "/controller?action=userMain";
  public static final String COMMAND_USER_PROFILE = "/controller?action=profile";
  public static final String COMMAND_USER_PASS_TEST = "/controller?action=passTest";


  public static final String LOCALE_NAME_UA = "ua";
  public static final String LOCALE_NAME_EN = "en";
}
