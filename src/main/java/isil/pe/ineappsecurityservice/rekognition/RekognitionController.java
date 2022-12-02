package isil.pe.ineappsecurityservice.rekognition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/security/rekognition")
public class RekognitionController {

    @Autowired
    private RekognitionService rekognitionService;

    @PostMapping("/validate-user")
    public RekognitionResponse validateUser(
            @RequestPart(value="document",required = true) MultipartFile document,
            @RequestPart(value="selfie",required = true) MultipartFile selfie){

        RekognitionRequest request = RekognitionRequest.builder()
                .document(document)
                .selfie(selfie).build();

        RekognitionResponse responseValidateDocument = rekognitionService.validateDocumentId(request);

        if(responseValidateDocument.getCode().equals("200")){
            return rekognitionService.validateFaces(request);
        }else{
            return RekognitionResponse.whenError("Los rostros no coinciden");
        }

    }
}
