package junitPerf;

import java.util.UUID;

public class DemoPerfService {

    public String getServiceId(String userId){

        return UUID.randomUUID().toString();
    }
}