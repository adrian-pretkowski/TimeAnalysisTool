package pl.adi.timeanalysistool.extractfromlog;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ReadWriteFile {

    public List<String> readFile(String inputFilePath) {
        File file = new File(inputFilePath);
        List<String> resultFileList = new ArrayList<>();

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                resultFileList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return resultFileList;
    }
}
