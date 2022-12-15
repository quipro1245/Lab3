package com.example.lab2.controller;

import com.example.lab2.locations.models.LocationDTO;
import com.example.lab2.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
public class RestSpringBootController {
    final static Logger logger = LogManager.getLogger(RestSpringBootController.class);

    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping(value = "/callclienthello")
    private String getHelloClient() {
        String url = "https://api.weatherapi.com/v1/search.json?key=fd22952abb98454e9b324431222911&q=uk";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return result;

    }

    @GetMapping(value = "/Locations")
    public List<LocationDTO> getLocations() {
        String url = "https://api.weatherapi.com/v1/search.json?key=fd22952abb98454e9b324431222911&q=uk";
        RestTemplate restTemplate = new RestTemplate();
        LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
        String uri = "mongodb://localhost:27017";

        //System.out.println(item);
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Tạo database mới nếu chưa có database đó
            MongoDatabase database = mongoClient.getDatabase("all_things");
            for (var item : locations) {

                Document document = new Document();
                document.append("id", item.getId());
                document.append("name", item.getName());
                document.append("region", item.getRegion());
                document.append("country", item.getCountry());
                document.append("lat", item.getLat());
                document.append("lon", item.getLon());
                document.append("url", item.getUrl());
                // insert vô collection nếu chưa có collection sẽ tạo mới collection
                database.getCollection("Locations").insertOne(document);
                System.out.println("Document inserted successfully");
            }
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
        return Arrays.asList(locations);
    }

    @GetMapping(value = "/ListLocations")
    public List<LocationDTO> ListLocations() {
        String url = "https://api.weatherapi.com/v1/search.json?key=fd22952abb98454e9b324431222911&q=uk";
        RestTemplate restTemplate = new RestTemplate();
        LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("all_things");
        return Arrays.asList(locations);
    }

    @GetMapping(value = "/getListLocations")
    public FindIterable<Document> GetListLocations() {

        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("all_things");
        MongoCollection<Document> collection = database.getCollection("Locations");

        FindIterable<Document> myDoc = collection.find();
        if (myDoc == null) {
            logger.error("location không tìm thấy");
        }
        return myDoc;
    }

    @GetMapping(value = "/getname")
    public List<String> getName() {
        List<String> locations = new ArrayList<String>();
        FindIterable<Document> myDoc = GetListLocations();
        for (Document doc : myDoc
        ) {
            Object name = doc.get("name");
            locations.add(name.toString());
        }
        return locations;
    }

