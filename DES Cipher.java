/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package des.cipher;

import java.security.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import java.io.*;

/**
 *
 * @author Latorre
 */

public class Des_Cipher
{
	public static void main(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			formaDeUso();
			System.exit(1);
		}
		
		System.out.println("\nIniciando el Programa (DES Cipher)...");
		System.out.println("\n1. Generando clave DES");
		KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
		generadorDES.init(56);
		SecretKey clave = generadorDES.generateKey();

		System.out.print("Clave Obtenida:");
		cargarBytesClave(clave.getEncoded());
		System.out.println();

		Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
		System.out.println("\n2. Cifrando (DES) " +args[0] + " -> "+args[0]+".cifrado");

		cifrador.init(Cipher.ENCRYPT_MODE, clave);
		
		byte[] buffer = new byte[1000];
		byte[] bufferCifrado;

		FileInputStream in = new FileInputStream(args[0]);
		FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
		
		int bytesLeidos = in.read(buffer, 0, 1000);
		while (bytesLeidos != -1)
		{
			bufferCifrado = cifrador.update(buffer, 0, bytesLeidos);
			out.write(bufferCifrado);
			bytesLeidos = in.read(buffer, 0, 1000);
		}
		bufferCifrado = cifrador.doFinal();
		out.write(bufferCifrado);
		
		in.close();
		out.close();
		
		System.out.println("\n3. Descifrando (DES) "+args[0]+".cifrado -> "+args[0]+".descifrado");
		cifrador.init(Cipher.DECRYPT_MODE, clave);

		in = new FileInputStream(args[0]+".cifrado");
		out = new FileOutputStream(args[0]+".descifrado");
		byte[] bufferPlano;
		
		bytesLeidos = in.read(buffer, 0, 1000);
		while (bytesLeidos != -1)
		{
			bufferPlano = cifrador.update(buffer, 0, bytesLeidos);
			out.write(bufferPlano);
			bytesLeidos = in.read(buffer, 0, 1000);
		}
		bufferPlano = cifrador.doFinal();
		out.write(bufferPlano);
		
		in.close();
		out.close();
	}

	public static void cargarBytesClave(byte [] buffer)
	{
		System.out.write(buffer, 0, buffer.length);
	} 
	
	public static void formaDeUso()
	{
		System.out.println("Forma de Utilizar el Programa");
		System.out.println("\tSintaxis:   java -jar Des_Cipher.jar mensajeACifrar.txt\n");
	}

}