package com.karnaukh.currency.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ComponentScan("com.karnaukh.currency")
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
public class SpringConfig {

    @Bean
    public Logger logger() {
        return LogManager.getLogger();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

	/*@Bean
	public MongoDatabase connectorMongoDB(){
		MongoClientURI uri = new MongoClientURI(
				"mongodb://maxim:7503613maks@currencycluster-shard-00-00.9p3nt.mongodb.net:27017,currencycluster-shard-00-01.9p3nt.mongodb.net:27017,currencycluster-shard-00-02.9p3nt.mongodb.net:27017/currency?ssl=true&replicaSet=atlas-pstf4n-shard-0&authSource=admin&retryWrites=true&w=majority");

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("currency");
		return database;
	}*/

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoClientURI uri = new MongoClientURI(
                "mongodb://maxim:7503613maks@currencycluster-shard-00-00.9p3nt.mongodb.net:27017,currencycluster-shard-00-01.9p3nt.mongodb.net:27017,currencycluster-shard-00-02.9p3nt.mongodb.net:27017/currency?ssl=true&replicaSet=atlas-pstf4n-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "currency");
        return mongoTemplate;
    }

    @Bean("cities")
    public List<String> parsingCities() {
        List<String> cities = new ArrayList<>();
        String fileName = "C:\\Users\\MAKS\\IdeaProjects\\GIT\\exchange_rates\\src\\main\\resources\\cities.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            cities = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Bean("absolutBankIdOffices")
    public Map<String,List<String>> parsingAbsolutbankOffices() {
        Map<String,List<String>> absolutbankOffices = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("C:\\Users\\MAKS\\IdeaProjects\\GIT\\exchange_rates\\src\\main\\resources\\absolutbank"), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                String city = line;
                line= reader.readLine();
                List<String> idOffices = new ArrayList<>();
                for (String subString:line.split("\\s")){
                    idOffices.add(subString);
                }
                absolutbankOffices.put(city,new ArrayList<>(idOffices));
                idOffices.clear();
            }
        } catch (IOException e) {
            // log error
        }
        return absolutbankOffices;
    }


    @Bean("alfabankIndexCities")
    public Map<String,String> parsingAlfabankIndexCities() {
        Map<String,String> alfabankIndexCities = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("C:\\Users\\MAKS\\IdeaProjects\\GIT\\exchange_rates\\src\\main\\resources\\alfabank"), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                String city = line;
               String  index= reader.readLine();


                alfabankIndexCities.put(city,index);
            }
        } catch (IOException e) {
            // log error
        }
        return alfabankIndexCities;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Asynchronous Process-");
        executor.initialize();
        return executor;
    }
}
