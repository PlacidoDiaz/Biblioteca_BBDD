package modelo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;

import excepciones.CamposVaciosException;

public class Libro {

	private int idlibros;
	private String titulo,autor,editorial,isbn;
	private boolean prestado;
	private LocalDate fechaPrestamo, fechaDevolucion,fechaAlta;
	
	public Libro(String titulo,String autor,String editorial,String isbn) throws CamposVaciosException { // faltan excepciones
		super();
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setEditorial(editorial);
		this.setIsbn(isbn);
		this.setPrestado(false);
		java.util.Date fecha=new java.util.Date();
		this.setFechaAlta(new java.sql.Timestamp(fecha.getTime()));
		this.setFechaPrestamo(null);
		this.setFechaDevolucion(null);
	}

	@Override
	public String toString() {	

		return  idlibros +","+ titulo +","+ autor +","+ editorial +","+ isbn +","+ prestado +","+ fechaPrestamo +","+ fechaDevolucion +","+ fechaAlta;
	}

	public Libro(int idLibros,String titulo, String autor, String editorial, String isbn, boolean prestado, Date fechaPrestamo, Date fechaDevolucion, Timestamp fechaAlta) throws CamposVaciosException { // faltan excepciones
		this.setIdlibros(idLibros);
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setEditorial(editorial);
		this.setIsbn(isbn);
		this.setPrestado(prestado);
		this.setFechaAlta(fechaAlta);
		this.setFechaPrestamo(fechaPrestamo);
		this.setFechaDevolucion(fechaDevolucion);
	}
	

	public String getIdlibros() {
		return Integer.toString(idlibros);
	}

	public void setIdlibros(int idlibros) {
		this.idlibros = idlibros;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws CamposVaciosException {
		if (titulo.length()==0) throw new CamposVaciosException();
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) throws CamposVaciosException {
		if (autor.length()==0) throw new CamposVaciosException();
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) throws CamposVaciosException {
		if (editorial.length()==0) throw new CamposVaciosException();
		this.editorial = editorial;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws CamposVaciosException {
		if (isbn.length()==0) throw new CamposVaciosException();
		// comprobar ISBN
		this.isbn = isbn;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public LocalDate getFechaPrestamo() {
		if (fechaPrestamo==null) 
			fechaPrestamo=null;
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		
		if(fechaPrestamo != null) 
			this.fechaPrestamo=fechaPrestamo.toLocalDate();
		 else 
			this.fechaPrestamo=null;
		
	}

	public LocalDate getFechaDevolucion() {
		if (fechaDevolucion==null) 
			fechaDevolucion=null;
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		
		if(fechaDevolucion != null) 
			this.fechaDevolucion=fechaDevolucion.toLocalDate();
		 else 
			this.fechaDevolucion=null;	
	
	}

	public LocalDate getFechaAlta() {
		if (fechaAlta==null) 
			fechaAlta=null;
		 
		return fechaAlta;
	}

	public void setFechaAlta(Timestamp fechaAlta) {
		
		if(fechaAlta != null)
			this.fechaAlta=fechaAlta.toLocalDateTime().toLocalDate();
		 else 
			this.fechaAlta=null;
	}

}