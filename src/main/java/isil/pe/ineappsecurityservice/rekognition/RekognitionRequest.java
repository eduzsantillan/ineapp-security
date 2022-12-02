package isil.pe.ineappsecurityservice.rekognition;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RekognitionRequest {

    private MultipartFile document;
    private MultipartFile selfie;

}
