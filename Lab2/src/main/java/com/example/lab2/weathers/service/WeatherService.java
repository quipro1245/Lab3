package com.example.lab2.weathers.service;

import com.example.lab2.controller.ConnectionMongoDB;
import com.example.lab2.controller.ExportExcel;
import com.example.lab2.weathers.models.WeatherDTO;
import com.example.lab2.weathers.models.WeatherRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.lab2.locations.service.LocationService.*;

public class WeatherService {
    private final static Logger logger = LogManager.getLogger(WeatherService.class);
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final static DateTimeFormatter formatterWeather = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String excelFilePath = "D:/weathers.xlsx";
    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_TIME_EPOCH = 1;
    private static final int COLUMN_TIME = 2;
    private static final int COLUMN_TEMP_C = 3;
    private static final int COLUMN_TEMP_F = 4;
    private static final int COLUMN_IS_DAY = 5;
    private static final int COLUMN_CONDITION = 6;
    private static final int COLUMN_WIND_MPH = 7;
    private static final int COLUMN_WIND_KPH = 8;
    private static final int COLUMN_WIND_DEGREE = 9;
    private static final int COLUMN_WIND_DIR = 10;
    private static final int COLUMN_PRESSURE_MB = 11;
    private static final int COLUMN_PRESSURE_IN = 12;
    private static final int COLUMN_PRECIP_MM = 13;
    private static final int COLUMN_PRECIP_IN = 14;
    private static final int COLUMN_HUMIDITY = 15;
    private static final int COLUMN_CLOUD = 16;
    private static final int COLUMN_FEELSLIKE_C = 17;
    private static final int COLUMN_FEELSLIKE_F = 18;
    private static final int COLUMN_WINDCHILL_C = 19;
    private static final int COLUMN_WINDCHILL_F = 20;
    private static final int COLUMN_HEATINDEX_C = 21;
    private static final int COLUMN_HEATINDEX_F = 22;
    private static final int COLUMN_DEWPOINT_C = 23;
    private static final int COLUMN_DEWPOINT_F = 24;
    private static final int COLUMN_WILL_IT_RAIN = 25;
    private static final int COLUMN_CHANCE_OF_RAIN = 26;
    private static final int COLUMN_WILL_IT_SNOW = 27;
    private static final int COLUMN_CHANCE_IT_SNOW = 28;
    private static final int COLUMN_VIS_KM = 29;
    private static final int COLUMN_VIS_MILES = 30;
    private static final int COLUMN_GUST_MPH = 31;
    private static final int COLUMN_GUST_KPH = 32;

