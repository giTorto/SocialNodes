/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtri;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Gruppo;
import modelDB.Utente;

/**
 *
 * @author Lorenzo
 */
public class FilterGroupCtrl implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private DBmanager manager;

    public FilterGroupCtrl() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        boolean ok = false;
        //ricerca parametro con cui si accede alla afterlogin
        String operazione = request.getParameter("op");
        RequestDispatcher dispatcher;

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        //gestione delle operazioni preliminari prima di permettere l'accesso alla servelt
        switch (operazione) {
            case "displaygroup":
                //gestione dell'accesso al dato gruppo
                //necessario confrontare utente corrente e gruppo richiesto per vedere se l'utente può accedere al gruppo
                boolean userpuòaccedere = false;
                if (debug) {
                    log("CheckAccessUser2Group:doFilter()");
                }
                String ingruppo = (request.getParameter("groupid"));

                int idgruppo = Integer.parseInt(ingruppo);

                if (session != null) {
                    user = (Utente) session.getAttribute("user");
                }

                try {

                    ArrayList<Integer> ListidsPartecipanti = (ArrayList<Integer>) manager.getUtenti(idgruppo);
                    Gruppo gruppo = manager.getGruppo(idgruppo);
                    Utente owner = manager.getMoreByUserName(gruppo.getNomeOwner());
                    ListidsPartecipanti.add(owner.getId());
                    if (ListidsPartecipanti.contains(user.getId())) {
                        userpuòaccedere = true;
                        ok = true;
                    }
                    if (gruppo.getIsPublic() == 1) {
                        userpuòaccedere = true;
                        ok = true;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(FilterGroupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                    //((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/afterLogged/afterLogin?op=showgroups");
                }

                if (!userpuòaccedere) {
                    //  ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/afterLogged/afterLogin?op=showgroups");
                }

                break;
            case "settings":
                //gestione delle credenziali per la modifica del dato gruppo
                //necessario confrontare utente corrente e gruppo richiesto per vedere se l'utente può modificare le impostazioni del gruppo
                boolean userisowner = false;
                if (debug) {
                    log("CheckAccessUser2Group:doFilter()");
                }
                String gruppoparam = (request.getParameter("groupid"));

                int gruppoid = Integer.parseInt(gruppoparam);

                if (session != null) {
                    user = (Utente) session.getAttribute("user");
                }

                try {
                    Gruppo gruppo = manager.getGruppo(gruppoid);

                    if (gruppo.getIdOwner() == user.getId()) {
                        userisowner = true;
                        ok = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FilterGroupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                    //((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/afterLogged/afterLogin?op=showgroups");
                }

                if (!userisowner) {
                    //((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/afterLogged/afterLogin?op=showgroups");
                }
                break;
            default:
                ok = true;
        }

        if (ok) {
            chain.doFilter(request, response);
        } else {

            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/afterLogged/afterLogin?op=showgroups");

        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("GroupChangeName:Initializing filter");
            }
        }

        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBmanager) filterConfig.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FilterGroupCtrl()");
        }
        StringBuffer sb = new StringBuffer("FilterGroupCtrl(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
