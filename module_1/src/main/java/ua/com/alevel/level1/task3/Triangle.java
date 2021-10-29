package ua.com.alevel.level1.task3;

import java.awt.*;

public class Triangle {

    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isTriangle() {
        int area = a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y);
        if (area == 0)
            return false;
        return true;
    }

    public double calcPerimeter() {
        return a.distance(b) + b.distance(c) + a.distance(c);
    }

    public double calcSquare() {
        double halfPerimeter = calcPerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - a.distance(b)) *
                (halfPerimeter - b.distance(c)) * (halfPerimeter - a.distance(c)));
    }
}
