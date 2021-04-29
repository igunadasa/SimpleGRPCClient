
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import mtech.work_orders_api.WorkOrders;
import mtech.work_orders_api.WorkOrderServiceGrpc;

public class SimpleGRPCClient {
    public static void main(String[] args) throws InterruptedException {

        Metadata header=new Metadata();
        Metadata.Key<String> key =
                Metadata.Key.of("content-type", Metadata.ASCII_STRING_MARSHALLER);
        header.put(key, "Text");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("work-orders-v1", 6565)
                .usePlaintext()
                .build();

        WorkOrderServiceGrpc.WorkOrderServiceBlockingStub stub =
                WorkOrderServiceGrpc.newBlockingStub(channel);

       stub = io.grpc.stub.MetadataUtils.attachHeaders(stub, header);

        WorkOrders.ProInvoice helloResponse = stub.getInvoiceByID(StringValue.getDefaultInstance());

        System.out.println(helloResponse);

        channel.shutdown();
    }
}
