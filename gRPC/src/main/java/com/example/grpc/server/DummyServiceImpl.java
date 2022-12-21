package com.example.grpc.server;


import com.proto.greet.DummyMessage;
import com.proto.greet.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DummyServiceImpl extends DummyServiceGrpc.DummyServiceImplBase {
    @Override
    public void sayHi(DummyMessage request, StreamObserver<DummyMessage> responseObserver) {

    // Block 1: extract the data required
        String txt = request.getTxt();

    // Block 2: create the response message
        String result = "Welcome back admin " + txt + "😸";
        DummyMessage response = DummyMessage.newBuilder().setTxt(result).build();

    // Block 3: send the response
        responseObserver.onNext(response);

    // Block 4: complete the RPC call
        responseObserver.onCompleted();
    }
}
