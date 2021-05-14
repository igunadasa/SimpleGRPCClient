import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import mtech.account_api.AccountOpenServiceGrpc;

public class SimpleGRPCClient {
    public static void main(String[] args) {

        Metadata header = new Metadata();
        Metadata.Key<String> auth =
                Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlFUa3pSa0U1TmtNNE9VVXpRVEJHT1RVelFqQTBRemt4TTBVNU56YzFNVFJGTWtJNU56ZEVRdyJ9.eyJuaWNrbmFtZSI6IisxNDI1NzgwNzQ3NiIsIm5hbWUiOiIrMTQyNTc4MDc0NzYiLCJwaWN0dXJlIjoiaHR0cHM6Ly9jZG4uYXV0aDAuY29tL2F2YXRhcnMvMS5wbmciLCJ1cGRhdGVkX2F0IjoiMjAyMS0wNC0xNVQwODowNToxOC45MTRaIiwiaXNzIjoiaHR0cHM6Ly9tcmJ1aWxkZXIuYXV0aDAuY29tLyIsInN1YiI6InNtc3w1ZTFkYWNlZTExNDg0ZDY2MzRmYmEyYmUiLCJhdWQiOiJtMGdEdVVNYVoyQnU0T3Y0TDdudUlNcGVMNXRaTXNNbSIsImlhdCI6MTYxODUwOTAzMiwiZXhwIjoxNjIyMTA5MDMyLCJub25jZSI6InRpS2U3aDRTQ1hJUlJKc3haRFBaTHV0WHdBaUd4ZUQ4In0.Km9UDJjC_LgHeOMMILWrZmmLl5wV7TuZCHMGfpTtIEGeJnEFsTOO2kkHDEAhEURgnVAYEJBA_FRBrcsXVv_Iegs3QeifVwN2ARhiP60K4cDUE5OaCoIVYIHf_cE79bBVVYfHz4FwXem9edBMbF7CI676Dve28J6baZzRN-kf7LLyHTO-f720ODQr2_PZf7flgppnDHISOJM-kf4v3rYHvjjKscMbpQwnjWUHwCRnnfp2wp4ZPJOVD4P1Q6JroztFHm3kGEWnEkc85DtSiYbjzPIJFMAtYQ9J5pNcftrbWFLyxc7uonhSEE97ZcN8WUkZv5llpfIBLJW2dEADRAk8qw";
        header.put(auth, "Bearer " + token);

        ManagedChannel channel = NettyChannelBuilder
                .forAddress("accounts-v1-grpc.grpc.qa-v2.mrbuilder.io", 443)
                .build();

        AccountOpenServiceGrpc.AccountOpenServiceBlockingStub stub =
                AccountOpenServiceGrpc.newBlockingStub(channel);

        stub = io.grpc.stub.MetadataUtils.attachHeaders(stub, header);

        var list = stub.getInvoicingMethodList(Empty.newBuilder().build());

        System.out.println(list);

        channel.shutdown();
    }
}
