package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

public class PiResultService {

    private static String instance1 = "http://ec2-54-185-172-22.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";
    private static String instance2 = "http://ec2-54-190-132-56.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";
    private static String instance3 = "http://ec2-34-220-223-23.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";

    private String numOfThrows;

    public PiResultService(String numberOfThrows) {
        numOfThrows = numberOfThrows;
    }

    public LinkedList<String> getResultsFromInstances() {
        LinkedList<String> results = new LinkedList<>();

        String url1 = instance1 + "?throws=" + numOfThrows;
        String url2 = instance2 + "?throws=" + numOfThrows;
        String url3 = instance3 + "?throws=" + numOfThrows;

        RestTemplate restTemplate = new RestTemplate();
        PiResult result1 = restTemplate.getForObject(url1, PiResult.class);
        result1.setNameOfInstance("INSTANCE 1 : " + instance1);
        PiResult result2 = restTemplate.getForObject(url2, PiResult.class);
        result2.setNameOfInstance("INSTANCE 2 : " + instance2);
        PiResult result3 = restTemplate.getForObject(url3, PiResult.class);
        result3.setNameOfInstance("INSTANCE 3 : " + instance3);

        results.add(result1.toString());
        results.add(result2.toString());
        results.add(result3.toString());

        return results;
    }

}
