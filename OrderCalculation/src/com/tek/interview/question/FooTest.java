package com.tek.interview.question;

import static org.junit.Assert.*;

import org.junit.Test;

public class FooTest {
    calculator calcul = new calculator();
    Order order = new Order();
   
    @Test
    public void testOrderSize(){

          try {
               order.add(new OrderLine(new Item("coke",17.52f),1));
               order.add(new OrderLine(new Item("Milk",20.20f),1));
          } catch (Exception e) {
               e.printStackTrace();
          }
          assertEquals("Size should be 2",2,order.size());
    }
   
    @Test
    public void testGetItem(){
          Item item = new Item("Paper",10.10f);
          OrderLine orderLine = null;
          try {
               orderLine = new OrderLine(item,1);
          } catch (Exception e) {

               e.printStackTrace();
          }
          assertTrue("Are we getting item? ",item==orderLine.getItem());
   
    }
   
    @Test
    public void testItemShouldbeCreated(){
          assertNotNull("Item not null", new Item("Mobile",15.15f));
    }
}
