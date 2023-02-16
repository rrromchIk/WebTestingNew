package com.epam.testing.util;

import com.epam.testing.model.dto.CheckedAnswer;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.test.TestInfo;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.service.TestQuestionService;
import com.epam.testing.model.service.UserService;
import com.epam.testing.model.service.UserTestService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.http.HttpServletResponse;

/**
 * PDF builder util. Generates pdf-files for test result
 *
 * @author rom4ik
 */
public class PdfBuilderUtil {
  private static BaseFont baseFont;
  private static final Logger LOGGER = LogManager.getLogger(PdfBuilderUtil.class);
  private static final TestQuestionService testQuestionService = new TestQuestionService();
  private static final UserTestService userTestService = new UserTestService();
  private static final UserService userService = new UserService();

  /**
   * Don't let anyone instantiate this class.
   */
  private PdfBuilderUtil() {}

  /**
   * @param response to display pdf file
   * @param userId for User identification
   * @param testId for Test identification
   */
  public static void createResultPdf(HttpServletResponse response, long userId, long testId) {
    Document document = new Document(PageSize.A4, 50, 50, 20, 50);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
      PdfWriter.getInstance(document, baos);
      document.open();

      baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", "cp1251", BaseFont.EMBEDDED);
      addMetaData(document);
      addHeader(document);
      addTestAnswersView(document, userId, testId);
      addTestInfo(document, userId, testId);
      addUserInfo(document, userId);
      addFooter(document);

      document.close();
      openInBrowser(response, baos);
    } catch (DocumentException e) {
      LOGGER.warn(e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void addMetaData(Document document) {
    document.addTitle("Test results");
    document.addAuthor("rom4ik");
  }

  private static void addHeader(Document document) throws DocumentException {
    Paragraph header = new Paragraph("Test overview", new Font(baseFont, 20, Font.BOLD));
    header.setAlignment(Element.ALIGN_CENTER);
    document.add(header);
    addLines(document, 2);
  }

  private static void addTestAnswersView(Document document, long userId, long testId) throws DocumentException {
    List<Question> questions = testQuestionService.getQuestionsByTestId(testId);

    for (Question question: questions) {
      addQuestion(document, question, userId);
      addLines(document, 1);
    }
  }

  private static void addQuestion(Document document, Question question, long userId) throws DocumentException {
    Paragraph paragraph = new Paragraph(
            String.format("%d. %s?", question.getNumber(), question.getText()),
            new Font(baseFont, 16));

    document.add(paragraph);
    addAnswers(document, question.getId(), userId);
    addLines(document, 1);
    addCorrectAnswers(document, question.getId());
    addScore(document, question, userId);
  }

  private static void addAnswers(Document document, long questionId, long userId) throws DocumentException {
    List<CheckedAnswer> answers = testQuestionService.getAnswerVariantsByQuestionIdWithCheckedStatus(userId, questionId);
    BaseColor baseColor;
    StringBuilder sb = new StringBuilder();
    for (CheckedAnswer answer : answers) {
      baseColor = BaseColor.BLACK;
      sb.setLength(0);
      sb.append(" - ").append(answer.getText());
      if(answer.getChecked()) {
        sb.append("       — Your answer");
        baseColor = CalculateTestResultService.isAnswerCorrect(questionId, answer.getText()) ?
                BaseColor.GREEN : BaseColor.RED;
      }
      document.add(new Paragraph(sb.toString(), new Font(baseFont, 14, Font.NORMAL, baseColor)));
    }
  }

  private static void addCorrectAnswers(Document document, long questionId) throws DocumentException {
    List<String> answers = testQuestionService.getCorrectAnswers(questionId);
    StringJoiner stringJoiner = new StringJoiner(",");
    for (String answer : answers) {
      stringJoiner.add(answer);
    }
    String text = "Correct answers: " + stringJoiner;
    document.add(new Paragraph(text, new Font(baseFont, 14)));
  }

  private static void addScore(Document document, Question question, long userId) throws DocumentException {
    int actualScore = CalculateTestResultService.getQuestionScore(question, userId);
    int maxScore = question.getMaxScore();
    String text = String.format("Score: %d/%d", actualScore, maxScore);
    document.add(new Paragraph(text, new Font(baseFont, 14)));
  }

  private static void addTestInfo(Document document, long userId, long testId) throws DocumentException {
    TestInfo testInfo = userTestService.getTestInfo(userId, testId);
    String testName = testInfo.getTestName();
    String startingTime = testInfo.getStartingTime();
    String endingTime = testInfo.getEndingTime();
    String result = testInfo.getResult().toString();

    String text = String.format("Test name: %s%n" +
                                "Starting time: %s%n" +
                                "Ending time: %s%n" +
                                "Result: %s%%", testName, startingTime, endingTime, result);
    document.add(new Paragraph("Test info", new Font(baseFont, 16, Font.BOLD)));
    document.add(new Paragraph(text, new Font(baseFont, 14)));
    addLines(document, 1);
  }

  private static void addUserInfo(Document document, long userId) throws DocumentException {
    User user = userService.getUserById(userId);

    String userLogin = user.getLogin();
    String userName = user.getName();
    String userEmail = user.getEmail();

    String text = String.format("Login: %s%n" +
            "Name: %s%n" +
            "Email: %s%n", userLogin, userName, userEmail);
    document.add(new Paragraph("User info", new Font(baseFont, 16, Font.BOLD)));
    document.add(new Paragraph(text, new Font(baseFont, 14)));
    addLines(document, 2);
  }

  private static void addFooter(Document document) throws DocumentException {
    Paragraph footer = new Paragraph("© 2023 «WebTesting»");
    footer.setAlignment(Element.ALIGN_CENTER);
    document.add(footer);
  }

  private static void addLines(Document document, int amountOfLines) throws DocumentException {
    for (int i = 0; i < amountOfLines; ++i) {
      document.add(new Paragraph(" "));
    }
  }

  private static void openInBrowser(HttpServletResponse response, ByteArrayOutputStream baos) {
    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    response.setHeader("Content-Disposition", "inline; filename=results.pdf");
    response.setContentType("application/pdf");
    //response.setCharacterEncoding("UTF-8");
    response.setContentLength(baos.size());


    OutputStream os;
    try {
      os = response.getOutputStream();
      baos.writeTo(os);
      os.flush();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
