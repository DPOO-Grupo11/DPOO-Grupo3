package clases;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

public class SistemaAlquiler {
	private ContenedorDeDatos datos;
	
	
	// toca pedir archivo de persistencia cada vez que se inicie programa
	private static final String dirDatos = System.getProperty("user.dir");;

	public SistemaAlquiler() throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		cargarDatos();
	}

	/*
	 * funciones
	 */
	/**
	 * si se lanza error, significa que no se pudieron cargar los datos los datos se
	 * cargan automaticamente, si no existe un archivo de
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void cargarDatos() throws IOException, ClassNotFoundException {
		// cargar bytes de archivo
		File archivoDatos = new File(dirDatos, "datos");
		if (archivoDatos.exists()) {
			byte[] bytes = Files.readAllBytes(archivoDatos.toPath());
			// convertir bytes a objeto
			ByteArrayInputStream bs = new ByteArrayInputStream(bytes); // bytes es el byte[]
			ObjectInputStream is = new ObjectInputStream(bs);
			datos = (ContenedorDeDatos) is.readObject();
			is.close();
		} else {
			datos = new ContenedorDeDatos();
		}
	}

	/**
	 * si lanza un error, significa que no se pudieron guardar datos
	 * 
	 * @throws IOException
	 */
	public void guardarDatos() throws IOException {
		// convertir objeto de datos a bytes
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);
		os.writeObject(datos);
		os.close();
		byte[] bytes = bs.toByteArray();
		// guardar bytes en archivo
		File archivoDatos = new File(dirDatos, "datos");
		if (!archivoDatos.exists()) {
			if (!archivoDatos.createNewFile())
				throw new FileNotFoundException("no se pudo crear el archivo");
		}
		try (FileOutputStream outputStream = new FileOutputStream(archivoDatos)) {
			outputStream.write(bytes);
		}
	}
	
	
	
	public Usuario getUsuario(String usuario, String clave)
	{
		Usuario usuarioInteres = datos.getUsuario(usuario, clave);
		return usuarioInteres;
	}
	
	public ArrayList<Sede> getSedes()
	{
		Map<String, Sede> mapaSedes = datos.getSedes();
		ArrayList<Sede> listaSedes = (ArrayList<Sede>) mapaSedes.values();
		return listaSedes;
	}
	
	public void registroAdmin(String usuario, String clave, String sede) 
	{
		Map<String, Admin> mapaAdmins = datos.getAdmins();
		if (!mapaAdmins.containsKey(usuario)) 
		{
            // El admin no existe, agregarlo
			Admin nuevoAdmin = new Admin(usuario, clave, sede);
			mapaAdmins.put(usuario, nuevoAdmin);
			datos.actUsuarios();
        } 
		else
        {
            // El admin ya existe
            System.out.println("El nombre de usuario ya esta en uso. Intenta con otro");
        }
		
	}
	
	public void registroCliente(String usuario, String clave, String nombres, String numeroTelefono, String direccion,
			String fechaNacimiento, String nacionalidad, String imagenDocumentoIdentidad,
			String numeroLicencia, String paisExpedicion, String fechaVencimientoLicencia, String imagen, String numeroTarjeta, String fechaVencimientoTarjeta, String cvv) 
	{
		Map<String, Cliente> mapaClientes = datos.getClientes();
		if (!mapaClientes.containsKey(usuario)) 
		{
            // El cliente no existe, agregarlo
			LicenciaDeConduccion licencia = new LicenciaDeConduccion(numeroLicencia, paisExpedicion, fechaVencimientoLicencia, imagen);
			TarjetaDeCredito tarjetaDeCredito = new TarjetaDeCredito( numeroTarjeta, fechaVencimientoTarjeta, cvv);
			Cliente nuevoCliente = new Cliente(usuario, clave, nombres, numeroTelefono, direccion, fechaNacimiento, nacionalidad, 
					imagenDocumentoIdentidad, licencia, tarjetaDeCredito);
			mapaClientes.put(usuario, nuevoCliente);
			datos.actUsuarios();
        } 
		else
        {
            // El cliente ya existe
            System.out.println("El nombre de usuario ya esta en uso. Intenta con otro");
        }
		
	}

	public void crearSede(String nomSede, String ubiSede, int hrsASede, int hrsCSede)
	{
		try 
		{
			Range<Integer> rangeHrs = new Range<Integer>(hrsASede, hrsCSede);
			
			HorarioDeAtencion hrs = new HorarioDeAtencion(rangeHrs);
						
			ArrayList<Empleado> empleados = new ArrayList<Empleado>();
			
			Map<String, Sede> mapaSedes = datos.getSedes();
			if (!mapaSedes.containsKey(nomSede)) 
			{
	            // La sede no existe, agregarla
				Sede nuevaSede = new Sede(nomSede, ubiSede, hrs, empleados);
				mapaSedes.put(nomSede, nuevaSede);
	        } 
			else
	        {
				// La sede ya existe 
	            System.out.println("El nombre de usuario ya esta en uso. Intenta con otro");
	        }
			
		}
		catch (IllegalArgumentException e) 
		{
            System.out.println("Rango no válido: " + e.getMessage());
        }	
		
	}


	public void modificarNombreSede(String nuevoNomSede, String actNomSede) 
	{
		Map<String, Sede> mapaSedes = datos.getSedes();
		if (mapaSedes.containsKey(actNomSede)) 
		{
			Sede sedeActual = mapaSedes.get(actNomSede);
			
			mapaSedes.remove(actNomSede);
			sedeActual.setNombre(nuevoNomSede);
			mapaSedes.put(actNomSede, sedeActual);
        } 
		else
        {
            System.out.println("La sede ingresada no fue encontrada ");
        }
		
	}

	public void modificarHorarioSede(String nomSede, int hrsASede, int hrsCSede)
	{
		Map<String, Sede> mapaSedes = datos.getSedes();
		if (mapaSedes.containsKey(nomSede)) 
		{
			Sede sedeActual = mapaSedes.get(nomSede);
			Range<Integer> rangeHrs = new Range<Integer>(hrsASede, hrsCSede);
			HorarioDeAtencion hrs = new HorarioDeAtencion(rangeHrs);
			sedeActual.setHorariosDeAtencion(hrs);
        } 
		else
        {
            System.out.println("La sede ingresada no fue encontrada ");
        }
	}

	
	

	
}
	