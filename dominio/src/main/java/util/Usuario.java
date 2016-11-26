package main.java.util;

import com.google.gson.Gson;

import main.java.entidad.Asesino;
import main.java.entidad.Elfo;
import main.java.entidad.Guerrero;
import main.java.entidad.Humano;
import main.java.entidad.Orco;
import main.java.entidad.Personaje;

public class Usuario {
	private String nombre;
	private String contraseña;
	private String gsonPersonaje;

	public Usuario(String nombre, String contraseña, String gsonPersonaje) {
		super();
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.gsonPersonaje = gsonPersonaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getGsonPersonaje() {
		return gsonPersonaje;
	}

	public void setGsonPersonaje(String gsonPersonaje) {
		this.gsonPersonaje = gsonPersonaje;
	}

	

}
