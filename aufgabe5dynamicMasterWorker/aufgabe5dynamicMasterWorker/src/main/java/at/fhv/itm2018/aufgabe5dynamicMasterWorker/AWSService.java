package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.waiters.Waiter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AWSService {

    private AmazonEC2 ec2Client;

    private KeyPair keyPair;

    private List<Instance> instances;


    public AWSService() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJOZ6WNQJ3QI27QLQ", "7qghBpZsohlIAMY6dbxuJLkQPJ8v6C1D0XSHQh/f");
        ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-west-2")
                .build();
    }


    public void createSecGroup() {
        CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
        csgr.withGroupName("JavaSecurityGroup").withDescription("My security group");
        CreateSecurityGroupResult createSecurityGroupResult =
                ec2Client.createSecurityGroup(csgr);

        IpPermission ipPermission = new IpPermission();

        IpRange ipRange1 = new IpRange().withCidrIp("111.111.111.111/32");
        IpRange ipRange2 = new IpRange().withCidrIp("150.150.150.150/32");

        ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1, ipRange2}))
                .withIpProtocol("tcp")
                .withFromPort(22)
                .withToPort(22);

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest =
                new AuthorizeSecurityGroupIngressRequest();

        authorizeSecurityGroupIngressRequest.withGroupName("JavaSecurityGroup")
                .withIpPermissions(ipPermission);

        ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
    }

    public void createKeyPair() {
        CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();

        createKeyPairRequest.withKeyName("aws-sdk-keypair");
        CreateKeyPairResult createKeyPairResult = ec2Client.createKeyPair(createKeyPairRequest);

        keyPair = new KeyPair();

        keyPair = createKeyPairResult.getKeyPair();

        String privateKey = keyPair.getKeyMaterial();

    }

    public void startInstances(int numOfInstances) {
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

        runInstancesRequest.withImageId("ami-0fd2edefa90906902")
                .withInstanceType(InstanceType.T2Micro)
                .withMinCount(numOfInstances)
                .withMaxCount(numOfInstances)
                .withKeyName("fhv-key-pair")
                .withSecurityGroups("worker");

        RunInstancesResult result = ec2Client.runInstances(
                runInstancesRequest);




    }

    public boolean allInstancesAreRunning() {
        for (Instance inst : instances) {
            if(!inst.getState().getName().equals("running")) {
                return false;
            }
        }
        return true;
    }

    public LinkedList<String> getWorkerDNSNames() {
        instances = new LinkedList<>();
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
                            instances.add(instance);
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


}
