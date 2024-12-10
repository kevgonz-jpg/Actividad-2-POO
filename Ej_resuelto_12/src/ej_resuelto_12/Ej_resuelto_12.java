package ej_resuelto_12;

import java.util.Scanner;

public class Ej_resuelto_12 {

    public static void main(String[] args) {
        double numero_horas, valor_hora_trabajo, salario_total = 0;
        String nombre;

        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese el nombre del trabajador:");
        nombre = teclado.next();

        System.out.print("Ingrese el número de horas trabajadas:");
        numero_horas = teclado.nextDouble();

        System.out.print("Ingrese el valor de la hora trabajada:");
        valor_hora_trabajo = teclado.nextDouble();

        if (numero_horas <= 40) {
            // Cálculo del salario sin horas extra
            salario_total = numero_horas * valor_hora_trabajo;
        } else if (numero_horas <= 48) {
            // Cálculo del salario con horas extra (hasta 8 horas)
            double horas_extra = numero_horas - 40;
            salario_total = (40 * valor_hora_trabajo) + (horas_extra * valor_hora_trabajo * 2);
        } else {
            // Cálculo del salario con más de 48 horas
            double horas_extra = 8; // Máximo 8 horas extras al doble
            double horas_adicionales = numero_horas - 48; // Horas adicionales al triple
            salario_total = (40 * valor_hora_trabajo) + (horas_extra * valor_hora_trabajo * 2) 
                            + (horas_adicionales * valor_hora_trabajo * 3);
        }

        System.out.println("El salario semanal de " + nombre + " es: " + salario_total);
    }

    }
    
