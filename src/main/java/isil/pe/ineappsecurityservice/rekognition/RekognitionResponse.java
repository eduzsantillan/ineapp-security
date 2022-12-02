package isil.pe.ineappsecurityservice.rekognition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RekognitionResponse {

    private String code;
    private String message;
    private Object detail;

    public static RekognitionResponse whenSuccess(Object jsonResponse){
        return RekognitionResponse.builder()
                .code("200")
                .detail(jsonResponse)
                .message("Clientes autorizado").build();
    }

    public static RekognitionResponse whenDocumentIsValid(Object jsonResponse){
        return RekognitionResponse.builder()
                .code("200")
                .detail(jsonResponse)
                .message("Es un numero de documento").build();
    }

    public static RekognitionResponse whenDocumentIsNotValid(Object jsonResponse){
        return RekognitionResponse.builder()
                .code("500")
                .detail(jsonResponse)
                .message("No es un numero de documento").build();
    }


    public static RekognitionResponse whenError(String e){
        return RekognitionResponse.builder()
                .code("500")
                .message("Error en aplicacion ".concat(e)).build();
    }


}
