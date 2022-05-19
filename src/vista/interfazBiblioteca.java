package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.LibroController;
import dao.DbConnection;
import excepciones.CamposVaciosException;
import modelo.Libro;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class interfazBiblioteca extends JFrame {

	private JPanel contentPane, panel;
	private JScrollPane scrollPane;
	DefaultTableModel modelo;	
	private JPanel panel_Libros,panel_Navegador,panel_Mantenimiento;
	JButton btnPrimero,btnAtras,btnSiguiente,btnUltimo,btnNuevo,btnEditar,btnBorrar,btnGuardar,btnRetroceder,btnFiltrar;
	private JTextField textIDLibros,textTitulos,textFiltra,textTitulo,textAutor,textEditorial,textIsbn,textFechaPrestamo,textIdlibros,textFechaDevolucion,textFechaAlta;
	private JCheckBox chckbxPrestado;
	JComboBox cmbFiltrado;
	DefaultComboBoxModel filtrado;
	private JScrollPane scrollPane_1;
	private JTable tableLibros;
	private JLabel lblTitulo,lblAutor,lblEditorial,lblIsbn,lblFechaPrestamo,lblidLibros,lblFechaDevolucion,lblFechaAlta;
	List<Libro>bibliteca;
	int indice=0;
	String modo="";
	
	public interfazBiblioteca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1227, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		elementos();
		eventos();
		
		setVisible(true);
		
	}

	private void eventos() {
		navegador(); // |< < > >| 
		mantenimiento(); // N E B G R
		filtrar();
		
	}

	private void filtrar() {
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprueba porque campo se filtra
				String filtrado=cmbFiltrado.getSelectedItem().toString();
				String campo="";
				
				if (filtrado=="Todos") 
					campo="idlibros";
				 else if (filtrado=="Autores") 
					 campo="autor";
				 else if (filtrado=="Editorial") 
					 campo="editorial";
				 else if (filtrado=="Libro") 
					 campo="titulo";

				String cadenaBusqueda=textFiltra.getText();
				String sql="select * from libros where "+campo+" like '"+cadenaBusqueda+"%'";
			
				DbConnection dbc=new DbConnection();
				Connection cn=dbc.getConnection();
				LibroController libroController=new LibroController(cn);
				
				libroController=null;
				dbc.disconnect();
				dbc=null;
				
				cargarGridFiltrado(sql);
			}
		});
		
	}

	protected void cargarGridFiltrado(String sql) {
		// VACIA LA TABLA
				modelo.getDataVector().removeAllElements();
				tableLibros = new JTable(modelo);
				
				// RELLENA UN ARRAY CON TODOS LOS DATOS
				DbConnection dbc=new DbConnection();
				Connection cn=dbc.getConnection();	// instanciamos objeto con los datos de la conexion
				LibroController libroController=new LibroController(cn);
			
				try {
					bibliteca=libroController.getLibros(sql);
				} catch (SQLException | CamposVaciosException e) {
					System.out.println(e.getMessage());
				}
				dbc.disconnect();
				
				// INTRODUCE LOS DATOS EN LA TABLA
				for (Libro l : bibliteca) {
					String[]fila = l.toString().split(",");
					modelo.addRow(fila);
				}
		
	}

	private void mantenimiento() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonesHabilitados(1);
				modo="nuevo";
				
				textIdlibros.setText("");
				textTitulo.setText("");
				textTitulo.setEditable(true);
				textAutor.setText("");
				textAutor.setEditable(true);
				textEditorial.setText("");
				textEditorial.setEditable(true);
				textIsbn.setText("");
				textIsbn.setEditable(true);
				textFechaPrestamo.setText("");
				textFechaDevolucion.setText("");
				textFechaAlta.setText("");
				chckbxPrestado.setEnabled(true);
				
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonesHabilitados(1);
				modo="editar";
				
				textTitulo.setEditable(true);
				textAutor.setEditable(true);
				textEditorial.setEditable(true);
				textIsbn.setEditable(true);
				chckbxPrestado.setEnabled(true);
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonesHabilitados(1);
				modo="borrar";
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonesHabilitados(0);
				
				if (modo=="nuevo") {
					
					DbConnection dbc=new DbConnection();
					Connection cn=dbc.getConnection();
					String titulo=textTitulo.getText();
					String autor=textAutor.getText();
					String editorial=textEditorial.getText();
					String isbn=textIsbn.getText();
					boolean prestado=chckbxPrestado.isSelected();
					
					//Instanciamos el controlador
					LibroController libroController=new LibroController(cn);
					
					try {
						if (libroController.agregar(titulo, autor, editorial, isbn, prestado));
						System.err.println("El libro se ha agregado correctamente");
					} catch (CamposVaciosException | SQLException e1) {
						System.err.println(e1.getMessage());
					}
						
					libroController=null;
					dbc.disconnect();
					dbc=null;
				} else if (modo=="editar") {
					
					DbConnection dbc=new DbConnection();
					Connection cn=dbc.getConnection();
					int id=Integer.parseInt(textIdlibros.getText());
					String titulo=textTitulo.getText();
					String autor=textAutor.getText();
					String editorial=textEditorial.getText();
					String isbn=textIsbn.getText();
					boolean prestado=chckbxPrestado.isSelected();
					
					LibroController libroController=new LibroController(cn);
					try {
						if (libroController.editar(id, titulo, autor, editorial, isbn, prestado));
						System.err.println("El libro se ha editado correctamente");
					} catch (CamposVaciosException | SQLException e1) {
						System.err.println(e1.getMessage());
					}
					
				} else if (modo=="borrar") {
					int input = JOptionPane.showConfirmDialog(null, "¿Estas seguro?"); // 0 = si , 1 = no , 2 = no
					
					if (input==0) {
						String campo="idlibros"; 
						String cadenaBusqueda=textIdlibros.getText();
						
						String sql="delete from libros where "+campo+" = '"+cadenaBusqueda+"'";
						
						DbConnection dbc=new DbConnection();
						Connection cn=dbc.getConnection();
						LibroController libroController=new LibroController(cn);
					
						try {
							int rows=libroController.borrar(sql);
							if (rows>0) 
								System.err.println("Se borraron:"+rows+" registros");
							else 
								System.err.println("No se encontró NINGUN REGISTRO");
							
						} catch (SQLException e3) {
								System.err.println(e3.getMessage());
						} catch (IndexOutOfBoundsException e3) {
								System.err.println("");
						}
						
						libroController=null;
						dbc.disconnect();
						dbc=null;
						indice--;
					} 
					
				}
				
				cargarGrid();
				cargarFormulario();
			}
		});
		btnRetroceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonesHabilitados(0);
				cargarFormulario();
			}
		});
	}

	private void navegador() {
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(indice!=bibliteca.size()-1) {
					indice+=1;
				}
				cargarFormulario();
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(indice!=0) {
					indice-=1;
				}
				cargarFormulario();
			}
		});
		
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indice=0;
				cargarFormulario();
			}
		});
		
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indice=bibliteca.size()-1;
				cargarFormulario();
			}
		});
		
	}

	protected void cargarFormulario() {
		String[]datosLibro=bibliteca.get(indice).toString().split(",");
		
		
		textIdlibros.setText(datosLibro[0]);
		textTitulo.setText(datosLibro[1]);
		textAutor.setText(datosLibro[2]);
		textEditorial.setText(datosLibro[3]);
		textIsbn.setText(datosLibro[4]);
		textFechaPrestamo.setText(datosLibro[6]);
		textFechaDevolucion.setText(datosLibro[7]);
		textFechaAlta.setText(datosLibro[8]);
		if ((datosLibro[5]).equals("true")) {
			chckbxPrestado.setSelected(true);
		} else {
			chckbxPrestado.setSelected(false);
		}
		
		textTitulo.setEditable(false);
		textIdlibros.setEditable(false);
		textTitulo.setEditable(false);
		textAutor.setEditable(false);
		textEditorial.setEditable(false);
		textIsbn.setEditable(false);
		textFechaPrestamo.setEditable(false);
		textFechaDevolucion.setEditable(false);
		textFechaAlta.setEditable(false);
		chckbxPrestado.setEnabled(false);
		
	}

	private void elementos() {
	
		
		// BOTONES Y CAJAS DE TEXTO
		botonesNavegador();	
		cargarGrid(); // vacia, recarga y carga
		
		txtYlabelLibro();
		
		botonesHabilitados(0); // Coloca modo Inicio los botones
		cargarFormulario();
		
		
	}

	private void botonesHabilitados(int modo) { // Inicio | Guardar | Retroceder -> 0 , (N E B) -> 2
		if (modo==0) {
			btnAtras.setEnabled(true);
			btnSiguiente.setEnabled(true);
			btnPrimero.setEnabled(true);
			btnUltimo.setEnabled(true);
			
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			btnBorrar.setEnabled(true);
			
			btnGuardar.setEnabled(false);
			btnRetroceder.setEnabled(false);
			
			
		} else if (modo==1) {
			btnAtras.setEnabled(false);
			btnSiguiente.setEnabled(false);
			btnPrimero.setEnabled(false);
			btnUltimo.setEnabled(false);
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(false);
			btnBorrar.setEnabled(false);
			
			btnGuardar.setEnabled(true);
			btnRetroceder.setEnabled(true);
			
		}
		
		
	}

	private void cargarGrid() {
		// VACIA LA TABLA
		modelo.getDataVector().removeAllElements();
		tableLibros = new JTable(modelo);
		
		// RELLENA UN ARRAY CON TODOS LOS DATOS
		DbConnection dbc=new DbConnection();
		Connection cn=dbc.getConnection();	// instanciamos objeto con los datos de la conexion
		LibroController libroController=new LibroController(cn);
	
		String sql="select * from libros";
		try {
			bibliteca=libroController.getLibros(sql);
		} catch (SQLException | CamposVaciosException e) {
			System.out.println(e.getMessage());
		}
		dbc.disconnect();
		
		// INTRODUCE LOS DATOS EN LA TABLA
		for (Libro l : bibliteca) {
			String[]fila = l.toString().split(",");
			modelo.addRow(fila);
		}
		
	}

	private void botonesNavegador() {						// Botones del navegar y Mantenimiento
		panel_Navegador = new JPanel();
		panel_Navegador.setBorder(new TitledBorder(null, "Navegador", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Navegador.setBounds(58, 432, 234, 82);
		contentPane.add(panel_Navegador);
		panel_Navegador.setLayout(null);
		
		btnPrimero = new JButton("");
		btnPrimero.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/navPri.jpg")));
		btnPrimero.setBounds(10, 24, 46, 44);
		panel_Navegador.add(btnPrimero);
		
		btnAtras = new JButton("");
		btnAtras.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/navIzq.jpg")));
		btnAtras.setBounds(66, 24, 46, 44);
		panel_Navegador.add(btnAtras);
		
		btnSiguiente = new JButton("");
		btnSiguiente.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/navDer.jpg")));
		btnSiguiente.setBounds(122, 24, 46, 44);
		panel_Navegador.add(btnSiguiente);
		
		btnUltimo = new JButton("");
		btnUltimo.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/navUlt.jpg")));
		btnUltimo.setBounds(178, 24, 46, 44);
		panel_Navegador.add(btnUltimo);
		
		panel_Mantenimiento = new JPanel();
		panel_Mantenimiento.setBorder(new TitledBorder(null, "Mantenimiento Libros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Mantenimiento.setBounds(58, 34, 292, 82);
		contentPane.add(panel_Mantenimiento);
		panel_Mantenimiento.setLayout(null);
		
		btnNuevo = new JButton("");
		btnNuevo.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/botonAgregar.jpg")));
		btnNuevo.setBounds(10, 24, 46, 44);
		panel_Mantenimiento.add(btnNuevo);
		
		btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/botonEditar.jpg")));
		btnEditar.setBounds(66, 24, 46, 44);
		panel_Mantenimiento.add(btnEditar);
		
		btnBorrar = new JButton("");
		btnBorrar.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/borrar.jpg")));
		btnBorrar.setBounds(122, 24, 46, 44);
		panel_Mantenimiento.add(btnBorrar);
		
		btnGuardar = new JButton("");
		btnGuardar.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/botonGuardar.jpg")));
		btnGuardar.setBounds(178, 24, 46, 44);
		panel_Mantenimiento.add(btnGuardar);
		
		btnRetroceder = new JButton("");
		btnRetroceder.setIcon(new ImageIcon(interfazBiblioteca.class.getResource("/Images/botonDeshacer.jpg")));
		btnRetroceder.setBounds(234, 24, 46, 44);
		panel_Mantenimiento.add(btnRetroceder);
		
		JLabel lblConsulta = new JLabel("Consulta");
		lblConsulta.setBounds(426, 34, 73, 14);
		contentPane.add(lblConsulta);
		
		filtrado=new DefaultComboBoxModel();
		filtrado.addElement("Todos");
		filtrado.addElement("Autores");
		filtrado.addElement("Editorial");
		filtrado.addElement("Libro");
		
		cmbFiltrado = new JComboBox(filtrado);
		cmbFiltrado.setBounds(426, 59, 112, 22);
		contentPane.add(cmbFiltrado);
		
		textFiltra = new JTextField();
		textFiltra.setBounds(557, 60, 193, 22);
		contentPane.add(textFiltra);
		textFiltra.setColumns(10);
		
		btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setBounds(770, 59, 89, 23);
		contentPane.add(btnFiltrar);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(426, 114, 775, 400);
		contentPane.add(scrollPane_1);
		
		modelo=new DefaultTableModel();
		tableLibros = new JTable(modelo);
		tableLibros.setEnabled(false);
		scrollPane_1.setViewportView(tableLibros);
		
		String[]titulos= {"IDLibros","Titulos","Autor","Editorial","Isbn","Prestado","FechaPrestamo","FechaDevolucion","FechaAlta"};
		modelo.setRowCount(0);
		modelo.setColumnCount(0);
		modelo.setColumnIdentifiers(titulos);
		
	}
	
	private void txtYlabelLibro() {							// Cajas de texto del panel Libro
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitulo.setBounds(58, 160, 73, 22);
		contentPane.add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setBounds(172, 160, 151, 22);
		contentPane.add(textTitulo);
		textTitulo.setColumns(10);
		textTitulo.setText(bibliteca.get(0).getTitulo());
		
		lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAutor.setBounds(58, 192, 73, 22);
		contentPane.add(lblAutor);
		
		textAutor = new JTextField();
		textAutor.setColumns(10);
		textAutor.setBounds(172, 192, 151, 22);
		contentPane.add(textAutor);
		textAutor.setText(bibliteca.get(0).getAutor());
		
		lblEditorial = new JLabel("Editorial");
		lblEditorial.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEditorial.setBounds(58, 224, 73, 22);
		contentPane.add(lblEditorial);
		
		textEditorial = new JTextField();
		textEditorial.setColumns(10);
		textEditorial.setBounds(172, 224, 151, 22);
		contentPane.add(textEditorial);
		textEditorial.setText(bibliteca.get(0).getEditorial());
		
		lblIsbn = new JLabel("Isbn");
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIsbn.setBounds(58, 256, 73, 22);
		contentPane.add(lblIsbn);
		
		textIsbn = new JTextField();
		textIsbn.setColumns(10);
		textIsbn.setBounds(172, 256, 151, 22);
		contentPane.add(textIsbn);
		textIsbn.setText(bibliteca.get(0).getIsbn());
		
		lblFechaPrestamo = new JLabel("Fecha Prestamo");
		lblFechaPrestamo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaPrestamo.setBounds(58, 318, 94, 22);
		contentPane.add(lblFechaPrestamo);
		
		textFechaPrestamo = new JTextField();
		textFechaPrestamo.setColumns(10);
		textFechaPrestamo.setBounds(172, 320, 151, 22);
		contentPane.add(textFechaPrestamo);
		textFechaPrestamo.setText(bibliteca.get(0).getFechaPrestamo().toString());
		
		lblidLibros = new JLabel("idLibros");
		lblidLibros.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblidLibros.setBounds(58, 126, 73, 22);
		contentPane.add(lblidLibros);
		
		textIdlibros = new JTextField();
		textIdlibros.setColumns(10);
		textIdlibros.setBounds(172, 126, 151, 22);
		contentPane.add(textIdlibros);
		textIdlibros.setText(bibliteca.get(0).getIdlibros());
		
		lblFechaDevolucion = new JLabel("Fecha Devolucion");
		lblFechaDevolucion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaDevolucion.setBounds(58, 350, 104, 22);
		contentPane.add(lblFechaDevolucion);
		
		textFechaDevolucion = new JTextField();
		textFechaDevolucion.setColumns(10);
		textFechaDevolucion.setBounds(172, 352, 151, 22);
		contentPane.add(textFechaDevolucion);
		textFechaDevolucion.setText(bibliteca.get(0).getFechaDevolucion().toString());
		
		lblFechaAlta = new JLabel("Fecha Alta");
		lblFechaAlta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaAlta.setBounds(58, 382, 73, 22);
		contentPane.add(lblFechaAlta);
		
		textFechaAlta = new JTextField();
		textFechaAlta.setColumns(10);
		textFechaAlta.setBounds(172, 384, 151, 22);
		contentPane.add(textFechaAlta);
		textFechaAlta.setText(bibliteca.get(0).getFechaAlta().toString());
		
		chckbxPrestado = new JCheckBox("Prestado");
		chckbxPrestado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxPrestado.setBounds(59, 291, 93, 21);
		contentPane.add(chckbxPrestado);
		chckbxPrestado.setSelected(bibliteca.get(0).isPrestado());
	}
}
