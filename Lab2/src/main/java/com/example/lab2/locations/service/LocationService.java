package com.example.lab2.locations.service;

import com.example.lab2.controller.ConnectionMongoDB;
import com.example.lab2.controller.ExportExcel;
import com.example.lab2.locations.models.LocationDTO;
import com.example.lab2.locations.models.LocationName;
import com.example.lab2.locations.models.LocationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    // Write header
    public static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = ExportExcel.createStyleForHeader(sheet);
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


    // Get list locations
    //@PostMapping(value = "/getLocations")

    public static List<LocationDTO> getListLocations(String url, String db) {
        List<LocationDTO> listLocation = new ArrayList<>();
        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
            MongoCollection<Document> collection = database.getCollection("Locations");
            FindIterable<Document> myDoc = collection.find();
            if (myDoc.first().isEmpty()) {
                logger.error("location không tìm thấy");
            } else {
                for (Document doc : myDoc) {
                    LocationDTO locationDTO = new LocationDTO();
                    locationDTO.setId(ObjectUtils.isEmpty(doc.get("id")) ? "" : doc.get("id").toString());
                    locationDTO.setName(ObjectUtils.isEmpty(doc.get("name")) ? "" : doc.get("name").toString());
                    locationDTO.setCountry(ObjectUtils.isEmpty(doc.get("country")) ? "" : doc.get("country").toString());
                    locationDTO.setRegion(ObjectUtils.isEmpty(doc.get("region")) ? "" : doc.get("region").toString());
                    locationDTO.setLat(ObjectUtils.isEmpty(doc.get("lat")) ? "" : doc.get("lat").toString());
                    locationDTO.setLon(ObjectUtils.isEmpty(doc.get("lon")) ? "" : doc.get("lon").toString());
                    locationDTO.setUrl(ObjectUtils.isEmpty(doc.get("url")) ? "" : doc.get("url").toString());
                    listLocation.add(locationDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Không thể kết nối");
            logger.error("getListLocations, url: " + url);
        }
        return listLocation;
    }

    public static List<LocationDTO> importFileExcelLocation(String url, String db, MultipartFile reapExcelDataFile) {
        List<LocationDTO> listLocation = new ArrayList<>();
        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
            XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                LocationDTO locationDTO = new LocationDTO();
                XSSFRow row = worksheet.getRow(i);
                DataFormatter formatter = new DataFormatter();

                locationDTO.setId(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_ID))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_ID)));
                locationDTO.setName(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_NAME))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_NAME)));
                locationDTO.setRegion(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_REGION))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_REGION)));
                locationDTO.setCountry(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_COUNTRY))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_COUNTRY)));
                locationDTO.setLat(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_LAT))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_LAT)));
                locationDTO.setLon(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_LON))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_LON)));
                locationDTO.setUrl(ObjectUtils.isEmpty(formatter.formatCellValue(row.getCell(COLUMN_INDEX_URL))) ? "" : formatter.formatCellValue(row.getCell(COLUMN_INDEX_URL)));

                ObjectMapper mapper = new ObjectMapper();
                BasicDBObject query = new BasicDBObject();
                query.put("id", locationDTO.getId());
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", mapper.convertValue(locationDTO, Document.class));
                UpdateOptions options = new UpdateOptions().upsert(true);
                database.getCollection("Locations").updateOne(query, updateObject, options);
                logger.info("Post Locations Document locations upsert successfully " + locationDTO.getId());
                listLocation.add(locationDTO);
            }

        } catch (Exception e) {
            logger.error("Không thể kết nối: " + e);
            logger.error("getListLocations, url: " + url);
        }
        return listLocation;
    }

    // Find Location follow request
    //@PostMapping(value = "/postFindLocations")
    public static List<LocationDTO> findLocations(LocationRequest input, String url, String db) {
        String inputRequest = input.getInput();
//        String inputTrim = input.getInput().trim();
//        String inputFormat = inputTrim;
//        if (inputTrim.contains("  "))
//            inputFormat = inputTrim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
        List<LocationDTO> listLocation = new ArrayList<>();
        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
            MongoCollection<Document> collection = database.getCollection("Locations");
            Bson filters = Filters.or(
                    Filters.regex("name", inputRequest, "$i"),
                    Filters.regex("country", inputRequest, "$i"),
                    Filters.regex("region", inputRequest, "$i"),
                    Filters.regex("lat", inputRequest),
                    Filters.regex("lon", inputRequest)
                    //Filters.regex("url", inputRequest,"$i")
            );
            FindIterable<Document> myDoc = collection.find(filters);
            if (myDoc.first() == null) {
                logger.error(String.format("Find Location: location không tìm thấy theo request: ,input: %s, url: %s, db: %s ", input.getInput(), url, db));
            } else {
                for (Document doc : myDoc) {
                    LocationDTO locationDTO = new LocationDTO();

                    locationDTO.setId(ObjectUtils.isEmpty(doc.get("id")) ? "" : doc.get("id").toString());
                    locationDTO.setName(ObjectUtils.isEmpty(doc.get("name")) ? "" : doc.get("name").toString());
                    locationDTO.setCountry(ObjectUtils.isEmpty(doc.get("country")) ? "" : doc.get("country").toString());
                    locationDTO.setRegion(ObjectUtils.isEmpty(doc.get("region")) ? "" : doc.get("region").toString());
                    locationDTO.setLat(ObjectUtils.isEmpty(doc.get("lat")) ? "" : doc.get("lat").toString());
                    locationDTO.setLon(ObjectUtils.isEmpty(doc.get("lon")) ? "" : doc.get("lon").toString());
                    locationDTO.setUrl(ObjectUtils.isEmpty(doc.get("url")) ? "" : doc.get("url").toString());
                    listLocation.add(locationDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Find location, Không thể kết nối Exception: " + e);
            logger.error(String.format("Find location,input: %s, url: %s, db: %s ", input.getInput(), url, db));

        }
        return listLocation;
    }

    public static List<LocationDTO> findLocationByName(LocationName input, String url, String db) {
        String[] inputRequest = input.getInput().split(",");
//        String inputTrim = input.getInput().trim();
//        String inputFormat = inputTrim;
//        if (inputTrim.contains("  "))
//            inputFormat = inputTrim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
        List<LocationDTO> listLocation = new ArrayList<>();
        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
            MongoCollection<Document> collection = database.getCollection("Locations");
            Bson filters = Filters.in("name", inputRequest);
            FindIterable<Document> myDoc = collection.find(filters);
            if (myDoc.first() == null) {
                logger.error(String.format("Find Location: location không tìm thấy theo request: ,input: %s, url: %s, db: %s ", inputRequest, url, db));
            } else {
                for (Document doc : myDoc) {
                    LocationDTO locationDTO = new LocationDTO();

                    locationDTO.setId(ObjectUtils.isEmpty(doc.get("id")) ? "" : doc.get("id").toString());
                    locationDTO.setName(ObjectUtils.isEmpty(doc.get("name")) ? "" : doc.get("name").toString());
                    locationDTO.setCountry(ObjectUtils.isEmpty(doc.get("country")) ? "" : doc.get("country").toString());
                    locationDTO.setRegion(ObjectUtils.isEmpty(doc.get("region")) ? "" : doc.get("region").toString());
                    locationDTO.setLat(ObjectUtils.isEmpty(doc.get("lat")) ? "" : doc.get("lat").toString());
                    locationDTO.setLon(ObjectUtils.isEmpty(doc.get("lon")) ? "" : doc.get("lon").toString());
                    locationDTO.setUrl(ObjectUtils.isEmpty(doc.get("url")) ? "" : doc.get("url").toString());
                    listLocation.add(locationDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Find location, Không thể kết nối Exception: " + e);
            logger.error(String.format("Find location,input: %s, url: %s, db: %s ", inputRequest, url, db));

        }
        return listLocation;
    }

    // Export file Json for Locations
    //@PostMapping(value = "/exportJsonLocations")
    public static List<LocationDTO> exportJsonLocations(LocationRequest input, String url, String db) {
        // Get Current Directory using getAbsolutePath()
        // Get list locations
        List<LocationDTO> listLocation = findLocations(input, url, db);

        if (listLocation.isEmpty())
            logger.error("Export Json Locations: ListLocation is null");
            // Create a JsonArray
//        JSONArray listJsonObject = new JSONArray();
//        for (LocationDTO location : listLocation) {
//            //Creating a JSONObject object
//            JSONObject jsonObject = new JSONObject();
//            //Inserting key-value pairs into the json object
//            jsonObject.put("id", location.getId());
//            jsonObject.put("name", location.getName());
//            jsonObject.put("region", location.getRegion());
//            jsonObject.put("country", location.getCountry());
//            jsonObject.put("lat", location.getLat());
//            jsonObject.put("lon", location.getLon());
//            jsonObject.put("url", location.getUrl());
//            listJsonObject.add(jsonObject);
//        }
        else {
            try {
                //Gson gson = new Gson();
                Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC).create();
                String prettyJsonString = gson.toJson(listLocation);
                // Create one file
                File file = new File("./export");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String currentDirectory = file.getAbsolutePath();
                LocalDateTime localDateTime = LocalDateTime.now();
                FileWriter fileWriter = new FileWriter(file.getName() + "/location" + localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) + ".json");
                // Writing file

                fileWriter.write(prettyJsonString);
                fileWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error("Export Json Locations: IOException " + e);
            } catch (Exception e) {
                logger.error("Export Json Locations: Exception " + e);
            }
        }
        return listLocation;
    }

    public static String exportDownloadJsonLocations(LocationRequest input, String url, String db) {
        // Get Current Directory using getAbsolutePath()
        // Get list locations
        String fileName = null;
        List<LocationDTO> listLocation = findLocations(input, url, db);
        if (listLocation.isEmpty())
            logger.error("Export Json Locations: ListLocation is null");
        else {
            try {
                //Gson gson = new Gson();
                Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC).create();
                //List< LocationDTO> locationDTO = List.of(gson.fromJson(listJsonObject.toString(), LocationDTO[].class));
                String prettyJsonString = gson.toJson(listLocation);
                // Create one file
                File file = new File("./export");
                if (!file.exists()) {
                    file.mkdirs();
                }
                LocalDateTime localDateTime = LocalDateTime.now();
                String currentDirectory = file.getName() + "/location" + localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) + ".json";
                FileWriter fileWriter = new FileWriter(currentDirectory);
                // Writing file
                fileWriter.write(prettyJsonString);
                //System.out.println(listJsonObject.toString());
                fileWriter.close();
                fileName = currentDirectory;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.error("Export Json Locations: IOException " + e);
            } catch (Exception e) {
                logger.error("Export Json Locations: Exception " + e);
            }
        }
        return fileName;
    }

    // Export file excel for locations
    //@PostMapping(value = "/exportExcelLocations")
    public static List<LocationDTO> exportExcelLocations(LocationRequest input, String url, String db) {
        List<LocationDTO> listLocation = findLocations(input, url, db);
        if (listLocation == null)
            logger.error("Export Excel Location: ListLocation không có data");
        try {
            // Create Workbook
            Workbook workbook = ExportExcel.getWorkbook(excelFilePath);
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
            ExportExcel.autoSizeColumn(sheet, numberOfColumn);
            // Create file excel
            ExportExcel.createOutputFile(workbook, excelFilePath);
            logger.info("Export Excel Location: Done!!!");
        } catch (Exception e) {
            logger.error("Export Excel Location: Lỗi tạo file excel " + excelFilePath);
        }
        return listLocation;
    }

    public static String exportDownloadExcelLocations(LocationRequest input, String url, String db) {
        String fileName = null;
        List<LocationDTO> listLocation = findLocations(input, url, db);
        if (listLocation == null)
            logger.error("Export Excel Location: ListLocation không có data");
        try {
            // Create Workbook
            File file = new File("./export");
            if (!file.exists()) {
                file.mkdirs();
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String currentDirectory = file.getName() + "/location" + localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) + ".xlsx";
            Workbook workbook = ExportExcel.getWorkbook(currentDirectory);
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
            ExportExcel.autoSizeColumn(sheet, numberOfColumn);
            // Create file excel
            ExportExcel.createOutputFile(workbook, currentDirectory);
            logger.info("Export Excel Location: Done!!!");
            fileName = currentDirectory;
        } catch (Exception e) {
            logger.error("Export Excel Location: Lỗi tạo file excel " + excelFilePath);
        }
        return fileName;
    }
