package fileRw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class csvReader {

	public static void main(String[] args) {
		FileInOut fileInOut = new FileInOut();
		String formattedData = fileInOut.readFileAll("file/input.txt");
		fileInOut.writeFile(formattedData, "file/output.txt");
	}
}

class FileInOut {
	public String readFileAll(String inputFile) {
		String allLine = "";
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String thisLine;
			while ((thisLine = br.readLine()) != null) {
				// allLine += formatData(splitComma(thisLine), "\n") + "\n";
				allLine += formatDataReplace(thisLine, "\n") + "\n";
			}
			System.out.println(allLine);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allLine;
	}
	
	public void writeFile(String input, String outputFile) {
		try(PrintWriter output = new PrintWriter(outputFile)){
			output.print(changeLineSeparator(input));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	final String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
	final String thisSysLineSp = System.lineSeparator();
	
	public String[] splitComma(String inputLine) {
		return inputLine.split(regex);
	}
	
	public String changeLineSeparator(String original) {
		return original.replaceAll("\n", thisSysLineSp);
	}

	public String formatData(String[] thisDataArr, String seperator) {
		String returnLine = "";
		for(int i = 0; i < thisDataArr.length; i++) {
			returnLine += thisDataArr[i] + seperator;
		}
		return returnLine;
	}

	public String formatDataReplace(String thisLine, String seperator) {
		return thisLine.replaceAll(regex, seperator) + "\n";
	}

}