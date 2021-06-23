package com;

public class Main {

    public static void main(String[] args) {
        long m = System.currentTimeMillis();

        int x[][] = new int[8][3];
        double x_norm[][] = new double[8][3];
        int y[] = new int[8];
        double y_etalon;
        int a[] = {1,2,3,4};
        double dy_min = -1000;
        double x0[] = new double[3];
        double dx[] = new double[3];
        int x_max[] = {-1,-1,-1};
        int x_min[] = {21,21,21};

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                x[i][j] = (int)(Math.random()*20);
            }
            y[i] = a[0] + a[1]*x[i][0] + a[2]*x[i][1] + a[3]*x[i][2];
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (x_max[i] < x[j][i]) x_max[i] = x[j][i];
                if (x_min[i] > x[j][i]) x_min[i] = x[j][i];
            }
            x0[i] = (x_max[i] + x_min[i])/2.;
            dx[i] = x0[i] - (double)x_min[i];
        }

        System.out.println("№\tX1\tX2\tX3\tY\t \tXH1\t\tXH2\t\tXH3");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                x_norm[i][j] = ((double) x[i][j] - x0[j])/dx[j];
            }
            System.out.print( (i+1) + "\t" + x[i][0] +"\t" + x[i][1 ]+ "\t" + x[i][2] + "\t");
            System.out.print(y[i] + "\t \t");
            System.out.printf("%.2f\t%.2f\t%.2f", x_norm[i][0], x_norm[i][1], x_norm[i][2]);
            System.out.println();
        }

        System.out.print("x0\t");
        for (int i = 0; i < 3; i++) {
            System.out.print(x0[i] + "\t");
        }
        System.out.println();

        System.out.print("dx\t");
        for (int i = 0; i < 3; i++) {
            System.out.print(dx[i] + "\t");
        }

        y_etalon = a[0] + a[1]*x0[0]+a[2]*x0[1]+a[3]*x0[2];
        System.out.println("\nЕталонне значення У =  " + y_etalon);

        for (int i = 0; i < 8; i++) {
            double temp = y_etalon - (double)y[i];
            if (temp < 0)
                if (dy_min < temp)
                    dy_min = temp;
        }
        int index;
        for (index = 0; index < 8; index++) {
            double temp = y_etalon - dy_min;
            if (y[index] == temp) break;
        }
        System.out.println("Точка плану за варіантом 204 : У(" + x[index][0] + ", " + x[index][1] + ", " + x[index][2] + ") = " + y[index]);



        System.out.println((double) (System.currentTimeMillis() - m));
    }
}