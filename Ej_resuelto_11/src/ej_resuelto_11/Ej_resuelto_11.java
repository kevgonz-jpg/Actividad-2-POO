package ej_resuelto_11;

import java.util.Scanner;

public class Ej_resuelto_11 {


    public static void main(String[] args) {
        
        double n1, n2, n3, mayor;

        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese el valor numerico de n1:");
        n1 = teclado.nextDouble();

        System.out.print("Ingrese el valor numerico de n2:");
        n2 = teclado.nextDouble();

        System.out.print("Ingrese el valor numerico de n3:");
        n3 = teclado.nextDouble();

        if ((n1 > n2) && (n1 > n3)) {
            mayor = n1;
        } else if (n2 > n3) {
            mayor = n2;
        } else {
            mayor = n3;
        }
        System.out.println("El mayor valor entre " + n1 + ", " + n2 + " y " + n3 + " es: " + mayor);
    }

    }
    
