package com.milodevs;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public interface Converter {
    double convert(double amount, String fromCurrency, String toCurrency);
    String getCurrencies();
    boolean isValidCurrency(String currencyCode);
}

class CurrencyConverter implements Converter {
    private final Map<String, Double> exchangeRates;

    public CurrencyConverter() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.91);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 151.37);
        exchangeRates.put("INR", 82.35);
        exchangeRates.put("AUD", 1.61);
        exchangeRates.put("CAD", 1.39);
        exchangeRates.put("CHF", 0.94);
        exchangeRates.put("HKD", 7.85);
    }

    @Override
    public double convert(double amount, String fromCurrency, String toCurrency) {
        if (!isValidCurrency(fromCurrency) || !isValidCurrency(toCurrency)) {
            throw new IllegalArgumentException("Invalid currency code");
        }

        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);

        return amount * (toRate / fromRate);
    }

    @Override
    public String getCurrencies() {
        return String.join(", ", exchangeRates.keySet());
    }

    @Override
    public boolean isValidCurrency(String currencyCode) {
        return exchangeRates.containsKey(currencyCode);
    }
    
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        Scanner scanner = new Scanner(System.in);
        
        // Convert currencies list to array for indexed access
        String[] currencies = converter.getCurrencies().split(", ");
        
        System.out.println("Enter the amount to convert:");
        double amount = scanner.nextDouble();
        
        // Display numbered currency menu
        System.out.println("\nSelect a currency to convert from:");
        System.out.println("=======");
        for (int i = 0; i < currencies.length; i++) {
            System.out.printf("%d. %s%n", i + 1, currencies[i]);
        }
        System.out.println("=======");
        
        int fromCurrency = scanner.nextInt();
        String fromCurrencyCode = currencies[fromCurrency - 1];
        
        System.out.println("\nSelect a currency to convert to:");
        System.out.println("=======");
        for (int i = 0; i < currencies.length; i++) {
            System.out.printf("%d. %s%n", i + 1, currencies[i]);
        }
        System.out.println("=======");
        
        int toCurrency = scanner.nextInt();
        String toCurrencyCode = currencies[toCurrency - 1];
        
        scanner.close();
        double convertedAmount = converter.convert(amount, fromCurrencyCode, toCurrencyCode);
        System.out.println(amount + " " + fromCurrencyCode + " is " + convertedAmount + " " + toCurrencyCode);
    }
}