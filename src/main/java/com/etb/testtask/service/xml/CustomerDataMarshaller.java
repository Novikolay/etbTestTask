package com.etb.testtask.service.xml;

public interface CustomerDataMarshaller {
    <T> String marshallRequest(T request, Class<T> clazz);

    <T> Object unmarshallResponse(String xml, Class<T> clazz);
}