    // Get list weather
    //@PostMapping(value = "/getWeather")
//    public static List<WeatherDTO> getListWeather(String date) {
//
//        String url = "mongodb://localhost:27017";
//        List<WeatherDTO> listLocation = new ArrayList<>();
//
//        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
//            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, "all_things");
//            MongoCollection<Document> collection = database.getCollection(String.format("Weather_%s", date));
//            FindIterable<Document> myDoc = collection.find();
//            if (myDoc == null) {
//                logger.error("Get list weather không tìm thấy");
//            } else {
//                for (Document doc : myDoc) {
//                    WeatherDTO weatherDTO = new WeatherDTO();
//                    weatherDTO.setId(doc.get("id").toString());
//                    weatherDTO.setTime_epoch(Integer.parseInt(doc.get("time_epoch").toString()));
//                    weatherDTO.setTime(doc.get("time").toString());
//                    weatherDTO.setTempC(Double.parseDouble(doc.get("temp_c").toString()));
//                    weatherDTO.setTempF(Double.parseDouble(doc.get("temp_f").toString()));
//                    weatherDTO.setIsDay(Integer.parseInt(doc.get("is_day").toString()));
//                    weatherDTO.setCondition(doc.get("condition"));
//                    weatherDTO.setWindMph(Double.parseDouble(doc.get("wind_mph").toString()));
//                    weatherDTO.setWindKph(Double.parseDouble(doc.get("wind_kph").toString()));
//                    weatherDTO.setWindDegree(Integer.parseInt(doc.get("wind_degree").toString()));
//                    weatherDTO.setWindDir(doc.get("wind_dir").toString());
//                    weatherDTO.setPressureMb(Double.parseDouble(doc.get("pressure_mb").toString()));
//                    weatherDTO.setPressureIn(Double.parseDouble(doc.get("pressure_in").toString()));
//                    weatherDTO.setPrecipMm(Double.parseDouble(doc.get("precip_mm").toString()));
//                    weatherDTO.setPrecipIn(Double.parseDouble(doc.get("precip_in").toString()));
//                    weatherDTO.setHumidity(Integer.parseInt(doc.get("humidity").toString()));
//                    weatherDTO.setCloud(Integer.parseInt(doc.get("cloud").toString()));
//                    weatherDTO.setFeelsLikeC(Double.parseDouble(doc.get("feelslike_c").toString()));
//                    weatherDTO.setFeelsLikeF(Double.parseDouble(doc.get("feelslike_f").toString()));
//                    weatherDTO.setWindChillC(Double.parseDouble(doc.get("windchill_c").toString()));
//                    weatherDTO.setWindChillF(Double.parseDouble(doc.get("windchill_f").toString()));
//                    weatherDTO.setHeatIndexC(Double.parseDouble(doc.get("heatindex_c").toString()));
//                    weatherDTO.setHeatIndexF(Double.parseDouble(doc.get("heatindex_f").toString()));
//                    weatherDTO.setDewPointC(Double.parseDouble(doc.get("dewpoint_c").toString()));
//                    weatherDTO.setDewPointF(Double.parseDouble(doc.get("dewpoint_f").toString()));
//                    weatherDTO.setWillItRain(Integer.parseInt(doc.get("will_it_rain").toString()));
//                    weatherDTO.setChanceOfRain(Integer.parseInt(doc.get("chance_of_rain").toString()));
//                    weatherDTO.setWillItSnow(Integer.parseInt(doc.get("will_it_snow").toString()));
//                    weatherDTO.setChanceOfSnow(Integer.parseInt(doc.get("chance_of_snow").toString()));
//                    weatherDTO.setVisKm(Double.parseDouble(doc.get("vis_km").toString()));
//                    weatherDTO.setVisMiles(Double.parseDouble(doc.get("vis_miles").toString()));
//                    weatherDTO.setGustMph(Double.parseDouble(doc.get("gust_mph").toString()));
//                    weatherDTO.setGustKph(Double.parseDouble(doc.get("gust_kph").toString()));
//
//                    listLocation.add(weatherDTO);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(String.format("Get list weather: Không thể lấy được data url: %s, date: %s", url, date));
//
//        }
//        return listLocation;
//    }

    // Get list weather from start day to end day
//    public static List<WeatherDTO> getListWeatherStartToEndDate(String startDate, String endDate) {
//        List<WeatherDTO> listWeatherDTO = new ArrayList<>();
//        try {
//            LocalDateTime dateTime_StartDate = LocalDateTime.parse(startDate.trim(), formatter);
//            LocalDateTime dateTime_EnnDate = LocalDateTime.parse(endDate.trim(), formatter);
//
//            while (dateTime_StartDate.compareTo(dateTime_EnnDate) <= 0) {
//                List<WeatherDTO> weatherDTO = getListWeather(dateTime_StartDate.format(DateTimeFormatter.BASIC_ISO_DATE));
//                listWeatherDTO.addAll(weatherDTO);
//                dateTime_StartDate = dateTime_StartDate.plusDays(1);
//            }
//        } catch (Exception e) {
//            logger.error(String.format("Get list weather start to end date: start date: %s, end date: %s", startDate, endDate));
//        }
//        return listWeatherDTO;
//    }

