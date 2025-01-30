public class NBody {

    static double readRadius(String filename) {
        In in = new In(filename);
        int firstItemInFile = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    static Body[] readBodys(String filename) {
        In in = new In(filename);
        int firstItemInFile = in.readInt();
        double radius = in.readDouble();
        int line = 0;
        Body[] Array = new Body[firstItemInFile];
        while (line < firstItemInFile) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Array[line] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            line++;
        }
        return Array;
    }

    public static void main(String[] args) {
        String imageToDraw = "images/starfield.jpg";
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        /* read in the bodies and universe radius */
        Body[] bodys = readBodys(filename);
        double radius = readRadius(filename);

        /* set the scale */
        StdDraw.setScale(-radius, radius);
        /* draw the image as the background */

        /* 1.Clears the drawing window. */
        StdDraw.clear();
        /* draw the picture */
        StdDraw.picture(0, 0, imageToDraw);
        Body[] bodies = readBodys(filename);
        for (Body body : bodies) {
            body.draw();
        }
        StdDraw.enableDoubleBuffering();
        for (double t = 0; t <= T; t = t + dt) {
            Double[] xForces = new Double[bodys.length];
            Double[] yForces = new Double[bodys.length];
            for (int i = 0; i < bodys.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            /* draw the background image */
            StdDraw.picture(0, 0, imageToDraw);
            /* 1.Clears the drawing window. */
            StdDraw.clear();
            /* draw the picture */
            StdDraw.picture(0, 0, imageToDraw);
            /* draw all the Bodys */
            for (Body body : bodies) {
                body.draw();
            }
            /* show the offscreen buffer */

            StdDraw.show(10);
            StdDraw.pause(10);

        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }

}