package com.thabitha.thabithamart.controller;
import javax.servlet.*;import javax.servlet.http.*;import java.io.IOException;
public class LogoutServlet extends HttpServlet{protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException{if(r.getSession(false)!=null)r.getSession(false).invalidate();s.sendRedirect("login");}}