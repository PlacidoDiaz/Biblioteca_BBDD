package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dao.LibroDao;
import excepciones.CamposVaciosException;
import modelo.Libro;

public class LibroController {

	private List<Libro>libros;
	Connection cn;
	
	public LibroController(Connection cn) {
		this.cn=cn;
	}

	public List<Libro> getLibros(String sql) throws SQLException, CamposVaciosException {
		LibroDao biblioteca=new LibroDao(cn);
		return biblioteca.getConsultaLibros(sql);

	}
	
	// ----------------------------------------------------------------
	
	public boolean agregar(String titulo,String autor, String editorial, String isbn, boolean prestado) throws CamposVaciosException, SQLException {
		boolean agregado=false;
		Libro libro=new Libro(titulo,autor,editorial,isbn);
		LibroDao libroDao=new LibroDao(cn);
		libroDao.agregarLibroPst(libro, prestado);
		agregado=true;
		return agregado;
	}
	
	// ----------------------------------------------------------------
	
	public int borrar(String sql) throws SQLException {
		int rows;
		LibroDao libroDao=new LibroDao(cn);
		rows=libroDao.borrarLibroPst(sql);
		return rows;
	}

	// ----------------------------------------------------------------
	
	public boolean editar(int id, String titulo, String autor, String editorial, String isbn, boolean prestado) throws CamposVaciosException, SQLException {
		boolean editado=false;
		Libro libro=new Libro(titulo,autor,editorial,isbn);
		LibroDao libroDao=new LibroDao(cn);
		libroDao.editadoPst(id,libro, prestado);
		editado=true;
		return editado;
	}

}