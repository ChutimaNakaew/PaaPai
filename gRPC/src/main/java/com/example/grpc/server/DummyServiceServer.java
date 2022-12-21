package com.example.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class DummyServiceServer {
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50055)
                .addService(new DummyServiceImpl()).build();
        try { server.start(); }
        catch (IOException e) { e.printStackTrace(); }
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully Shutdown Request");
        }));
        try { server.awaitTermination(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
