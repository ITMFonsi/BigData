package at.fhv.itm2018.aufgabe4master;

import org.springframework.web.client.RestTemplate;

public class PiResultService {

    private static String instance1 = "http://ec2-54-185-172-22.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";
    private static String instance2 = "http://ec2-54-190-132-56.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";
    private static String instance3 = "http://ec2-34-220-223-23.us-west-2.compute.amazonaws.com:8080/awswebapp/getpi";

    public PiResultService() {
    }

    public String getResultsFromInstances() {
        RestTemplate restTemplate = new RestTemplate();
        PiResult result1 = restTemplate.getForObject(instance1, PiResult.class);
        PiResult result2 = restTemplate.getForObject(instance2, PiResult.class);
        PiResult result3 = restTemplate.getForObject(instance2, PiResult.class);


        StringBuilder sb = new StringBuilder();
        sb.append("Result 1");
        sb.append(result1.toString());
        sb.append("Result 2");
        sb.append(result2.toString());
        sb.append("Result 3");
        sb.append(result3.toString());

        return sb.toString();
    }

}