    @GetMapping(value = "/Locations/{q}")
    public List<LocationDTO> getLocationsForID(@PathVariable String q) {
        String url = String.format("https://api.weatherapi.com/v1/search.json?key=fd22952abb98454e9b324431222911&q=%s", q);
        RestTemplate restTemplate = new RestTemplate();
        LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
        String uri = "mongodb://localhost:27017";

        // Kiểm tra locations có tồn tại hay không
        if (locations.length == 0) {
            logger.error(String.format("Không tìm thấy locations có religion %s", q));
        } else {
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                // Tạo database mới nếu chưa có database đó
                MongoDatabase database = mongoClient.getDatabase("all_things");
                for (var item : locations) {
                    Document document = new Document();
                    document.put("id", item.getId());
                    document.put("name", item.getName());
                    document.put("region", item.getRegion());
                    document.put("country", item.getCountry());
                    document.put("lat", item.getLat());
                    document.put("lon", item.getLon());
                    document.put("url", item.getUrl());
                    // insert vô collection nếu chưa có collection sẽ tạo mới collection
                    //MongoCollection<Document> collection = database.getCollection("Locations");
                    //Document myDoc = collection.find(eq("id", item.getId())).first();
                    // Kiểm tra doc có tồn tại không, nếu có thì update, ngược lại thì insert
//                    if(myDoc!= null){
//                        //System.out.println("Đã tồn tại"+myDoc.toJson());
//
//                        BasicDBObject query = new BasicDBObject();
//                        query.put("id", item.getId()); // (1)
//
//                        BasicDBObject newDocument = new BasicDBObject();
//                        newDocument.put("name", item.getName()); // (2)
//                        newDocument.put("region", item.getRegion());
//                        newDocument.put("country", item.getCountry());
//                        newDocument.put("lat", item.getLat());
//                        newDocument.put("lon", item.getLon());
//                        newDocument.put("url", item.getUrl());
//
//                        BasicDBObject updateObject = new BasicDBObject();
//                        updateObject.put("$set", newDocument); // (3)
//
//                        database.getCollection("Locations").updateOne(query, updateObject); // (4)
//                        System.out.println("Document updated successfully");
//                    }
//                    else {
//                        database.getCollection("Locations").insertOne(document);
//                        System.out.println("Document inserted successfully");
//                    }
                    BasicDBObject query = new BasicDBObject();
                    query.put("id", item.getId());
                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", document);
                    UpdateOptions options = new UpdateOptions().upsert(true);
                    database.getCollection("Locations").updateOne(query, updateObject, options); // (4)
                    logger.info("Document locations upsert successfully GetLocations" + document);

                }
            } catch (MongoException me) {
                logger.error("Unable to insert due to an error: " + me);
            }
        }
        return Arrays.asList(locations);
    }

    @GetMapping("/get/{id}")
    public String getId(@PathVariable String id) {
        return "ID " + id;
    }

    @PostMapping("/postJson")
    public Map<String, Object> postJson(@RequestBody Map<String, Object> q) {
        Object listvalue = q.get("country");
        ArrayList arrayList = (ArrayList) listvalue;
        for (Object item : arrayList
        ) {
            System.out.println(item.toString());
        }
        return q;
    }

    // Post locations
    @PostMapping("/postLocations")
    public Map<String, Object> postLocationsForID(@RequestBody Map<String, Object> q) {
        String uri = "mongodb://localhost:27017";
        Object listvalue = q.get("country");
        ArrayList arrayList = (ArrayList) listvalue;
        if (q == null) {
            logger.error("Chuỗi Json không hợp lệ hoặc country không tồn tại");
        }

        for (Object location : arrayList) {
            String url = String.format("https://api.weatherapi.com/v1/search.json?key=fd22952abb98454e9b324431222911&q=%s", location.toString());
            RestTemplate restTemplate = new RestTemplate();
            LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
            // Kiểm tra locations có tồn tại hay không
            if (locations.length == 0) {
                logger.error(String.format("Không tìm thấy locations cho giá trị %s", q));
            } else {
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Tạo database mới nếu chưa có database đó
                    MongoDatabase database = mongoClient.getDatabase("all_things");
                    for (var item : locations) {
                        Document document = new Document();
                        document.put("id", item.getId());
                        document.put("name", item.getName());
                        document.put("region", item.getRegion());
                        document.put("country", item.getCountry());
                        document.put("lat", item.getLat());
                        document.put("lon", item.getLon());
                        document.put("url", item.getUrl());

                        BasicDBObject query = new BasicDBObject();
                        query.put("id", item.getId());
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", document);
                        UpdateOptions options = new UpdateOptions().upsert(true);
                        database.getCollection("Locations").updateOne(query, updateObject, options); // (4)
                        logger.info("Post Locations Document locations upsert successfully " + document);

                    }
                } catch (MongoException me) {
                    logger.error("Unable to insert due to an error: " + me);
                }
            }
        }
        return q;
    }

    @PostMapping("/post")
    public String post(@RequestBody String body) {
        return "Body " + body;
    }

    public boolean checkYear(int nam) {
        return (nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0;
    }

    @PostMapping(value = "/testGetDay")
    public Map<String, Object> testGetDay(@RequestBody Map<String, Object> Year_Month) {
        Object listvalue = Year_Month.get("date");
        ArrayList arrayList = (ArrayList) listvalue;
        for (Object item : arrayList
        ) {
            System.out.println(getDay(item.toString() + "-01"));
        }

        int day = 0;

        int year = 0;// Integer.parseInt(date_string[0]);
        int month = 1;// Integer.parseInt(date_string[1]);

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 3;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 2;
                break;
            case 2:
                if (checkYear(year)) {
                    day = 1;
                } else {
                    day = 0;
                }
                break;
            default:
                System.out.print("Invalid month.");
                break;
        }

        return Year_Month;
    }

    public int getDay(String date) {
        int day = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);
        int year = dateTime.getYear();//Integer.parseInt(year_string);
        int month = dateTime.getMonthValue(); //Integer.parseInt(dateFormat.format(year_string));

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 3;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 2;
                break;
            case 2:
                if (checkYear(year)) {
                    day = 1;
                } else {
                    day = 0;
                }
                break;
            default:
                logger.error("Invalid month.");
                break;
        }

        return 28 + day;
    }

    @GetMapping(value = "/WeatherPutAll")
    public Weather getWeatherPutAll() throws ParseException {
        String date_string = "2022-11-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse(date_string);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";
        //List<LocationDTO> listLocations = ListLocations();
        FindIterable<Document> myDoc = GetListLocations();
        for (int i = 0; i < 28; i++) {
            //nextDate = new Date(date.getTime() + (1000 * 60 * 60 * 24*i));

            String dataf = dateFormat.format(c1.getTime());
            for (Document item : myDoc) {
                url = String.format("http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=%s&dt=%s", item.get("name"), dataf);
                restTemplate = new RestTemplate();
                weather = restTemplate.getForObject(url, Weather.class);
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Tạo database mới nếu chưa có database đó
                    MongoDatabase database = mongoClient.getDatabase("all_things");
                    Object forecastday = weather.getForecast().get("forecastday");
                    ArrayList arrforacastday = (ArrayList) forecastday;
                    Object itemobj = new Object();
                    Map<String, Object> arr;
                    for (Object a : arrforacastday) {
                        itemobj = a;
                    }
                    arr = (Map) itemobj;
                    Object hours = arr.get("hour");
                    ArrayList listHours = (ArrayList) hours;
                    for (Object itemHour : listHours) {

                        Document document = new Document();
                        document.append("id", item.get("id"));
                        Document document1 = new Document((Map) itemHour);
                        document.putAll(document1);
                        dataf.replace("-", "");
                        // insert vô collection nếu chưa có collection sẽ tạo mới collection
                        String name = dataf.replace("-", "");
                        MongoCollection<Document> collection = database.getCollection(String.format("Weather_%s", name));
//                        Document myDoc = collection.find(eq("id", item.getId())).first();
//                        Document myDoc1 = collection.find(eq("id", item.getId())).first();
//                        if(myDoc!=null){
//                            System.out.println("Đã tồn tại"+myDoc.toJson());
//                        }
//                        else {
//                            database.getCollection(String.format("Weather_%s", name)).insertOne(document);
//                            System.out.println("Document inserted successfully");
//                        }
                        database.getCollection(String.format("Weather_%s", name)).insertOne(document);
                        System.out.println("Document inserted successfully");
                    }

                } catch (MongoException me) {
                    System.err.println("Unable to insert due to an error: " + me);
                }
            }
            c1.roll(Calendar.DATE, true);
            //System.out.println(dataf);
        }
        return weather;
    }

    // Post weather follow list date
    @PostMapping(value = "/postWeatherfollowlistdate")
    public Map<String, Object> postWeatherfollowlistdate(@RequestBody Map<String, Object> Year_Month) throws ParseException {
        Object listvalue = Year_Month.get("date");
        ArrayList arrayList = (ArrayList) listvalue;
        for (Object itemdate : arrayList) {
            String date_string;
            System.out.println(getDay(itemdate.toString() + "-01"));
            date_string = itemdate + "-01";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = dateFormat.parse(date_string);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            String url;
            RestTemplate restTemplate;
            Weather weather;
            String uri = "mongodb://localhost:27017";

            FindIterable<Document> myDoc = GetListLocations();

            for (int i = 0; i < getDay(date_string); i++) {
                //nextDate = new Date(date.getTime() + (1000 * 60 * 60 * 24*i));

                String dataf = dateFormat.format(c1.getTime());
                for (Document item : myDoc) {
                    url = String.format("http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=%s&dt=%s", item.get("name"), dataf);
                    restTemplate = new RestTemplate();
                    try {
                        weather = restTemplate.getForObject(url, Weather.class);


                        try (MongoClient mongoClient = MongoClients.create(uri)) {
                            // Tạo database mới nếu chưa có database đó
                            MongoDatabase database = mongoClient.getDatabase("all_things");
                            Object forecastday = weather.getForecast().get("forecastday");
                            ArrayList arrforacastday = (ArrayList) forecastday;
                            Object itemobj = new Object();
                            Map<String, Object> arr;
                            for (Object a : arrforacastday) {
                                itemobj = a;
                            }
                            arr = (Map) itemobj;
                            Object hours = arr.get("hour");
                            ArrayList listHours = (ArrayList) hours;
                            for (Object itemHour : listHours) {

                                Document document = new Document();
                                document.append("id", item.get("id"));
                                Document document1 = new Document((Map) itemHour);
                                document.putAll(document1);

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate dateTime = LocalDate.parse(dataf, formatter);

                                String name = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
                                //database.getCollection(String.format("Weather_%s", name)).insertOne(document);
                                try {
                                    database.getCollection(String.format("Weather_%s", name)).insertOne(document);
                                } catch (Exception e) {
                                    logger.error("Unable to insert due to an error: " + e);
                                    logger.error("Hour" + itemHour);
                                }
                                logger.info("Post weather for ID document inserted successfully" + document);
                            }

                        } catch (MongoException me) {
                            logger.error("Unable to insert due to an error: " + me);
                            logger.error("Url" + url);
                            logger.error("Doc" + item);
                        }
                    } catch (Exception e) {
                        logger.error("Lỗi " + e);
                        logger.error("Weather không thể kết nối");
                    }
                }
                c1.roll(Calendar.DATE, true);

            }
            date_string = "";
        }
        return Year_Month;
    }

    // Post weather follow date
    @PostMapping(value = "/postWeatherPutAll")
    public Weather postWeatherPutAll(@RequestBody String q) throws ParseException {

        String date_string = q + "-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse(date_string);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";
        //List<LocationDTO> listLocations = ListLocations();
        FindIterable<Document> myDoc = GetListLocations();

        for (int i = 0; i < getDay(date_string); i++) {
            //nextDate = new Date(date.getTime() + (1000 * 60 * 60 * 24*i));

            String dataf = dateFormat.format(c1.getTime());
            for (Document item : myDoc) {
                url = String.format("http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=%s&dt=%s", item.get("name"), dataf);
                restTemplate = new RestTemplate();
                weather = restTemplate.getForObject(url, Weather.class);
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Tạo database mới nếu chưa có database đó
                    MongoDatabase database = mongoClient.getDatabase("all_things");
                    Object forecastday = weather.getForecast().get("forecastday");
                    ArrayList arrforacastday = (ArrayList) forecastday;
                    Object itemobj = new Object();
                    Map<String, Object> arr;
                    for (Object a : arrforacastday) {
                        itemobj = a;
                    }
                    arr = (Map) itemobj;
                    Object hours = arr.get("hour");
                    ArrayList listHours = (ArrayList) hours;
                    for (Object itemHour : listHours) {

                        Document document = new Document();
                        document.append("id", item.get("id"));
                        Document document1 = new Document((Map) itemHour);
                        document.putAll(document1);

                        //dataf.replace("-","");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate dateTime = LocalDate.parse(dataf, formatter);


                        String name = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
                        BasicDBObject query = new BasicDBObject("id", item.get("id"));
                        // query.put("id", item.get("id"));
                        //query.append("time",item.get("time"));
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", document);
                        UpdateOptions options = new UpdateOptions().upsert(true);
                        // insert vô collection nếu chưa có collection sẽ tạo mới collection
                        //String name = dataf.replace("-","") ;
                        //String name = c1.get(Calendar.YEAR)+""+c1.get(Calendar.MONTH)+""+c1.get(Calendar.DAY_OF_MONTH) ;
                        //MongoCollection<Document> collection = database.getCollection(String.format("Weather_%s",name));
//                        Document myDoc = collection.find(eq("id", item.getId())).first();
//                        Document myDoc1 = collection.find(eq("id", item.getId())).first();
//                        if(myDoc!=null){
//                            System.out.println("Đã tồn tại"+myDoc.toJson());
//                        }
//                        else {
//                            database.getCollection(String.format("Weather_%s", name)).insertOne(document);
//                            System.out.println("Document inserted successfully");
//                        }
                        try {
                            database.getCollection(String.format("Weather_%s", name)).updateOne(query, updateObject, options);
                        } catch (Exception e) {
                            logger.error("Unable to insert due to an error: " + e);
                            logger.error("Hour" + itemHour);
                        }

                        logger.info("Post weather for ID document upsert successfully " + document);
                    }

                } catch (MongoException me) {
                    logger.error("Unable to insert due to an error: " + me);
                    logger.error("Url" + url);
                    logger.error("Doc" + item);
                }
            }
            c1.roll(Calendar.DATE, true);
            //System.out.println(dataf);
        }
        return weather;
    }


    @GetMapping(value = "/WeatherPutAll/{q}")
    public Weather getWeatherPutAllForID(@PathVariable String q) throws ParseException {
        String date_string = "2022-11-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse(date_string);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";
        List<LocationDTO> listLocations = getLocations();

        for (int i = 0; i < 28; i++) {
            //nextDate = new Date(date.getTime() + (1000 * 60 * 60 * 24*i));

            String dataf = dateFormat.format(c1.getTime());
            for (var item : listLocations) {
                url = String.format("http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=%s&dt=%s", item.getName(), dataf);
                restTemplate = new RestTemplate();
                weather = restTemplate.getForObject(url, Weather.class);
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Tạo database mới nếu chưa có database đó
                    MongoDatabase database = mongoClient.getDatabase("all_things");
                    Object forecastday = weather.getForecast().get("forecastday");
                    ArrayList arrforacastday = (ArrayList) forecastday;
                    Object itemobj = new Object();
                    Map<String, Object> arr;
                    for (Object a : arrforacastday) {
                        itemobj = a;
                    }
                    arr = (Map) itemobj;
                    Object hours = arr.get("hour");
                    ArrayList listHours = (ArrayList) hours;
                    for (Object itemHour : listHours) {
                        Document document = new Document();
                        document.append("id", item.getId());
                        Document document1 = new Document((Map) itemHour);
                        document.putAll(document1);
                        dataf.replace("-", "");
                        // insert vô collection nếu chưa có collection sẽ tạo mới collection
                        String name = dataf.replace("-", "");
                        database.getCollection(String.format("Weather_%s", name)).insertOne(document);
                        System.out.println("Document inserted successfully");
                    }


                } catch (MongoException me) {
                    System.err.println("Unable to insert due to an error: " + me);
                }
            }
            c1.roll(Calendar.DATE, true);
            //System.out.println(dataf);
        }
        return weather;
    }

    @GetMapping(value = "/TestWeather")
    public Weather getWeatherFull() {
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";


        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Tạo database mới nếu chưa có database đó
            MongoDatabase database = mongoClient.getDatabase("all_things");
            Object forecastday = weather.getForecast().get("forecastday");
            ArrayList arrforacastday = (ArrayList) forecastday;
            Object item = new Object();
            Map<String, Object> arr;
            for (Object a : arrforacastday) {
                item = a;
            }
            arr = (Map) item;
            Object hours = arr.get("hour");
            ArrayList listHours = (ArrayList) hours;
            for (Object i : listHours
            ) {
                Document document = new Document();
                document.append("id", "2234565432");
                Document document1 = new Document((Map) i);
                document.putAll(document1);
                database.getCollection("Weathers").insertOne(document);
                System.out.println("Document inserted successfully");
            }

        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
        return weather;
    }

    @GetMapping(value = "/Test")
    public Weather getWeathers() {
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";


        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Tạo database mới nếu chưa có database đó
            MongoDatabase database = mongoClient.getDatabase("all_things");
            //JSONParser parser = new JSONParser();

            // insert vô collection nếu chưa có collection sẽ tạo mới collection
            //database.getCollection("Weathers").insertOne(document);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(weather);
            Document document = Document.parse(json);

            database.getCollection("Weathers").insertOne(document);

            System.out.println("Document inserted successfully");

        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return weather;
    }

    @GetMapping(value = "/Weathers")
    public Weather getWeather() throws ParseException {
        String date_string = "2022-11-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse(date_string);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        String url = "http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=Brixton&dt=2022-11-28";
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        String uri = "mongodb://localhost:27017";
        List<LocationDTO> listLocations = getLocations();

        for (int i = 0; i < 28; i++) {
            //nextDate = new Date(date.getTime() + (1000 * 60 * 60 * 24*i));

            String dataf = dateFormat.format(c1.getTime());
            for (var item : listLocations) {

                url = String.format("http://api.weatherapi.com/v1/history.json?key=fd22952abb98454e9b324431222911&q=%s&dt=%s", item.getName(), dataf);
                restTemplate = new RestTemplate();
                weather = restTemplate.getForObject(url, Weather.class);


                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Tạo database mới nếu chưa có database đó
                    MongoDatabase database = mongoClient.getDatabase("all_things");
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = ow.writeValueAsString(weather);
                    Document document = Document.parse(json);
                    dataf.replace("-", "");
                    // insert vô collection nếu chưa có collection sẽ tạo mới collection
                    String name = dataf.replace("-", "");
                    database.getCollection(String.format("Weather_%s", name)).insertOne(document);

                    System.out.println("Document inserted successfully");

                } catch (MongoException me) {
                    System.err.println("Unable to insert due to an error: " + me);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            c1.roll(Calendar.DATE, true);
            //System.out.println(dataf);
        }
        return weather;
    }
}
