package com.thabitha.thabithamart.controller;
import javax.servlet.*;import javax.servlet.http.*;import java.io.IOException;
public class HomeServlet extends HttpServlet{protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(r,s);}}