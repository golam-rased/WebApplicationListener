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
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author irfan
 */
public class RegistrationServlet1 extends HttpServlet {

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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        String username = req.getParameter("username");
        String pwd = req.getParameter("userpwd");
        String city = req.getParameter("usercity");
        String phno = req.getParameter("userphno");
        try {
            ps.setInt(1, Integer.parseInt(username));
            ps.setString(2, pwd);
            ps.setString(3, city);
            ps.setString(4, phno);
            int result = ps.executeUpdate();
            if (result == 1) {
                pw.println("<b>Thank u for registration</b>");
                pw.println("<br><a href='home.html'>Please login yourself </a>");
            } else {
                pw.println("<b>Your registration could not be done</b>");
                pw.println("<br><a href='register.html'>Please try again </a>");
            }
        } catch (SQLException sq) {
            System.out.println("Sql Error:" + sq);
            throw new ServletException("Sql Error:" + sq);
        }

        pw.close();
    }
}
