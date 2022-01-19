package pl.adi.timeanalysistool.extractfromlog;

import pl.adi.timeanalysistool.domain.model.Ecu;
import pl.adi.timeanalysistool.domain.model.Function;
import pl.adi.timeanalysistool.domain.model.TestPlan;
import pl.adi.timeanalysistool.domain.model.Vehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractFromLog {

    private static final String START_TEST_KEY_WORD = "Start with";
    private static final String END_TEST_KEY_WORD = "Done with";

    private List<String> fileLines = new ArrayList<>();

    public TestPlan extractLog(String inputLogPath, String testLocation, String vehicleTyp) {

        ReadWriteFile readWriteFile = new ReadWriteFile();
        Map<String, Ecu> ecuMap;

        fileLines = readWriteFile.readFile(inputLogPath);
        ecuMap = getEcuMap(fileLines);
        getFunctions(fileLines, ecuMap);

        Double testDuration = getTestDuration(getFileLines());
        String kennNumber = getKennNumberFromLog(fileLines);
        String vin = getVinFromLog(fileLines);
        String testDate = getTestDateFromLog(fileLines);

        Vehicle vehicle = new Vehicle(null, vehicleTyp, kennNumber, vin, null, null);
        vehicle.setEcuMap(ecuMap);
        ecuMap.forEach((name, ecu) -> ecu.setVehicle(vehicle));

        TestPlan testPlan = new TestPlan(null, testLocation, testDate, testDuration, null);
        testPlan.setVehicle(vehicle);
        return testPlan;
    }

    private String getTestDateFromLog(List<String> fileLines) {
        for (String fileLine : fileLines) {
            if (fileLine.contains("Test date")) {
                return findBetweenCharacters(fileLine, "'", "'");
            }
        }
        return null;
    }

    private String getVinFromLog(List<String> fileLines) {
        for (String fileLine : fileLines) {
            if (fileLine.contains("Vehicle VIN:")) {
                return findBetweenCharacters(fileLine, "'", "'");
            }
        }
        return null;
    }

    private String getKennNumberFromLog(List<String> fileLines) {
        for (String fileLine : fileLines) {
            if (fileLine.contains("Start with KNR:")) {
                return findBetweenCharacters(fileLine, "'", "'");
            }
        }
        return null;
    }

    private double getTestDuration(List<String> fileLines) {
        String startTime = null, endTime = null;
        Date newStartTime = null, newEndTime = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        double diffInMillis = 0;

        for (String fileLine : fileLines) {
            if (fileLine.contains(START_TEST_KEY_WORD)) {
                String[] startLine = fileLine.split(" ");
                startTime = (startLine[0] + " " + startLine[1].replace(",", "."));
            }
            if (fileLine.contains(END_TEST_KEY_WORD)) {
                String[] endLine = fileLine.split(" ");
                endTime = (endLine[0] + " " + endLine[1].replace(",", "."));
            }
        }

        try {
            newStartTime = formatter.parse(startTime);
            newEndTime = formatter.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (newEndTime != null) {
            diffInMillis = newEndTime.getTime() - newStartTime.getTime();
        } else {
            System.out.println("End TIme Value equals 0!");
        }

        return (diffInMillis / 1000);

    }

    private void getFunctions(List<String> fileLines, Map<String, Ecu> ecuMap) {
        for (Map.Entry<String, Ecu> entry : ecuMap.entrySet()) {
            double ecuDurationValue = 0;
            for (int i = 0, fileLinesSize = fileLines.size(); i < fileLinesSize; i++) {
                String fileLine = fileLines.get(i);

                String functionName = getFunctionName(fileLine, entry.getKey());
                String functionStartTime = getFunctionStartTime(fileLine, entry.getKey());
                String functionDuration = getFunctionDuration(fileLine, entry.getKey());

                if (functionDuration != null) {
                    ecuDurationValue += Double.parseDouble(functionDuration);
                }

                if (functionName != null && functionStartTime != null && functionDuration != null) {
                    Function function = new Function(null, i + 1, functionName, functionStartTime, Double.parseDouble(functionDuration), null);
                    function.setEcu(entry.getValue());
                    entry.getValue().addFunction(function);
                }
                if (fileLine.contains("ConnectionOpen") && fileLine.contains(entry.getKey())) {
                    entry.getValue().addConnectionOpenCounter();
                }
                if (fileLine.contains("ConnectionRelease") && fileLine.contains(entry.getKey())) {
                    entry.getValue().addConnectionReleaseCounter();
                }
                if (fileLine.contains("AssemblyCheck") && fileLine.contains(entry.getKey())) {
                    entry.getValue().addAssemblyCheckCounter();
                }
            }
            entry.getValue().setTotalEcuDuration(ecuDurationValue);
        }
    }

    private String getFunctionDuration(String fileLine, String ecuName) {
        String tempEcuName = null;
        if (fileLine.contains("TM_")) {
            String[] lineWithEcu = fileLine.split(" ");
            tempEcuName = lineWithEcu[3].substring(0, lineWithEcu[3].length() - 1);
        }

        String result;
        if (ecuName.equals(tempEcuName)
                && !fileLine.contains("FISeQS")
                && !fileLine.contains("ConnectionOpen")
                && !fileLine.contains("ConnectionRelease")) {
            String betweenCharacters = findBetweenCharacters(fileLine, "'", "'");
            result = betweenCharacters.replace(",", ".");

            return result;
        } else {
            return null;
        }
    }

    private String findBetweenCharacters(String fileLine, String firstChar, String secondChar) {
        String result = null;
        String regex = firstChar + "(.*?)" + secondChar;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileLine);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    private String getFunctionStartTime(String fileLine, String ecuName) {
        String result;
        if (fileLine.contains(ecuName)
                && !fileLine.contains("FISeQS")
                && !fileLine.contains("ConnectionOpen")
                && !fileLine.contains("ConnectionRelease")) {
            String[] splitLine = fileLine.split(" ");
            result = splitLine[1];
            return result;
        } else {
            return null;
        }
    }

    private String getFunctionName(String fileLine, String ecuName) {
        String result;
        if (fileLine.contains(ecuName)
                && !fileLine.contains("FISeQS")
                && !fileLine.contains("ConnectionOpen")
                && !fileLine.contains("ConnectionRelease")) {
            String[] splitLine = fileLine.split(" ");
            result = splitLine[4].substring(splitLine[4].lastIndexOf("_") + 1);
            return result;
        } else {
            return null;
        }
    }

    private Map<String, Ecu> getEcuMap(List<String> fileLines) {
        Map<String, Ecu> resultEcuMap = new LinkedHashMap<>();

        for (String fileLine : fileLines) {
            if (fileLine.contains("TM_")) {
                String[] line = fileLine.split(" ");
                String ecuName = line[3].substring(0, line[3].length() - 1);
                if (!resultEcuMap.containsKey(ecuName)) {
                    resultEcuMap.put(ecuName, new Ecu(ecuName));
                }
            }
        }

        return resultEcuMap;
    }


    //-----------------GETTERS-------------------
    public List<String> getFileLines() {
        return this.fileLines;
    }

}
