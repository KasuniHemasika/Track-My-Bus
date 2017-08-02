package TrackMyBus;

import java.io.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/stream"})
public final class TrackServlet extends HttpServlet {

    final SendUpdates updates = new SendUpdates();

    @Override
    public void init(final ServletConfig config) {
        updates.start();
        Logger.getGlobal().log(Level.INFO, "Started coordinate updates");
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException,IOException {
        response.setContentType("text/event-stream, charset=UTF-8");

        try (final PrintWriter out = response.getWriter()) {

            while (!Thread.interrupted()) {
                synchronized (updates) {
                    updates.wait();
                }
                updates.sleep(500);
                out.print("data: ");
                out.println(updates);
                out.println();
                out.flush();
            }
        } catch (InterruptedException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            updates.interrupt();
            updates.join();
            Logger.getGlobal().log(Level.INFO, "Stopped coordinate updates");

        } catch (InterruptedException e) {
        }
    }
}
