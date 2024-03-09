package scrapper.controllers.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListLinksResponse {
    public List<LinkResponse> links;
    public Integer size;
}
