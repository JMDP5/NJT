/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagefilters;

import domen.Korisnik;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aleksandar
 */
@WebFilter(value = "/stranice/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        http://stackoverflow.com/questions/16136089/why-doesnt-servlet-filter-redirect-to-home-if-request-login-isloggedin-is
//        http://stackoverflow.com/questions/15090423/restricting-users-from-accessing-pages-by-directly-changing-the-url-in-jsf
//        http://stackoverflow.com/questions/7872265/protected-urls-leaking-unprotected-components-of-the-webapge-to-unauthenticated
//      
        HttpServletRequest req = (HttpServletRequest) request;
        Korisnik k = (Korisnik) req.getSession().getAttribute("korisnik");
        System.out.println("Usao u filter - korisnik " + k);
        boolean isLoggedIn = (k != null);
        // Check if the user is accessing "login.xhtml"
        System.out.println("RequestURI" + req.getRequestURI());
        if (req.getRequestURI().equals("/NJTProjekat-war/stranice/logovanje.xhtml")) {

            if (isLoggedIn) {
                // Redirect to "home.xhtml"
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath() + "/stranice/obradazadataka.xhtml");
            } else {
                // Otherwise, nothing to do if he has not logged in
                chain.doFilter(request, response);
            }

        } else {
            // For all other pages,
            if (isLoggedIn) {
                // Nothing to do
                chain.doFilter(request, response);
            } else {
                // Redirect to "login.xhtml" if he has not logged in
                HttpServletResponse res = (HttpServletResponse) response;
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                res.setDateHeader("Expires", 0); // Proxies.
                res.sendRedirect(req.getContextPath() + "/stranice/logovanje.xhtml");
            }
        }

    }

    @Override
    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
