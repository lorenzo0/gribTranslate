//package com.ph.grib2tools.test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.ph.grib2tools.grib2file.GribFile;
import com.ph.grib2tools.grib2file.GribSection1;
import com.ph.grib2tools.grib2file.RandomAccessGribFile;
import com.ph.grib2tools.grib2file.griddefinition.GridDefinitionTemplate30;
import com.ph.grib2tools.grib2file.productdefinition.ProductDefinitionTemplate4x;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GRIB2FileTest {
    
        public static float convKeltoC(double a)
        {
            float k = (float)(a - 273.15);
            //System.out.printf("%.2f",k);
            return k;
        }

	public static void main(String[] args) throws IOException {
		
		/*if (args.length < 1) {
			System.out.println("Syntax: java GRIB2FileTest <filename> <structureid (optional)>");
			return;
		}*/

		// Name of the GRIB2 file
		String filename = "format.grib";
		Scanner scanner = new Scanner(System.in);
                int scelta=0;
                
                //Creazione del file log
                FileWriter log = new FileWriter("log.txt", true);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date(); //2019/04/26 09:29:43
                
                //Applet usata per ricavare il nome dell'utente che sta usando l'applicazione al momento dell'esecuzione
                com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();
                System.out.println(NTSystem.getName());
                
                String info = NTSystem.getName() + " sta lavorando sul file " + filename + " in data: " +dateFormat.format(date) ;
                log.write(info + "\r\n");
                log.write("---------------------------\r\n\r\n");
		
                //Definisce quante strutture del file Grib vogliono essere non analizzate dal programma
		int numskip;
		try {
			numskip = Integer.parseInt(args[1]);
		} catch (Exception e) {
			numskip = 0;
		}
				
                //Id della griglia, possono essere contenute diverse griglie (Guarda sezioni 5-7)
		int gridid = 0;
		
		System.out.println("Reading file " + filename + ", file structure " + numskip + ":");
                System.getProperty("user.name");
		try {

			//Apro il file e lo leggo
			InputStream inputstream;
			inputstream = Files.newInputStream(Paths.get(filename));
			RandomAccessGribFile gribFile = new RandomAccessGribFile("testdata", filename);
			gribFile.importFromStream(inputstream, numskip);
			
			//Prendo informazioni rigurdanti data, orario del file
			GribSection1 section1 = gribFile.getSection1();
			System.out.println("Date: " + String.format("%02d", section1.day) + "." + String.format("%02d", section1.month) + "." + section1.year);
			System.out.println("Time: " + String.format("%02d", section1.hour) + ":" + String.format("%02d", section1.minute) + "." + String.format("%02d", section1.second));
			System.out.println("Generating centre: " + section1.generatingCentre);
                        
			
			ProductDefinitionTemplate4x productDefinition = (ProductDefinitionTemplate4x)gribFile.getProductDefinitionTemplate();
			/*System.out.println("Forecast time: " + productDefinition.forecastTime);
			System.out.println("Parameter category: " + productDefinition.parameterCategory);
			System.out.println("Parameter number: " + productDefinition.parameterNumber);*/

			//Vado ad esaminare informazioni rigurardanti l'area di copertura del file in termini di longitudine e latitudine
			GridDefinitionTemplate30 gridDefinition = (GridDefinitionTemplate30)gribFile.getGridDefinitionTemplate();
			System.out.println("Covered area:");
			System.out.println("   from (latitude, longitude): " + GribFile.unitsToDeg(gridDefinition.firstPointLat) + ", " + GribFile.unitsToDeg(gridDefinition.firstPointLon));		
			System.out.println("   to: (latitude, longitude): " + GribFile.unitsToDeg(gridDefinition.lastPointLat) + ", " + GribFile.unitsToDeg(gridDefinition.lastPointLon));		
                        
                        
                        do{
                            
                            System.out.println("Inserisci la latitudine: ");
                            double latitude = scanner.nextDouble();
                            
                            System.out.println("Inserisci la longitudine: ");
                            double longitude = scanner.nextDouble();
			// Get grid data
			/*double latitude = 44.3; //funzionante con 44.3
			double longitude = 10.3; //funzionante con 10.3*/
                        
			//System.out.println("Value at (" + latitude + ", " + longitude + "): " + gribFile.interpolateValueAtLocation(gridid, latitude, longitude));
			float res = convKeltoC(gribFile.interpolateValueAtLocation(gridid, latitude, longitude));
                        
                        System.out.print("Value at (" + latitude + ", " + longitude + "): ");
                        System.out.printf("%.2f",res);
                        System.out.println("°C");
                        
                        System.out.println("Value at (" + latitude + ", " + longitude + "): " + gribFile.interpolateValueAtLocation(gridid, latitude, longitude) + " °K");
                        
                        info = "Viene richiesto dall'utente informazioni nel punto: " + latitude + ", " + longitude + "\n";
                        log.write(info + "\r\n");
                        
                        info = "Si restituisce come responso: " + res + "°C e " + gribFile.interpolateValueAtLocation(gridid, latitude, longitude) + " °K";
                        log.write(info + "\r\n");
                        
                        System.out.println("Vuoi osservare anche un altra parte della mappa? (1/0)");
                        scelta=scanner.nextInt();
                        }while(scelta!=0);
                        } catch (Exception e) {
			e.printStackTrace();
		}
                log.write("\r\n---------------------------\r\n\r\n");
                log.close();
	}
}
