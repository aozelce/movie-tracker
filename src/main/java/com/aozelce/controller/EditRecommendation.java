package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.entity.Source;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/editRecommendation")
public class EditRecommendation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the current session only if a session already exists for
        // the current request. If no session exists, it does not create a
        // new one
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String idParam = request.getParameter("id");
        if (user == null || idParam == null) {
            response.sendRedirect("logIn");
            return;
        }
        int recId = Integer.parseInt(idParam);
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);
        GenericDao<Source> sourceDao = new GenericDao<>(Source.class);
        List<Source> sources = sourceDao.getByPropertyEqual("user", user);
        request.setAttribute("recommendation", rec);
        request.setAttribute("sources", sources);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecommendation.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String idParam = request.getParameter("id");
        if (user == null || idParam == null) {
            response.sendRedirect("logIn");
            return;
        }
        int recId = Integer.parseInt(idParam);
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);
        String notes = request.getParameter("notes");
        String isWatched = request.getParameter("isWatched");
        String sourceName = request.getParameter("sourceName");
        rec.setNotes(notes != null ? notes.trim() : null);
        // Set the watched status based on the form input.
        rec.setWatched("on".equals(isWatched));
        if (sourceName != null && !sourceName.trim().isEmpty()) {
            GenericDao<Source> sourceDao = new GenericDao<>(Source.class);
            List<Source> existingSources = sourceDao.getByPropertyEqual("name", sourceName.trim());
            Source source;
            if (existingSources != null && !existingSources.isEmpty()) {
                source = existingSources.get(0);
            } else {
                source = new Source();
                source.setUser(user);
                source.setName(sourceName.trim());
                int sourceId = sourceDao.insert(source);
                source.setId(sourceId);
            }
            rec.setSource(source);
        } else {
            rec.setSource(null);
        }
        recDao.saveOrUpdate(rec);
        response.sendRedirect("recommendations");
    }
}