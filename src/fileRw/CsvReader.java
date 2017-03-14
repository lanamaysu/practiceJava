package fileRw;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.time.LocalDate;
//import java.time.Period;




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
      thisCustomer.setProperties(thisLine[4]);
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
    allLine.append(String.format("%6s | %8s | %10s | %3s | %26s | %15s\n", "Name", "Phone", "Birth", "Age", "Address", "Properties"));
    for (Customer data : customers) {
      allLine.append(String.format("%5s | %8s | %10s | %3d | %20s | %,15d\n", 
          data.getName(),
          data.getPhone(),
          data.getBirth().replace("-", "/"),
          countAgeOld(data.getBirth()),
          data.getAddress(),
          Long.parseLong(data.getProperties()) )
      );
    }
    return allLine.toString();
  }
  
//  public int countAge(String ageStr) {
//    LocalDate birthday = LocalDate.parse(ageStr);
//    LocalDate today = LocalDate.now();
//    Period periodAge = Period.between(birthday, today);
//    return periodAge.getYears();
//  }
  
  public int countAgeOld(String ageStr) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date birth = null;
    try {
      birth = sdf.parse(ageStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar today = Calendar.getInstance();
    Calendar birthDate = Calendar.getInstance();
    birthDate.setTime(birth);
    int diff = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    if (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH) || 
        (birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) && 
        birthDate.get(Calendar.DATE) > today.get(Calendar.DATE))) {
        diff--;
    }
    return diff;
  }

}
