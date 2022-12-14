package com.example.Lab2.locations.service;

import com.example.Lab2.locations.models.LocationDTO;
import com.example.Lab2.locations.models.LocationRequest;
import com.example.Lab2.controller.ConnectionMongoDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_INDEX_NAME = 1;
    private static final int COLUMN_INDEX_REGION = 2;
    private static final int COLUMN_INDEX_COUNTRY = 3;
    private static final int COLUMN_INDEX_LAT = 4;
    private static final int COLUMN_INDEX_LON = 5;
    private static final int COLUMN_INDEX_URL = 6;
    private final static Logger logger = LogManager.getLogger(LocationService.class);
    private static final String excelFilePath = "D:/locations.xlsx";

    // Create workbook
    public static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Create CellStyle for header
    public static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    // Write header
    public static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
        // Create row
        Row row = sheet.createRow(rowIndex);
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("id");

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("name");

        cell = row.createCell(COLUMN_INDEX_REGION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("region");

        cell = row.createCell(COLUMN_INDEX_COUNTRY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("country");

        cell = row.createCell(COLUMN_INDEX_LAT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("lat");

        cell = row.createCell(COLUMN_INDEX_LON);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("lon");

        cell = row.createCell(COLUMN_INDEX_URL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("url");
    }

    // Write data
    public static void writeBook(LocationDTO locationDTO, Row row) {
//        if (cellStyleFormatNumber == null) {
//            // Format number
//            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
//            // DataFormat df = workbook.createDataFormat();
//            // short format = df.getFormat("#,##0");
//
//            //Create CellStyle
//            Workbook workbook = row.getSheet().getWorkbook();
//            cellStyleFormatNumber = workbook.createCellStyle();
//            cellStyleFormatNumber.setDataFormat(format);
//        }

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(locationDTO.getId());

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellValue(locationDTO.getName());

        cell = row.createCell(COLUMN_INDEX_REGION);
        cell.setCellValue(locationDTO.getRegion());
        // cell.setCellStyle(cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_COUNTRY);
        cell.setCellValue(locationDTO.getCountry());

        cell = row.createCell(COLUMN_INDEX_LAT);
        cell.setCellValue(locationDTO.getLat());

        cell = row.createCell(COLUMN_INDEX_LON);
        cell.setCellValue(locationDTO.getLon());

        cell = row.createCell(COLUMN_INDEX_URL);
        cell.setCellValue(locationDTO.getUrl());

        // Create cell formula
        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    // Auto resize column width
    public static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    public static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        File file = new File(excelFilePath);
        try (OutputStream os = new FileOutputStream(file)) {
            workbook.write(os);
        } catch (Exception e) {
            logger.error("Create Output File: " + e);

        }
    }

    // Get list locations
    //@PostMapping(value = "/getLocations")
    public static List<LocationDTO> getListLocations() {

        String url = "mongodb://localhost:27017";
        List<LocationDTO> listLocation = new ArrayList<>();

        // MongoDatabase database = getDatabase();
        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, "all_things");
            //MongoDatabase database = new ConnectionMongoDB().getMongoDatabase();
            MongoCollection<Document> collection = database.getCollection("Locations");
            FindIterable<Document> myDoc = collection.find();

            //        Document document = myDoc.first();
            //        LocationDTO locationDTO = new LocationDTO();
            //        locationDTO.setId(Integer.parseInt(document.get("id").toString()));
            //        locationDTO.setName(document.get("name").toString());
            //        locationDTO.setCountry(document.get("country").toString());
            //        locationDTO.setRegion(document.get("region").toString());
            //        locationDTO.setLat(Double.parseDouble(document.get("lat").toString()));
            //        locationDTO.setLon(Double.parseDouble(document.get("lon").toString()));
            //        locationDTO.setUrl(document.get("url").toString());
            //ArrayList<Document> location = new ArrayList<>();
            //locationDTO.add(document);
            if (myDoc == null) {
                logger.error("location không tìm thấy");
            } else {
                for (Document doc : myDoc) {
                    LocationDTO locationDTO = new LocationDTO();
                    locationDTO.setId(Integer.parseInt(doc.get("id").toString()));
                    locationDTO.setName(doc.get("name").toString());
                    locationDTO.setCountry(doc.get("country").toString());
                    locationDTO.setRegion(doc.get("region").toString());
                    locationDTO.setLat(Double.parseDouble(doc.get("lat").toString()));
                    locationDTO.setLon(Double.parseDouble(doc.get("lon").toString()));
                    locationDTO.setUrl(doc.get("url").toString());
                    listLocation.add(locationDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Không thể kết nối");
            logger.error("getListLocations, url: " + url);
        }
        return listLocation;
    }

    //@PostMapping(value = "/postFindLocations")
    public static List<LocationDTO> findLocations(LocationRequest input) throws IOException {
        String input_object = input.getInput();

        List<LocationDTO> listLocation = getListLocations();
        List<LocationDTO> findLocation = new ArrayList<>();
        // Xóa khoảng trắng đầu và cuối chuỗi
        String input_trim = input_object.trim();
        String input_format = input_trim;
        if (input_trim.contains("  "))
            input_format = input_trim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
        if (input_format.equals(" "))
            findLocation = listLocation;
        for (LocationDTO location : listLocation) {
            if (String.format("%d", location.getId()).toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getName().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getRegion().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getCountry().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (String.format("%f", location.getLat()).toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (String.format("%f", location.getLon()).contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getUrl().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            }
        }
        return findLocation;
    }

    // Export file Json for Locations
    //@PostMapping(value = "/exportJsonLocations")
    public static List<LocationDTO> exportJsonLocations(LocationRequest input) throws IOException {
        String input_object = input.getInput();

        // Get list locations
        List<LocationDTO> listLocation = findLocations(input);
        if (listLocation == null)
            logger.error("Export Json Locations: ListLocation is null");
        // Create a JsonArray
        JSONArray listJsonObject = new JSONArray();
        for (LocationDTO location : listLocation) {
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            //Inserting key-value pairs into the json object
            jsonObject.put("id", location.getId());
            jsonObject.put("name", location.getName());
            jsonObject.put("region", location.getRegion());
            jsonObject.put("country", location.getCountry());
            jsonObject.put("lat", location.getLat());
            jsonObject.put("lon", location.getLon());
            jsonObject.put("url", location.getUrl());
            listJsonObject.add(jsonObject);
        }
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            LocationDTO[] locationDTO = gson.fromJson(listJsonObject.toString(), LocationDTO[].class);
            String prettyJsonString = gson.toJson(locationDTO);
            // Create one file
            FileWriter file = new FileWriter("D:/location.json");
            // Writing file
            file.write(prettyJsonString);
            //System.out.println(listJsonObject.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listLocation;
    }

    // Export file excel for locations
    //@PostMapping(value = "/exportExcelLocations")
    public static List<LocationDTO> exportExcelLocations(LocationRequest input) throws IOException {
        //List<LocationDTO> listLocation = getListLocations();
        List<LocationDTO> listLocation = findLocations(input);
        if (listLocation == null)
            logger.error("Export Excel Location: ListLocation không có data");
        try {
            // Create Workbook
            Workbook workbook = getWorkbook(excelFilePath);
            // Create sheet
            Sheet sheet = workbook.createSheet("Location");
            int rowIndex = 0;
            // Write header
            writeHeader(sheet, rowIndex);
            // Write data
            rowIndex++;
            for (LocationDTO locationDTO : listLocation) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(locationDTO, row);
                rowIndex++;
            }
            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            autosizeColumn(sheet, numberOfColumn);
            // Create file excel
            createOutputFile(workbook, excelFilePath);
            logger.info("Export Excel Location: Done!!!");
        } catch (Exception e) {
            logger.error("Export Excel Location: Lỗi tạo file excel " + excelFilePath);
        }
        return listLocation;
    }

    public static List<LocationDTO> testfindLocations(LocationRequest input) throws IOException {
        String input_object = input.getInput();

        List<LocationDTO> listLocation = getListLocations();
        List<LocationDTO> findLocation = new ArrayList<>();
        // Xóa khoảng trắng đầu và cuối chuỗi
        String input_trim = input_object.trim();
        String input_format = input_trim;
        if (input_trim.contains("  "))
            input_format = input_trim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
        if (input_format.equals(" "))
            findLocation = listLocation;
        for (LocationDTO location : listLocation) {
            if (String.format("%d", location.getId()).toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getName().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getRegion().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getCountry().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (String.format("%f", location.getLat()).toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (String.format("%f", location.getLon()).contains(input_format.toLowerCase())) {
                findLocation.add(location);
            } else if (location.getUrl().toLowerCase().contains(input_format.toLowerCase())) {
                findLocation.add(location);
            }
        }
        return findLocation;
    }

    public MongoDatabase getDatabase() {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("all_things");
        return database;
    }
}