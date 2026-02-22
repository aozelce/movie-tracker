package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Recommendation servlet.
 * @author aozelce
 */
@WebServlet("/recommendations")
public class RecommendationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // hardcoding user id 2 for now until implementing the AWS login
        GenericDao<User> genericDaoUser = new GenericDao<>(User.class);
        User user = genericDaoUser.getById(2);

        List<Recommendation> recommendations = user.getRecommendations();

        request.setAttribute("recommendations", recommendations);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/recommendations.jsp");
        dispatcher.forward(request, response);
    }
}