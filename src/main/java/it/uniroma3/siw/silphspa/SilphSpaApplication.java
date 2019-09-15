package it.uniroma3.siw.silphspa;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilphSpaApplication {
	
	public static String application_pathToStaticFolder;

	public static void main(String[] args) {
		
		/* all'avvio viene resettata la cartella dove verra' gestita la galleria di immagini */
		try {
			/* prima era System.getProperty("user.dir")+"/src/main/resources/static/ */
			application_pathToStaticFolder = new File(".").getCanonicalPath()+"/src/main/resources/static";
			
			/* cancello e ricreo la cartella classpath:/src/main/resources/static/downloads_silph/ */
			File file = new File(application_pathToStaticFolder+"/downloads_silph/");
			/*deleteDirectory(file);*/
			//if (file.mkdir())
			file.mkdir();
			SpringApplication.run(SilphSpaApplication.class, args);
			/*else
				System.out.println("NON SONO RIUSCITO A CREARE LA CARTELLA DOWNLOADS");
			*/
		} catch (IOException e) {
			System.out.println("errore I/O, leggi lo stack sotto");
			e.printStackTrace();
		}
	}
	
	/**
	 * Questo metodo resetta la directory che gestisce la gallery e tutto il suo contenuto 
	 * - metodo ricorsivo e con una logica di debug molto basilare
	 * @param path - il percorso alla directory destinata
	 * @return true se la cancellazione e' andata a buon fine, false altrimenti
	 */
	/*private static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for( File f : files ) {
				if (f.isDirectory()) {
					if (!deleteDirectory(f)) {
						System.out.println("chiamata ricorsiva fallita");
						return false;
					}
				} else {
					if (!f.delete()) {
						try {
							System.out.println("eliminazione file _"+f.getCanonicalPath()+"_ non riuscita");
						} catch (IOException e) {
							System.out.println("errore I/O, leggi stack sotto");
							e.printStackTrace();
						}
						return false;
					}
				}
			}
			return path.delete();
		}
		else {
			try {
				System.out.println("non esiste il path _"+path.getCanonicalPath()+"_ da cancellare");
			} catch (IOException e) {
				System.out.println("errore I/O, leggi stack sotto");
				e.printStackTrace();
			}
			return false;
		}
	}*/

}
