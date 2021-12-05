package ua.com.alevel.controller;

import java.io.BufferedReader;

public interface StudentController extends BaseController {

    void findAllByGroupId(BufferedReader reader);
}
