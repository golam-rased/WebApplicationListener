/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irfan.listener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author irfan
 */
public class LoginServlet1 extends HttpServlet {

    private Connection conn;
    private PreparedStatement ps;

    public void init() throws ServletException {
        ServletContext sc = super.getServletContext();
        conn = (Connection) sc.getAttribute("myconn");
        try {
            ServletConfig cfg = super.getServletConfig();
            String qry = cfg.getInitParameter("qry");
            ps = conn.prepareStatement(qry);
        } catch (SQLException sq) {
            System.out.println("Sql Error:" + sq);
            throw new ServletException("Sql Error:" + sq);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        String username = req.getParameter("username");
        String pwd = req.getParameter("userpwd");
        if (username.equals("") || pwd.equals("")) {
            pw.println("<b>Cannot leave username or password blank");
        } else {
            try {
                ps.setInt(1, Integer.parseInt(username));
                ps.setString(2, pwd);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    pw.println("<b>Welcome back</b>");
                } else {
                    pw.println("<b>You are not registered</b>");
                    pw.println("<br><a href='home.html'>Try again</a>");
                }
            } catch (SQLException sq) {
                System.out.println("Sql Error:" + sq);
                throw new ServletException("Sql Error:" + sq);
            }
        }
        pw.close();
    }
}
