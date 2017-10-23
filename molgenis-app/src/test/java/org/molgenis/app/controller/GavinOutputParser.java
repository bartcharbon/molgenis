package org.molgenis.app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GavinOutputParser
{
	private static List<Effect> effects;
	private static Map<String, String> cDnaMap;

	public static void main(String args[]) throws FileNotFoundException
	{
		effects = new ArrayList<>();
		cDnaMap = new HashMap<>();

		Scanner gavinScanner = new Scanner(new File("C:/Users/Bart/Downloads/v1_3_780c5c6a9cb970c2-gavin (1).vcf"));
		while (gavinScanner.hasNextLine())
		{
			String line = gavinScanner.nextLine();
			if (!line.startsWith("#"))
			{
				String[] lineArray = line.split("\t");
				String key = lineArray[0] + "_" + lineArray[1] + "_" + lineArray[3] + "_" + lineArray[4];
				Effect effect = new Effect(key, lineArray[7].split(";")[lineArray[7].split(";").length - 1]);
				effects.add(effect);
			}
		}

		Scanner cDnaScanner = new Scanner(new File("C:/Users/Bart/Downloads/ywtuaeul4d62w2CF.vcf"));
		while (cDnaScanner.hasNextLine())
		{
			String line = cDnaScanner.nextLine();
			if (!line.startsWith("#"))
			{
				String[] lineArray = line.split("\t");
				String key = lineArray[0] + "_" + lineArray[1] + "_" + lineArray[3] + "_" + lineArray[4];
				cDnaMap.put(key, lineArray[3]);
			}
		}
	}
}
