package com.example.grpc.service;

import com.proto.greet.DummyMessage;
import com.proto.greet.DummyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DummyGatewayService {

    @RequestMapping(value = "/grpc/welcome/{name}", method = RequestMethod.GET)
    public String sayHi(@PathVariable("name") String name, Model model) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50055)
                .usePlaintext().build();
        DummyServiceGrpc.DummyServiceBlockingStub syncClient
                = DummyServiceGrpc.newBlockingStub(channel);

        // created a greet service client (blocking - synchronous)
        DummyServiceGrpc.DummyServiceBlockingStub dummyClient
                = DummyServiceGrpc.newBlockingStub(channel);

        // created a protocol buffer greeting message
        DummyMessage requestMessage = DummyMessage.newBuilder().setTxt(name).build();

        // call the RPC and get back a GreetResponse (Protocol Buffers)
        DummyMessage responseMessage = dummyClient.sayHi(requestMessage);
        channel.shutdown();

        model.addAttribute("name", name);
        model.addAttribute("greet", responseMessage.getTxt());

        return "AdminMenu";
    }
}
