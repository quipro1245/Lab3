package com.example.Lab2.weathers.service;

import com.example.Lab2.weathers.models.WeatherDTO;
import com.example.Lab2.weathers.models.WeatherRequest;
import com.example.Lab2.controller.ConnectionMongoDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.Lab2.locations.service.LocationService.*;

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
    public static List<WeatherDTO> getListWeather(String date) {

        String url = "mongodb://localhost:27017";
        List<WeatherDTO> listLocation = new ArrayList<>();

        try (MongoClient mongoClient = new ConnectionMongoDB().getMongoClient(url)) {
            MongoDatabase database = new ConnectionMongoDB().getMongoDatabase(mongoClient, "all_things");
            MongoCollection<Document> collection = database.getCollection(String.format("Weather_%s", date));
            FindIterable<Document> myDoc = collection.find();

            if (myDoc == null) {
                logger.error("Get list weather không tìm thấy");
            } else {
                for (Document doc : myDoc) {
                    WeatherDTO weatherDTO = new WeatherDTO();
                    weatherDTO.setId(Integer.parseInt(doc.get("id").toString()));
                    weatherDTO.setTime_epoch(Integer.parseInt(doc.get("time_epoch").toString()));
                    weatherDTO.setTime(doc.get("time").toString());
                    weatherDTO.setTemp_c(Double.parseDouble(doc.get("temp_c").toString()));
                    weatherDTO.setTemp_f(Double.parseDouble(doc.get("temp_f").toString()));
                    weatherDTO.setIs_day(Integer.parseInt(doc.get("is_day").toString()));
                    weatherDTO.setCondition(doc.get("condition"));
                    weatherDTO.setWind_mph(Double.parseDouble(doc.get("wind_mph").toString()));
                    weatherDTO.setWind_kph(Double.parseDouble(doc.get("wind_kph").toString()));
                    weatherDTO.setWind_degree(Integer.parseInt(doc.get("wind_degree").toString()));
                    weatherDTO.setWind_dir(doc.get("wind_dir").toString());
                    weatherDTO.setPressure_mb(Double.parseDouble(doc.get("pressure_mb").toString()));
                    weatherDTO.setPressure_in(Double.parseDouble(doc.get("pressure_in").toString()));
                    weatherDTO.setPrecip_mm(Double.parseDouble(doc.get("precip_mm").toString()));
                    weatherDTO.setPrecip_in(Double.parseDouble(doc.get("precip_in").toString()));
                    weatherDTO.setHumidity(Integer.parseInt(doc.get("humidity").toString()));
                    weatherDTO.setCloud(Integer.parseInt(doc.get("cloud").toString()));
                    weatherDTO.setFeelslike_c(Double.parseDouble(doc.get("feelslike_c").toString()));
                    weatherDTO.setFeelslike_f(Double.parseDouble(doc.get("feelslike_f").toString()));
                    weatherDTO.setWindchill_c(Double.parseDouble(doc.get("windchill_c").toString()));
                    weatherDTO.setWindchill_f(Double.parseDouble(doc.get("windchill_f").toString()));
                    weatherDTO.setHeatindex_c(Double.parseDouble(doc.get("heatindex_c").toString()));
                    weatherDTO.setHeatindex_f(Double.parseDouble(doc.get("heatindex_f").toString()));
                    weatherDTO.setDewpoint_c(Double.parseDouble(doc.get("dewpoint_c").toString()));
                    weatherDTO.setDewpoint_f(Double.parseDouble(doc.get("dewpoint_f").toString()));
                    weatherDTO.setWill_it_rain(Integer.parseInt(doc.get("will_it_rain").toString()));
                    weatherDTO.setChance_of_rain(Integer.parseInt(doc.get("chance_of_rain").toString()));
                    weatherDTO.setWill_it_snow(Integer.parseInt(doc.get("will_it_snow").toString()));
                    weatherDTO.setChance_of_snow(Integer.parseInt(doc.get("chance_of_snow").toString()));
                    weatherDTO.setVis_km(Double.parseDouble(doc.get("vis_km").toString()));
                    weatherDTO.setVis_miles(Double.parseDouble(doc.get("vis_miles").toString()));
                    weatherDTO.setGust_mph(Double.parseDouble(doc.get("gust_mph").toString()));
                    weatherDTO.setGust_kph(Double.parseDouble(doc.get("gust_kph").toString()));

                    listLocation.add(weatherDTO);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Get list weather: Không thể lấy được data url: %s, date: %s", url, date));

        }
        return listLocation;
    }

    // Get list weather from start day to end day
    public static List<WeatherDTO> getListWeatherStartToEndDate(String startDate, String endDate) {
        List<WeatherDTO> listWeatherDTO = new ArrayList<>();
        try {
            LocalDateTime dateTime_StartDate = LocalDateTime.parse(startDate.trim(), formatter);
            LocalDateTime dateTime_EnnDate = LocalDateTime.parse(endDate.trim(), formatter);

            while (dateTime_StartDate.compareTo(dateTime_EnnDate) <= 0) {
                List<WeatherDTO> weatherDTO = getListWeather(dateTime_StartDate.format(DateTimeFormatter.BASIC_ISO_DATE));
                listWeatherDTO.addAll(weatherDTO);
                dateTime_StartDate = dateTime_StartDate.plusDays(1);
            }
        } catch (Exception e) {
            logger.error(String.format("Get list weather start to end date: start date: %s, end date: %s", startDate, endDate));
        }
        return listWeatherDTO;
    }

    // Compare date
    public static boolean compareItemTimeWeatherWithDateTime_range(String startDate, String endDate, String itemTimeWeather) {
        try {
            LocalDateTime dateTime_StartDate = LocalDateTime.parse(startDate.trim(), formatter);
            LocalDateTime dateTime_EnnDate = LocalDateTime.parse(endDate.trim(), formatter);
            LocalDateTime dateTime_itemTimeWeather = LocalDateTime.parse(itemTimeWeather, formatterWeather);
            if (dateTime_itemTimeWeather.compareTo(dateTime_StartDate) >= 0 && dateTime_itemTimeWeather.compareTo(dateTime_EnnDate) <= 0)
                return true;
        } catch (Exception e) {
            logger.error(String.format("Compare item time weather with date time range: start date: %s, end date: %s, item time weather: %s", startDate, endDate, itemTimeWeather));
        }
        return false;
    }

    //@PostMapping(value = "/findWeatherFollowDate")
    public static List<WeatherDTO> findWeatherFollowDate(WeatherRequest input) {
        String startDate;
        String endDate;
        String[] location_id_arr = null;
        String datetime_range_Object = input.getDatetime_range();
        String location_id_Object = input.getLocation_id();
        List<WeatherDTO> listWeatherDTO;
        List<WeatherDTO> findWeatherFollowDate = new ArrayList<>();
        if (datetime_range_Object.trim().equals("")) {
            startDate = "";
            endDate = "";
        } else {
            String[] datetime_range_arr = datetime_range_Object.split("-");
            if (datetime_range_arr.length < 2) {
                startDate = datetime_range_arr[0];
                endDate = "";
            } else {
                startDate = datetime_range_arr[0];
                endDate = datetime_range_arr[1];
            }
        }
        if (location_id_Object.trim().equals("")) {
            logger.warn("Find weather follow date: location is null");
        } else {
            location_id_arr = location_id_Object.split(",");
        }
        try {
            // Trường hợp datetime_range có giá trị 1 trong 2 hoặc cả 2 bị null
            if (startDate.trim().equals("") || endDate.trim().equals("")) {
//                // Cả ngày bắt đầu và kết thúc bị null
//                if (startDate.trim().equals("") && endDate.trim().equals("")) {
//                    listWeatherDTO = getListWeatherStartToEndDate("01/09/2022 00:00:00", "31/10/2022 23:00:00");
//                } else if (startDate.trim().equals("")) { // Ngày bắt đầu bị null
//                    listWeatherDTO = getListWeatherStartToEndDate("01/09/2022 00:00:00", endDate);
//                } else { // Ngày kết thúc bị null
//                    listWeatherDTO = getListWeatherStartToEndDate(startDate, "31/10/2022 23:00:00");
//                }
//                for (WeatherDTO weather : listWeatherDTO) {
//                    if (location_id_arr == null) {
//                        findWeatherFollowDate.add(weather);
//                    } else {
//                        for (String itemLocationID : location_id_arr) {
//                            if (weather.getId() == Integer.parseInt(itemLocationID.trim())) {
//                                findWeatherFollowDate.add(weather);
//                            }
//                        }
//                    }
//                }
                logger.error("Find weather follow date: datetime_range không được bỏ trống và nhập đúng định dạng dd/MM/yyyy HH:mm:ss - dd/MM/yyyy HH:mm:ss");
            } else {
                listWeatherDTO = getListWeatherStartToEndDate(startDate, endDate);
                for (WeatherDTO weather : listWeatherDTO) {
                    // Trường họp location là null
                    if (location_id_arr == null) {
//                        if (compareItemTimeWeatherWithDateTime_range(startDate, endDate, weather.getTime()))
//                            findWeatherFollowDate.add(weather);
                        logger.error("Find weather follow date: location_id không được bỏ trống");
                    } else {
                        // Trường hợp location có giá trị
                        for (String itemLocationID : location_id_arr) {
                            if (weather.getId() == Integer.parseInt(itemLocationID)) {
                                if (compareItemTimeWeatherWithDateTime_range(startDate, endDate, weather.getTime()))
                                    findWeatherFollowDate.add(weather);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Find weather follow date: lỗi sai định dạng hoặc date không tồn tại");
            logger.error("Find weather follow date: " + e);
        }
        return findWeatherFollowDate;
    }

    //@PostMapping(value = "/exportJsonWeather")
    public static List<WeatherDTO> exportJsonWeather(WeatherRequest input) {
        List<WeatherDTO> listWeather = findWeatherFollowDate(input);
        JSONArray listJsonObject = new JSONArray();
        for (WeatherDTO weather : listWeather) {
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            //Inserting key-value pairs into the json object
            jsonObject.put("id", weather.getId());
            jsonObject.put("time_epoch", weather.getTime_epoch());
            jsonObject.put("time", weather.getTime());
            jsonObject.put("temp_c", weather.getTemp_c());
            jsonObject.put("temp_f", weather.getTemp_f());
            jsonObject.put("is_day", weather.getIs_day());
            jsonObject.put("condition", weather.getCondition());
            jsonObject.put("wind_mph", weather.getWind_mph());
            jsonObject.put("wind_kph", weather.getWind_kph());
            jsonObject.put("wind_degree", weather.getWind_degree());
            jsonObject.put("wind_dir", weather.getWind_dir());
            jsonObject.put("pressure_mb", weather.getPressure_mb());
            jsonObject.put("pressure_in", weather.getPressure_in());
            jsonObject.put("precip_mm", weather.getPrecip_mm());
            jsonObject.put("precip_in", weather.getPrecip_in());
            jsonObject.put("humidity", weather.getHumidity());
            jsonObject.put("cloud", weather.getCloud());
            jsonObject.put("feelslike_c", weather.getFeelslike_c());
            jsonObject.put("feelslike_f", weather.getFeelslike_f());
            jsonObject.put("windchill_c", weather.getWindchill_c());
            jsonObject.put("windchill_f", weather.getWindchill_f());
            jsonObject.put("heatindex_c", weather.getHeatindex_c());
            jsonObject.put("heatindex_f", weather.getHeatindex_f());
            jsonObject.put("dewpoint_c", weather.getDewpoint_c());
            jsonObject.put("dewpoint_f", weather.getDewpoint_f());
            jsonObject.put("will_it_rain", weather.getWill_it_rain());
            jsonObject.put("chance_of_rain", weather.getChance_of_rain());
            jsonObject.put("will_it_snow", weather.getWill_it_snow());
            jsonObject.put("chance_of_snow", weather.getChance_of_snow());
            jsonObject.put("vis_km", weather.getVis_km());
            jsonObject.put("vis_miles", weather.getVis_miles());
            jsonObject.put("gust_mph", weather.getGust_mph());
            jsonObject.put("gust_kph", weather.getGust_kph());
            listJsonObject.add(jsonObject);
        }
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            WeatherDTO[] weatherDTOS = gson.fromJson(listJsonObject.toString(), WeatherDTO[].class);
            String prettyJsonString = gson.toJson(weatherDTOS);
            FileWriter file = new FileWriter("D:/weather.json");
            file.write(prettyJsonString);
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Export file json weather:" + e);
            logger.error("Export file json weather: input: " + input);
        }
        return listWeather;
    }

    public static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
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
        cell.setCellValue(weatherDTO.getId());

        cell = row.createCell(COLUMN_TIME_EPOCH);
        cell.setCellValue(weatherDTO.getTime_epoch());

        cell = row.createCell(COLUMN_TIME);
        cell.setCellValue(weatherDTO.getTime());

        cell = row.createCell(COLUMN_TEMP_C);
        cell.setCellValue(weatherDTO.getTemp_c());

        cell = row.createCell(COLUMN_TEMP_F);
        cell.setCellValue(weatherDTO.getTemp_f());

        cell = row.createCell(COLUMN_IS_DAY);
        cell.setCellValue(weatherDTO.getIs_day());

        cell = row.createCell(COLUMN_CONDITION);
        cell.setCellValue(weatherDTO.getCondition().toString());


        cell = row.createCell(COLUMN_WIND_MPH);
        cell.setCellValue(weatherDTO.getWind_mph());

        cell = row.createCell(COLUMN_WIND_KPH);
        cell.setCellValue(weatherDTO.getWind_kph());

        cell = row.createCell(COLUMN_WIND_DEGREE);
        cell.setCellValue(weatherDTO.getWind_degree());

        cell = row.createCell(COLUMN_WIND_DIR);
        cell.setCellValue(weatherDTO.getWind_dir());

        cell = row.createCell(COLUMN_PRESSURE_MB);
        cell.setCellValue(weatherDTO.getPressure_mb());

        cell = row.createCell(COLUMN_PRESSURE_IN);
        cell.setCellValue(weatherDTO.getPressure_in());

        cell = row.createCell(COLUMN_PRECIP_MM);
        cell.setCellValue(weatherDTO.getPrecip_mm());

        cell = row.createCell(COLUMN_PRECIP_IN);
        cell.setCellValue(weatherDTO.getPrecip_in());

        cell = row.createCell(COLUMN_HUMIDITY);
        cell.setCellValue(weatherDTO.getHumidity());

        cell = row.createCell(COLUMN_CLOUD);
        cell.setCellValue(weatherDTO.getCloud());

        cell = row.createCell(COLUMN_FEELSLIKE_C);
        cell.setCellValue(weatherDTO.getFeelslike_c());

        cell = row.createCell(COLUMN_FEELSLIKE_F);
        cell.setCellValue(weatherDTO.getFeelslike_f());

        cell = row.createCell(COLUMN_WINDCHILL_C);
        cell.setCellValue(weatherDTO.getWindchill_c());

        cell = row.createCell(COLUMN_WINDCHILL_F);
        cell.setCellValue(weatherDTO.getWindchill_f());

        cell = row.createCell(COLUMN_HEATINDEX_C);
        cell.setCellValue(weatherDTO.getHeatindex_c());

        cell = row.createCell(COLUMN_HEATINDEX_F);
        cell.setCellValue(weatherDTO.getHeatindex_f());

        cell = row.createCell(COLUMN_DEWPOINT_C);
        cell.setCellValue(weatherDTO.getDewpoint_c());

        cell = row.createCell(COLUMN_DEWPOINT_F);
        cell.setCellValue(weatherDTO.getDewpoint_f());

        cell = row.createCell(COLUMN_WILL_IT_RAIN);
        cell.setCellValue(weatherDTO.getWill_it_rain());

        cell = row.createCell(COLUMN_CHANCE_OF_RAIN);
        cell.setCellValue(weatherDTO.getChance_of_rain());

        cell = row.createCell(COLUMN_WILL_IT_SNOW);
        cell.setCellValue(weatherDTO.getWill_it_snow());

        cell = row.createCell(COLUMN_CHANCE_IT_SNOW);
        cell.setCellValue(weatherDTO.getChance_of_snow());

        cell = row.createCell(COLUMN_VIS_KM);
        cell.setCellValue(weatherDTO.getVis_km());

        cell = row.createCell(COLUMN_VIS_MILES);
        cell.setCellValue(weatherDTO.getVis_miles());

        cell = row.createCell(COLUMN_GUST_MPH);
        cell.setCellValue(weatherDTO.getGust_mph());

        cell = row.createCell(COLUMN_GUST_KPH);
        cell.setCellValue(weatherDTO.getGust_kph());


        // Create cell formula
        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    public static List<WeatherDTO> exportExcelWeather(WeatherRequest input) throws IOException {
        //List<LocationDTO> listLocation = getListLocations();
        List<WeatherDTO> listWeather = findWeatherFollowDate(input);
        if (listWeather == null)
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
            for (WeatherDTO weatherDTO : listWeather) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(weatherDTO, row);
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
        return listWeather;
    }
}
