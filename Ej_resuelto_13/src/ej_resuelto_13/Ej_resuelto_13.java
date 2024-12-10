package ej_resuelto_13;

import java.util.Scanner;

public class Ej_resuelto_13 {
     public static void main(String[] args) {

    double valorCompra, descuento, valorFinal;
        String colorBolita;

        Scanner teclado = new Scanner(System.in);

        System.out.println("Ingrese el valor total de la compra:");
        valorCompra = teclado.nextDouble();

        System.out.println("Ingrese el color de la bolita (blanca, verde, amarilla, azul, roja):");
        colorBolita = teclado.next().toLowerCase(); // Convertimos a minúsculas para facilitar comparación

        switch (colorBolita) {
            case "blanca":
                descuento = 0;
                break;
            case "verde":
                descuento = 0.10; // 10% de descuento
                break;
            case "amarilla":
                descuento = 0.25; // 25% de descuento
                break;
            case "azul":
                descuento = 0.50; // 50% de descuento
                break;
            case "roja":
                descuento = 1.00; // 100% de descuento
                break;
            default:
                System.out.println("Color de bolita no válido. Por favor, intente de nuevo.");
                return; // Finaliza el programa si el color es inválido
        }

        // Cálculo del valor final
        valorFinal = valorCompra * (1 - descuento);

        System.out.println("El descuento aplicado es del " + (descuento * 100) + "%");
        System.out.println("El valor final que debe pagar es: $" + valorFinal);
    }    
}