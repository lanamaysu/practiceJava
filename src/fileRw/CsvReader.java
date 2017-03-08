package fileRw;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.Customer;

public class CsvReader {

  public static void main(String[] args) {
    FileInOut fileInOut = new FileInOut();
    String formattedData = fileInOut.fileToString("file/input.txt");
    fileInOut.writeFile(formattedData, "file/output.txt");

    String formattedData2 = fileInOut.fileToObj("file/inputBirthday.txt");
    fileInOut.writeFile(formattedData2, "file/outputBirthday.txt");
  }
}


class FileInOut {
  final String SPLIT_REG = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
  final String SYSSP = System.lineSeparator();

  public List<String> readFile(String inputFile) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(inputFile), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  public String fileToString(String inputFile) {
    StringBuilder allLine = new StringBuilder();
    List<String> lines = readFile(inputFile);
    for (String data : lines) {
      allLine.append(formatDataReplace(data, "\n")).append("\n");
    }
    return allLine.toString();
  }

  public String fileToObj(String inputFile) {
    List<Customer> customers = new ArrayList<Customer>();
    List<String> lines = readFile(inputFile);
    for (String data : lines) {
      Customer thisCustomer = new Customer();
      String[] thisLine = splitComma(data);
      thisCustomer.setName(thisLine[0]);
      thisCustomer.setPhone(thisLine[1]);
      thisCustomer.setAddress(thisLine[2]);
      thisCustomer.setBirth(thisLine[3]);
      customers.add(thisCustomer);
    }
    // System.out.println(formatListObj(customers));
    return formatListObj(customers);
  }

  public void writeFile(String input, String outputFile) {
    try (PrintWriter output = new PrintWriter(outputFile)) {
      output.println(new Date().toString() + SYSSP);
      output.print(changeLineSeparator(input));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String[] splitComma(String inputLine) {
    return inputLine.split(SPLIT_REG, -1);
  }

  public String changeLineSeparator(String original) {
    return original.replaceAll("\n", SYSSP);
  }

  public String formatData(String[] thisDataArr, String seperator) {
    String returnLine = "";
    for (int i = 0; i < thisDataArr.length; i++) {
      returnLine += thisDataArr[i] + seperator;
    }
    return returnLine;
  }

  public String formatDataReplace(String thisLine, String seperator) {
    return thisLine.replaceAll(SPLIT_REG, seperator) + "\n";
  }

  public String formatListObj(List<Customer> customers) {
    StringBuilder allLine = new StringBuilder();
    for (Customer data : customers) {
      allLine.append(data.getName()).append(":");
      allLine.append(data.getPhone()).append("--");
      allLine.append(data.getBirth().replaceAll("-", "/")).append("\n");
      allLine.append(data.getAddress()).append("\n");
      allLine.append("\n");
    }
    return allLine.toString();
  }

}