//    public static List<LocationDTO> testFindLocations(LocationRequest input, String url, String db) throws IOException {
//        String input_object = input.getInput();
//
//        List<LocationDTO> listLocation = getListLocations(url, db);
//        List<LocationDTO> findLocation = new ArrayList<>();
//        // Xóa khoảng trắng đầu và cuối chuỗi
//        String input_trim = input_object.trim();
//        String input_format = input_trim;
//        if (input_trim.contains("  "))
//            input_format = input_trim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
//        if (input_format.equals(" "))
//            findLocation = listLocation;
//        for (LocationDTO location : listLocation) {
//            if (String.format("%d", location.getId()).toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (location.getName().toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (location.getRegion().toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (location.getCountry().toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (String.format("%f", location.getLat()).toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (String.format("%f", location.getLon()).contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            } else if (location.getUrl().toLowerCase().contains(input_format.toLowerCase())) {
//                findLocation.add(location);
//            }
//        }
//        return findLocation;
//    }

//    public static List<LocationDTO> testGetListLocations(LocationRequest input, String url, String db) {
//        String input_object = input.getInput();
//        String input_trim = input_object.trim();
//        String input_format = input_trim;
//        if (input_trim.contains("  "))
//            input_format = input_trim.replaceAll("( ){2,}", " ");// Xóa khoảng trắng giữa chuỗi
//        List<LocationDTO> listLocation = new ArrayList<>();
//        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
//            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
//            MongoCollection<Document> collection = database.getCollection("Locations");
//            Bson filters = Filters.or(
//                    Filters.regex("name", input_format,"$i"),
//                    Filters.regex("country", input_format,"$i"),
//                    Filters.regex("region", input_format,"$i"),
//                    Filters.regex("lat", input_format),
//                    Filters.regex("lon", input_format),
//                    Filters.regex("url", input_format,"$i"));
//            FindIterable<Document> myDoc = collection.find(filters);
//            if (myDoc.first()==null) {
//                logger.error("location không tìm thấy");
//            } else {
//                for (Document doc : myDoc) {
//                    LocationDTO locationDTO = new LocationDTO();
//                    locationDTO.setId(Integer.parseInt(doc.get("id").toString()));
//                    locationDTO.setName(doc.get("name").toString());
//                    locationDTO.setCountry(doc.get("country").toString());
//                    locationDTO.setRegion(doc.get("region").toString());
//                    locationDTO.setLat(doc.get("lat").toString());
//                    locationDTO.setLon(doc.get("lon").toString());
//                    locationDTO.setUrl(doc.get("url").toString());
//                    listLocation.add(locationDTO);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Không thể kết nối");
//            logger.error("getListLocations, url: " + url);
//        }
//        return listLocation;
//    }
}
