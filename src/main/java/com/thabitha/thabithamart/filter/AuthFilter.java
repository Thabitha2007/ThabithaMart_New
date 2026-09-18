package com.thabitha.thabithamart.filter;
import javax.servlet.*;import javax.servlet.http.*;import java.io.IOException;
public class AuthFilter implements Filter {
 public void doFilter(ServletRequest a,ServletResponse b,FilterChain f)throws IOException,ServletException{
  HttpServletRequest r=(HttpServletRequest)a;HttpServletResponse s=(HttpServletResponse)b;
  if(r.getSession(false)==null||r.getSession(false).getAttribute("user")==null){s.sendRedirect(r.getContextPath()+"/login");return;} f.doFilter(a,b);
 }
}