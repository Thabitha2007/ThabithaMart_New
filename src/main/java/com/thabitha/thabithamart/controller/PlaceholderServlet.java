package com.thabitha.thabithamart.controller;
import javax.servlet.*;import javax.servlet.http.*;import java.io.IOException;
public class PlaceholderServlet extends HttpServlet{protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException{s.setContentType("text/plain");s.getWriter().println("Feature endpoint scaffold: "+r.getRequestURI());}}