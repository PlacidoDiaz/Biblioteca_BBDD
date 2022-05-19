package dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import excepciones.CamposVaciosException;
import modelo.Libro;

public class LibroDao {

	private Connection cn;

	public LibroDao(Connection cn) {
		this.cn=cn;
	}
	
	public List<Libro> getConsultaLibros(String sql) throws SQLException, CamposVaciosException {
		List<Libro> libros=new ArrayList<Libro>();
		PreparedStatement pst=cn.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		Libro libro;
		while (rs.next()) {
			int id=rs.getInt("idlibros");
			String titulo=rs.getString("titulo");
			String autor=rs.getString("autor");
			String editorial=rs.getString("editorial");
			String isbn=rs.getString("isbn");
			boolean prestado=rs.getBoolean("prestado");
			Date fechaPrestamo=rs.getDate("fechaPrestamo");
			Date fechaDevolucion=rs.getDate("fechaDevolucion");
			Timestamp fechaAlta=rs.getTimestamp("fechaAlta");	
			
			//LocalDateTime now = LocalDateTime.now();
	        //Timestamp fechaAlkta = Timestamp.valueOf(now);
	   
			
			//libro=new Libro(id,titulo,autor,editorial,isbn,prestado,fechaPrestamo,fechaDevolucion,fechaAlta);
			libro=new Libro(id, titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaDevolucion, fechaAlta);
			libros.add(libro);
			libro=null;
		}
		return libros;
		
	}

	public boolean agregarLibroPst(Libro libro, boolean prestado) throws SQLException {
		boolean agregado=false;
		
		String titulo=libro.getTitulo();
		String autor=libro.getAutor();
		String editorial=libro.getEditorial();
		String isbn=libro.getIsbn();
		Date fprest = new Date(System.currentTimeMillis());
		Timestamp ts = Timestamp.from(Instant.now());
		
		String sql = "insert into libros values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = cn.prepareStatement(sql);
		java.util.Date date=new java.util.Date();
		Timestamp time=new Timestamp(date.getTime());
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, titulo);
		preparedStatement.setString(3, autor);
		preparedStatement.setString(4, editorial);
		preparedStatement.setBoolean(5, prestado);
		
		if (prestado==true) 
			preparedStatement.setDate(6, fprest);	// dia de hoy
		 else 
			preparedStatement.setDate(6, null);
		
		preparedStatement.setDate(7, null);
		preparedStatement.setString(8, isbn);
		preparedStatement.setTimestamp(9, ts); // alta
		preparedStatement.executeUpdate();
		preparedStatement=null;
		
		agregado=true;
		
		return agregado;
	}

	public int borrarLibroPst(String sql) throws SQLException {
		int rows;
		PreparedStatement prepareStatement = cn.prepareStatement(sql);
		rows=prepareStatement.executeUpdate();
		return rows;
	}
	
	public boolean editadoPst(int id, Libro libro, boolean prestado) throws SQLException {
		boolean agregado=false;
		
		//String id=libro; // id
		String titulo=libro.getTitulo();
		String autor=libro.getAutor();
		String editorial=libro.getEditorial();
		String isbn=libro.getIsbn();
		Date fprest = new Date(System.currentTimeMillis());
		Timestamp ts = Timestamp.from(Instant.now());
		
		String sql = "update libros Set titulo=? , autor=? , editorial=? , isbn=? , prestado=? , fechaprestamo=?, fechadevolucion=? Where idlibros=?" ;
		PreparedStatement preparedStatement = cn.prepareStatement(sql);
	
		preparedStatement.setString(1, titulo);
		preparedStatement.setString(2, autor);
		preparedStatement.setString(3, editorial);
		preparedStatement.setString(4, isbn);
		preparedStatement.setBoolean(5, prestado);
		preparedStatement.setTimestamp(7, null);
		
		if (prestado==true) 
			preparedStatement.setDate(6, fprest);	// dia de hoy
		 else {
			preparedStatement.setDate(6, null);
			preparedStatement.setTimestamp(7, ts);
		 }
		
		preparedStatement.setInt(8, id);
	
		preparedStatement.executeUpdate();
		preparedStatement=null;
		
		agregado=true;
		
		return agregado;
	}
	
}
