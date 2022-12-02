package isil.pe.ineappsecurityservice.rekognition;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.List;

@Service
public class RekognitionServiceImpl implements RekognitionService{


    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    @Override
    public RekognitionResponse validateFaces(RekognitionRequest request ) {

        CompareFacesResult result = compareFaces(request.getDocument(),request.getSelfie());
        List<CompareFacesMatch> faceDetails = result.getFaceMatches();

        if(faceDetails.size()>0){
            return RekognitionResponse.whenSuccess(faceDetails);
        }else {
            return RekognitionResponse.whenError("Error");
        }
    }

    @Override
    public RekognitionResponse validateDocumentId(RekognitionRequest request) {
        DetectLabelsResult detectLabelsResult = detectLabelsResult(request.getDocument());

        List<Label> labels = detectLabelsResult.getLabels();

        for(Label label : labels){
            if(label.getName().equals("Document")){
                return RekognitionResponse.whenDocumentIsValid(detectLabelsResult);
            }
        }

        return RekognitionResponse.whenDocumentIsNotValid(detectLabelsResult);
    }


    public DetectLabelsResult detectLabelsResult(MultipartFile file){
        try{

            ByteBuffer imageBytes = ByteBuffer.wrap(file.getBytes());

            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes))
                    .withMaxLabels(10)
                    .withMinConfidence(90F);

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            return result;

        }catch (Exception e)
        {
            return null;
        }
    }

    public CompareFacesResult compareFaces(MultipartFile document, MultipartFile selfie){
        try{

            CompareFacesRequest request = new CompareFacesRequest()
                    .withSourceImage(new Image().withBytes(ByteBuffer.wrap(document.getBytes())))
                    .withTargetImage(new Image().withBytes(ByteBuffer.wrap(selfie.getBytes())))
                    .withSimilarityThreshold(90F);


            CompareFacesResult result = rekognitionClient.compareFaces(request);
            return result;

        }catch (Exception e){
            return null;
        }
    }

}
