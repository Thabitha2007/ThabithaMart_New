package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * /seller/* URL-க்கு போற எல்லா requests-ஐயும் இது முதல்ல check பண்ணும்.
 * Login ஆகி, role = SELLER ஆனவங்க மட்டும் உள்ள போக முடியும்.
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        boolean isSeller = (user != null && "SELLER".equalsIgnoreCase(user.role));

        if (isSeller) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override public void init(FilterConfig filterConfig) {}
    @Override public void destroy() {}
}
