package com.example.grpc.server;
import com.proto.greet.DummyMessage;
import com.proto.greet.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DummyServiceImpl extends DummyServiceGrpc.DummyServiceImplBase {
    @Override
    public void sayHi(DummyMessage request, StreamObserver<DummyMessage> responseObserver) {

        String txt = request.getTxt();
        String result = "Welcome back admin " + txt + "😸";
        DummyMessage response = DummyMessage.newBuilder().setTxt(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



