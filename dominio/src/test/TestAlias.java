package test;

import org.junit.Assert;
import org.junit.Test;

import personajes.Humano;
import personajes.Personaje;


public class TestAlias {
	
	@Test
	public void probarAlias(){
		
		Personaje juan = new Humano();
		juan.insertarAlias();
		Assert.assertEquals("rodrigo", juan.getAlias());
		
	}
}
