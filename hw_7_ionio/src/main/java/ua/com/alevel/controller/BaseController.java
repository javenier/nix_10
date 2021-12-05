package ua.com.alevel.controller;

import java.io.BufferedReader;

public interface BaseController {

    void run();

    void runNavigation();

    void crud(String position, BufferedReader reader);

    void create(BufferedReader reader);

    void update(BufferedReader reader);

    void delete(BufferedReader reader);

    void findById(BufferedReader reader);

    void findAll(BufferedReader reader);
}