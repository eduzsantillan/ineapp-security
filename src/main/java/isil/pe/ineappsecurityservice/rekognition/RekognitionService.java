package isil.pe.ineappsecurityservice.rekognition;

public interface RekognitionService {

    RekognitionResponse validateFaces(RekognitionRequest request);

    RekognitionResponse validateDocumentId(RekognitionRequest request);

}