    // Compare date
//    public static boolean compareItemTimeWeatherWithDateTime_range(String startDate, String endDate, String itemTimeWeather) {
//        try {
//            LocalDateTime dateTime_StartDate = LocalDateTime.parse(startDate.trim(), formatter);
//            LocalDateTime dateTime_EnnDate = LocalDateTime.parse(endDate.trim(), formatter);
//            LocalDateTime dateTime_itemTimeWeather = LocalDateTime.parse(itemTimeWeather, formatterWeather);
//            if (dateTime_itemTimeWeather.compareTo(dateTime_StartDate) >= 0 && dateTime_itemTimeWeather.compareTo(dateTime_EnnDate) <= 0)
//                return true;
//        } catch (Exception e) {
//            logger.error(String.format("Compare item time weather with date time range: start date: %s, end date: %s, item time weather: %s", startDate, endDate, itemTimeWeather));
//        }
//        return false;
//    }

    //@PostMapping(value = "/findWeatherFollowDate")
//    public static List<WeatherDTO> findWeatherFollowDate(WeatherRequest input) {
//        String startDate = "";
//        String endDate = "";
//        String[] location_id_arr = null;
//        String datetime_range_Object = input.getDatetimeRange();
//        String location_id_Object = input.getLocationId();
//        List<WeatherDTO> listWeatherDTO;
//        List<WeatherDTO> findWeatherFollowDate = new ArrayList<>();
//        if (datetime_range_Object.trim().isBlank()) {
//            logger.error("Find weather follow date: datetime_range không có giá trị");
//        } else {
//            String[] datetime_range_arr = datetime_range_Object.split("-");
//            if (datetime_range_arr.length < 2 || datetime_range_arr.length > 2) {
//                logger.error("Find weather follow date: datetime_range không đúng định dạng");
//            } else {
//                startDate = datetime_range_arr[0];
//                endDate = datetime_range_arr[1];
//            }
//        }
//        if (location_id_Object.trim().equals("")) {
//            logger.warn("Find weather follow date: location is null");
//        } else {
//            location_id_arr = location_id_Object.split(",");
//        }
//        try {
//            // Trường hợp datetime_range có giá trị 1 trong 2 hoặc cả 2 bị null
//            if (startDate.isBlank() || endDate.isBlank()) {
//
//                logger.error("Find weather follow date: datetime_range không được bỏ trống và nhập đúng định dạng dd/MM/yyyy HH:mm:ss - dd/MM/yyyy HH:mm:ss");
//            } else {
//                listWeatherDTO = getListWeatherStartToEndDate(startDate, endDate);
//                for (WeatherDTO weather : listWeatherDTO) {
//                    // Trường họp location là null
//                    if (location_id_arr == null) {
////                        if (compareItemTimeWeatherWithDateTime_range(startDate, endDate, weather.getTime()))
////                            findWeatherFollowDate.add(weather);
//                        logger.error("Find weather follow date: location_id không được bỏ trống");
//                    } else {
//                        // Trường hợp location có giá trị
//                        for (String itemLocationID : location_id_arr) {
//                            if (weather.getId() == itemLocationID) {
//                                if (compareItemTimeWeatherWithDateTime_range(startDate, endDate, weather.getTime()))
//                                    findWeatherFollowDate.add(weather);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Find weather follow date: lỗi sai định dạng hoặc date không tồn tại");
//            logger.error("Find weather follow date: " + e);
//        }
//        return findWeatherFollowDate;
//    }

