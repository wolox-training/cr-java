package com.wolox.training.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.dtos.BookApiDTO;
import com.wolox.training.exceptions.NotFoundException;

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

    private BookApiDTO createBookApiDto(JsonNode node, String isbn) throws NotFoundException{
        BookApiDTO bookDto = new BookApiDTO();
        JsonNode apiBook = node.get("ISBN:"+isbn);

        if(apiBook!=null) {
            bookDto.setIsbn(isbn);

            JsonNode titleApi = apiBook.get("title");
            String title = titleApi == null ? "" : titleApi.asText();
            bookDto.setTitle(title);

            JsonNode subtitleApi = apiBook.get("subtitle");
            String subtitle = subtitleApi == null ? "" : subtitleApi.asText();
            bookDto.setSubtitle(subtitle);

            JsonNode authorsApi = apiBook.get("authors");
            if (authorsApi != null && authorsApi.isArray()) {
                for (JsonNode authorObject : authorsApi) {
                    bookDto.addAuthor(authorObject.get("name").asText());
                }
            }

            JsonNode numberOfPagesApi = apiBook.get("number_of_pages");
            int numberOfPages = numberOfPagesApi == null ? 0 : numberOfPagesApi.asInt();
            bookDto.setNumberOfPages(numberOfPages);


            JsonNode publishDateApi = apiBook.get("publish_date");
            String publishDate = publishDateApi == null ? "" : publishDateApi.asText();
            bookDto.setPublishDate(publishDate);


            JsonNode publishersApi = apiBook.get("publishers");
            if (publishersApi != null && publishersApi.isArray()) {
                for (JsonNode publisherObject : publishersApi) {
                    bookDto.addPublisher(publisherObject.get("name").asText());
                }
            }

            return bookDto;
        }
        throw new NotFoundException(ErrorMessages.notFoundBookErrorMessage);
    }
}
