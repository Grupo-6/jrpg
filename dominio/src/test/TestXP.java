package test;

import org.junit.Assert;
import org.junit.Test;

import personajes.Humano;
import personajes.Personaje;

public class TestXP {
	
	@Test	
	public void aumentarXP(){
		
		Personaje juan = new Humano();
		juan.aumentarXP(3);
		Assert.assertEquals(60, juan.getExperiencia());
		
	}
	
	public void aumentarNivel(){
		
		Personaje juan = new Humano();
		juan.aumentarXP(10);
		Assert.assertEquals(2, juan.getLvl());
		
		
	}
		
	public void xpAcumulativa(){
		
		Personaje juan = new Humano();
		juan.aumentarXP(10);
		Assert.assertEquals(100, juan.getExperiencia());
	}
	@Test	
	public void aumentarAtributo(){
		
		Personaje juan = new Humano();
		juan.aumentarXP(10);
		Assert.assertEquals(16, juan.obtenerPuntosDeAtaque());
	}
	
	
	
}