    //@PostMapping(value = "/exportJsonWeather")
    public static List<WeatherDTO> exportJsonWeather(WeatherRequest input, String url, String db) {
        List<WeatherDTO> listWeather = findWeatherFollowRequest(input, url, db);
        if(listWeather.isEmpty()){
            logger.error("Export json weather: weather is null");
        }
        else {
            try {
                //Gson gson = new Gson();
                File file = new File("./export");
                if (!file.exists()){
                    file.mkdirs();
                }
                LocalDateTime localDateTime = LocalDateTime.now();
                String currentDirectory = file.getName()+"/weather"+localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) +".json";
                //Gson gson = new Gson();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                //WeatherDTO[] weatherDTOS = gson.fromJson(listJsonObject.toString(), WeatherDTO[].class);
                String prettyJsonString = gson.toJson(listWeather);
                FileWriter fileWriter = new FileWriter(currentDirectory);
                fileWriter.write(prettyJsonString);
                fileWriter.close();
            } catch (IOException e) {
                logger.error("Export file json weather, IOException: " + e);
            } catch (Exception e) {
                logger.error("Export file json weather, Exception:" + e);
                logger.error("Export file json weather : Error cannot create json file");
            }
        }
        return listWeather;
    }
    public static String exportDownloadJsonWeather(WeatherRequest input, String url, String db) {
        String fileName = null;
        List<WeatherDTO> listWeather = findWeatherFollowRequest(input, url, db);
        if(listWeather.isEmpty()){
            logger.error("Export json weather: weather is null");
        }
        else {
            try {
                File file = new File("./export");
                if (!file.exists()){
                    file.mkdirs();
                }
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String jsonArray = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listWeather);

                LocalDateTime localDateTime = LocalDateTime.now();
                String currentDirectory = file.getName()+"/weather"+localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) +".json";

                FileWriter fileWriter = new FileWriter(currentDirectory);
                fileWriter.write(jsonArray);
                fileWriter.close();
                fileName =currentDirectory;
            } catch (IOException e) {
                logger.error("Export file json weather, IOException: " + e);
            } catch (Exception e) {
                logger.error("Export file json weather, Exception:" + e);
                logger.error("Export file json weather : Error cannot create json file");
            }
        }
        return fileName;
    }
    public static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = ExportExcel.createStyleForHeader(sheet);
        // Create row
        Row row = sheet.createRow(rowIndex);
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("id");

        cell = row.createCell(COLUMN_TIME_EPOCH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("time_epoch");

        cell = row.createCell(COLUMN_TIME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("time");

        cell = row.createCell(COLUMN_TEMP_C);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("temp_c");

        cell = row.createCell(COLUMN_TEMP_F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("temp_f");

        cell = row.createCell(COLUMN_IS_DAY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("is_day");

        cell = row.createCell(COLUMN_CONDITION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("condition");


        cell = row.createCell(COLUMN_WIND_MPH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("wind_mph");

        cell = row.createCell(COLUMN_WIND_KPH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("wind_kph");

        cell = row.createCell(COLUMN_WIND_DEGREE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("wind_degree");

        cell = row.createCell(COLUMN_WIND_DIR);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("wind_dir");

        cell = row.createCell(COLUMN_PRESSURE_MB);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("pressure_mb");

        cell = row.createCell(COLUMN_PRESSURE_IN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("pressure_in");

        cell = row.createCell(COLUMN_PRECIP_MM);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("precip_mm");

        cell = row.createCell(COLUMN_PRECIP_IN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("precip_in");

        cell = row.createCell(COLUMN_HUMIDITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("humidity");

        cell = row.createCell(COLUMN_CLOUD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("cloud");

        cell = row.createCell(COLUMN_FEELSLIKE_C);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("feelslike_c");

        cell = row.createCell(COLUMN_FEELSLIKE_F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("feelslike_f");

        cell = row.createCell(COLUMN_WINDCHILL_C);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("windchill_c");

        cell = row.createCell(COLUMN_WINDCHILL_F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("windchill_f");

        cell = row.createCell(COLUMN_HEATINDEX_C);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("heatindex_c");

        cell = row.createCell(COLUMN_HEATINDEX_F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("heatindex_f");

        cell = row.createCell(COLUMN_DEWPOINT_C);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("dewpoint_c");

        cell = row.createCell(COLUMN_DEWPOINT_F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("dewpoint_f");

        cell = row.createCell(COLUMN_WILL_IT_RAIN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("will_it_rain");

        cell = row.createCell(COLUMN_CHANCE_OF_RAIN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("chance_of_rain");

        cell = row.createCell(COLUMN_WILL_IT_SNOW);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("will_it_snow");

        cell = row.createCell(COLUMN_CHANCE_IT_SNOW);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("chance_of_snow");

        cell = row.createCell(COLUMN_VIS_KM);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("vis_km");

        cell = row.createCell(COLUMN_VIS_MILES);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("vis_miles");

        cell = row.createCell(COLUMN_GUST_MPH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("gust_mph");

        cell = row.createCell(COLUMN_GUST_KPH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("gust_kph");

    }
    // Write data
    public static void writeBook(WeatherDTO weatherDTO, Row row) {

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(weatherDTO.getLocationId());

        cell = row.createCell(COLUMN_TIME_EPOCH);
        cell.setCellValue(weatherDTO.getTimeEpoch());

        cell = row.createCell(COLUMN_TIME);
        cell.setCellValue(weatherDTO.getTime());

        cell = row.createCell(COLUMN_TEMP_C);
        cell.setCellValue(weatherDTO.getTempC());

        cell = row.createCell(COLUMN_TEMP_F);
        cell.setCellValue(weatherDTO.getTempF());

        cell = row.createCell(COLUMN_IS_DAY);
        cell.setCellValue(weatherDTO.getIsDay());

        cell = row.createCell(COLUMN_CONDITION);
        cell.setCellValue(weatherDTO.getObjectToString(weatherDTO.getCondition()));


        cell = row.createCell(COLUMN_WIND_MPH);
        cell.setCellValue(weatherDTO.getWindMph());

        cell = row.createCell(COLUMN_WIND_KPH);
        cell.setCellValue(weatherDTO.getWindKph());

        cell = row.createCell(COLUMN_WIND_DEGREE);
        cell.setCellValue(weatherDTO.getWindDegree());

        cell = row.createCell(COLUMN_WIND_DIR);
        cell.setCellValue(weatherDTO.getWindDir());

        cell = row.createCell(COLUMN_PRESSURE_MB);
        cell.setCellValue(weatherDTO.getPressureMb());

        cell = row.createCell(COLUMN_PRESSURE_IN);
        cell.setCellValue(weatherDTO.getPressureIn());

        cell = row.createCell(COLUMN_PRECIP_MM);
        cell.setCellValue(weatherDTO.getPrecipMm());

        cell = row.createCell(COLUMN_PRECIP_IN);
        cell.setCellValue(weatherDTO.getPrecipIn());

        cell = row.createCell(COLUMN_HUMIDITY);
        cell.setCellValue(weatherDTO.getHumidity());

        cell = row.createCell(COLUMN_CLOUD);
        cell.setCellValue(weatherDTO.getCloud());

        cell = row.createCell(COLUMN_FEELSLIKE_C);
        cell.setCellValue(weatherDTO.getFeelsLikeC());

        cell = row.createCell(COLUMN_FEELSLIKE_F);
        cell.setCellValue(weatherDTO.getFeelsLikeF());

        cell = row.createCell(COLUMN_WINDCHILL_C);
        cell.setCellValue(weatherDTO.getWindChillC());

        cell = row.createCell(COLUMN_WINDCHILL_F);
        cell.setCellValue(weatherDTO.getWindChillF());

        cell = row.createCell(COLUMN_HEATINDEX_C);
        cell.setCellValue(weatherDTO.getHeatIndexC());

        cell = row.createCell(COLUMN_HEATINDEX_F);
        cell.setCellValue(weatherDTO.getHeatIndexF());

        cell = row.createCell(COLUMN_DEWPOINT_C);
        cell.setCellValue(weatherDTO.getDewPointC());

        cell = row.createCell(COLUMN_DEWPOINT_F);
        cell.setCellValue(weatherDTO.getDewPointF());

        cell = row.createCell(COLUMN_WILL_IT_RAIN);
        cell.setCellValue(weatherDTO.getWillItRain());

        cell = row.createCell(COLUMN_CHANCE_OF_RAIN);
        cell.setCellValue(weatherDTO.getChanceOfRain());

        cell = row.createCell(COLUMN_WILL_IT_SNOW);
        cell.setCellValue(weatherDTO.getWillItSnow());

        cell = row.createCell(COLUMN_CHANCE_IT_SNOW);
        cell.setCellValue(weatherDTO.getChanceOfSnow());

        cell = row.createCell(COLUMN_VIS_KM);
        cell.setCellValue(weatherDTO.getVisKm());

        cell = row.createCell(COLUMN_VIS_MILES);
        cell.setCellValue(weatherDTO.getVisMiles());

        cell = row.createCell(COLUMN_GUST_MPH);
        cell.setCellValue(weatherDTO.getGustMph());

        cell = row.createCell(COLUMN_GUST_KPH);
        cell.setCellValue(weatherDTO.getGustKph());


        // Create cell formula
        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }
    public static List<WeatherDTO> exportExcelWeather(WeatherRequest input, String url, String db) throws IOException {
        //List<LocationDTO> listLocation = getListLocations();
        List<WeatherDTO> listWeather = findWeatherFollowRequest(input, url, db);
        if (listWeather == null)
            logger.error("Export Excel Weather: ListWeather không có data");
        try {
            File file = new File("./export");
            if (!file.exists()){
                file.mkdirs();
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String currentDirectory = file.getName()+"/weather"+localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) +".json";
            // Create Workbook
            Workbook workbook = ExportExcel.getWorkbook(currentDirectory);
            // Create sheet
            Sheet sheet = workbook.createSheet("Weather");
            int rowIndex = 0;
            // Write header
            writeHeader(sheet, rowIndex);
            // Write data
            rowIndex++;
            for (WeatherDTO weatherDTO : listWeather) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(weatherDTO, row);
                rowIndex++;
            }
            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            ExportExcel.autoSizeColumn(sheet, numberOfColumn);
            // Create file excel
            ExportExcel.createOutputFile(workbook, currentDirectory);
            logger.info("Export Excel Weather: Done!!!");
        } catch (Exception e) {
            logger.error("Export Excel Weather: Lỗi tạo file excel " + e);
        }
        return listWeather;
    }
    public static String exportDownloadExcelWeather(WeatherRequest input, String url, String db) throws IOException {

        String fileName = null;
        List<WeatherDTO> listWeather = findWeatherFollowRequest(input, url, db);
        if (listWeather == null)
            logger.error("Export and download excel weather: ListWeather không có data");
        try {
            File file = new File("./export");
            if (!file.exists()){
                file.mkdirs();
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String currentDirectory = file.getName()+"/weather"+localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) +".xlsx";
            // Create Workbook
            Workbook workbook = ExportExcel.getWorkbook(currentDirectory);
            // Create sheet
            Sheet sheet = workbook.createSheet("Weather");
            int rowIndex = 0;
            // Write header
            writeHeader(sheet, rowIndex);
            // Write data
            rowIndex++;
            for (WeatherDTO weatherDTO : listWeather) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(weatherDTO, row);
                rowIndex++;
            }
            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            ExportExcel.autoSizeColumn(sheet, numberOfColumn);
            // Create file excel
            ExportExcel.createOutputFile(workbook, currentDirectory);
            fileName =currentDirectory;
            logger.info("Export and download excel weather: Done!!!");
        } catch (Exception e) {
            logger.error("Export and download excel weather: Lỗi tạo file excel " + e);
        }
        return fileName;
    }
    public static List<WeatherDTO> findWeatherFollowRequest(WeatherRequest input, String url, String db) {
        String startDate = "";
        String endDate = "";
        List<WeatherDTO> listWeather = new ArrayList<>();
        String locationId = input.getLocationId().trim();
        //locationId = locationId.replaceAll(" ", "");
        String[] arrLocation = locationId.split(",");
//        if (input.getDatetimeRange().isBlank()) {
//            logger.error("Test get list weather: get datetime_range is null");
//        } else {
        String[] dateTimeRange = input.getDatetimeRange().split("-");
        if (dateTimeRange.length < 2 || dateTimeRange.length > 2) {
            logger.error("Find weather follow request: dateTimeRange have must startDate and endDate");
        } else {
            startDate = dateTimeRange[0].trim();
            endDate = dateTimeRange[1].trim();
        }
////         }
//        if (input.getLocationId().isBlank()) {
//            logger.error("Test get list weather: get location_id is null");
//        } else {
//            arrLocation = locationId.split(",");
//        }
        try {
            LocalDateTime dateTimeStartDate = LocalDateTime.parse(startDate.trim(), formatter);
            LocalDateTime dateTimeEndDate = LocalDateTime.parse(endDate.trim(), formatter);
            startDate = dateTimeStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            endDate = dateTimeEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            LocalDateTime dateTimeTmp = dateTimeStartDate;
            while (dateTimeTmp.compareTo(dateTimeEndDate) <= 0) {
                try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
                    MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, db);
                    MongoCollection<Document> collection = database.getCollection(String.format("Weather_%s", dateTimeTmp.format(DateTimeFormatter.BASIC_ISO_DATE)));
                    Bson filters = Filters.and(
                            Filters.gte("time", startDate),
                            Filters.lte("time", endDate),
                            Filters.in("location_id", arrLocation)
                    );
                    FindIterable<Document> myDoc = collection.find(filters);

                    //listWeather = List.of(objectMapper.readValue(myDoc.toString(), WeatherDTO[].class));
                    if (myDoc.first() == null) {
                        logger.error(String.format("Find weather follow date, Error, dateTimeRange: %s,locationId: %s, url: %s, db: %s, date: %s", input.getDatetimeRange(), locationId, url, db, dateTimeTmp.format(DateTimeFormatter.BASIC_ISO_DATE)));
                    } else {
                        for (Document doc : myDoc) {
                            WeatherDTO weatherDTO = new WeatherDTO();
                            ObjectMapper objectMapper = new ObjectMapper();
                            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            weatherDTO = objectMapper.readValue(doc.toBsonDocument().toString(), WeatherDTO.class);

//                            weatherDTO.setLocation_id(ObjectUtils.isEmpty(doc.get("location_id"))?"":doc.get("id").toString());
//                            weatherDTO.setTime_epoch(ObjectUtils.isEmpty(doc.get("time_epoch"))?0:Integer.parseInt(doc.get("time_epoch").toString()));
//                            weatherDTO.setTime(ObjectUtils.isEmpty(doc.get("time"))?"":doc.get("time").toString());
//                            weatherDTO.setTempC(ObjectUtils.isEmpty(doc.get("temp_c"))?0:Double.parseDouble(doc.get("temp_c").toString()));
//                            weatherDTO.setTempF(ObjectUtils.isEmpty(doc.get("temp_f"))?0:Double.parseDouble(doc.get("temp_f").toString()));
//                            weatherDTO.setIsDay(ObjectUtils.isEmpty(doc.get("is_day"))?0:Integer.parseInt(doc.get("is_day").toString()));
//                            weatherDTO.setCondition(ObjectUtils.isEmpty(doc.get("condition"))?"":doc.get("condition"));
//                            weatherDTO.setWindMph(ObjectUtils.isEmpty(doc.get("wind_mph"))?0:Double.parseDouble(doc.get("wind_mph").toString()));
//                            weatherDTO.setWindKph(ObjectUtils.isEmpty(doc.get("wind_kph"))?0:Double.parseDouble(doc.get("wind_kph").toString()));
//                            weatherDTO.setWindDegree(ObjectUtils.isEmpty(doc.get("wind_degree"))?0:Integer.parseInt(doc.get("wind_degree").toString()));
//                            weatherDTO.setWindDir(ObjectUtils.isEmpty(doc.get("wind_dir"))?"":doc.get("wind_dir").toString());
//                            weatherDTO.setPressureMb(ObjectUtils.isEmpty(doc.get("pressure_mb"))?0:Double.parseDouble(doc.get("pressure_mb").toString()));
//                            weatherDTO.setPressureIn(ObjectUtils.isEmpty(doc.get("pressure_in"))?0:Double.parseDouble(doc.get("pressure_in").toString()));
//                            weatherDTO.setPrecipMm(ObjectUtils.isEmpty(doc.get("precip_mm"))?0:Double.parseDouble(doc.get("precip_mm").toString()));
//                            weatherDTO.setPrecipIn(ObjectUtils.isEmpty(doc.get("precip_in"))?0:Double.parseDouble(doc.get("precip_in").toString()));
//                            weatherDTO.setHumidity(ObjectUtils.isEmpty(doc.get("humidity"))?0:Integer.parseInt(doc.get("humidity").toString()));
//                            weatherDTO.setCloud(ObjectUtils.isEmpty(doc.get("cloud"))?0:Integer.parseInt(doc.get("cloud").toString()));
//                            weatherDTO.setFeelsLikeC(ObjectUtils.isEmpty(doc.get("feelslike_c"))?0:Double.parseDouble(doc.get("feelslike_c").toString()));
//                            weatherDTO.setFeelsLikeF(ObjectUtils.isEmpty(doc.get("feelslike_f"))?0:Double.parseDouble(doc.get("feelslike_f").toString()));
//                            weatherDTO.setWindChillC(ObjectUtils.isEmpty(doc.get("windchill_c"))?0:Double.parseDouble(doc.get("windchill_c").toString()));
//                            weatherDTO.setWindChillF(ObjectUtils.isEmpty(doc.get("windchill_f"))?0:Double.parseDouble(doc.get("windchill_f").toString()));
//                            weatherDTO.setHeatIndexC(ObjectUtils.isEmpty(doc.get("heatindex_c"))?0:Double.parseDouble(doc.get("heatindex_c").toString()));
//                            weatherDTO.setHeatIndexF(ObjectUtils.isEmpty(doc.get("heatindex_f"))?0:Double.parseDouble(doc.get("heatindex_f").toString()));
//                            weatherDTO.setDewPointC(ObjectUtils.isEmpty(doc.get("dewpoint_c"))?0:Double.parseDouble(doc.get("dewpoint_c").toString()));
//                            weatherDTO.setDewPointF(ObjectUtils.isEmpty(doc.get("dewpoint_f"))?0:Double.parseDouble(doc.get("dewpoint_f").toString()));
//                            weatherDTO.setWillItRain(ObjectUtils.isEmpty(doc.get("will_it_rain"))?0:Integer.parseInt(doc.get("will_it_rain").toString()));
//                            weatherDTO.setChanceOfRain(ObjectUtils.isEmpty(doc.get("chance_of_rain"))?0:Integer.parseInt(doc.get("chance_of_rain").toString()));
//                            weatherDTO.setWillItSnow(ObjectUtils.isEmpty(doc.get("will_it_snow"))?0:Integer.parseInt(doc.get("will_it_snow").toString()));
//                            weatherDTO.setChanceOfSnow(ObjectUtils.isEmpty(doc.get("chance_of_snow"))?0:Integer.parseInt(doc.get("chance_of_snow").toString()));
//                            weatherDTO.setVisKm(ObjectUtils.isEmpty(doc.get("vis_km"))?0:Double.parseDouble(doc.get("vis_km").toString()));
//                            weatherDTO.setVisMiles(ObjectUtils.isEmpty(doc.get("vis_miles"))?0:Double.parseDouble(doc.get("vis_miles").toString()));
//                            weatherDTO.setGustMph(ObjectUtils.isEmpty(doc.get("gust_mph"))?0:Double.parseDouble(doc.get("gust_mph").toString()));
//                            weatherDTO.setGustKph(ObjectUtils.isEmpty(doc.get("gust_kph"))?0:Double.parseDouble(doc.get("gust_kph").toString()));

                            listWeather.add(weatherDTO);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Find weather follow request, exception: "+e);
                    logger.error(String.format("Find weather follow request: Error, dateTimeRange: %s,locationId: %s, url: %s, db: %s, date: %s", input.getDatetimeRange(), locationId, url, db, dateTimeTmp.format(DateTimeFormatter.BASIC_ISO_DATE)));
                }
                dateTimeTmp = dateTimeTmp.plusDays(1);
            }
        } catch (Exception e){
            logger.error("Find weather follow request, Exception: "+e);
            logger.error(String.format("Find weather follow request, Lỗi không đúng format, startDate: %s, endDate: %s, format: dd/MM/yyyy HH:mm:ss",startDate, endDate));
        }
        return listWeather;
    }
}
