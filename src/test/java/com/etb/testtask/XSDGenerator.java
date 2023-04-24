package com.etb.testtask;

import com.etb.testtask.model.CustomerData;

import java.io.IOException;
import javax.xml.bind.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class XSDGenerator {

    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(CustomerData.class);
        jc.generateSchema(new SchemaOutputResolver() {

            @Override
            public Result createOutput(String namespaceURI, String suggestedFileName)
                    throws IOException {
                return new StreamResult(suggestedFileName);
            }

        });

    }

}