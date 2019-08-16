package com.wolox.training.services;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wolox.training.deserializers.CustomBookDeserializer;
import com.wolox.training.dtos.BookApiDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class OpenLibraryService {
    
    public BookApiDTO bookInfo(String isbn) throws IOException {
        String url = "https://openlibrary.org/api/books?bibkeys=" +
                "ISBN:"+isbn+"&format=json&jscmd=data";
        RestTemplate restTemplate = new RestTemplate();
        String apiBook = restTemplate.getForObject(url,String.class);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomBookDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(BookApiDTO.class, new CustomBookDeserializer());
        mapper.registerModule(module);

        BookApiDTO bookApiDTO = mapper.setInjectableValues(injectEventType(isbn)).readValue(apiBook, BookApiDTO.class);
        return bookApiDTO;
    }

    private InjectableValues injectEventType(String isbn) {
        return new InjectableValues.Std()
                .addValue("isbn", isbn);
    }
}
