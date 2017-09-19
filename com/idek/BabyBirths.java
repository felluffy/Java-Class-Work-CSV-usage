package com.idek;
import org.apache.commons.csv.*;
import edu.duke.*;
public class BabyBirths 
{
	public static void main(String[] args) 
	{
		BabyBirths births = new BabyBirths();
		births.testClassMethods();
	}
	
	public void printNames()
	{
		FileResource fr = new FileResource();
		for(CSVRecord rec : fr.getCSVParser(false))
		{
			int numBorn = Integer.parseInt(rec.get(2));
			if(numBorn <= 100)
			{
				System.out.println("Name: " + rec.get(0) + ", Gender: " + rec.get(1) + ", Num born: " + rec.get(2));
			}
		}
	}
	
	public void totalBirths(FileResource fr)
	{
		int totalBirths = 0;
		int maleBirths = 0;
		int femaleBirths = 0;
		for(CSVRecord rec : fr.getCSVParser(false))
		{
			int numBorn = Integer.parseInt(rec.get(2));

			if(rec.get(1).equals("M"))
				maleBirths += numBorn;
			else
				femaleBirths += numBorn;
				
			//System.out.println("Number of babies named \'" +  rec.get(0) + "\' born are " + numBorn);
		}
		totalBirths = maleBirths + femaleBirths;
		System.out.println("Total births: " + totalBirths);
		System.out.println("Total male births: " + maleBirths);
		System.out.println("Total female births: " + femaleBirths);
	}
	
	//Returns the rank of the name of a name in a specific year if present
	public int getRank(String name, String gender, int year)
	{
		String fileName = "data/yob" + String.valueOf(year) + "short.csv";
		int rank = 0;
		FileResource fr = new FileResource(fileName);
		for(CSVRecord rec : fr.getCSVParser(false))
		{
			if(!rec.get(1).equals(gender))
				continue;
			rank++;
			if(rec.get(0).equals(name))
				return rank;
		}
		return -1;
	}
	
	public void testTotalBirths()
	{
		FileResource fr = new FileResource();
		totalBirths(fr);
	}
	
	public void testGetRank()
	{
		System.out.println(getRank("Mason", "M", 2012));
		System.out.println(getRank("Mason", "F", 2012));
		
	}
	
	public void testClassMethods()
	{
		testTotalBirths();
		testGetRank();
	}
}
