package com.wolox.training.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.wolox.training.dtos.BookApiDTO;

import java.io.IOException;

public class CustomBookDeserializer extends StdDeserializer<BookApiDTO> {
    public CustomBookDeserializer(Class<?> vc) {
        super(vc);
    }

    public CustomBookDeserializer(){
        this(null);
    }

    @Override
    public BookApiDTO deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        String isbn = String.valueOf(deserializer.findInjectableValue("isbn", null, null));
        return createBookApiDto(node, isbn);
    }

    private BookApiDTO createBookApiDto(JsonNode node, String isbn){
        BookApiDTO bookDto = new BookApiDTO();
        JsonNode apiBook = node.get("ISBN:"+isbn);

        bookDto.setIsbn(isbn);
        bookDto.setTitle(apiBook.get("title").asText());
        bookDto.setSubtitle(apiBook.get("subtitle").asText());

        JsonNode authors = apiBook.get("authors");
        if(authors.isArray()){
            for(JsonNode authorObject : authors){
                bookDto.addAuthor(authorObject.get("name").asText());
            }
        }

        bookDto.setNumberOfPages(apiBook.get("number_of_pages").asInt());
        bookDto.setPublishDate(apiBook.get("publish_date").asText());

        JsonNode publishers = apiBook.get("publishers");
        if(publishers.isArray()){
            for(JsonNode publisherObject: publishers){
                bookDto.addPublisher(publisherObject.get("name").asText());
            }
        }

        return bookDto;
    }
}
