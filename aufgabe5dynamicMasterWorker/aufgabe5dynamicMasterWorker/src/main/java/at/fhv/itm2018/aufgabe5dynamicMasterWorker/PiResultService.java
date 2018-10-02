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

        awsService.startInstances(Integer.valueOf(numOfInstances));
        LinkedList<String> dnsNames = awsService.getWorkerDNSNames();





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
