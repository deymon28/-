package com;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        final int x1Min = 15;
        final int x1Max = 45;
        final int x2Min = 15;
        final int x2Max = 50;
        final int yMax = -2010;
        final int yMin = -2020;
        int m = 22;

        if (m > 20){
            System.out.println("Завелика кількість експериментів");
            return;
        }

        double[] mx = new double[2];
        double my = 0;
        double[] a = new double[3];
        double a11 = 0;
        double a22 = 0;
        double[] b = new double[3];
        double[] yAverage = new double[3];
        final double[] Rkr_Table = {1.73, 2.16, 2.43, 2.62, 2.75, 2.9, 3.08};
        boolean work = true;

        int[][] x = {
                {-1,-1},
                {1,-1},
                {-1,1}
        };

        while (work) {
            List<int[]> y = new ArrayList<>();
            System.out.println("Лінійне рівняння регресії для нормованих значень х має вигляд : y = b0 + b1 * x1 + b2 * x2");
            System.out.println();

            System.out.println("Нормована матриця планування експерименту : ");
            System.out.print("X1\tX2\t");
            for (int i = 0; i < m; i++) {
                System.out.print("Y" + (i+1) + "\t");
            }
            System.out.println();
            for (int i = 0; i < 3; i++) {
                int[] yTemp = new int[m];
                for (int j = 0; j < 2; j++) {
                    System.out.print(x[i][j] + "\t");
                }
                for (int j = 0; j < m; j++) {
                    yTemp[j] = (int) (Math.random() * (yMax - yMin)) + yMin;
                    System.out.print(yTemp[j] + "\t");
                }
                System.out.println();
                y.add(yTemp);
            }

            //перевірка за критерієм Романовського


            double[] dispersion = new double[3];
            double deviation = 0;
            double[] Fuv = new double[3];
            double[] θuv = new double[3];
            double[] Ruv = new double[3];
            double Rkr = 0;


            for (int i = 0; i < 3; i++) {
                double sum = 0;
                int[] yTemp = y.get(i);
                for (int j = 0; j < m; j++) {
                    sum += yTemp[j];
                }
                yAverage[i] = sum / m;
            }

            for (int i = 0; i < 3; i++) {
                double sum = 0;
                int[] yTemp = y.get(i);
                for (int j = 0; j < m; j++) {
                    sum += Math.pow((yTemp[j] - yAverage[i]), 2);
                }
                dispersion[i] = sum / m;
                //System.out.println("Дисперсія: " + dispersion[i]);
            }

            deviation = Math.sqrt((2 * (2 * m - 2)) /(double) (m * (m - 4)));
            //System.out.println("dev" + deviation);

            Fuv[0] = dispersion[0] / dispersion[1];
            Fuv[1] = dispersion[2] / dispersion[0];
            Fuv[2] = dispersion[2] / dispersion[1];
            /*System.out.println("Fuv");
            for (int i = 0; i < 3; i++) {
                System.out.println(Fuv[i]);
            }*/



            System.out.println("\nRuv");
            for (int i = 0; i < 3; i++) {
                θuv[i] = ((m - 2) / (double)m) * Fuv[i];
                Ruv[i] = Math.abs((θuv[i] - 1) / deviation);
                System.out.println(Ruv[i]);
            }

            if (m <= 4) Rkr = Rkr_Table[0];
            else if (m <= 6) Rkr = Rkr_Table[1];
            else if (m <= 8) Rkr = Rkr_Table[2];
            else if (m <= 10) Rkr = Rkr_Table[3];
            else if (m <= 13) Rkr = Rkr_Table[4];
            else if (m <= 17) Rkr = Rkr_Table[5];
            else if (m <= 20) Rkr = Rkr_Table[6];

            System.out.println("\nRкр = " + Rkr + "\n");
            if (Ruv[0] < Rkr && Ruv[1] < Rkr && Ruv[2] < Rkr ) System.out.println("Дисперсії однорідні\n");
            for (int i = 0; i < 3; i++) {
                if (Ruv[i] < Rkr ) {
                    work = false;

                }

                else work = true;
            }
            m++;
            if (work) System.out.println("ПОМИЛКА : Ruv > Rкр\nЗБІЛЬШУЄМО КІЛЬКІСТЬ ДОСЛІДІВ : m+1\n");
        }

        // розрахунок нормованих коефіцієнтів рівняння регресії

        for (int i = 0; i < 2; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += x[j][i];
            }
            mx[i] = sum/3;
        }

        my = (yAverage[0] + yAverage[1] + yAverage[2])/3;

        a[0] = (Math.pow(x[0][0],2) + Math.pow(x[1][0],2) + Math.pow(x[2][0],2))/3;
        a[1] = (x[0][0]*x[0][1] + x[1][0]*x[1][1] + x[2][0]*x[2][1])/3.;
        a[2] = (Math.pow(x[0][1],2) + Math.pow(x[1][1],2) + Math.pow(x[2][1],2))/3;

        a11 = (x[0][0]*yAverage[0] + x[1][0]*yAverage[1] + x[2][0]*yAverage[2])/3;
        a22 = (x[0][1]*yAverage[0] + x[1][1]*yAverage[1] + x[2][1]*yAverage[2])/3;

        double det11,det12,det21,det22,det31,det32;

        det11 = (my*a[0]*a[2]) + (mx[0]*a[1]*a22) + (mx[1]*a11*a[1]) - (a22*a[0]*mx[1]) - (my*a[1]*a[1]) - (mx[0]*a11*a[2]);
        det12 = (1*a[0]*a[2]) + (mx[0]*a[1]*mx[1]) + (mx[1]*mx[0]*a[1]) - (mx[1]*mx[1]*a[0]) - (mx[0]*mx[0]*a[2]) - (1*a[1]*a[1]);
        b[0] = det11/det12;
        det21 = (1*a11*a[2]) + (my*a[1]*mx[1]) + (mx[0]*a22*mx[1]) - (mx[1]*a11*mx[1]) - (mx[0]*my*a[2]) - (1*a22*a[1]);
        det22 = (1*a[0]*a[2]) + (mx[0]*a[1]*mx[1]) + (mx[1]*mx[0]*a[1]) - (mx[1]*mx[1]*a[0]) - (mx[0]*mx[0]*a[2]) - (a[1]*a[1]*1);
        b[1] = det21/det22;
        det31 = (1*a[0]*a22) + (mx[0]*a11*mx[1]) + (mx[0]*a[1]*my) - (mx[1]*a[0]*my) - (mx[0]*mx[0]*a22) - (1*a[1]*a11);
        det32 = (1*a[0]*a[2]) + (mx[0]*a[1]*mx[1]) + (mx[0]*a[1]*mx[1]) - (mx[1]*a[0]*mx[1]) - (mx[0]*mx[0]*a[2]) - (a[1]*a[1]*1);
        b[2] = det31/det32;

        System.out.println("Нормоване рівняння регресії: ");
        System.out.printf("y = %.2f",b[0]);
        if (b[1] < 0 ) System.out.print(" - "); else System.out.print(" + ");
        System.out.printf("%.2f * x1", Math.abs(b[1]));
        if (b[2] < 0 ) System.out.print(" - "); else System.out.print(" + ");
        System.out.printf("%.2f * x2\n", Math.abs(b[2]));

        System.out.println("\nПеревірка: ");
        boolean ok = false;
        for (int i = 0; i < 3; i++) {
            if ((float)(b[0] + b[1]*x[i][0] + b[2]*x[i][1]) == (float)yAverage[i]) ok = true;
            else ok = false;
            System.out.printf("%.2f = %.2f\n", (b[0] + b[1]*x[i][0] + b[2]*x[i][1]),yAverage[i]);
        }
        if (ok) System.out.println("\nНормовані коефіцієнти рівняння регресії b0,b1,b2 визначено правильно");
        else System.out.println("Нормовані коефіцієнти рівняння регресії b0,b1,b2 визначено неправильно");

        // натуралізація коефіцієнтів

        double deltaX1, deltaX2, x10, x20, a0, a1, a2;

        deltaX1 = Math.abs(x1Max - x1Min)/2.;
        deltaX2 = Math.abs(x2Max - x2Min)/2.;
        x10 = (x1Max + x1Min)/2.;
        x20 = (x2Max + x2Min)/2.;

        a0 = b[0] - b[1]*x10/deltaX1 - b[2]*x20/deltaX2;
        a1 = b[1]/deltaX1;
        a2 = b[2]/deltaX2;

        System.out.println();
        System.out.println("Натуралізоване рівнання регресії : ");

        System.out.printf("y = %.2f",a0);
        if (a1 < 0 ) System.out.print(" - "); else System.out.print(" + ");
        System.out.printf("%.2f * x1", Math.abs(a1));
        if (a2 < 0 ) System.out.print(" - "); else System.out.print(" + ");
        System.out.printf("%.2f * x2\n", Math.abs(a2));

        System.out.println();
        System.out.println("Перевірка: ");

        System.out.printf("%.2f = %.2f\n", (a0 + a1*x1Min + a2*x2Min),yAverage[0]);
        System.out.printf("%.2f = %.2f\n", (a0 + a1*x1Max + a2*x2Min),yAverage[1]);
        System.out.printf("%.2f = %.2f\n", (a0 + a1*x1Min + a2*x2Max),yAverage[2]);

        if ((float)(a0 + a1*x1Min + a2*x2Min) == (float)yAverage[0] &&
                (float)(a0 + a1*x1Max + a2*x2Min) == (float)yAverage[1] &&
                (float)(a0 + a1* x1Min + a2*x2Max) == (float)yAverage[2]){
            System.out.println();
            System.out.println("Коефіцієнти натуралізованого рівняння регресії a0, a1, a2 визначено правильно");
        }
        else System.out.println("Коефіцієнти натуралізованого рівняння регресії a0, a1, a2 визначено неправильно");

    }
}