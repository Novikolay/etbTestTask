package com.etb.testtask.service.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class CustomerDataMarshallerImpl implements CustomerDataMarshaller {

    public <T> String marshallRequest(T request, Class<T> clazz) {
        try (StringWriter sw = new StringWriter()) {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(request, sw);
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to marshal request to XML", ex);
        }
    }

    public <T> Object unmarshallResponse(String xml, Class<T> clazz) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return unmarshaller.unmarshal(bais);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to unmarshal request from XML", ex);
        }
    }

}
