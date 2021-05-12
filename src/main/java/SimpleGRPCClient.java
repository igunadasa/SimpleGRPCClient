import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import mtech.account_api.AccountOpenServiceGrpc;

import javax.net.ssl.SSLException;
import java.io.File;

public class SimpleGRPCClient {
    public static void main(String[] args) throws InterruptedException, SSLException {

        Metadata header=new Metadata();
        Metadata.Key<String> key =
                Metadata.Key.of("content-type", Metadata.ASCII_STRING_MARSHALLER);
        header.put(key, "Text");

        ManagedChannel channel = NettyChannelBuilder.forAddress("accounts-v1.grpc.qa-v2.mrbuilder.io", 443)

                .build();
//        ManagedChannel channel = NettyCh.forTarget("https://work-orders-v1.dev-v2.mrbuilder.io")
//                .useTransportSecurity()
//                .build();

        AccountOpenServiceGrpc.AccountOpenServiceBlockingStub stub =
                AccountOpenServiceGrpc.newBlockingStub(channel);

        stub = io.grpc.stub.MetadataUtils.attachHeaders(stub, header);

       var list = stub.getInvoicingMethodList(Empty.newBuilder().build());

        System.out.println(list);

        channel.shutdown();
    }
}
