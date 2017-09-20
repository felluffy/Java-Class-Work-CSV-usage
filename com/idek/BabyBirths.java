package com.idek;
import org.apache.commons.csv.*;
import java.io.File;
import java.text.DecimalFormat;

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
		//String fileName = "data/yob" + String.valueOf(year) + "short.csv";
		String fileName = "data/yob" + String.valueOf(year) + ".csv";
		int rank = 0;
		FileResource fr = new FileResource(fileName);
		for(CSVRecord rec : fr.getCSVParser(false))
		{
			if(!rec.get(1).equals(gender))
				continue;
			rank++;
			if(rec.get(0).equals(name))
			{
				//System.out.println("Number of babies named \'" +  rec.get(0) + "\' born are " + rec.get(2));
				return rank;
			}
		}
		return -1;
	}
	
	//returns highest rank of a name in all of the data files
	public int yearOfHighestRank(String name, String gender)
	{
		int minRank = Integer.MAX_VALUE;
		int currRank;
		int minRankContainingYear = -1;
		for(int year = 1880; year <= 2014; year++)
		{
			/*
			String fileName = "data/yob" + String.valueOf(year) + "short.csv";
			FileResource fr = new FileResource(fileName);
			currRank = 0;
			for(CSVRecord rec : fr.getCSVParser(false))
			{
				if(!rec.get(1).equals(gender))
					continue;
				currRank++;
				if(rec.get(0).equals(name))
					break;
			}
			if(maxRank <= currRank)
				currRank = maxRank;
			*/
			currRank = getRank(name, gender, year);
			if(minRank > currRank)
			{
				minRank = currRank;
				minRankContainingYear = year;
			}
		}
		return minRankContainingYear;
	}
	
	//Returns the name of a given rank in a year's file
	public String getName(int year, int rank, String gender)
	{
		//String fileName = "data/yob" + String.valueOf(year) + "short.csv";
		String fileName = "data/yob" + String.valueOf(year) + ".csv";
		FileResource fr = new FileResource(fileName);
		int currRank = 0;
		for(CSVRecord rec : fr.getCSVParser(false))
		{
			if(!rec.get(1).equals(gender))
				continue;
			currRank++;
			if(currRank == rank)
				return rec.get(0);
		}
		return "NO NAME";
		
	}
	
	public double getAverageRank(String name, String gender)
	{
		DirectoryResource dr = new DirectoryResource();
		//int year;
		DecimalFormat twoPoints = new DecimalFormat("#.##");
		boolean found = false;
		int currRank = 0;
		int totalRank = 0;
		int totalNumberOfRanks = 0;
		//String fileName;
		//String year;
		
		for (File fl : dr.selectedFiles())
		{
			currRank = 0;
			FileResource fr = new FileResource(fl);
			for(CSVRecord rec : fr.getCSVParser(false))
			{
				if(!rec.get(1).equals(gender))
					continue;
				currRank++;
				if(rec.get(0).equals(name))
				{
					System.out.println(currRank + " " + totalRank);
					found = true;
					totalRank++;
					totalNumberOfRanks += currRank;
					break;
				}
			}

		}
		if(found == false)
			return -1.0;
		else
			return Double.valueOf(twoPoints.format(totalNumberOfRanks / (double)totalRank));
	}
	
	void whatIsNameInYear(String name, int year, int newYear, String gender)
	{
		int rank = getRank(name, gender, year);
		String newName = getName(newYear, rank, gender);
		if(newName.equals("NO NAME"))
			newName = "\"Unspecified\"";
		System.out.println(name + " born in " + year + " would be named " + newName + " if " + (gender == "M" ? "he" : "she") + " was born in " + newYear);
	}
	
	public void testGetAverageRank()
	{
		//System.out.println(getAverageRank("Mason", "M"));
		System.out.println(getAverageRank("Jacob", "M"));
	}
	
	public void testYearOfHighestRank()
	{
		System.out.println(yearOfHighestRank("Mason", "M"));
	}
	
	public void testWhatIsNameInYear()
	{
		whatIsNameInYear("Isabella", 2012, 2014, "F");
	}
	
	public void testTotalBirths()
	{
		FileResource fr = new FileResource();
		totalBirths(fr);
	}
	
	public void testGetName()
	{
		System.out.println(getName(2012,  2, "M"));
		System.out.println(getName(2012,  1, "F"));
		System.out.println(getName(2012, 12, "M"));
	}
	
	public void testGetRank()
	{
		System.out.println(getRank("Mason", "M", 2012));
		System.out.println(getRank("Mason", "F", 2012));
		
	}
	
	public void testClassMethods()
	{
		testGetAverageRank();
		testYearOfHighestRank();
		testWhatIsNameInYear();
		testTotalBirths();
		testGetRank();
		testGetName();
	}
}
