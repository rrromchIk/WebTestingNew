package com.epam.testing.util.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * PaginationTag custom Tag class
 *
 * @author rom4ik
 */
public class PaginationTag extends TagSupport {
    private int amountOfPages;
    private int activePage;
    private String activeTab;
    private String action;
    private String sortMethod;
    private String groupBy;
    private String contextPath;

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();

        String html = "";
        if(amountOfPages > 1) {

            StringBuilder elements = new StringBuilder();
            for(int i = 1; i <= amountOfPages; ++i) {
                String href = formHref(i);
                elements.append(formElements(i, href));
            }

            html = String.format("<nav id=\"pagination-nav\">\n" +
                    "                <ul id=\"paginationLine\" class=\"pagination justify-content-center\">\n" +
                    "                    <ul id=\"paginationNumbers\" class=\"pagination justify-content-center\">\n" +
                    "                        %s\n" +
                    "                    </ul>\n" +
                    "                </ul>\n" +
                    "            </nav>\n", elements);
        }


        try{
            out.print(html);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    private String formHref(int loopIndex) {
        return String.format("href=\"%s/controller?action=%s&tab=%s%s%s&page=%d\">%d",
                contextPath,
                action,
                activeTab,
                !sortMethod.isEmpty() ? "&sortMethod=" + sortMethod : "",
                !groupBy.isEmpty() ? "&groupBy=" + groupBy : "",
                loopIndex,
                loopIndex);
    }

    private String formElements(int loopIndex, String href) {
        String active = "";
        if(activePage == loopIndex) {
            active = "active";
        }
        return String.format("<li class=\"page-item page-number %s\">\n" +
                "                              <a class=\"page-link\"\n" +
                "                                 %s</a>\n" +
                "                          </li>", active, href);
    }

    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
