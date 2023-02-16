package com.epam.testing.controller.filter;

import com.epam.testing.controller.Path;
import com.epam.testing.model.entity.user.UserRole;
import com.epam.testing.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Checks for permissions for different roles in webapp.
 *
 * @author rom4ik
 */
@WebFilter(filterName = "SecurityFilter",
        urlPatterns = "/controller",
        initParams = {
                @WebInitParam(name = "guest", value = "logIn signUp logOut i18n resetPassword updatePassword"),
                @WebInitParam(name = "client", value = "userMain profile editProfile startTest passTest " +
                        "submitAnswers endTest testResult uploadAvatar resetAvatar"),
                @WebInitParam(name = "admin", value = "adminMain userInfo editUser deleteTest addTest " +
                        "changeUserStatus submitTestInfo addQuestions submitQuestionInfo testInfo editTest deleteQuestion")
        })
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(SecurityFilter.class);

    private static final UserService userService = new UserService();
    private static final Map<UserRole, List<String>> accessMap = new HashMap<>();
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public void init(FilterConfig config) {
        LOGGER.debug("Security filter initialization");

        List<String> adminCommands = asList(config.getInitParameter("admin"));
        List<String> clientCommands = asList(config.getInitParameter("client"));
        List<String> guestCommands = asList(config.getInitParameter("guest"));
        clientCommands.addAll(guestCommands);
        adminCommands.addAll(guestCommands);
        accessMap.put(UserRole.CLIENT, clientCommands);
        accessMap.put(UserRole.ADMIN, adminCommands);
        accessMap.put(UserRole.GUEST, guestCommands);

        LOGGER.info("Client commands -> {}", clientCommands);
        LOGGER.info("Admin commands -> {}", clientCommands);
        LOGGER.info("Guest commands -> {}", guestCommands);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("Security filter");
        if (accessAllowed(request)) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
        }
    }

    /**
     * @param request contains info about required page and related resources
     * @return true if access permitted, else false
     */
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String command = request.getParameter("action");

        HttpSession session = httpRequest.getSession(false);
        if(session == null) {
            String errorMessage = "Invalid session";
            LOGGER.warn("Access invalid -> {}", errorMessage);
            request.setAttribute(ERROR_MESSAGE, errorMessage);
            return false;
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        String userLogin = request.getParameter("login");
        if(userLogin == null) {
            userLogin = (String)session.getAttribute("login");
        }

        if(userRole == null) {
            session.setAttribute("userRole", UserRole.GUEST);
            userRole = UserRole.GUEST;
        }

        if(userLogin != null && userService.userIsBlocked(userLogin)) {
            LOGGER.warn("Access invalid -> User is blocked");
            request.setAttribute(ERROR_MESSAGE,"Seems that you are blocked(" );
            return false;
        }

        boolean result = accessMap.get(userRole).contains(command);
        if(!result) {
            LOGGER.warn("Access invalid -> Permission fails");
            request.setAttribute(ERROR_MESSAGE, "You don't have permission to access the requested resource");
        }
        return result;
    }

    @Override
    public void destroy() {
        LOGGER.debug("Security filter destroyed");
    }

    /**
     * @param param represents Filter initParam that need to be parsed
     * @return parsed List of strings
     */
    private List<String> asList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
