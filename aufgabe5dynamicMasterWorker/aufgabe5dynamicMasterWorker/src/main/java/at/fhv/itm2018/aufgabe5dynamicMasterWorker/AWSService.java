package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AWSService {

    private AmazonEC2 ec2Client;
    private KeyPair keyPair;
    private List<String> instancesId;


    public AWSService() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAI54FL63RF3MMIR4A", "ZtDCCYQLPqPt9IWgxhQGbwOGwxokSog8a1jAWJGD");
        ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-west-2")
                .build();
    }

    public LinkedList<String>  startInstances(int numOfInstances) {
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

        runInstancesRequest.withImageId("ami-0fd2edefa90906902")
                .withInstanceType(InstanceType.T2Micro)
                .withMinCount(numOfInstances)
                .withMaxCount(numOfInstances)
                .withKeyName("fhv-key-pair")
                .withSecurityGroups("worker");

        RunInstancesResult result = ec2Client.runInstances(
                runInstancesRequest);

        LinkedList<String> dnsNames = getWorkerDNSNames();
        waitForInstanceState(instancesId, InstanceStateName.Running);

        return dnsNames;
    }

    private void waitForInstanceState(List<String> instancesId, InstanceStateName state) {
        int numAchievedState = 0;

        while (numAchievedState != instancesId.size()) {

            try {
                Thread.sleep(15000);
            } catch (InterruptedException ex) {

            }

            numAchievedState = 0;

            DescribeInstancesRequest describeInstance = new DescribeInstancesRequest().withInstanceIds(instancesId);
            DescribeInstancesResult describeResult = ec2Client.describeInstances(describeInstance);
            List<Reservation> reservations = describeResult.getReservations();

            //different instances might be in different reservation requests
            //so we need to traverse those
            for (Reservation reservation : reservations) {
                List<Instance> instances = reservation.getInstances();
                for (Instance instance : instances) {
                    if (instance.getState().getName().equals(state.toString())) {
                        numAchievedState++;
                    }
                }
            }
        }
    }

    public LinkedList<String> getWorkerDNSNames() {
        instancesId = new LinkedList<>();
        boolean done = false;
        LinkedList<String> dnsNames = new LinkedList<>();
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2Client.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances()) {
                    for (GroupIdentifier id : instance.getSecurityGroups()) {
                        if(id.getGroupName().equals("worker")) {
                            dnsNames.add(instance.getPublicDnsName());
                            instancesId.add(instance.getInstanceId());
                        }
                    }
                }
            }
            request.setNextToken(response.getNextToken());
            if(response.getNextToken() == null) {
                done = true;
            }
        }
        return dnsNames;
    }

    public void terminateInstances() {
        TerminateInstancesRequest request = new TerminateInstancesRequest(instancesId);
        ec2Client.terminateInstances(request);
    }
}
