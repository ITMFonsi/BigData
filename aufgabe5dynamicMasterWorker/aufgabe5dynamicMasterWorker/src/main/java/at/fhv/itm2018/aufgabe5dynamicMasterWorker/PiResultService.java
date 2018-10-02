package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

public class PiResultService {

    private String numOfThrows;
    private String numOfInstances;

    private AWSService awsService;

    public PiResultService(String numberOfThrows, String numOfInstances) {
        numOfThrows = numberOfThrows;
        this.numOfInstances = numOfInstances;
        awsService = new AWSService();
    }

    public LinkedList<String> getResultsFromInstances() {
        LinkedList<String> results = new LinkedList<>();
        LinkedList<String> dnsNames = new LinkedList<>();

        dnsNames = awsService.startInstances(Integer.valueOf(numOfInstances));
        RestTemplate restTemplate = new RestTemplate();
        int dividedThrows =  Integer.valueOf(numOfThrows) / Integer.valueOf(numOfInstances);
        for (String dnsName: dnsNames) {
           String url = "http://" + dnsName + ":8080/awswebapp/getpi"+"?throws=" + String.valueOf(dividedThrows);
           PiResult piResult = restTemplate.getForObject(url, PiResult.class);
           piResult.setNameOfInstance(dnsName);
           results.add(piResult.toString());
        }
        awsService.terminateInstances();
        return results;
    }

}
