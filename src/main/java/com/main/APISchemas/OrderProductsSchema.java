package com.main.APISchemas;

import java.util.HashMap;
import java.util.Map;


public class OrderProductsSchema {

    // Product serial number : wanted count
   public Map<String, Integer> products;

   OrderProductsSchema(){
       products = new HashMap<>();
   }
}
