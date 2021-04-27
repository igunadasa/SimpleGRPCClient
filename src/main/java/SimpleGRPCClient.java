import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import mtech.work_orders_api.WorkOrders;
import mtech.work_orders_api.WorkOrderServiceGrpc;

import javax.net.ssl.SSLException;
import java.io.File;

public class SimpleGRPCClient {
    public static void main(String[] args) throws InterruptedException, SSLException {

        Metadata header=new Metadata();
        Metadata.Key<String> key =
                Metadata.Key.of("content-type", Metadata.ASCII_STRING_MARSHALLER);
        header.put(key, "Text");

        ManagedChannel channel = NettyChannelBuilder.forAddress("work-orders-v1.dev-v2.mrbuilder.io", 443)
                .sslContext(GrpcSslContexts.forClient()
                        .trustManager(new File(SimpleGRPCClient.class.getClassLoader().getResource("sca1b.crt").getFile()))
                        .build())
                .build();
//        ManagedChannel channel = NettyCh.forTarget("https://work-orders-v1.dev-v2.mrbuilder.io")
//                .useTransportSecurity()
//                .build();

        WorkOrderServiceGrpc.WorkOrderServiceBlockingStub stub =
                WorkOrderServiceGrpc.newBlockingStub(channel);

        stub = io.grpc.stub.MetadataUtils.attachHeaders(stub, header);

        WorkOrders.ProInvoice helloResponse = stub.getInvoiceByID(StringValue.getDefaultInstance());

        System.out.println(helloResponse);

        channel.shutdown();
    }
}
